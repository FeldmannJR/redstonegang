package dev.feldmann.redstonegang.wire.game.base.addons.server.floatshop;

import java.sql.Timestamp;

public class ItemTransaction {

    long id = -1;
    int itemId;
    int quantity;
    Timestamp min;
    Timestamp max;


    public ItemTransaction(int itemId, int quantity, Timestamp min, Timestamp max) {
        this.itemId = itemId;
        this.quantity = quantity;
        this.min = min;
        this.max = max;
    }

    public boolean isValid() {
        return System.currentTimeMillis() - max.getTime() < 1000 * 60 * FloatShopAddon.horasDemanda;
    }

}
