package org.terraform.utils;

import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.terraform.biome.BiomeBank;

import java.util.Objects;

public class WoodUtils {

    public static @NotNull Material getWoodForBiome(@NotNull BiomeBank biome, @NotNull WoodType wood) {
        return switch (biome) {
            case BADLANDS, BADLANDS_RIVER, SAVANNA, DESERT, DESERT_RIVER, BADLANDS_BEACH -> wood.getWood(WoodSpecies.ACACIA);
            case SCARLET_FOREST -> wood.getWood(WoodSpecies.BIRCH);
            case SWAMP, PLAINS, MUDFLATS, RIVER,
                 ERODED_PLAINS, FOREST -> wood.getWood(WoodSpecies.OAK);
            case TAIGA, SNOWY_WASTELAND, SNOWY_TAIGA, ROCKY_BEACH,
                 FROZEN_RIVER, ICY_BEACH, ICE_SPIKES ->
                    wood.getWood(WoodSpecies.SPRUCE);
            case SANDY_BEACH, JUNGLE, JUNGLE_RIVER, BAMBOO_FOREST -> wood.getWood(WoodSpecies.JUNGLE);
            case CHERRY_GROVE, DARK_FOREST, DARK_FOREST_RIVER, DARK_FOREST_BEACH ->
                    wood.getWood(WoodSpecies.DARK_OAK);
            default -> wood.getWood(WoodSpecies.OAK);
        };
    }

    public enum WoodSpecies {
        OAK, SPRUCE, BIRCH, JUNGLE, ACACIA, DARK_OAK
    }

    public enum WoodType {
        PLANKS("%WOOD%_PLANKS"),
        SAPLING("%WOOD%_SAPLING"),
        POTTED_SAPLING("POTTED_%WOOD%_SAPLING"),
        LOG("%WOOD%_LOG"),
        STRIPPED_LOG("STRIPPED_%WOOD%_LOG"),
        WOOD("%WOOD%_WOOD"),
        STRIPPED_WOOD("STRIPPED_%WOOD%_WOOD"),
        LEAVES("%WOOD%_LEAVES"),
        SLAB("%WOOD%_SLAB"),
        PRESSURE_PLATE("%WOOD%_PRESSURE_PLATE"),
        FENCE("%WOOD%_FENCE"),
        TRAPDOOR("%WOOD%_TRAPDOOR"),
        FENCE_GATE("%WOOD%_FENCE_GATE"),
        STAIRS("%WOOD%_STAIRS"),
        BUTTON("%WOOD%_BUTTON"),
        DOOR("%WOOD%_DOOR"),
        SIGN("%WOOD%_SIGN"),
        WALL_SIGN("%WOOD%_WALL_SIGN"),
        BOAT("%WOOD%_BOAT"),
        ;
        final String template;

        WoodType(String template) {
            this.template = template;
        }

        /**
         * Converts an oak material to a WoodType template.
         * Do not use for any other wood.
         * <br><br>
         * The dark oak check is there because schematics only check for the
         * keyword "oak"
         *
         * @param oak material to convert to an WoodType enum
         */
        public static @NotNull WoodType parse(@NotNull Material oak) {
            return WoodType.valueOf(oak.toString().replace("DARK_OAK", "OAK").replace("OAK_", ""));
        }

        // I am the pinnacle of optimisation
        // Fear my absolutely unbeatable timings
        public @NotNull Material getWood(@NotNull WoodSpecies species) {
            //This cannot be null
            return Objects.requireNonNull(Material.getMaterial(template.replace("%WOOD%", species.toString())));
        }
    }

}
