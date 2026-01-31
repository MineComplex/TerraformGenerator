package org.terraform.main;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.event.world.WorldUnloadEvent;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.terraform.biome.BiomeBank;
import org.terraform.coregen.ChunkCache;
import org.terraform.coregen.HeightMap;
import org.terraform.coregen.NMSInjectorAbstract;
import org.terraform.coregen.bukkit.TerraformGenerator;
import org.terraform.data.TerraformWorld;
import org.terraform.populators.OrePopulator;
import org.terraform.schematic.SchematicListener;
import org.terraform.structure.StructureLocator;
import org.terraform.structure.StructureRegistry;
import org.terraform.tree.SaplingOverrider;
import org.terraform.utils.BlockUtils;
import org.terraform.utils.GenUtils;
import org.terraform.utils.datastructs.ConcurrentLRUCache;
import org.terraform.utils.noise.NoiseCacheHandler;
import org.terraform.utils.version.Version;
import org.terraform.watchdog.TfgWatchdogSuppressant;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class TerraformGeneratorPlugin extends JavaPlugin implements Listener {

    public static final Set<String> INJECTED_WORLDS = new HashSet<>();
    public static TLogger logger;

    //Injector "can" be null, but the plugin can be assumed to be completely broken
    // in that case. Just crash.
    public static @NotNull NMSInjectorAbstract injector;
    public static TfgWatchdogSuppressant watchdogSuppressant;
    private static TerraformGeneratorPlugin instance;

    private LanguageManager lang;

    public static TerraformGeneratorPlugin get() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        try {
            TConfig.init(new File(getDataFolder(), "config.yml"));
        } catch (IOException e) {
            getLogger().severe("Failed to load config.yml: " + e.getMessage());
            getPluginLoader().disablePlugin(this);
            return;
        }

        logger = new TLogger();
        lang = new LanguageManager(this, TConfig.c);

        GenUtils.initGenUtils();
        BlockUtils.initBlockUtils();


        // Initiate the height map flat radius value
        HeightMap.spawnFlatRadiusSquared = TConfig.c.HEIGHT_MAP_SPAWN_FLAT_RADIUS;
        if (HeightMap.spawnFlatRadiusSquared > 0) {
            HeightMap.spawnFlatRadiusSquared *= HeightMap.spawnFlatRadiusSquared;
        }

        BiomeBank.initSinglesConfig(); // Initiates single biome modes.

        // Initialize chunk cache based on config size
        TerraformGenerator.CHUNK_CACHE = new ConcurrentLRUCache<>(
                "CHUNK_CACHE", TConfig.c.DEVSTUFF_CHUNKCACHE_SIZE, (key) -> {
            return new ChunkCache(key.tw(), key.x(), key.z());
        }
        );

        // Initialize biome query cache based on config size
        GenUtils.BIOME_QUERY_CACHE = new ConcurrentLRUCache<>(
                "biomeQueryCache", TConfig.c.DEVSTUFF_CHUNKBIOMES_SIZE, (key) -> {
            EnumSet<BiomeBank> banks = EnumSet.noneOf(BiomeBank.class);
            int gridX = key.chunkX * 16;
            int gridZ = key.chunkZ * 16;
            for (int x = gridX; x < gridX + 16; x++) {
                for (int z = gridZ; z < gridZ + 16; z++) {
                    BiomeBank bank = key.tw.getBiomeBank(x, z);
                    if (!banks.contains(bank)) {
                        banks.add(bank);
                    }
                }
            }
            return banks;
        }
        );

        LangOpt.init(this);
        watchdogSuppressant = new TfgWatchdogSuppressant();

        TerraformGenerator.updateSeaLevelFromConfig();
        new TerraformCommandManager(this, "terraform", "terra");
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(new SchematicListener(), this);
        String version = Version.VERSION.getPackName();
        logger.stdout("Detected version: " + version + ", packName: " + Version.VERSION.getPackName());
        try {
            injector = Version.getInjector();
            if (injector != null) {
                injector.startupTasks();
            } else {
                throw new ClassNotFoundException(); //injector no longer throws on no version mapping.
            }
        } catch (ClassNotFoundException e) {
            TerraformGeneratorPlugin.logger.stackTrace(e);
            logger.stdout("&cNo support for this version has been made yet!");
        } catch (InstantiationException |
                 IllegalAccessException |
                 IllegalArgumentException |
                 InvocationTargetException |
                 NoSuchMethodException |
                 SecurityException e) {
            TerraformGeneratorPlugin.logger.stackTrace(e);
            logger.stdout("&cSomething went wrong initiating the injector!");
        }

        if (TConfig.c.MISC_SAPLING_CUSTOM_TREES_ENABLED) {
            Bukkit.getPluginManager().registerEvents(new SaplingOverrider(), this);
        }

        StructureRegistry.init();
    }

    @SuppressWarnings("unused")
    @EventHandler
    public void onWorldInit(@NotNull WorldInitEvent event) {
        if (event.getWorld().getGenerator() instanceof TerraformGenerator) {
            logger.stdout("Detected world: " + event.getWorld().getName() + ", commencing injection... ");
            TerraformWorld tw = TerraformWorld.forceOverrideSeed(event.getWorld());
            if (injector.attemptInject(event.getWorld())) {
                INJECTED_WORLDS.add(event.getWorld().getName());
                tw.minY = injector.getMinY();
                tw.maxY = injector.getMaxY();

                logger.stdout("&aInjection success! Proceeding with generation.");

            } else {
                logger.stdout("&cInjection failed.");
            }
        }
    }

    @SuppressWarnings("unused")
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onWorldUnload(WorldUnloadEvent event) {
        if (INJECTED_WORLDS.contains(event.getWorld().getName())) {
            TerraformWorld world = TerraformWorld.get(event.getWorld());
            logger.stdout("Flushing caches for world " + event.getWorld().getName());
            clearCache();
            OrePopulator.ORE_NOISE_CACHE.remove(world);
        }
    }

    public void clearCache() {
        NoiseCacheHandler.NOISE_CACHE.clear();
        BiomeBank.BIOME_SECTION_CACHE.clear();
        GenUtils.BIOME_QUERY_CACHE.clear();
        StructureRegistry.STRUCTURE_QUERY_CACHE.clear();
        HeightMap.BLUR_CACHE.clear();
        TerraformGenerator.CHUNK_CACHE.clear();
        StructureLocator.STRUCTURE_LOCATION_CACHE.invalidateAll();
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(@NotNull String worldName, String id) {
        return new TerraformGenerator();
    }

    public LanguageManager getLang() {
        // TODO Auto-generated method stub
        return lang;
    }

}
