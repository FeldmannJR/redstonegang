package dev.feldmann.redstonegang.wire.game.base.addons.server.chestshops;

public class ChestShopNotfication {
    double mCompras;
    double mVendas;
    int vendas;
    int compras;

    public ChestShopNotfication(int compras, double mCompras, double mVendas, int vendas) {
        this.mCompras = mCompras;
        this.mVendas = mVendas;
        this.vendas = vendas;
        this.compras = compras;
    }
}
