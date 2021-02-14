package dev.feldmann.redstonegang.wire.modulos.menus;

public interface FilterableMultipageMenu<T> {
    boolean filter(T t, String key);
}
