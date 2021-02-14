package dev.feldmann.redstonegang.common.currencies;

public class CurrencyMaterial {

    private int id;
    private byte data;

    public CurrencyMaterial(int id, byte data) {
        this.id = id;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public byte getData() {
        return data;
    }
}
