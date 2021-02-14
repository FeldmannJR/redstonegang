package dev.feldmann.redstonegang.wire.base.cmds;

import dev.feldmann.redstonegang.wire.Wire;
import dev.feldmann.redstonegang.wire.base.cmds.defaults.Free;
import dev.feldmann.redstonegang.wire.base.cmds.defaults.cash.CashAdm;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;

import java.lang.reflect.Field;
import java.util.Map;

public class CmdRegistrator {

    private static CommandMap cmap = null;

    private static void carregaComandos() {
        try {
            if (Bukkit.getServer() instanceof CraftServer) {
                final Field f = CraftServer.class.getDeclaredField("commandMap");
                f.setAccessible(true);
                cmap = (CommandMap) f.get(Bukkit.getServer());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void addCommand(RedstoneCmd cmd) {
        if (cmap == null) {
            carregaComandos();
        }
        if (cmd.canOverride()) {
            unregisterCommand(cmd);
        }
        cmap.register("wire", cmd);
        cmd.setExecutor(Wire.instance);
    }

    public void unregisterCommand(RedstoneCmd cmd) {
        Command existe = cmap.getCommand(cmd.getName());
        if (existe != null) {
            existe.unregister(cmap);
            try {
                final Field f = SimpleCommandMap.class.getDeclaredField("knownCommands");
                f.setAccessible(true);
                Map<String, Command> known = (Map<String, Command>) f.get(cmap);
                known.remove(cmd.getLabel().toLowerCase());
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }

    public void registerDefaults() {
        addCommand(new Free());
        addCommand(new CashAdm());
    }
}
