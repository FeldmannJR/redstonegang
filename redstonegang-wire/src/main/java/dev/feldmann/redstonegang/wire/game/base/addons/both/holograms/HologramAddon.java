/*
 *  _   __   _   _____   _____       ___       ___  ___   _____
 * | | |  \ | | /  ___/ |_   _|     /   |     /   |/   | /  ___|
 * | | |   \| | | |___    | |      / /| |    / /|   /| | | |
 * | | | |\   | \___  \   | |     / / | |   / / |__/ | | | |
 * | | | | \  |  ___| |   | |    / /  | |  / /       | | | |___
 * |_| |_|  \_| /_____/   |_|   /_/   |_| /_/        |_| \_____|
 *
 * Classe criada por Carlos Andre Feldmann Junior
 * Apoio: Isaias Finger, Gabriel Slomka, Gabriel Augusto Souza
 * Skype: junior.feldmann
 * GitHub: https://github.com/feldmannjr
 * Facebook: https://www.facebook.com/carlosandre.feldmannjunior
 */
package dev.feldmann.redstonegang.wire.game.base.addons.both.holograms;

import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import dev.feldmann.redstonegang.wire.utils.location.LocUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.HashSet;

public class HologramAddon extends Addon {

    public HashSet<Hologram> holos = new HashSet();

    public Hologram createHolo(Location l, String... linhas) {
        if (linhas.length < 1) {
            return null;
        }
        Hologram holo = new Hologram(linhas, l, Hologram.DisplayType.ALL);
        holo.create();
        holos.add(holo);
        return holo;
    }

    @Override
    public void onEnable() {

    }

    @EventHandler
    public void move(PlayerMoveEvent ev) {
        if (!LocUtils.isSameBlock(ev.getFrom(), ev.getTo())) {
            check(ev.getPlayer(), ev.getFrom(), ev.getTo());
        }
    }

    @EventHandler
    public void tp(PlayerTeleportEvent ev) {
        if (!LocUtils.isSameBlock(ev.getFrom(), ev.getTo())) {
            check(ev.getPlayer(), ev.getFrom(), ev.getTo());
        }
    }


    private void check(Player p, Location from, Location to) {
        for (Hologram holo : holos) {
            if (holo.canSee(from) && !holo.canSee(to)) {
                holo.api.destroy(p);
            } else {
                if (!holo.canSee(from) && holo.canSee(to)) {
                    holo.api.display(p);
                }
            }
        }
    }


    public void removeHologram(Hologram holo) {
        if (!holos.contains(holo)) {
            return;
        }
        holo.delete();
        holos.remove(holo);
    }


    public void reset() {
        for (Hologram holo : holos) {
            holo.delete();
        }
        holos.clear();
    }

    @Override
    public void onDisable() {
        reset();
    }


}
