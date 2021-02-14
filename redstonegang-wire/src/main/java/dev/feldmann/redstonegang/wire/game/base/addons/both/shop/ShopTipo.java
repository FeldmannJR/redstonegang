package dev.feldmann.redstonegang.wire.game.base.addons.both.shop;


import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.tipos.*;
import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.tipos.*;

public enum ShopTipo {

    Pagina(ShopPage.class),
    Itens(ShopItemStack.class),
    Coins(ShopCoins.class),
    Titulo(ShopTitulo.class),
    Vip(ShopVIP.class),
    Aleatorio(ShopPacoteAleatorio.class),
    Permission(ShopPermission.class);


    Class<? extends ShopClick> classe;

    ShopTipo(Class<? extends ShopClick> classe) {
        this.classe = classe;
    }

    public static ShopTipo getByObject(ShopClick item) {
        for (ShopTipo t : ShopTipo.values()) {
            if (t.classe == item.getClass()) {
                return t;
            }
        }
        return null;
    }

    public static Class<? extends ShopClick> getClassByName(String s) {
        for (ShopTipo t : ShopTipo.values()) {
            if (t.name().equalsIgnoreCase(s)) {
                return t.classe;
            }
        }
        return null;
    }
}
