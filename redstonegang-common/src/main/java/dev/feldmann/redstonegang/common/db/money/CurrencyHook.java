package dev.feldmann.redstonegang.common.db.money;

import dev.feldmann.redstonegang.common.currencies.Currency;

public interface CurrencyHook {

    public void handleCurrencyChange(int playerId, Currency currency, CurrencyChangeType changeType);
}
