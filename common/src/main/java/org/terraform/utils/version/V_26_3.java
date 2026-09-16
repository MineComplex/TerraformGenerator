package org.terraform.utils.version;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.PointedDripstone;
import org.jetbrains.annotations.NotNull;
import org.terraform.data.SimpleBlock;
import org.terraform.main.config.TConfig;

/**
 * You're almost certainly going to have to drop old version support in 26.2 to
 * accommodate API breakages.
 */
public class V_26_3 {
    public static Material POPLAR_WOOD = Version.VERSION.isAtLeast(Version.v26_3) ?
                                         Material.valueOf("POPLAR_WOOD") : Material.BIRCH_WOOD;
    public static Material POPLAR_LOG = Version.VERSION.isAtLeast(Version.v26_3) ?
                                        Material.valueOf("POPLAR_LOG") : Material.BIRCH_LOG;
    public static Material RED_SHRUB = Version.VERSION.isAtLeast(Version.v26_3) ?
                                        Material.valueOf("RED_SHRUB") : Material.GRASS;
    public static Material RED_POPLAR_LEAVES = Version.VERSION.isAtLeast(Version.v26_3) ?
                                       Material.valueOf("RED_POPLAR_LEAVES") : Material.BIRCH_LEAVES;
    public static Material ORANGE_POPLAR_LEAVES = Version.VERSION.isAtLeast(Version.v26_3) ?
                                       Material.valueOf("ORANGE_POPLAR_LEAVES") : Material.BIRCH_LEAVES;
    public static Material YELLOW_POPLAR_LEAVES = Version.VERSION.isAtLeast(Version.v26_3) ?
                                       Material.valueOf("YELLOW_POPLAR_LEAVES") : Material.BIRCH_LEAVES;
    //Cocoa's blockdata also implements Directional and Ageable, so use that as a substitute
    public static Material SHELF_MUSHROOM = Version.VERSION.isAtLeast(Version.v26_3) ?
                                       Material.valueOf("SHELF_MUSHROOM") : Material.COCOA;
    public static Biome DAPPLED_FOREST = Version.VERSION.isAtLeast(Version.v26_3) ?
                                       Biome.valueOf("DAPPLED_FOREST") : Biome.BIRCH_FOREST;

}
