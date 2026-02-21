package org.terraform.biome;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.terraform.biome.beach.*;
import org.terraform.biome.cavepopulators.AbstractCavePopulator;
import org.terraform.biome.cavepopulators.FrozenCavePopulator;
import org.terraform.biome.cavepopulators.MossyCavePopulator;
import org.terraform.biome.flat.*;
import org.terraform.biome.river.*;
import org.terraform.coregen.HeightMap;
import org.terraform.coregen.bukkit.TerraformGenerator;
import org.terraform.data.CoordPair;
import org.terraform.data.TerraformWorld;
import org.terraform.main.TConfig;
import org.terraform.main.TerraformGeneratorPlugin;
import org.terraform.utils.datastructs.ConcurrentLRUCache;
import org.terraform.utils.noise.FastNoise;
import org.terraform.utils.noise.FastNoise.NoiseType;
import org.terraform.utils.noise.NoiseCacheHandler;
import org.terraform.utils.noise.NoiseCacheHandler.NoiseCacheEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Random;

public enum BiomeBank {
    // RIVERS (Don't include in selectBiome)
    // Rivers are handled specially and will not be allocated in selectBiome
    RIVER(new RiverHandler(), BiomeType.RIVER, BiomeClimate.TRANSITION),
    BOG_RIVER(new BogRiverHandler(), BiomeType.RIVER, BiomeClimate.DRY_VEGETATION),
    CHERRY_GROVE_RIVER(new CherryGroveRiverHandler(), BiomeType.RIVER, BiomeClimate.COLD),
    SCARLET_FOREST_RIVER(new ScarletForestRiverHandler(), BiomeType.RIVER, BiomeClimate.COLD),
    JUNGLE_RIVER(new JungleRiverHandler(), BiomeType.RIVER, BiomeClimate.HUMID_VEGETATION),
    FROZEN_RIVER(
            new FrozenRiverHandler(),
            BiomeType.RIVER,
            BiomeClimate.SNOWY,
            new FrozenCavePopulator()
    ), // Special case, handle later
    DARK_FOREST_RIVER(
            new DarkForestRiverHandler(),
            BiomeType.RIVER,
            BiomeClimate.HUMID_VEGETATION,
            new FrozenCavePopulator()
    ), // Special case, handle later
    DESERT_RIVER(new DesertRiverHandler(), BiomeType.RIVER, BiomeClimate.HOT_BARREN),
    BADLANDS_RIVER(new BadlandsRiverHandler(), BiomeType.RIVER, BiomeClimate.HOT_BARREN),

    // FLAT
    PLAINS(new PlainsHandler(), BiomeType.FLAT, BiomeClimate.TRANSITION, TConfig.c.BIOME_PLAINS_WEIGHT),
    MEADOW(new MeadowHandler(), BiomeType.FLAT, BiomeClimate.TRANSITION, TConfig.c.BIOME_MEADOW_WEIGHT),
    ELEVATED_PLAINS(
            new ElevatedPlainsHandler(),
            BiomeType.FLAT,
            BiomeClimate.TRANSITION,
            TConfig.c.BIOME_ELEVATED_PLAINS_WEIGHT
    ),
    GORGE(new GorgeHandler(), BiomeType.FLAT, BiomeClimate.TRANSITION, TConfig.c.BIOME_GORGE_WEIGHT),
    PETRIFIED_CLIFFS(
            new PetrifiedCliffsHandler(),
            BiomeType.FLAT,
            BiomeClimate.TRANSITION,
            TConfig.c.BIOME_PETRIFIEDCLIFFS_WEIGHT
    ),
    ARCHED_CLIFFS(
            new ArchedCliffsHandler(),
            BiomeType.FLAT,
            BiomeClimate.TRANSITION,
            TConfig.c.BIOME_ARCHED_CLIFFS_WEIGHT
    ),
    SAVANNA(new SavannaHandler(), BiomeType.FLAT, BiomeClimate.DRY_VEGETATION, TConfig.c.BIOME_SAVANNA_WEIGHT),
    MUDDY_BOG(new MuddyBogHandler(), BiomeType.FLAT, BiomeClimate.DRY_VEGETATION, TConfig.c.BIOME_MUDDYBOG_WEIGHT),
    FOREST(new ForestHandler(), BiomeType.FLAT, BiomeClimate.HUMID_VEGETATION, TConfig.c.BIOME_FOREST_WEIGHT),
    FLOWER_FOREST(
            new FlowerForestHandler(),
            BiomeType.FLAT,
            BiomeClimate.HUMID_VEGETATION,
            TConfig.c.BIOME_FLOWERFOREST_WEIGHT
    ),
    JUNGLE(new JungleHandler(), BiomeType.FLAT, BiomeClimate.HUMID_VEGETATION, TConfig.c.BIOME_JUNGLE_WEIGHT),
    SPARSE_JUNGLE(
            new SparseJungleHandler(),
            BiomeType.FLAT,
            BiomeClimate.HUMID_VEGETATION,
            TConfig.c.BIOME_SPARSE_JUNGLE_WEIGHT
    ),
    BAMBOO_FOREST(
            new BambooForestHandler(),
            BiomeType.FLAT,
            BiomeClimate.HUMID_VEGETATION,
            TConfig.c.BIOME_BAMBOO_FOREST_WEIGHT
    ),
    DESERT(new DesertHandler(), BiomeType.FLAT, BiomeClimate.HOT_BARREN, TConfig.c.BIOME_DESERT_WEIGHT),
    BADLANDS(new BadlandsHandler(), BiomeType.FLAT, BiomeClimate.HOT_BARREN, TConfig.c.BIOME_BADLANDS_WEIGHT),
    ERODED_PLAINS(new ErodedPlainsHandler(), BiomeType.FLAT, BiomeClimate.COLD, TConfig.c.BIOME_ERODED_PLAINS_WEIGHT),
    SCARLET_FOREST(new ScarletForestHandler(), BiomeType.FLAT, BiomeClimate.COLD, TConfig.c.BIOME_SCARLETFOREST_WEIGHT),
    CHERRY_GROVE(new CherryGroveHandler(), BiomeType.FLAT, BiomeClimate.COLD, TConfig.c.BIOME_CHERRYGROVE_WEIGHT),
    TAIGA(new TaigaHandler(), BiomeType.FLAT, BiomeClimate.COLD, TConfig.c.BIOME_TAIGA_WEIGHT),
    SNOWY_TAIGA(
            new SnowyTaigaHandler(),
            BiomeType.FLAT,
            BiomeClimate.SNOWY,
            TConfig.c.BIOME_SNOWY_TAIGA_WEIGHT,
            new FrozenCavePopulator()
    ),
    SNOWY_WASTELAND(
            new SnowyWastelandHandler(),
            BiomeType.FLAT,
            BiomeClimate.SNOWY,
            TConfig.c.BIOME_SNOWY_WASTELAND_WEIGHT,
            new FrozenCavePopulator()
    ),
    ICE_SPIKES(
            new IceSpikesHandler(),
            BiomeType.FLAT,
            BiomeClimate.SNOWY,
            TConfig.c.BIOME_ICE_SPIKES_WEIGHT,
            new FrozenCavePopulator()
    ),
    DARK_FOREST(
            new DarkForestHandler(),
            BiomeType.FLAT,
            BiomeClimate.HUMID_VEGETATION,
            TConfig.c.BIOME_DARK_FOREST_WEIGHT
    ),
    PALE_FOREST(
            new PaleForestHandler(),
            BiomeType.FLAT,
            BiomeClimate.HUMID_VEGETATION,
            TConfig.c.BIOME_PALE_FOREST_WEIGHT
    ),
    SWAMP(new SwampHandler(), BiomeType.FLAT, BiomeClimate.HUMID_VEGETATION, TConfig.c.BIOME_SWAMP_WEIGHT),
    MANGROVE(new MangroveHandler(), BiomeType.FLAT, BiomeClimate.HUMID_VEGETATION, TConfig.c.BIOME_MANGROVE_WEIGHT),

    // BEACHES (Don't include in selectBiome)
    SANDY_BEACH(new SandyBeachHandler(), BiomeType.BEACH, BiomeClimate.TRANSITION),
    BOG_BEACH(new BogBeachHandler(), BiomeType.BEACH, BiomeClimate.DRY_VEGETATION),
    DARK_FOREST_BEACH(new DarkForestBeachHandler(), BiomeType.BEACH, BiomeClimate.HUMID_VEGETATION),
    BADLANDS_BEACH(new BadlandsBeachHandler(), BiomeType.BEACH, BiomeClimate.HOT_BARREN),
    MUSHROOM_BEACH(new MushroomBeachHandler(), BiomeType.BEACH, BiomeClimate.TRANSITION),
    BLACK_OCEAN_BEACH(new BlackOceanBeachHandler(), BiomeType.BEACH, BiomeClimate.COLD),
    ROCKY_BEACH(new RockBeachHandler(), BiomeType.BEACH, BiomeClimate.COLD),
    ICY_BEACH(new IcyBeachHandler(), BiomeType.BEACH, BiomeClimate.SNOWY, new FrozenCavePopulator()),
    MUDFLATS(new MudflatsHandler(), BiomeType.BEACH, BiomeClimate.HUMID_VEGETATION), // Special case, handle later
    CHERRY_GROVE_BEACH(new CherryGroveBeachHandler(), BiomeType.BEACH, BiomeClimate.COLD),
    SCARLET_FOREST_BEACH(new ScarletForestBeachHandler(), BiomeType.BEACH, BiomeClimate.COLD),
    ;
    // public static final BiomeBank[] VALUES = values();
    public static boolean debugPrint = false;
    public static @Nullable BiomeBank singleLand = null;
    public static final ConcurrentLRUCache<BiomeSection, BiomeSection> BIOME_SECTION_CACHE = new ConcurrentLRUCache<>(
            "BIOME_SECTION_CACHE", 250, (key) -> {
        key.doCalculations();
        return key;
    }
    );
    private final BiomeHandler handler;
    private final BiomeType type;
    private final AbstractCavePopulator cavePop;
    private final BiomeClimate climate;
    private final int biomeWeight;

    BiomeBank(BiomeHandler handler, BiomeType type, BiomeClimate climate) {
        this.handler = handler;
        this.type = type;

        this.climate = climate;
        // Impossible to pick from selectBiome.
        this.biomeWeight = 0;

        this.cavePop = new MossyCavePopulator();
    }

    BiomeBank(BiomeHandler handler, BiomeType type, BiomeClimate climate, AbstractCavePopulator cavePop) {
        this.handler = handler;
        this.type = type;
        this.climate = climate;

        // Impossible to pick from selectBiome.
        this.biomeWeight = 0;

        this.cavePop = cavePop;
    }

    BiomeBank(BiomeHandler handler, BiomeType type, BiomeClimate climate, int biomeWeight) {
        this.handler = handler;
        this.type = type;
        this.climate = climate;
        this.biomeWeight = biomeWeight;
        this.cavePop = new MossyCavePopulator();
    }

    BiomeBank(BiomeHandler handler,
              BiomeType type,
              BiomeClimate climate,
              int biomeWeight,
              AbstractCavePopulator cavePop)
    {
        this.handler = handler;
        this.type = type;
        this.climate = climate;
        this.cavePop = cavePop;
        this.biomeWeight = biomeWeight;
    }

    /**
     * @param x Block X
     * @param z Block Z
     */
    public static @NotNull BiomeSection getBiomeSectionFromBlockCoords(TerraformWorld tw, int x, int z) {
        BiomeSection sect = new BiomeSection(tw, x, z);
        //		sect.doCalculations();
        sect = BIOME_SECTION_CACHE.get(sect);
        return sect;
    }

    /**
     * ChunkX, ChunkZ
     *
     * @return the biome section that this chunk belongs to.
     */
    public static @NotNull BiomeSection getBiomeSectionFromChunk(TerraformWorld tw, int chunkX, int chunkZ) {
        BiomeSection sect = new BiomeSection(tw, chunkX << 4, chunkZ << 4);
        sect = BIOME_SECTION_CACHE.get(sect);

        return sect;
    }

    public static @NotNull BiomeSection getBiomeSectionFromSectionCoords(TerraformWorld tw,
                                                                         int x,
                                                                         int z,
                                                                         boolean useSectionCoords)
    {
        BiomeSection sect = new BiomeSection(tw, x, z, useSectionCoords);
        sect = BIOME_SECTION_CACHE.get(sect);

        return sect;
    }

    /**
     * WARNING: NOBODY SHOULD BE CALLING THIS METHOD.
     * THIS METHOD WILL RUN ALL CALCULATIONS.
     * <br><br>
     * Use terraformWorld.getCache(...).getBiomeBank(x,y,z) instead.
     *
     * @return exact biome that will appear at these coordinates
     */
    public static @NotNull BiomeBank calculateBiome(@NotNull TerraformWorld tw, int rawX, int height, int rawZ) {
        if (debugPrint) {
            TerraformGeneratorPlugin.logger.info("calculateBiome called with args: "
                                                 + tw.getName()
                                                 + ","
                                                 + rawX
                                                 + ","
                                                 + height
                                                 + ","
                                                 + rawZ);
        }

        BiomeBank bank = calculateHeightIndependentBiome(tw, rawX, rawZ);

        // Bitshift rawX and rawZ. Biome storage is done every 4 blocks,
        // so there's no need to recalculate for every block.

        FastNoise beachNoise = NoiseCacheHandler.getNoise(
                tw, NoiseCacheEntry.BIOME_BEACH_HEIGHT, (world) -> {
                    FastNoise n = new FastNoise((int) world.getSeed());
                    n.SetNoiseType(NoiseType.PerlinFractal);
                    n.SetFrequency(0.01f);
                    n.SetFractalOctaves(4);

                    return n;
                }
        );
        // If calculated height is less than sea level, but more than sea level after
        // adding back river height, it means that the river height
        // carved dry land into the sea level.
        // That's a river.
        if (height < TerraformGenerator.seaLevel
            && height + HeightMap.getRawRiverDepth(tw, rawX, rawZ) >= TerraformGenerator.seaLevel)
        {
            bank = bank.getHandler().getRiverType();
            if (debugPrint) {
                TerraformGeneratorPlugin.logger.info("calculateBiome -> River Detected");
            }
            // If the height is at, or slightly higher than, sea level,
            // it is a beach.
        }
        else if (height >= TerraformGenerator.seaLevel && height <= TerraformGenerator.seaLevel + 4 * 2 * Math.abs(
                beachNoise.GetNoise(rawX, rawZ)))
        {
            bank = bank.getHandler().getBeachType();
            if (debugPrint) {
                TerraformGeneratorPlugin.logger.info("calculateBiome -> Beach calculated");
            }
        }

        // Correct submerged biomes. They'll be rivers.
        // Exclude swamps from this check, as swamps are submerged.
        if (bank != BiomeBank.SWAMP
            && bank != BiomeBank.MANGROVE
            && height < TerraformGenerator.seaLevel
            && bank.isDry())
        {
            bank = bank.getHandler().getRiverType();
            if (debugPrint) {
                TerraformGeneratorPlugin.logger.info("calculateBiome -> Biome is submerged, defaulting to river");
            }
        }

        // Oceanic biomes that are above water level
        // should be handled as the closest, most dominant dry biome, or be a beach

        if (!bank.isDry() && height >= TerraformGenerator.seaLevel) {
            if (debugPrint) {
                TerraformGeneratorPlugin.logger.info("calculateBiome -> Submerged biome above ground detected");
            }
            BiomeBank replacement = null;

            // If the ocean handler wants to force a beach default, it will be a beach default.
            if (!bank.getHandler().forceDefaultToBeach()) {
                int highestDom = Integer.MIN_VALUE;
                for (BiomeSection sect : BiomeSection.getSurroundingSections(tw, rawX, rawZ)) {
                    if (debugPrint) {
                        TerraformGeneratorPlugin.logger.info("calculateBiome -> -> Comparison Section: "
                                                             + sect.toString());
                    }
                    if (sect.getBiomeBank().isDry()) {
                        int compDist = (int) sect.getDominanceBasedOnRadius(rawX, rawZ);
                        if (debugPrint) {
                            TerraformGeneratorPlugin.logger.info("calculateBiome -> -> -> Dominance: " + compDist);
                        }
                        if (compDist > highestDom) {
                            replacement = sect.getBiomeBank();
                            highestDom = compDist;
                        }
                    }
                }
            }

            // Fallback to beach if surrounding biomes are not dry
            bank = replacement == null ? bank.getHandler().getBeachType() : replacement;

            if (debugPrint) {
                TerraformGeneratorPlugin.logger.info("calculateBiome -> -> Submerged biome defaulted to: "
                                                     + replacement);
            }

        }
        if (debugPrint) {
            TerraformGeneratorPlugin.logger.info("calculateBiome -> Evaluated: " + bank);
        }

        return bank;
    }

    /**
     * NOBODY SHOULD BE CALLING THIS METHOD. THIS IS AN INTERNAL CALCULATION,
     * AND IT WILL NOT RETURN THE FINAL BIOME.
     * Use terraformWorld.getCache(...).getBiomeBank(x,y,z) instead.
     * <br><br>
     * Supply y with getHighestGround.
     * <br><br>
     * If for whatever reason, the biome must be calculated intead of
     * fetched from the cache, use calculateBiome(tw,x,y,z);
     *
     * @return a biome type
     */
    public static @NotNull BiomeBank calculateHeightIndependentBiome(TerraformWorld tw, int x, int z)
    {
        // This optimisation doesn't work here. Many aesthetic options rely on
        // the fact that this is block-accurate. Calculating once per 4x4 blocks
        // creates obvious ugly 4x4 artifacts
        // x = (x >> 2) << 2; z = (z >> 2) << 2;

        //There used to be a cache here, but it had an abysmal hitrate of near 0
        // when caching 32 chunks
        BiomeSection mostDominant = BiomeSection.getMostDominantSection(tw, x, z);
        return mostDominant.getBiomeBank();
    }

    public static void initSinglesConfig() {
        try {
            singleLand = BiomeBank.valueOf(TConfig.c.BIOME_SINGLE_TERRESTRIAL_TYPE.toUpperCase(Locale.ENGLISH));
        }
        catch (IllegalArgumentException e) {
            singleLand = null;
        }
    }

    /**
     * Does not currently work for beach and river.
     */
    public static boolean isBiomeEnabled(@NotNull BiomeBank bank) {

        if (bank.getBiomeWeight() <= 0) {
            return false;
        }

        return switch (bank.getType()) {
            case BEACH, RIVER -> true; // L
            case FLAT -> singleLand == null || singleLand == bank;
        };
    }

    /**
     * Used to get a biomebank from temperature and moisture values.
     */
    public static @NotNull BiomeBank selectBiome(@NotNull BiomeSection section, double temperature, double moisture) {
        Random sectionRand = section.getSectionRandom();

        if (TConfig.c.BIOME_FORCE_RADIUS > 0) {
            CoordPair lowerZoneBound = new CoordPair(
                    (-TConfig.c.BIOME_FORCE_RADIUS) >> BiomeSection.bitshifts,
                    (-TConfig.c.BIOME_FORCE_RADIUS) >> BiomeSection.bitshifts
            );
            CoordPair upperZoneBound = new CoordPair(
                    (TConfig.c.BIOME_FORCE_RADIUS) >> BiomeSection.bitshifts,
                    (TConfig.c.BIOME_FORCE_RADIUS) >> BiomeSection.bitshifts
            );
            if (lowerZoneBound.x() <= section.getX()
                && section.getX() <= upperZoneBound.x()
                && lowerZoneBound.z() <= section.getZ()
                && section.getZ() <= upperZoneBound.z())
            {
                return BiomeBank.valueOf(TConfig.c.BIOME_FORCED_BIOME);
            }
        }
        BiomeType targetType = BiomeType.FLAT;
        BiomeClimate climate = BiomeClimate.selectClimate(temperature, moisture);

        // Force types if they're set.
        switch (targetType) {
            case FLAT -> {
                if (singleLand != null) {
                    return singleLand;
                }
            }
        }

        ArrayList<BiomeBank> contenders = new ArrayList<>();
        for (BiomeBank biome : BiomeBank.values()) {
            //Excludes beaches and rivers
            if (biome.biomeWeight <= 0) {
                continue;
            }
            if (biome.getType() != targetType) {
                continue;
            }
            if (biome.climate == climate) {
                for (int i = 0; i < biome.biomeWeight; i++) {
                    contenders.add(biome);
                }
            }
        }

        Collections.shuffle(contenders, sectionRand);

        if (contenders.isEmpty()) {
            TerraformGeneratorPlugin.logger.info("Defaulted for: "
                                                 + temperature
                                                 + " : "
                                                 + moisture
                                                 + ","
                                                 + climate
                                                 + ":"
                                                 + targetType);
            return switch (targetType) {
                case BEACH -> BiomeBank.valueOf(TConfig.c.BIOME_DEFAULT_BEACH);
                case FLAT -> BiomeBank.valueOf(TConfig.c.BIOME_DEFAULT_FLAT);
                case RIVER -> BiomeBank.valueOf(TConfig.c.BIOME_DEFAULT_RIVER);
            };
        }
        else {
            return contenders.get(0);
        }
    }

    /**
     * @return the cavePop
     */
    public AbstractCavePopulator getCavePop() {
        return cavePop;
    }

    public BiomeType getType() {
        return type;
    }

    /**
     * @return the handler
     */
    public BiomeHandler getHandler() {
        return handler;
    }

    public BiomeClimate getClimate() {
        // TODO Auto-generated method stub
        return climate;
    }

    public int getBiomeWeight() {
        return biomeWeight;
    }

    public boolean isDry() {
        return getType().isDry();
    }
}
