package dev.feldmann.redstonegang.wire.game.base.addons.both.simplecmds;

import dev.feldmann.redstonegang.common.player.permissions.PermissionDescription;
import dev.feldmann.redstonegang.common.utils.ObjectLoader;
import dev.feldmann.redstonegang.wire.game.base.database.addons.tables.records.SimplecmdsWarpsRecord;
import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import dev.feldmann.redstonegang.wire.utils.location.BungeeLocation;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

import java.util.*;
import java.util.jar.JarEntry;

public class SimpleCmds extends Addon {

    SimpleCmdsDatabase db;
    List<Class<? extends SimpleCmd>> disabled = new ArrayList<>();

    @SafeVarargs
    public SimpleCmds(String database, Class<? extends SimpleCmd>... disabled) {
        this.db = new SimpleCmdsDatabase(database);
        if (disabled != null)
            this.disabled.addAll(Arrays.asList(disabled));
    }

    @Override
    public void onEnable() {
        List<SimpleCmd> cmds = ObjectLoader.load("dev.feldmann.redstonegang.wire.game.base.addons.both.simplecmds.cmds", SimpleCmd.class, this::filter);
        for (SimpleCmd cmd : cmds) {
            if (disabled.contains(cmd.getClass())) {
                continue;
            }
            cmd.setAddon(this);
            registerCommand(cmd);
        }
        db.loadAllWarps();
        HashMap<String, SimplecmdsWarpsRecord> warps = db.warps;
        for (SimplecmdsWarpsRecord warp : warps.values()) {
            if (!warp.getPublic()) {
                addOption(new PermissionDescription("Warp " + warp.getName(), generateWarpPermission(warp.getName()), "pode usar o warp " + warp.getName()));
            }
        }

    }

    public String generateWarpPermission(String warp) {
        return generateConfigName("warp." + warp);
    }

    public boolean hasWarpPermission(CommandSender sender, String warp) {
        return sender.hasPermission(generateWarpPermission(warp));
    }

    public Collection<SimplecmdsWarpsRecord> getWarps() {
        return db.warps.values();
    }

    public void deleteWarp(String warp) {
        db.deleteWarp(warp);
    }

    public BungeeLocation getSpawn() {
        return db.getLocation("spawn");
    }

    public void setSpawn(Location loc) {
        db.setLocation("spawn", BungeeLocation.fromLocation(loc));
    }

    public void setWarp(String nome, Location location, boolean isPublic) {
        db.setWarp(nome, BungeeLocation.fromLocation(location), isPublic);
    }

    public SimplecmdsWarpsRecord getWarp(String nome) {
        return db.getWarp(nome);
    }


    public boolean filter(JarEntry entry) {
        String name = entry.getName();
        return !name.contains(".subs.");
    }

    @Override
    public void onDisable() {

    }

    public static String getPermission(String perm) {
        return "rg.commands." + perm;
    }
}
