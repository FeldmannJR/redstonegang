package dev.feldmann.redstonegang.wire.game.base.addons.server.quests;

import dev.feldmann.redstonegang.common.currencies.CurrencyInteger;
import dev.feldmann.redstonegang.common.currencies.CurrencyMaterial;
import dev.feldmann.redstonegang.common.db.Database;
import dev.feldmann.redstonegang.common.db.money.CurrencyIntegerDatabase;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.jooq.DSLContext;

public class QuestPoints extends Database implements CurrencyIntegerDatabase, CurrencyInteger {
    public QuestPoints(String database) {
        super(database);
    }

    @Override
    public void createTables() {
        createMoneyTable();
    }

    @Override
    public String getTableName() {
        return "quest_points";
    }

    @Override
    public DSLContext getDsl() {
        return dsl();
    }

    @Override
    public String getMoedaName(int i) {
        return getMoedaName() + (i != 0 ? "s" : "");
    }

    @Override
    public String getMoedaName() {
        return "QuestPoint";
    }

    @Override
    public String getColor() {
        return ChatColor.DARK_AQUA.toString();
    }

    @Override
    public String getIdentifier() {
        return "QUESTPOINT";
    }

    @Override
    public CurrencyMaterial getMaterial() {
        return new CurrencyMaterial(Material.MAP.getId(), (byte) 0);
    }
}
