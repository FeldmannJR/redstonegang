package dev.feldmann.redstonegang.wire.utils.world;

import dev.feldmann.redstonegang.common.utils.FileUtils;
import dev.feldmann.redstonegang.wire.utils.hitboxes.Hitbox2D;
import dev.feldmann.redstonegang.wire.utils.hitboxes.Vector2;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class WorldUtils {
    private static int CHUNK_SHIFTS = 4;

    public static Set<Vector2> getChunks(Hitbox2D hit) {
        Set<Vector2> chunks = new HashSet<>();
        for (int x = hit.getMinX() >> CHUNK_SHIFTS; x <= hit.getMaxX() >> CHUNK_SHIFTS; ++x) {
            for (int z = hit.getMinY() >> CHUNK_SHIFTS; z <= hit.getMaxY() >> CHUNK_SHIFTS; ++z) {
                chunks.add(new Vector2(x, z));
            }
        }

        return chunks;
    }

    private static int getMaxY() {
        return Bukkit.getWorlds().get(0).getMaxHeight() - 1;
    }

    public static boolean regenerate(Hitbox2D hitbox, World w) {

        BlockSnapshot[] history = new BlockSnapshot[16 * 16 * (getMaxY() + 1)];

        for (Vector2 chunk : getChunks(hitbox)) {
            Vector min = new Vector(chunk.getX() * 16, 0, chunk.getY() * 16);

            // First save all the blocks inside
            for (int x = 0; x < 16; ++x) {
                for (int y = 0; y < (getMaxY() + 1); ++y) {
                    for (int z = 0; z < 16; ++z) {
                        Vector pt = min.clone().add(new Vector(x, y, z));
                        int index = y * 16 * 16 + z * 16 + x;
                        history[index] = BlockSnapshot.getSnapshot(w.getBlockAt(pt.getBlockX(), pt.getBlockY(), pt.getBlockZ()));

                    }
                }
            }

            w.regenerateChunk(chunk.getX(), chunk.getY());


            // Then restore
            for (int x = 0; x < 16; ++x) {
                for (int y = 0; y < getMaxY() + 1; ++y) {
                    for (int z = 0; z < 16; ++z) {
                        Vector pt = min.clone().add(new Vector(x, y, z));
                        int index = y * 16 * 16 + z * 16 + x;

                        // We have to restore the block if it was outside
                        if (!hitbox.isInside(pt.getBlockX(), pt.getBlockZ())) {
                            Block b = w.getBlockAt(pt.getBlockX(), pt.getBlockY(), pt.getBlockZ());
                            BlockSnapshot old = history[index];
                            old.apply(b);
                        }
                    }
                }
            }
        }

        return true;
    }

    public static void removePlayerFiles() {
        File world = Bukkit.getWorldContainer();
        for (World w : Bukkit.getWorlds()) {
            File worldFolder = new File(world, w.getName());
            if (worldFolder.isDirectory()) {
                for (String d : new String[]{"playerdata"}) {
                    File f = new File(worldFolder, d);
                    if (f.exists() && f.isDirectory()) {
                        FileUtils.deleteDirectory(f);
                    }
                    f.mkdir();
                }

            }
        }
    }


}
