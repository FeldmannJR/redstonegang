package dev.feldmann.redstonegang.common.currencies;

public interface CurrencyDouble extends Currency<Double> {

    default boolean have(int playerId, Integer qtd) {
        int c = get(playerId).compareTo(qtd.doubleValue());
        return c >= 0;
    }

    default boolean have(int playerId, Double qtd) {
        return get(playerId).compareTo(qtd) >= 0;
    }
}
