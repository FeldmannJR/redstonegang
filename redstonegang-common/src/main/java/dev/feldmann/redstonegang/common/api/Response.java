package dev.feldmann.redstonegang.common.api;

import dev.feldmann.redstonegang.common.RedstoneGang;
import com.google.gson.Gson;

import java.util.Map;

public class Response<T> {
    private static Gson gson;


    private int code;
    private String json;
    private Class<T> responseClass;
    private boolean success;

    public Response(int code, String json, boolean success) {
        this(null, code, json, success);
    }

    public Response(Class<T> repsonseClass, int code, String json, boolean success) {
        this.code = code;
        this.json = json;
        this.responseClass = repsonseClass;
        this.success = success;
    }


    public T collect() {
        return getGson().fromJson(this.json, responseClass);
    }

    public <V> V collectAnother(Class<V> classe) {
        return getGson().fromJson(this.json, classe);
    }

    public Map collectMap() {
        return getGson().fromJson(json, Map.class);
    }

    private Gson getGson() {
        return RedstoneGang.instance().webapi().getGson();
    }

    public boolean hasFailed() {
        return !success;
    }

    public int getCode() {
        return code;
    }

    public String getJson() {
        return json;
    }
}
