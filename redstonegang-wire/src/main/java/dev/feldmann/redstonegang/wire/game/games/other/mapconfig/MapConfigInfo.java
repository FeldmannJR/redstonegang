package dev.feldmann.redstonegang.wire.game.games.other.mapconfig;

import dev.feldmann.redstonegang.wire.game.base.addons.both.npcs.RedstoneNPC;
import dev.feldmann.redstonegang.wire.game.base.objects.maps.MapConfigEntry;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;


public class MapConfigInfo {

    public Player p;
    public Location loc;
    public Vector block;
    public Vector rg1 = null;
    public Vector rg2 = null;
    public RedstoneNPC locnpc;
    public String editandoConfig = null;

    public MapConfigEntry seq;


    public MapConfigInfo(Player p) {
        this.p = p;
    }


}
