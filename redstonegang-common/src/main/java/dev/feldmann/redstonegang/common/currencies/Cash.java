package dev.feldmann.redstonegang.common.currencies;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.db.money.CurrencyChangeType;

public class Cash implements CurrencyInteger {
    @Override
    public String getMoedaName(int qtd) {
        return qtd == 1 ? "Rubi" : "Rubis";
    }

    @Override
    public String getMoedaName() {
        return "Rubi";
    }

    @Override
    public String getColor() {
        return "§c§l";
    }

    @Override
    public String getIdentifier() {
        return "CASH";
    }

    @Override
    public Integer get(int playerId) {
        return RedstoneGang.getPlayer(playerId).getCash();
    }

    @Override
    public CurrencyMaterial getMaterial() {
        return new CurrencyMaterial(331, (byte) 0);
    }

    @Override
    public boolean add(int playerId, Integer qtd) {
        boolean success = RedstoneGang.getPlayer(playerId).addCash(qtd);
        RedstoneGang.instance().currencies().notifyHooks(playerId, this, CurrencyChangeType.ADD);
        return success;
    }

    @Override
    public boolean remove(int playerId, Integer qtd) {
        boolean success = RedstoneGang.getPlayer(playerId).removeCash(qtd);
        RedstoneGang.instance().currencies().notifyHooks(playerId, this, CurrencyChangeType.REMOVE);
        return success;
    }
}
