package dev.feldmann.redstonegang.common.db.money;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.currencies.Currency;

import java.util.ArrayList;
import java.util.List;

public class CurrencyManager {
    private List<CurrencyHook> hooks = new ArrayList<>();

    public void addHook(CurrencyHook hook) {
        hooks.add(hook);
    }

    public void notifyHooks(int playerId, Currency currency, CurrencyChangeType type) {
        RedstoneGang.instance().runSync(() -> {
            for (CurrencyHook hook : hooks) {
                try {
                    hook.handleCurrencyChange(playerId, currency, type);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

}
