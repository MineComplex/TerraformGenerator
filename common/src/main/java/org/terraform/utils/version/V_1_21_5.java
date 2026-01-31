package org.terraform.utils.version;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.jetbrains.annotations.NotNull;
import org.terraform.coregen.populatordata.PopulatorDataAbstract;
import org.terraform.main.TConfig;
import org.terraform.utils.BlockUtils;

import java.util.Locale;
import java.util.Random;

public class V_1_21_5 {


    private static final BlockData[] WILD_FLOWER_SET = {
            Bukkit.createBlockData("wildflowers[flower_amount=1]"),
            Bukkit.createBlockData("wildflowers[flower_amount=2]"),
            Bukkit.createBlockData("wildflowers[flower_amount=3]"),
            Bukkit.createBlockData("wildflowers[flower_amount=4]")
    };
    private static final BlockData[] LEAF_LITTERS = {
            Bukkit.createBlockData("leaf_litter[segment_amount=1]"),
            Bukkit.createBlockData("leaf_litter[segment_amount=2]"),
            Bukkit.createBlockData("leaf_litter[segment_amount=3]"),
            Bukkit.createBlockData("leaf_litter[segment_amount=4]")
    };
    public static BlockData CREAKING_HEART = Bukkit.createBlockData(
            "minecraft:creaking_heart[creaking_heart_state=awake,natural=true]");
    public static BlockData PALE_HANGING_MOSS = Bukkit.createBlockData("minecraft:pale_hanging_moss[tip=false]");
    public static BlockData PALE_HANGING_MOSS_TIP = Bukkit.createBlockData("minecraft:pale_hanging_moss[tip=true]");

    public static @NotNull BlockData getPinkPetalData(int count) {
        return Bukkit.createBlockData("pink_petals[flower_amount=" + count + ",facing=" + BlockUtils.getDirectBlockFace(
                new Random()).toString().toLowerCase(Locale.ENGLISH) + "]");
    }

    public static void leafLitter(Random random, PopulatorDataAbstract data, int x, int y, int z) {
        if (!TConfig.c.FEATURE_PLANTS_ENABLED) {
            return;
        }

        if (data.getType(x, y, z) != Material.AIR) {
            return;
        }

        Directional d = (Directional) LEAF_LITTERS[random.nextInt(LEAF_LITTERS.length)];
        d.setFacing(BlockUtils.getDirectBlockFace(random));
        data.setBlockData(x, y, z, d);
    }

    public static void wildflowers(Random random, PopulatorDataAbstract data, int x, int y, int z) {
        if (!TConfig.c.FEATURE_PLANTS_ENABLED) {
            return;
        }

        if (data.getType(x, y, z) != Material.AIR) {
            return;
        }

        Directional d = (Directional) WILD_FLOWER_SET[random.nextInt(WILD_FLOWER_SET.length)];
        d.setFacing(BlockUtils.getDirectBlockFace(random));
        data.setBlockData(x, y, z, d);
    }

    public static @NotNull BlockData getActiveSculkShrieker() {
        return Bukkit.createBlockData("minecraft:sculk_shrieker[can_summon=true,shrieking=false,waterlogged=false]");
    }

    public static @NotNull BlockData getHangingMangrovePropagule() {
        return Bukkit.createBlockData("minecraft:mangrove_propagule[hanging=true,age=4,waterlogged=false]");
    }
}
