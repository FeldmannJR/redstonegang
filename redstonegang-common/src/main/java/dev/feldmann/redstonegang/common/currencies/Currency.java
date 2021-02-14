package dev.feldmann.redstonegang.common.currencies;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;

public interface Currency<T extends Comparable<T>> {

    public abstract String getMoedaName(int qtd);

    public abstract String getMoedaName();

    public abstract String getColor();

    public abstract String getIdentifier();

    public abstract T get(int playerId);

    public abstract CurrencyMaterial getMaterial();

    public abstract boolean add(int playerId, T qtd);

    public abstract boolean remove(int playerId, T qtd);

    boolean have(int playerId, Integer qtd);

    boolean have(int playerId, Double qtd);

    public default String getValue(int value) {
        return getColor() + value + " " + getMoedaName(value);
    }

    public default TextComponent getErrorMessage() {
        TextComponent t = new TextComponent("Você não tem " + getMoedaName() + " sufuciente!");
        t.setColor(ChatColor.RED);
        return t;
    }

}
