package dev.feldmann.redstonegang.common.currencies;

public interface CurrencyInteger extends Currency<Integer> {
    default boolean have(int playerId, Integer qtd) {
        return get(playerId).compareTo(qtd) >= 0;
    }

    default boolean have(int playerId, Double qtd) {
        return get(playerId).compareTo(qtd.intValue()) >= 0;
    }
}
