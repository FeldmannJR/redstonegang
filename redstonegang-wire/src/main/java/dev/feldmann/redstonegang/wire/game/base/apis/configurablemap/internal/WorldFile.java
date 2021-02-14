package dev.feldmann.redstonegang.wire.game.base.apis.configurablemap.internal;

import dev.feldmann.redstonegang.wire.game.base.apis.configurablemap.ConfigurableMapAPI;
import dev.feldmann.redstonegang.wire.utils.json.RGson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.*;
import java.util.Map;
import java.util.Set;

public class WorldFile {

    File f;

    ConfigurableMapAPI api;


    public WorldFile(File f, ConfigurableMapAPI api) {
        this.api = api;
        this.f = f;
    }

    public void set(ConfigurableEntry entr) throws IOException {
        JsonElement el = RGson.parse(f);
        JsonObject obj;
        if (el == null || !el.isJsonObject()) {
            obj = new JsonObject();
        } else {
            obj = el.getAsJsonObject();
        }

        JsonObject jentry = new JsonObject();
        jentry.addProperty("type", entr.getClass().getSimpleName());
        jentry.add("value", entr.serialize());
        obj.add(entr.getKeyName(), jentry);
        FileWriter writer = new FileWriter(f);
        RGson.gson().toJson(obj, writer);
        writer.close();
    }

    public void load() {
        JsonElement el = RGson.parse(f);
        JsonObject allKeys;
        if (el == null || !el.isJsonObject()) {
            allKeys = new JsonObject();
        } else {
            allKeys = el.getAsJsonObject();
        }
        Set<Map.Entry<String, JsonElement>> entries = allKeys.entrySet();
        for (Map.Entry<String, JsonElement> entry : entries) {
            JsonObject obj = entry.getValue().getAsJsonObject();
            String key = entry.getKey();
            String type = obj.get("type").getAsString();
            JsonObject value = obj.get("value").getAsJsonObject();
            api.load(type, key, value);
        }
    }

}
