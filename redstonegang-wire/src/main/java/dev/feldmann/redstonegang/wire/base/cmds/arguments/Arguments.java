package dev.feldmann.redstonegang.wire.base.cmds.arguments;

import java.util.HashMap;

public class Arguments {

    private int offset = 0;

    HashMap<Argument, Object> map = new HashMap<Argument, Object>();
    private String error = null;
    private String[] raw;

    public void setError(String error) {
        this.error = error;
    }

    public void setValue(Argument key, Object value) {
        map.put(key, value);
    }

    public <T> T getValue(Argument<T> arg) {
        if (map.containsKey(arg)) {
            return (T) map.get(arg);
        }
        return null;
    }

    public <T> T get(Argument<T> arg) {
        return getValue(arg);
    }


    public String get(int slot) {
        return raw[slot];
    }

    public int getSize() {
        return raw.length;
    }

    public String getError() {
        return error;
    }

    public int size() {
        return map.size();
    }

    public boolean isPresent(Argument arg) {
        return map.containsKey(arg);
    }

}
