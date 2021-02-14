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
package dev.feldmann.redstonegang.wire.game.base.addons.both.damageinfo;


import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

/**
 * @author Carlos
 */
public class PlayerCustomDeathEvent extends PlayerEvent {

    private DeathInfo info;
    private Player killer;
    private Player assist;
    private boolean keepItems = false;


    public PlayerCustomDeathEvent(Player killed, Player killer, Player assist, DeathInfo info) {
        super(killed);
        this.info = info;
        this.killer = killer;
        this.assist = assist;

    }

    public DeathInfo getInfo() {
        return info;
    }

    public Player getKiller() {
        return killer;
    }

    public void setKeepItems(boolean keepItems) {
        this.keepItems = keepItems;
    }

    public boolean isKeepItems() {
        return keepItems;
    }

    private static final HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }


}
