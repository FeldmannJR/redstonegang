package dev.feldmann.redstonegang.wire.game.base.apis.configurablemap.internal;

import com.google.gson.JsonObject;

public abstract class ConfigurableEntry<T> {


    private String keyName;
    protected T value = null;

    public ConfigurableEntry(String keyName) {
        this.keyName = keyName;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setValue(T t){
        this.value = t;
    }
    public abstract void deserialize(JsonObject s);

    public abstract JsonObject serialize();

    public T getValue() {
        return value;
    }
}
