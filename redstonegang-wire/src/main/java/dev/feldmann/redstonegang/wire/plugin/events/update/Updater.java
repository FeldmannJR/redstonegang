package dev.feldmann.redstonegang.wire.plugin.events.update;

import org.bukkit.Bukkit;

public class Updater implements Runnable {

    @Override
    public void run() {
        for (UpdateType updateType : UpdateType.values()) {
            if (updateType.isElapsed()) {
                Bukkit.getServer().getPluginManager().callEvent(new UpdateEvent(updateType));
            }
        }
    }
}
