package dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.ferreiro;

import org.bukkit.inventory.ItemStack;

import java.sql.Timestamp;

public class FerreiroItem {

    public static boolean REPARAR = false;
    public static boolean CRIAR = true;

    long id;
    public ItemStack item;
    public boolean type;
    public int playerId;
    private Timestamp readyTime;
    private Timestamp startTime;
    public boolean retirado = false;


    public FerreiroItem(long id, ItemStack item, boolean type, int playerId,Timestamp startTime, Timestamp readyTime) {
        this.id = id;
        this.item = item;
        this.type = type;
        this.playerId = playerId;
        this.readyTime = readyTime;
        this.startTime = startTime;
    }

    public Timestamp getReadyTime() {
        return readyTime;
    }

    public boolean canRemove() {
        return getReadyTime().getTime() < System.currentTimeMillis();
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public ItemStack buildDisplay() {
        ItemStack it = item.clone();
        return it;
    }
}
