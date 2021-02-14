package dev.feldmann.redstonegang.common.player.config.types;

import dev.feldmann.redstonegang.common.player.config.ConfigType;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jooq.DataType;
import org.jooq.impl.SQLDataType;

public class JsonConfig extends ConfigType<JsonObject, String> {

    JsonParser parser = null;

    public JsonConfig(String name, JsonObject defaultValue) {
        super(name, defaultValue);
    }

    @Override
    public JsonObject convert(String v) {
        JsonElement json = getParser().parse(v);
        if (json == null || !json.isJsonObject()) {
            return getDefault();
        }
        return json.getAsJsonObject();
    }

    protected JsonParser getParser() {
        if (parser == null) {
            parser = new JsonParser();
        }
        return parser;
    }

    @Override
    public String convertToDb(JsonObject v) {
        return v.toString();
    }

    @Override
    protected DataType<String> getDataType() {
        return SQLDataType.CLOB;
    }
}
