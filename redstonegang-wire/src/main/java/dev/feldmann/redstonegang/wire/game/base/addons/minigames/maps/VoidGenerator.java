package dev.feldmann.redstonegang.wire.game.base.addons.minigames.maps;

import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VoidGenerator extends ChunkGenerator {

    public List<BlockPopulator> getDefaultPopulators(World world) {
        ArrayList<BlockPopulator> populators = new ArrayList();
        return populators;
    }

    public ChunkGenerator.ChunkData generateChunkData(World world, Random random, int ChunkX, int ChunkZ, ChunkGenerator.BiomeGrid biome) {
        ChunkGenerator.ChunkData data = createChunkData(world);
        return data;
    }

}
