package org.terraform.coregen.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.WorldUnloadEvent;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.terraform.data.SimpleChunkLocation;
import org.terraform.main.TConfig;
import org.terraform.main.TerraformGeneratorPlugin;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class NativeGeneratorPatcherPopulator extends BlockPopulator implements Listener {

    // SimpleChunkLocation to a collection of location:blockdata entries marked for repair.
    private static final @NotNull Map<SimpleChunkLocation, Collection<ChunkBlockData>> cache = new ConcurrentHashMap<>();
    private static boolean flushIsQueued = false;

    public NativeGeneratorPatcherPopulator() {
        Bukkit.getPluginManager().registerEvents(this, TerraformGeneratorPlugin.get());
    }

    public static void pushChange(String world, int x, int y, int z, BlockData data) {
        if (!flushIsQueued && cache.size() > TConfig.c.DEVSTUFF_FLUSH_PATCHER_CACHE_FREQUENCY) {
            flushIsQueued = true;
            new BukkitRunnable() {
                @Override
                public void run() {
                    flushChanges();
                    flushIsQueued = false;
                }
            }.runTask(TerraformGeneratorPlugin.get());
        }

        SimpleChunkLocation scl = new SimpleChunkLocation(world, x, y, z);
        Collection<ChunkBlockData> cached = cache.getOrDefault(scl, new ArrayList<>());
        cached.add(new ChunkBlockData(x, y, z, data));
        cache.put(scl, cached);
    }

    public static void flushChanges() {
        if (cache.isEmpty())
            return;

        TerraformGeneratorPlugin.logger.info("[NativeGeneratorPatcher] Flushing repairs ("
                                             + cache.size()
                                             + " chunks), pushed by cache size");
        ArrayList<SimpleChunkLocation> locs = new ArrayList<>(cache.keySet());
        for (SimpleChunkLocation scl : locs) {
            World w = Bukkit.getWorld(scl.getWorld());
            if (w == null) {
                continue;
            }
            if (w.isChunkLoaded(scl.getX(), scl.getZ())) {
                repairChunk(w, scl);
            } else {
                // Let the event handler do it
                // TerraformGeneratorPlugin.logger.info("[NativeGeneratorPatcher]   - Loading a chunk to flush changes...");
                w.loadChunk(scl.getX(), scl.getZ());
            }
        }
    }

    @Override
    public void populate(@NotNull World world, @NotNull Random random, @NotNull Chunk chunk) {
        repairChunk(world, new SimpleChunkLocation(chunk));
    }

    @EventHandler
    public void onChunkLoad(@NotNull ChunkLoadEvent event) {
        repairChunk(event.getChunk().getWorld(), new SimpleChunkLocation(event.getChunk()));
    }

    private static void repairChunk(World world, SimpleChunkLocation scl) {
        Collection<ChunkBlockData> changes = cache.remove(scl);
        if (changes != null) {
            // TerraformGeneratorPlugin.logger.info("[NativeGeneratorPatcher] Flushing repairs for 1 chunk (" + scl.getX() + "," + scl.getZ() + "), pushed by chunkloadevent");
            for (ChunkBlockData entry : changes) {
                world.getBlockAt(entry.x, entry.y, entry.z).setBlockData(entry.data, false);
            }
        }
    }

    @EventHandler
    public void onWorldUnload(@NotNull WorldUnloadEvent event) {
        TerraformGeneratorPlugin.logger.info("[NativeGeneratorPatcher] Flushing repairs for "
                                             + event.getWorld()
                                                    .getName()
                                             + " ("
                                             + cache.size()
                                             + " chunks in cache), triggered by world unload");

        int processed = 0, size = cache.size();
        for (SimpleChunkLocation scl : Set.copyOf(cache.keySet())) {
            if (!scl.getWorld().equals(event.getWorld().getName())) {
                continue;
            }

            Collection<ChunkBlockData> changes = cache.remove(scl);
            if (changes != null) {
                for (ChunkBlockData entry : changes) {
                    event.getWorld().getBlockAt(entry.x, entry.y, entry.z).setBlockData(entry.data, false);
                }
            }

            processed++;
            if (processed % 20 == 0) {
                TerraformGeneratorPlugin.logger.info("[NativeGeneratorPatcher] Processed "
                                                     + processed
                                                     + "/"
                                                     + size
                                                     + " chunks");
            }
        }
    }

    public record ChunkBlockData(int x, int y, int z, BlockData data) {
    }

}
