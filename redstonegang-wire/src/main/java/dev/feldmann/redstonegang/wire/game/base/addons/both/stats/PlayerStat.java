package dev.feldmann.redstonegang.wire.game.base.addons.both.stats;

import org.bukkit.inventory.ItemStack;
import org.jooq.Field;
import org.jooq.OrderField;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

import java.util.HashMap;

public class PlayerStat {

    private StatAddon addon;

    public int topQuantity = 100;

    private String uniqueId;
    private ItemStack display;
    private HashMap<Integer, Long> top;

    protected boolean columnCreated = false;

    public PlayerStat(String uniqueId, ItemStack display) {
        this.uniqueId = uniqueId;
        this.display = display;
    }

    public String getUniqueId() {
        return uniqueId;
    }


    public OrderField order(Field<Integer> key) {
        return getField().desc();
    }

    public void initialize(StatAddon addon) {
        this.addon = addon;
    }


    public Field<Long> getField() {
        return DSL.field(DSL.name("st_" + uniqueId), SQLDataType.BIGINT.defaultValue(0l));
    }

    public void addPoints(int pId, long points) {
        addon.getDb().addPontos(pId, this, points);
        addon.getCache(pId).addValue(this, points);
    }

    public long getPoints(int pId) {
        return addon.getCache(pId).getValue(this);
    }

    public void setTop(HashMap<Integer, Long> top) {
        this.top = top;
    }

    public void reloadTop() {
        //addon.getDb().loadTop(this, topQuantity);
    }
}
