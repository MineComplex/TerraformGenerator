package org.terraform.main;

import org.avarion.yaml.*;

import java.io.File;
import java.io.IOException;


@YamlFile(lenient = Leniency.LENIENT)
public class TConfig extends YamlFileInterface {
    public static TConfig c;
    // -=[HEIGHTMAP]=-
    @YamlKey("heightmap.core-frequency")
    public float HEIGHT_MAP_CORE_FREQUENCY = 0.003f;
    @YamlKey("heightmap.river-frequency")
    public float HEIGHT_MAP_RIVER_FREQUENCY = 0.005f;
    @YamlKey("heightmap.land-height-amplifier")
    public float HEIGHT_MAP_LAND_HEIGHT_AMPLIFIER = 1f;
    @YamlKey("heightmap.sea-level")
    public int HEIGHT_MAP_SEA_LEVEL = 62;
    @YamlKey("heightmap.bedrock-height")
    public int HEIGHT_MAP_BEDROCK_HEIGHT = 5;
    @YamlKey("heightmap.bedrock-density")
    public int HEIGHT_MAP_BEDROCK_DENSITY = 70;
    @YamlKey("heightmap.spawn-flat-radius")
    public int HEIGHT_MAP_SPAWN_FLAT_RADIUS = -1;
    // -=[CLIMATES]=-
    @YamlKey("climate.humid-vegetation.minimum-temperature")
    public double CLIMATE_HUMIDVEGETATION_MINTEMP = -0.35d;
    @YamlKey("climate.humid-vegetation.maximum-temperature")
    public double CLIMATE_HUMIDVEGETATION_MAXTEMP = 4d;
    @YamlKey("climate.humid-vegetation.minimum-moisture")
    public double CLIMATE_HUMIDVEGETATION_MINMOIST = 0d;
    @YamlKey("climate.humid-vegetation.maximum-moisture")
    public double CLIMATE_HUMIDVEGETATION_MAXMOIST = 4d;
    @YamlKey("climate.dry-vegetation.minimum-temperature")
    public double CLIMATE_DRYVEGETATION_MINTEMP = -0.35d;
    @YamlKey("climate.dry-vegetation.maximum-temperature")
    public double CLIMATE_DRYVEGETATION_MAXTEMP = 4d;
    @YamlKey("climate.dry-vegetation.minimum-moisture")
    public double CLIMATE_DRYVEGETATION_MINMOIST = -4d;
    @YamlKey("climate.dry-vegetation.maximum-moisture")
    public double CLIMATE_DRYVEGETATION_MAXMOIST = 0d;
    @YamlKey("climate.hot-barren.minimum-temperature")
    public double CLIMATE_HOTBARREN_MINTEMP = 1d;
    @YamlKey("climate.hot-barren.maximum-temperature")
    public double CLIMATE_HOTBARREN_MAXTEMP = 4d;
    @YamlKey("climate.hot-barren.minimum-moisture")
    public double CLIMATE_HOTBARREN_MINMOIST = -4d;
    @YamlKey("climate.hot-barren.maximum-moisture")
    public double CLIMATE_HOTBARREN_MAXMOIST = -1d;
    @YamlKey("climate.cold.minimum-temperature")
    public double CLIMATE_COLD_MINTEMP = -4d;
    @YamlKey("climate.cold.maximum-temperature")
    public double CLIMATE_COLD_MAXTEMP = -0.5d;
    @YamlKey("climate.cold.minimum-moisture")
    public double CLIMATE_COLD_MINMOIST = -4d;
    @YamlKey("climate.cold.maximum-moisture")
    public double CLIMATE_COLD_MAXMOIST = 4d;
    @YamlKey("climate.snowy.minimum-temperature")
    public double CLIMATE_SNOWY_MINTEMP = -4d;
    @YamlKey("climate.snowy.maximum-temperature")
    public double CLIMATE_SNOWY_MAXTEMP = -2.7d;
    @YamlKey("climate.snowy.minimum-moisture")
    public double CLIMATE_SNOWY_MINMOIST = -4d;
    @YamlKey("climate.snowy.maximum-moisture")
    public double CLIMATE_SNOWY_MAXMOIST = 4d;
    // -=[BIOMES]=-
    // Biome globals
    @YamlKey("biome.force.radius")
    @YamlComment("Forces the blocks around 0,0 to be this biome. ROUNDS UP to the nearest biome section size. Does not disable rivers or beaches. Set to 0 to disable this.")
    public int BIOME_FORCE_RADIUS = 0;
    @YamlKey("biome.force.force-type")
    public String BIOME_FORCED_BIOME = "PLAINS";
    @YamlKey("biome.temperature-frequency")
    public float BIOME_TEMPERATURE_FREQUENCY = 0.03f;
    @YamlKey("biome.moisture-frequency")
    public float BIOME_MOISTURE_FREQUENCY = 0.03f;

    @YamlKey("biome.cave.crystalline-cluster.separation")
    public int BIOME_CAVE_CRYSTALLINECLUSTER_SEPARATION = 86;
    @YamlKey("biome.cave.crystalline-cluster.separation-maxpertub")
    public double BIOME_CAVE_CRYSTALLINECLUSTER_MAXPERTUB = 0.37f;
    @YamlKey("biome.cave.crystalline-cluster.minsize")
    public int BIOME_CAVE_CRYSTALLINECLUSTER_MINSIZE = 7;
    @YamlKey("biome.cave.crystalline-cluster.maxsize")
    public int BIOME_CAVE_CRYSTALLINECLUSTER_MAXSIZE = 11;
    @YamlKey("biome.cave.dripstone-cluster.separation")
    public int BIOME_CAVE_DRIPSTONECLUSTER_SEPARATION = 64;
    @YamlKey("biome.cave.dripstone-cluster.separation-maxpertub")
    public double BIOME_CAVE_DRIPSTONECLUSTER_MAXPERTUB = 0.35d;
    @YamlKey("biome.cave.dripstone-cluster.minsize")
    public int BIOME_CAVE_DRIPSTONECLUSTER_MINSIZE = 5;
    @YamlKey("biome.cave.dripstone-cluster.maxsize")
    public int BIOME_CAVE_DRIPSTONECLUSTER_MAXSIZE = 11;
    @YamlKey("biome.cave.lush-cluster.separation")
    public int BIOME_CAVE_LUSHCLUSTER_SEPARATION = 64;
    @YamlKey("biome.cave.lush-cluster.separation-maxpertub")
    public double BIOME_CAVE_LUSHCLUSTER_MAXPERTUB = 0.35d;
    @YamlKey("biome.cave.lush-cluster.minsize")
    public int BIOME_CAVE_LUSHCLUSTER_MINSIZE = 10;
    @YamlKey("biome.cave.lush-cluster.maxsize")
    public int BIOME_CAVE_LUSHCLUSTER_MAXSIZE = 15;
    @YamlKey("biome.dithering")
    public double BIOME_DITHER = 0.04d;
    @YamlKey("biome.biomesection-bitshifts")
    public int BIOME_SECTION_BITSHIFTS = 7;
    @YamlKey("biome.clay-deposit-radius")
    public float BIOME_CLAY_DEPOSIT_SIZE = 3f;
    @YamlKey("biome.clay-deposit-chance-out-of-thousand")
    public int BIOME_CLAY_DEPOSIT_CHANCE_OUT_OF_THOUSAND = 3;
    @YamlKey("biome.single.land")
    public String BIOME_SINGLE_TERRESTRIAL_TYPE = "disabled";
    @YamlKey("biome.defaults.flat")
    public String BIOME_DEFAULT_FLAT = "PLAINS";
    @YamlKey("biome.defaults.river")
    public String BIOME_DEFAULT_RIVER = "RIVER";
    @YamlKey("biome.defaults.beach")
    public String BIOME_DEFAULT_BEACH = "SANDY_BEACH";

    @YamlKey("biome.plains.weight")
    public int BIOME_PLAINS_WEIGHT = 7;
    @YamlKey("biome.plains.tree_interval")
    public int BIOME_PLAINS_TREE_INTERVAL = 16;
    @YamlKey("biome.meadow.weight")
    public int BIOME_MEADOW_WEIGHT = 3;
    @YamlKey("biome.elevatedplains.weight")
    public int BIOME_ELEVATED_PLAINS_WEIGHT = 8;
    @YamlKey("biome.petrifiedcliffs.weight")
    public int BIOME_PETRIFIEDCLIFFS_WEIGHT = 6;
    @YamlKey("biome.archedcliffs.weight")
    public int BIOME_ARCHED_CLIFFS_WEIGHT = 6;
    @YamlKey("biome.gorge.weight")
    public int BIOME_GORGE_WEIGHT = 6;
    @YamlKey("biome.erodedplains.weight")
    public int BIOME_ERODED_PLAINS_WEIGHT = 6;
    @YamlKey("biome.savanna.weight")
    public int BIOME_SAVANNA_WEIGHT = 6;
    @YamlKey("biome.muddybog.weight")
    public int BIOME_MUDDYBOG_WEIGHT = 2;
    @YamlKey("biome.forest.weight")
    public int BIOME_FOREST_WEIGHT = 8;
    @YamlKey("biome.flowerforest.weight")
    public int BIOME_FLOWERFOREST_WEIGHT = 8;
    @YamlKey("biome.desert.weight")
    public int BIOME_DESERT_WEIGHT = 6;
    @YamlKey("biome.jungle.weight")
    public int BIOME_JUNGLE_WEIGHT = 5;
    @YamlKey("biome.sparsejungle.weight")
    public int BIOME_SPARSE_JUNGLE_WEIGHT = 3;
    @YamlKey("biome.jungle.statue-chance-out-of-1000")
    public int BIOME_JUNGLE_STATUE_CHANCE = 4;
    @YamlKey("biome.bambooforest.weight")
    public int BIOME_BAMBOO_FOREST_WEIGHT = 2;
    @YamlKey("biome.badlands.weight")
    public int BIOME_BADLANDS_WEIGHT = 1;
    @YamlKey("biome.badlands.plateaus.height")
    public int BIOME_BADLANDS_PLATEAU_HEIGHT = 15;
    @YamlKey("biome.badlands.plateaus.sand-radius")
    public int BIOME_BADLANDS_PLATEAU_SAND_RADIUS = 7;
    @YamlKey("biome.badlands.plateaus.threshold")
    public double BIOME_BADLANDS_PLATEAU_THRESHOLD = 0.23d;
    @YamlKey("biome.badlands.plateaus.frequency")
    public float BIOME_BADLANDS_PLATEAU_FREQUENCY = 0.01f;
    @YamlKey("biome.badlands.plateaus.commonness")
    public double BIOME_BADLANDS_PLATEAU_COMMONNESS = 0.18d;
    @YamlKey("biome.taiga.weight")
    public int BIOME_TAIGA_WEIGHT = 6;
    @YamlKey("biome.cherrygrove.weight")
    public int BIOME_CHERRYGROVE_WEIGHT = 3;
    @YamlKey("biome.scarletforest.weight")
    public int BIOME_SCARLETFOREST_WEIGHT = 3;
    @YamlKey("biome.snowytaiga.weight")
    public int BIOME_SNOWY_TAIGA_WEIGHT = 6;
    @YamlKey("biome.snowywasteland.weight")
    public int BIOME_SNOWY_WASTELAND_WEIGHT = 4;
    @YamlKey("biome.icespikes.weight")
    public int BIOME_ICE_SPIKES_WEIGHT = 2;
    @YamlKey("biome.paleforest.weight")
    public int BIOME_PALE_FOREST_WEIGHT = 2;
    @YamlKey("biome.paleforest.creaking_tree_prob")
    @YamlComment("Represents the chance (per tree) to spawn a creaking heart in it")
    public double BIOME_PALE_FOREST_CREAKING_CHANCE = 0.085;
    @YamlKey("biome.darkforest.weight")
    public int BIOME_DARK_FOREST_WEIGHT = 5;
    @YamlKey("biome.darkforest.spawn-heads")
    public boolean BIOME_DARK_FOREST_SPAWN_HEADS = true;
    @YamlKey("biome.swamp.weight")
    public int BIOME_SWAMP_WEIGHT = 5;
    @YamlKey("biome.mangrove.weight")
    public int BIOME_MANGROVE_WEIGHT = 5;
    @YamlKey("biome.oasis.commonness")
    public double BIOME_OASIS_COMMONNESS = 1d;
    @YamlKey("biome.oasis.frequency")
    public float BIOME_OASIS_FREQUENCY = 0.012f;
    // -=[TREES]=-
    @YamlKey("trees.big-jungle-trees.enabled")
    public boolean TREES_JUNGLE_BIG_ENABLED = true;
    @YamlKey("trees.big-taiga-trees.enabled")
    public boolean TREES_TAIGA_BIG_ENABLED = true;
    @YamlKey("trees.big-forest-trees.enabled")
    public boolean TREES_FOREST_BIG_ENABLED = true;
    @YamlKey("trees.big-savanna-trees.enabled")
    public boolean TREES_SAVANNA_BIG_ENABLED = true;
    @YamlKey("trees.big-scarlet-trees.enabled")
    public boolean TREES_SCARLET_BIG_ENABLED = true;
    @YamlKey("trees.big-dark-forest-trees.enabled")
    public boolean TREES_DARK_FOREST_BIG_ENABLED = true;
    @YamlKey("trees.big-pale-forest-trees.enabled")
    public boolean TREES_PALE_FOREST_BIG_ENABLED = true;
    // -=[MISC]=-
    // MISC_SMOOTH_DESIGN("misc.smooth-design",false),
    @YamlKey("misc.custom-small-trees-from-saplings.enabled")
    public boolean MISC_SAPLING_CUSTOM_TREES_ENABLED = true;
    @YamlKey("misc.custom-small-trees-from-saplings.big-jungle-tree")
    public boolean MISC_SAPLING_CUSTOM_TREES_BIGTREES_JUNGLE = true;
    @YamlKey("misc.custom-small-trees-from-saplings.big-spruce-tree")
    public boolean MISC_SAPLING_CUSTOM_TREES_BIGTREES_SPRUCE = true;
    @YamlKey("misc.trees.only-use-logs-no-wood")
    public boolean MISC_TREES_FORCE_LOGS = false;
    @YamlKey("misc.trees.ground-gradient-limit")
    public double MISC_TREES_GRADIENT_LIMIT = 1.3d;
    @YamlKey("misc.use-slabs-to-smooth-terrain")
    public boolean MISC_USE_SLABS_TO_SMOOTH = true;
    // -=[DEVSTUFF]=-
    @YamlKey("dev-stuff.suppress-watchdog")
    public boolean DEVSTUFF_SUPPRESS_WATCHDOG = true;
    @YamlKey("dev-stuff.chunk-cache-size")
    public int DEVSTUFF_CHUNKCACHE_SIZE = 6000;
    @YamlKey("dev-stuff.biomecache-size")
    public int DEVSTUFF_CHUNKBIOMES_SIZE = 3000;
    // DEVSTUFF_EXPERIMENTAL_STRUCTURE_PLACEMENT("dev-stuff.experimental-structure-placement", false),
    @YamlKey("dev-stuff.patcher-cache-max-size")
    public int DEVSTUFF_FLUSH_PATCHER_CACHE_FREQUENCY = 100;
    @YamlKey("dev-stuff.extended-commands")
    public boolean DEVSTUFF_EXTENDED_COMMANDS = false;
    @YamlKey("dev-stuff.suppress-terraform-console-logs")
    public boolean DEVSTUFF_SUPPRESS_CONSOLE_LOGS = true;

    // -=[ANIMALS]=-
    // BEES
    @YamlKey("animals.bee.hive-frequency")
    public double ANIMALS_BEE_HIVEFREQUENCY = 0.02d;

    // -=[ORES]=-
    // AMETHYST
    @YamlKey("ore.amethyst.chance-per-chunk")
    public double ORE_AMETHYST_CHANCE = 0.05d;
    @YamlKey("ore.amethyst.geode-size")
    public int ORE_AMETHYST_GEODE_SIZE = 7;
    @YamlKey("ore.amethyst.min-depth")
    public int ORE_AMETHYST_MIN_DEPTH = 70;
    @YamlKey("ore.amethyst.min-depth-below-surface")
    public int ORE_AMETHYST_MIN_DEPTH_BELOW_SURFACE = 15;
    // COAL
    @YamlKey("ore.coal.chance-per-chunk")
    public int ORE_COAL_CHANCE = 50;
    @YamlKey("ore.coal.max-vein-size")
    public int ORE_COAL_VEINSIZE = 25;
    @YamlKey("ore.coal.max-vein-count")
    public int ORE_COAL_MAXVEINNUMBER = 115;
    @YamlKey("ore.coal.common-spawn-height")
    public int ORE_COAL_COMMONSPAWNHEIGHT = 96;
    @YamlKey("ore.coal.max-spawn-height")
    public int ORE_COAL_MAXSPAWNHEIGHT = 256;
    @YamlKey("ore.coal.min-spawn-height")
    public int ORE_COAL_MINSPAWNHEIGHT = 5;
    // IRON
    @YamlKey("ore.iron.chance-per-chunk")
    public int ORE_IRON_CHANCE = 50;
    @YamlKey("ore.iron.max-vein-size")
    public int ORE_IRON_VEINSIZE = 10;
    @YamlKey("ore.iron.max-vein-count")
    public int ORE_IRON_MAXVEINNUMBER = 73;
    @YamlKey("ore.iron.common-spawn-height")
    public int ORE_IRON_COMMONSPAWNHEIGHT = 16;
    @YamlKey("ore.iron.max-spawn-height")
    public int ORE_IRON_MAXSPAWNHEIGHT = 256;
    @YamlKey("ore.iron.min-spawn-height")
    public int ORE_IRON_MINSPAWNHEIGHT = -64;
    // GOLD
    @YamlKey("ore.gold.chance-per-chunk")
    public int ORE_GOLD_CHANCE = 40;
    @YamlKey("ore.gold.max-vein-size")
    public int ORE_GOLD_VEINSIZE = 10;
    @YamlKey("ore.gold.max-vein-count")
    public int ORE_GOLD_MAXVEINNUMBER = 15;
    @YamlKey("ore.gold.common-spawn-height")
    public int ORE_GOLD_COMMONSPAWNHEIGHT = -16;
    @YamlKey("ore.gold.max-spawn-height")
    public int ORE_GOLD_MAXSPAWNHEIGHT = 32;
    @YamlKey("ore.gold.min-spawn-height")
    public int ORE_GOLD_MINSPAWNHEIGHT = -64;
    // GOLD
    @YamlKey("ore.badlandsgold.chance-per-chunk")
    public int ORE_BADLANDSGOLD_CHANCE = 40;
    @YamlKey("ore.badlandsgold.max-vein-size")
    public int ORE_BADLANDSGOLD_VEINSIZE = 10;
    @YamlKey("ore.badlandsgold.max-vein-count")
    public int ORE_BADLANDSGOLD_MAXVEINNUMBER = 15;
    @YamlKey("ore.badlandsgold.common-spawn-height")
    public int ORE_BADLANDSGOLD_COMMONSPAWNHEIGHT = 32;
    @YamlKey("ore.badlandsgold.max-spawn-height")
    public int ORE_BADLANDSGOLD_MAXSPAWNHEIGHT = 256;
    @YamlKey("ore.badlandsgold.min-spawn-height")
    public int ORE_BADLANDSGOLD_MINSPAWNHEIGHT = -64;
    // DIAMOND
    @YamlKey("ore.diamond.chance-per-chunk")
    public int ORE_DIAMOND_CHANCE = 30;
    @YamlKey("ore.diamond.max-vein-size")
    public int ORE_DIAMOND_VEINSIZE = 7;
    @YamlKey("ore.diamond.max-vein-count")
    public int ORE_DIAMOND_MAXVEINNUMBER = 16;
    @YamlKey("ore.diamond.common-spawn-height")
    public int ORE_DIAMOND_COMMONSPAWNHEIGHT = -64;
    @YamlKey("ore.diamond.max-spawn-height")
    public int ORE_DIAMOND_MAXSPAWNHEIGHT = 16;
    @YamlKey("ore.diamond.min-spawn-height")
    public int ORE_DIAMOND_MINSPAWNHEIGHT = -64;
    // EMERALD
    @YamlKey("ore.emerald.chance-per-chunk")
    public int ORE_EMERALD_CHANCE = 30;
    @YamlKey("ore.emerald.max-vein-size")
    public int ORE_EMERALD_VEINSIZE = 7;
    @YamlKey("ore.emerald.max-vein-count")
    public int ORE_EMERALD_MAXVEINNUMBER = 3;
    @YamlKey("ore.emerald.common-spawn-height")
    public int ORE_EMERALD_COMMONSPAWNHEIGHT = 12;
    @YamlKey("ore.emerald.max-spawn-height")
    public int ORE_EMERALD_MAXSPAWNHEIGHT = 256;
    @YamlKey("ore.emerald.min-spawn-height")
    public int ORE_EMERALD_MINSPAWNHEIGHT = -16;
    // LAPIS
    @YamlKey("ore.lapis.chance-per-chunk")
    public int ORE_LAPIS_CHANCE = 30;
    @YamlKey("ore.lapis.max-vein-size")
    public int ORE_LAPIS_VEINSIZE = 6;
    @YamlKey("ore.lapis.max-vein-count")
    public int ORE_LAPIS_MAXVEINNUMBER = 40;
    @YamlKey("ore.lapis.common-spawn-height")
    public int ORE_LAPIS_COMMONSPAWNHEIGHT = 0;
    @YamlKey("ore.lapis.max-spawn-height")
    public int ORE_LAPIS_MAXSPAWNHEIGHT = 64;
    @YamlKey("ore.lapis.min-spawn-height")
    public int ORE_LAPIS_MINSPAWNHEIGHT = -64;
    // REDSTONE
    @YamlKey("ore.redstone.chance-per-chunk")
    public int ORE_REDSTONE_CHANCE = 40;
    @YamlKey("ore.redstone.max-vein-size")
    public int ORE_REDSTONE_VEINSIZE = 10;
    @YamlKey("ore.redstone.max-vein-count")
    public int ORE_REDSTONE_MAXVEINNUMBER = 15;
    @YamlKey("ore.redstone.common-spawn-height")
    public int ORE_REDSTONE_COMMONSPAWNHEIGHT = -32;
    @YamlKey("ore.redstone.max-spawn-height")
    public int ORE_REDSTONE_MAXSPAWNHEIGHT = 16;
    @YamlKey("ore.redstone.min-spawn-height")
    public int ORE_REDSTONE_MINSPAWNHEIGHT = -64;
    // COPPER
    @YamlKey("ore.copper.chance-per-chunk")
    public int ORE_COPPER_CHANCE = 40;
    @YamlKey("ore.copper.max-vein-size")
    public int ORE_COPPER_VEINSIZE = 10;
    @YamlKey("ore.copper.max-vein-count")
    public int ORE_COPPER_MAXVEINNUMBER = 45;
    @YamlKey("ore.copper.common-spawn-height")
    public int ORE_COPPER_COMMONSPAWNHEIGHT = 48;
    @YamlKey("ore.copper.max-spawn-height")
    public int ORE_COPPER_MAXSPAWNHEIGHT = 104;
    @YamlKey("ore.copper.min-spawn-height")
    public int ORE_COPPER_MINSPAWNHEIGHT = -16;
    // GRAVEL
    @YamlKey("ore.gravel.chance-per-chunk")
    public int ORE_GRAVEL_CHANCE = 75;
    @YamlKey("ore.gravel.max-vein-size")
    public int ORE_GRAVEL_VEINSIZE = 45;
    @YamlKey("ore.gravel.max-vein-count")
    public int ORE_GRAVEL_MAXVEINNUMBER = 100;
    @YamlKey("ore.gravel.common-spawn-height")
    public int ORE_GRAVEL_COMMONSPAWNHEIGHT = 255;
    @YamlKey("ore.gravel.max-spawn-height")
    public int ORE_GRAVEL_MAXSPAWNHEIGHT = 300;
    @YamlKey("ore.gravel.min-spawn-height")
    public int ORE_GRAVEL_MINSPAWNHEIGHT = -64;
    // ANDESITE
    @YamlKey("ore.andesite.chance-per-chunk")
    public int ORE_ANDESITE_CHANCE = 80;
    @YamlKey("ore.andesite.max-vein-size")
    public int ORE_ANDESITE_VEINSIZE = 45;
    @YamlKey("ore.andesite.max-vein-count")
    public int ORE_ANDESITE_MAXVEINNUMBER = 110;
    @YamlKey("ore.andesite.common-spawn-height")
    public int ORE_ANDESITE_COMMONSPAWNHEIGHT = 255;
    @YamlKey("ore.andesite.max-spawn-height")
    public int ORE_ANDESITE_MAXSPAWNHEIGHT = 300;
    @YamlKey("ore.andesite.min-spawn-height")
    public int ORE_ANDESITE_MINSPAWNHEIGHT = -64;
    // DIORITE
    @YamlKey("ore.diorite.chance-per-chunk")
    public int ORE_DIORITE_CHANCE = 80;
    @YamlKey("ore.diorite.max-vein-size")
    public int ORE_DIORITE_VEINSIZE = 45;
    @YamlKey("ore.diorite.max-vein-count")
    public int ORE_DIORITE_MAXVEINNUMBER = 110;
    @YamlKey("ore.diorite.common-spawn-height")
    public int ORE_DIORITE_COMMONSPAWNHEIGHT = 255;
    @YamlKey("ore.diorite.max-spawn-height")
    public int ORE_DIORITE_MAXSPAWNHEIGHT = 300;
    @YamlKey("ore.diorite.min-spawn-height")
    public int ORE_DIORITE_MINSPAWNHEIGHT = -64;
    // GRANITE
    @YamlKey("ore.granite.chance-per-chunk")
    public int ORE_GRANITE_CHANCE = 80;
    @YamlKey("ore.granite.max-vein-size")
    public int ORE_GRANITE_VEINSIZE = 45;
    @YamlKey("ore.granite.max-vein-count")
    public int ORE_GRANITE_MAXVEINNUMBER = 110;
    @YamlKey("ore.granite.common-spawn-height")
    public int ORE_GRANITE_COMMONSPAWNHEIGHT = 255;
    @YamlKey("ore.granite.max-spawn-height")
    public int ORE_GRANITE_MAXSPAWNHEIGHT = 300;
    @YamlKey("ore.granite.min-spawn-height")
    public int ORE_GRANITE_MINSPAWNHEIGHT = -64;
    // TUFF
    @YamlKey("ore.tuff.chance-per-chunk")
    public int ORE_TUFF_CHANCE = 40;
    @YamlKey("ore.tuff.max-vein-size")
    public int ORE_TUFF_VEINSIZE = 30;
    @YamlKey("ore.tuff.max-vein-count")
    public int ORE_TUFF_MAXVEINNUMBER = 50;
    @YamlKey("ore.tuff.common-spawn-height")
    public int ORE_TUFF_COMMONSPAWNHEIGHT = -64;
    @YamlKey("ore.tuff.max-spawn-height")
    public int ORE_TUFF_MAXSPAWNHEIGHT = 10;
    @YamlKey("ore.tuff.min-spawn-height")
    public int ORE_TUFF_MINSPAWNHEIGHT = -63;
    // DEEPSLATE
    @YamlKey("ore.deepslate.chance-per-chunk")
    public int ORE_DEEPSLATE_CHANCE = 80;
    @YamlKey("ore.deepslate.max-vein-size")
    public int ORE_DEEPSLATE_VEINSIZE = 45;
    @YamlKey("ore.deepslate.max-vein-count")
    public int ORE_DEEPSLATE_MAXVEINNUMBER = 50;
    @YamlKey("ore.deepslate.common-spawn-height")
    public int ORE_DEEPSLATE_COMMONSPAWNHEIGHT = 5;
    @YamlKey("ore.deepslate.max-spawn-height")
    public int ORE_DEEPSLATE_MAXSPAWNHEIGHT = 15;
    @YamlKey("ore.deepslate.min-spawn-height")
    public int ORE_DEEPSLATE_MINSPAWNHEIGHT = 0;
    @YamlComment("Turn this off to remove any cave carveouts, including their decorations.")
    @YamlKey("feature_toggle.caves")
    public boolean FEATURE_CAVES_ENABLED = true;
    @YamlComment("Turns off cave decorations like walls and stalactites/stalagmites")
    @YamlKey("feature_toggle.cavedecorators")
    public boolean FEATURE_CAVEDECORATORS_ENABLED = true;
    @YamlComment("Turns off clusters like dripstone and lush clusters. You can individually turn them off with maxsize set to 0.")
    @YamlKey("feature_toggle.caveclusters")
    public boolean FEATURE_CAVECLUSTERS_ENABLED = true;
    @YamlComment("Disables placing spawners")
    @YamlKey("feature_toggle.spawners")
    public boolean FEATURE_SPAWNERS_ENABLED = true;
    @YamlComment("Toggle (precious) ores")
    @YamlKey("feature_toggle.ores")
    public boolean FEATURE_ORES_ENABLED = true;
    @YamlComment("'Trees' means the big trees")
    @YamlKey("feature_toggle.trees")
    public boolean FEATURE_TREES_ENABLED = true;
    @YamlComment("'Tall' means those tree-like mushrooms. The smaller ones are governed by the plants-toggle.")
    @YamlKey("feature_toggle.tall_mushrooms")
    public boolean FEATURE_TALL_MUSHROOMS_ENABLED = true;
    @YamlComment("'Plants' mean everything but the big trees")
    @YamlKey("feature_toggle.plants")
    public boolean FEATURE_PLANTS_ENABLED = true;
    @YamlComment("Small lanterns, barrels, chests, ...")
    @YamlKey("feature_toggle.decorations")
    public boolean FEATURE_DECORATIONS_ENABLED = true;
    @YamlKey("feature_toggle.structures")
    public boolean FEATURE_STRUCTURES_ENABLED = true;
    // Extras
    @YamlComment("What language file should be used?")
    @YamlKey("lang")
    public String LANGUAGE_FILE = "eng.yml";

    // -=[STRUCTURES]=-
    @YamlKey("structures.technical.megachunk.numbiomesections")
    public int STRUCTURES_MEGACHUNK_NUMBIOMESECTIONS = 4;
    @YamlKey("structures.mansion.enabled")
    public boolean STRUCTURES_MANSION_ENABLED = true;
    @YamlKey("structures.mansion.size")
    public int STRUCTURES_MANSION_SIZE = 80;
    @YamlKey("structures.mansion.min-distance-blocks")
    public int STRUCTURES_MANSION_MINDISTANCE = 5000;
    @YamlKey("structures.mansion.pillager-spawn-aggression")
    public int STRUCTURES_MANSION_SPAWNAGGRESSION = 1;
    @YamlKey("structures.mansion.spawnratio")
    public double STRUCTURES_MANSION_SPAWNRATIO = 0.3d;
    @YamlKey("structures.mansion.chunk-exclusion-zone")
    public int STRUCTURES_MANSION_CHUNK_EXCLUSION_ZONE = 4;
    @YamlKey("structures.stronghold.enabled")
    public boolean STRUCTURES_STRONGHOLD_ENABLED = true;
    @YamlKey("structures.stronghold.failsafe-y")
    public int STRUCTURES_STRONGHOLD_FAILSAFE_Y = -16;
    @YamlKey("structures.stronghold.min-y")
    public int STRUCTURES_STRONGHOLD_MIN_Y = 0;
    @YamlKey("structures.stronghold.max-y")
    public int STRUCTURES_STRONGHOLD_MAX_Y = 25;
    @YamlKey("structures.pyramid.enabled")
    public boolean STRUCTURES_PYRAMID_ENABLED = true;
    @YamlKey("structures.pyramid.spawn-ratio")
    public double STRUCTURES_PYRAMID_SPAWNRATIO = 0.3d;
    @YamlKey("structures.pyramid.spawn-elder-guardian")
    public boolean STRUCTURES_PYRAMID_SPAWN_ELDER_GUARDIAN = true;
    @YamlKey("structures.pyramid.suspicious-sand-per-antechamber")
    public int STRUCTURES_PYRAMID_SUSPICIOUS_SAND_COUNT_PER_ANTECHAMBER = 4;
    @YamlKey("structures.villagehouse.spawnratio")
    public double STRUCTURES_VILLAGEHOUSE_SPAWNRATIO = 0.8d;
    @YamlKey("structures.farmhouse.enabled")
    public boolean STRUCTURES_FARMHOUSE_ENABLED = true;
    @YamlKey("structures.animalfarm.enabled")
    public boolean STRUCTURES_ANIMALFARM_ENABLED = true;
    @YamlKey("structures.trailruins.spawnratio")
    public double STRUCTURES_TRAILRUINS_SPAWNRATIO = 0.5d;
    @YamlKey("structures.trailruins.enabled")
    public boolean STRUCTURES_TRAILRUINS_ENABLED = true;
    @YamlKey("structures.trialchamber.spawnratio")
    public double STRUCTURES_TRIALCHAMBER_SPAWNRATIO = 0.3d;
    @YamlKey("structures.trialchamber.enabled")
    public boolean STRUCTURES_TRIALCHAMBER_ENABLED = true;
    @YamlKey("structures.village.spawnratio")
    public double STRUCTURES_VILLAGE_SPAWNRATIO = 1d;
    @YamlKey("structures.plainsvillage.enabled")
    public boolean STRUCTURES_PLAINSVILLAGE_ENABLED = true;
    @YamlKey("structures.plainsvillage.height-tolerance")
    public int STRUCTURES_PLAINSVILLAGE_HEIGHT_TOLERANCE = 10;
    @YamlKey("structures.village.chunk-exclusion-zone")
    public int STRUCTURES_VILLAGE_CHUNK_EXCLUSION_ZONE = 4;
    @YamlKey("structures.swamphut.enabled")
    public boolean STRUCTURES_SWAMPHUT_ENABLED = true;
    @YamlKey("structures.swamphut.spawnratio")
    public double STRUCTURES_SWAMPHUT_SPAWNRATIO = 0.4d;
    @YamlKey("structures.swamphut.count-per-megachunk")
    public int STRUCTURES_SWAMPHUT_COUNT_PER_MEGACHUNK = 1;
    @YamlKey("structures.desertwell.enabled")
    public boolean STRUCTURES_DESERTWELL_ENABLED = true;
    @YamlKey("structures.desertwell.spawnratio")
    public double STRUCTURES_DESERTWELL_SPAWNRATIO = 0.3d;
    @YamlKey("structures.desertwell.count-per-megachunk")
    public int STRUCTURES_DESERTWELL_COUNT_PER_MEGACHUNK = 2;
    @YamlKey("structures.small-dungeon.spawnratio")
    public double STRUCTURES_DUNGEONS_SPAWNRATIO = 0.4d;
    @YamlKey("structures.small-dungeon.count-per-megachunk")
    public int STRUCTURES_DUNGEONS_COUNT_PER_MEGACHUNK = 3;
    @YamlKey("structures.underground-dungeon.enabled")
    public boolean STRUCTURES_UNDERGROUNDDUNGEON_ENABLED = true;
    @YamlKey("structures.drowned-dungeon.enabled")
    public boolean STRUCTURES_DROWNEDDUNGEON_ENABLED = true;
    @YamlKey("structures.drowned-dungeon.min-chunk-y")
    public int STRUCTURES_DROWNEDDUNGEON_MIN_DEPTH = 52;
    @YamlKey("structures.drowned-dungeon.chance-out-of-1000")
    public int STRUCTURES_DROWNEDDUNGEON_CHANCE = 200;
    @YamlKey("structures.shipwreck.spawnratio")
    public double STRUCTURES_SHIPWRECK_SPAWNRATIO = 0.6d;
    @YamlKey("structures.shipwreck.enabled")
    public boolean STRUCTURES_SHIPWRECK_ENABLED = true;
    @YamlKey("structures.shipwreck.count-per-megachunk")
    public int STRUCTURES_SHIPWRECK_COUNT_PER_MEGACHUNK = 2;
    @YamlKey("structures.ruinedportal.spawnratio")
    public double STRUCTURES_RUINEDPORTAL_SPAWNRATIO = 0.4d;
    @YamlKey("structures.ruinedportal.enabled")
    public boolean STRUCTURES_RUINEDPORTAL_ENABLED = true;
    @YamlKey("structures.ruinedportal.count-per-megachunk")
    public int STRUCTURES_RUINEDPORTAL_COUNT_PER_MEGACHUNK = 1;
    @YamlKey("structures.igloo.spawnratio")
    public double STRUCTURES_IGLOO_SPAWNRATIO = 0.8d;
    @YamlKey("structures.igloo.enabled")
    public boolean STRUCTURES_IGLOO_ENABLED = true;
    @YamlKey("structures.igloo.count-per-megachunk")
    public int STRUCTURES_IGLOO_COUNT_PER_MEGACHUNK = 1;
    @YamlKey("structures.buriedtreasure.spawnratio")
    public double STRUCTURES_BURIEDTREASURE_SPAWNRATIO = 0.3d;
    @YamlKey("structures.buriedtreasure.enabled")
    public boolean STRUCTURES_BURIEDTREASURE_ENABLED = true;
    @YamlKey("structures.buriedtreasure.count-per-megachunk")
    public int STRUCTURES_BURIEDTREASURE_COUNT_PER_MEGACHUNK = 2;
    @YamlKey("structures.mineshaft.enabled")
    public boolean STRUCTURES_MINESHAFT_ENABLED = true;
    @YamlKey("structures.mineshaft.spawnratio")
    public double STRUCTURES_MINESHAFT_SPAWNRATIO = 0.8d;
    @YamlKey("structures.mineshaft.min-y")
    public int STRUCTURES_MINESHAFT_MIN_Y = -10;
    @YamlKey("structures.mineshaft.max-y")
    public int STRUCTURES_MINESHAFT_MAX_Y = 30;
    @YamlKey("structures.catacombs.enabled")
    public boolean STRUCTURES_CATACOMBS_ENABLED = true;
    @YamlKey("structures.catacombs.spawnratio")
    public double STRUCTURES_CATACOMBS_SPAWNRATIO = 0.6d;
    @YamlKey("structures.catacombs.sizerollchance")
    public double STRUCTURES_CATACOMBS_SIZEROLLCHANCE = 0.40d;
    @YamlKey("structures.catacombs.max-levels")
    public int STRUCTURES_CATACOMBS_MAX_LEVELS = 5;
    @YamlKey("structures.catacombs.min-y")
    public int STRUCTURES_CATACOMBS_MIN_Y = 0;
    @YamlKey("structures.catacombs.max-y")
    public int STRUCTURES_CATACOMBS_MAX_Y = 30;
    @YamlKey("structures.ancientcity.enabled")
    public boolean STRUCTURES_ANCIENTCITY_ENABLED = true;
    @YamlKey("structures.ancientcity.spawnratio")
    public double STRUCTURES_ANCIENTCITY_SPAWNRATIO = 0.5d;
    @YamlKey("structures.ancientcity.min-y")
    public int STRUCTURES_ANCIENTCITY_MIN_Y = -20;
    @YamlKey("structures.ancientcity.max-y")
    public int STRUCTURES_ANCIENTCITY_MAX_Y = -10;
    @YamlKey("structures.largecave.enabled")
    public boolean STRUCTURES_LARGECAVE_ENABLED = true;
    @YamlKey("structures.largecave.spawnratio")
    public double STRUCTURES_LARGECAVE_SPAWNRATIO = 0.8d;
    @YamlKey("structures.outpost.enabled")
    public boolean STRUCTURES_OUTPOST_ENABLED = true;
    @YamlKey("structures.outpost.spawnratio")
    public double STRUCTURES_OUTPOST_SPAWNRATIO = 0.8d;

    public static void init(final File f) throws IOException {
        if (c == null) {
            c = new TConfig().load(f);
            c.save(f); // updates the configuration file to the newest format
        }
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean areCavesEnabled() {
        return c.FEATURE_CAVES_ENABLED;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean areTreesEnabled() {
        return c.FEATURE_TREES_ENABLED;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean areTallMushroomsEnabled() {
        return c.FEATURE_TALL_MUSHROOMS_ENABLED;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean arePlantsEnabled() {
        return c.FEATURE_PLANTS_ENABLED;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean areStructuresEnabled() {
        return c.FEATURE_STRUCTURES_ENABLED;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean areDecorationsEnabled() {
        return c.FEATURE_DECORATIONS_ENABLED;
    }
}
