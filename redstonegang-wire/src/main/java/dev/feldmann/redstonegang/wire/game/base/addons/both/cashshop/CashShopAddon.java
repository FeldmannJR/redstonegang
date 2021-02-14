package dev.feldmann.redstonegang.wire.game.base.addons.both.cashshop;

import dev.feldmann.redstonegang.common.currencies.Currencies;
import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.ShopAddon;

public class CashShopAddon extends ShopAddon {
    public CashShopAddon(String dbName) {
        super(dbName, "cash_shop", Currencies.CASH);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        registerCommand(new CashShopCmd(this));
    }

    @Override
    public String getIdentifierName() {
        return "cashshop";
    }

    @Override
    public String getCommand() {
        return "loja";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"shop"};
    }

    @Override
    public String getNome() {
        return "Loja de Rubis";
    }
}
