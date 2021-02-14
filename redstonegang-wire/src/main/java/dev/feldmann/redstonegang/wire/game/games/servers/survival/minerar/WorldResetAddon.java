package dev.feldmann.redstonegang.wire.game.games.servers.survival.minerar;

import dev.feldmann.redstonegang.common.utils.FileUtils;
import dev.feldmann.redstonegang.common.utils.RandomUtils;
import dev.feldmann.redstonegang.wire.Wire;
import dev.feldmann.redstonegang.wire.game.base.objects.Addon;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WorldResetAddon extends Addon {

    private String worldName;

    public WorldResetAddon(String worldName) {
        this.worldName = worldName;
    }

    @Override
    public void onStart() {
        for (World w : Bukkit.getWorlds()) {
            w.setAutoSave(false);
        }
        File f = getWorldBackups();
        if (!f.exists()) {
            f.mkdir();
        }
        copyRandomWorld();
        loadWorld();
    }

    public void loadWorld() {
        World w = new WorldCreator(worldName)
                .createWorld();
        w.setDifficulty(Difficulty.HARD);
        w.setAutoSave(false);
        w.setKeepSpawnInMemory(false);
        Wire.log("World created!");
    }

    public void copyRandomWorld() {
        File worldFile = getWorldFolder();
        if (worldFile.exists()) worldFile.delete();
        worldFile.mkdir();
        File random = findRandomWorld(getWorldBackups());
        if (random != null) {
            try {
                Wire.log("Copying world " + random.getName() + " ...");
                FileUtils.copy(random, worldFile);
                Wire.log("World copied");
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        getServer().setInvalid(true);
        return;

    }

    public File getWorldFolder() {
        return new File(Bukkit.getWorldContainer(), worldName);
    }

    public File findRandomWorld(File folder) {
        if (!folder.isDirectory()) {
            return null;
        }
        List<File> valid = new ArrayList<>();
        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                for (File worldFile : file.listFiles()) {
                    if (worldFile.getName().equals("level.dat")) {
                        valid.add(file);
                        break;
                    }
                }
            }
        }
        return RandomUtils.getRandom(valid);
    }

    public File getWorldBackups() {
        return new File("./available-worlds/");
    }

}
