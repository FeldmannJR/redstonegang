package dev.feldmann.redstonegang.wire.plugin.events;

import dev.feldmann.redstonegang.common.currencies.Currency;
import dev.feldmann.redstonegang.common.db.money.CurrencyChangeType;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;


public class PlayerCurrencyChangeEvent extends Event {

    private static HandlerList handlers = new HandlerList();


    private int playerId;
    private Currency currency;
    private CurrencyChangeType type;

    public PlayerCurrencyChangeEvent(int playerId, Currency currency, CurrencyChangeType type) {
        this.playerId = playerId;
        this.currency = currency;
        this.type = type;
    }

    public CurrencyChangeType getType() {
        return type;
    }

    public Currency getCurrency() {
        return currency;
    }


    public int getPlayerId() {
        return playerId;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }


}
