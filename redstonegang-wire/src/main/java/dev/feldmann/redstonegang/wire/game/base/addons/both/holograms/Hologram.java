/*
 *  _   __   _   _____   _____       ___       ___  ___   _____
 * | | |  \ | | /  ___/ |_   _|     /   |     /   |/   | /  ___|
 * | | |   \| | | |___    | |      / /| |    / /|   /| | | |
 * | | | |\   | \___  \   | |     / / | |   / / |__/ | | | |
 * | | | | \  |  ___| |   | |    / /  | |  / /       | | | |___
 * |_| |_|  \_| /_____/   |_|   /_/   |_| /_/        |_| \_____|
 *
 * Projeto feito por Carlos Andre Feldmann Junior, Isaias Finger e Gabriel Augusto Souza
 */
package dev.feldmann.redstonegang.wire.game.base.addons.both.holograms;

import dev.feldmann.redstonegang.wire.modulos.holos.HoloAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Skype: junior.feldmann GitHub: https://github.com/feldmannjr Facebook:
 * https://www.facebook.com/carlosandre.feldmannjunior
 *
 * @author Feldmann
 */
public class Hologram {

    String[] lines;
    Location loc;
    HoloAPI api;
    DisplayType type;
    List<Player> viewers = new ArrayList();


    public Hologram(String[] lines, Location loc, DisplayType type) {
        this.lines = lines;
        this.loc = loc;
        api = new HoloAPI(loc, lines);
        this.type = type;
    }

    public void create() {
        Collection<? extends Player> praquem;
        if (type == DisplayType.ALL) {
            praquem = Bukkit.getOnlinePlayers();
        } else {
            praquem = viewers;
        }
        for (Player p : praquem) {
            if (canSee(p.getLocation())) {
                api.display(p);
            }
        }
    }

    public void delete() {
        Collection<? extends Player> praquem;
        if (type == DisplayType.ALL) {
            praquem = Bukkit.getOnlinePlayers();
        } else {
            praquem = viewers;
        }
        for (Player p : praquem) {
            api.destroy(p);
        }
    }

    public boolean canSee(Location p) {
        if (p.getWorld() == loc.getWorld()) {
            return p.distance(loc) < 32;
        }
        return false;
    }

    public enum DisplayType {
        ALL,
        PER_PLAYER
    }
}
