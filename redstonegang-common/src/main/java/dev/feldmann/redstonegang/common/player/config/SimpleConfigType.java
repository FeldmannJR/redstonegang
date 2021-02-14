package dev.feldmann.redstonegang.common.player.config;

public abstract class SimpleConfigType<D> extends ConfigType<D, D> {
    public SimpleConfigType(String name, D defaultValue) {
        super(name, defaultValue);
    }

    @Override
    public D convert(D v) {
        return v;
    }

    @Override
    public D convertToDb(D v) {
        return v;
    }


}
