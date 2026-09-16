package org.terraform.structure.small.camp;

import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.terraform.biome.BiomeBank;
import org.terraform.coregen.populatordata.PopulatorDataAbstract;
import org.terraform.data.MegaChunk;
import org.terraform.data.TerraformWorld;
import org.terraform.main.config.TConfig;
import org.terraform.structure.MultiMegaChunkStructurePopulator;
import org.terraform.utils.GenUtils;
import org.terraform.utils.WoodUtils;
import org.terraform.utils.version.Version;

import java.util.EnumSet;
import java.util.Random;
import java.util.Set;

public class AbandonedCampPopulator  extends MultiMegaChunkStructurePopulator {

    private static final Set<BiomeBank> ALLOWED_BIOMES = Set.of(
            BiomeBank.JUNGLE,BiomeBank.SPARSE_JUNGLE,
            BiomeBank.CHERRY_GROVE, BiomeBank.DAPPLED_FOREST,
            BiomeBank.FLOWER_FOREST, BiomeBank.FOREST,
            BiomeBank.MEADOW, BiomeBank.TAIGA,
            BiomeBank.PALE_FOREST,BiomeBank.SAVANNA,
            BiomeBank.SNOWY_TAIGA
    );
    @Override
    public void populate(@NotNull TerraformWorld tw, @NotNull PopulatorDataAbstract data) {
        if (!isEnabled()) {
            return;
        }

        Random random = this.getHashedRandom(tw, data.getChunkX(), data.getChunkZ());
        MegaChunk mc = new MegaChunk(data.getChunkX(), data.getChunkZ());
        for (int[] coords : getCoordsFromMegaChunk(tw, mc)) {
            int x = coords[0];
            int z = coords[1];
            if (x >> 4 != data.getChunkX() || z >> 4 != data.getChunkZ()) {
                continue;
            }
            int height = GenUtils.getHighestGround(data, x, z);
            spawnAbandonedCamp(tw, random, data, x, height, z, tw.getBiomeBank(x, z));
        }
    }

    public void spawnAbandonedCamp(TerraformWorld tw,
                                   @NotNull Random random,
                                   @NotNull PopulatorDataAbstract data,
                                   int x,
                                   int y,
                                   int z,
                                   BiomeBank variant){
        Material fence = WoodUtils.getWoodForBiome(variant, WoodUtils.WoodType.FENCE);

    }
    private boolean rollSpawnRatio(@NotNull TerraformWorld tw, int chunkX, int chunkZ) {
        return GenUtils.chance(tw.getHashedRand(chunkX, chunkZ, 857613244),
                (int) (TConfig.c.STRUCTURES_ABANDONEDCAMP_SPAWNRATIO * 10000),
                10000
        );
    }
    @Override
    public boolean canSpawn(@NotNull TerraformWorld tw, int chunkX, int chunkZ) {
        if (!isEnabled()) {
            return false;
        }

        MegaChunk mc = new MegaChunk(chunkX, chunkZ);
        int[][] allCoords = getCoordsFromMegaChunk(tw, mc);
        for (int[] coords : allCoords) {
            if (coords[0] >> 4 == chunkX && coords[1] >> 4 == chunkZ) {
                EnumSet<BiomeBank> biomes = GenUtils.getBiomesInChunk(tw, chunkX, chunkZ);
                for (BiomeBank b : biomes) {
                    if(!ALLOWED_BIOMES.contains(b)) return false;
                }
                return rollSpawnRatio(tw, chunkX, chunkZ);
            }
        }
        return false;
    }

    @Override
    public int[][] getCoordsFromMegaChunk(@NotNull TerraformWorld tw, @NotNull MegaChunk mc) {
        int num = TConfig.c.STRUCTURES_DESERTWELL_COUNT_PER_MEGACHUNK;
        int[][] coords = new int[num][2];
        for (int i = 0; i < num; i++) {
            coords[i] = mc.getRandomCoords(tw.getHashedRand(mc.getX(), mc.getZ(), 819227 * (1 + i)));
        }
        return coords;
    }

    @Override
    public int[] getNearestFeature(@NotNull TerraformWorld tw, int rawX, int rawZ) {
        MegaChunk mc = new MegaChunk(rawX, 0, rawZ);

        double minDistanceSquared = Integer.MAX_VALUE;
        int[] min = null;
        for (int nx = -1; nx <= 1; nx++) {
            for (int nz = -1; nz <= 1; nz++) {
                for (int[] loc : getCoordsFromMegaChunk(tw, mc)) {
                    double distSqr = Math.pow(loc[0] - rawX, 2) + Math.pow(loc[1] - rawZ, 2);
                    if (distSqr < minDistanceSquared) {
                        minDistanceSquared = distSqr;
                        min = loc;
                    }
                }
            }
        }
        return min;
    }

    @Override
    public boolean isEnabled() {
        return Version.VERSION.isAtLeast(Version.v26_3)
               && TConfig.areStructuresEnabled() && TConfig.c.STRUCTURES_ABANDONEDCAMP_ENABLED;
    }

    @Override
    public @NotNull Random getHashedRandom(@NotNull TerraformWorld world, int chunkX, int chunkZ) {
        return world.getHashedRand(61287433, chunkX, chunkZ);
    }

    @Override
    public int getChunkBufferDistance() {
        return 1;
    }
}
