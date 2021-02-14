package dev.feldmann.redstonegang.wire.game.base.addons.server.land.listener;

import dev.feldmann.redstonegang.wire.game.base.objects.BaseListener;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.Land;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.LandAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.properties.PlayerProperty;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class TerrenoListener extends BaseListener {
    private LandAddon manager;

    public TerrenoListener(LandAddon manager) {
        this.manager = manager;
    }

    public boolean byPass(Player p) {
        return p.hasPermission(LandAddon.TERRENOS_ADMIN) && manager.getUser(p).getConfig(manager.BYPASS_CONFIG);
    }

    public boolean byPassWild(Player p) {
        return p.hasPermission(LandAddon.TERRENOS_WILD) && manager.getUser(p).getConfig(manager.BYPASS_WILD_CONFIG);
    }

    public Land getTerreno(Entity entity) {
        return getTerreno(entity.getLocation());
    }

    public Land getTerreno(Block b) {
        return getTerreno(b.getLocation());
    }

    public Land getTerreno(Location t) {
        return manager.getTerreno(t);
    }

    public boolean can(Player p, Block b, PlayerProperty prop, boolean without) {
        return can(p, b.getLocation(), prop, without);
    }

    public boolean can(Player p, Entity ent, PlayerProperty prop, boolean without) {
        return can(p, ent.getLocation(), prop, without);
    }

    public boolean can(Player p, Location t, PlayerProperty prop, boolean without) {
        Land ter = getTerreno(t);
        if (ter != null) {
            return can(p, ter, prop);
        }
        return without;
    }

    public boolean can(Player p, Land t, PlayerProperty prop) {
        return manager.can(p, t, prop);
    }

    public void sendDeny(Player p) {
        sendDeny(p, "construir");
    }

    public void sendDeny(Player p, String action) {
        C.error(p, "Você não pode " + action + " aqui!");
    }

    public LandAddon manager() {
        return manager;
    }
}

