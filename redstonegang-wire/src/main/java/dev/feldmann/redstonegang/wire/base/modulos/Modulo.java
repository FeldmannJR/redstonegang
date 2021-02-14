package dev.feldmann.redstonegang.wire.base.modulos;

import dev.feldmann.redstonegang.wire.Wire;
import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;


public abstract class Modulo {

    public abstract void onEnable();

    public abstract void onDisable();

    public void onLoad() {

    }

    public void register(Listener l) {
        Bukkit.getPluginManager().registerEvents(l, Wire.instance);
    }

    public void register(RedstoneCmd cmd) {
        Wire.instance.cmds().addCommand(cmd);
    }


    public Wire plugin() {
        return Wire.instance;
    }

    public void log(String log) {
        Wire.log(log);
    }

}
