package dev.feldmann.redstonegang.wire.game.base.addons.server.land.properties;

import dev.feldmann.redstonegang.wire.game.base.addons.server.land.LandFlag;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.LandFlags;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.LandAddon;
import dev.feldmann.redstonegang.wire.utils.json.RGson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Optional;

public class LandFlagStorage {

    private HashMap<LandFlag, Boolean> values = new HashMap();
    private HashMap<PlayerProperty, Boolean> playerDefaults = new HashMap();
    private HashMap<String, Integer> customIntegers = new HashMap();

    private LandFlags flagManager;


    public LandFlagStorage(LandFlags flagManager) {
        this.flagManager = flagManager;
        for (LandFlag tp : flagManager.all()) {
            values.put(tp, tp.getDefaultValue());
        }
        for (PlayerProperty pp : PlayerProperty.values()) {
            if (pp.isAll()) {
                playerDefaults.put(pp, false);
            }
        }
    }

    public static LandFlagStorage fromJson(LandAddon addon, String json) {
        if (json == null) {
            json = "{}";
        }
        JsonObject obj = new JsonParser().parse(json).getAsJsonObject();
        LandFlagStorage props = new LandFlagStorage(addon.getFlagsManager());
        for (LandFlag tp : addon.getFlagsManager().all()) {
            if (obj.has(tp.getKey())) {
                props.values.put(tp, obj.get(tp.getKey()).getAsBoolean());
            }
        }
        if (obj.has("player")) {
            JsonObject player = obj.get("player").getAsJsonObject();
            for (PlayerProperty pp : PlayerProperty.values()) {
                if (pp.isAll() && player.has(pp.name())) {
                    props.playerDefaults.put(pp, player.get(pp.name()).getAsBoolean());
                }
            }
        }
        if (obj.has("customIntegers")) {
            JsonElement v = obj.get("customIntegers");
            props.customIntegers = RGson.gson().fromJson(v, new TypeToken<HashMap<String, Integer>>() {
            }.getType());
        }
        return props;

    }

    public void set(LandFlag prop, boolean val) {
        values.put(prop, val);
    }

    public void set(PlayerProperty prop, boolean val) {
        playerDefaults.put(prop, val);
    }

    public String toJson() {
        JsonObject obj = new JsonObject();
        for (LandFlag property : values.keySet()) {
            obj.addProperty(property.getKey(), values.get(property));
        }
        JsonObject player = new JsonObject();
        for (PlayerProperty pp : PlayerProperty.values()) {
            if (pp.isAll() && player.has(pp.name())) {
                player.addProperty(pp.name(), playerDefaults.get(pp));
            }
        }
        JsonElement el = RGson.gson().toJsonTree(customIntegers);
        obj.add("customIntegers", el);
        obj.add("player", player);
        return obj.toString();
    }

    public Optional<Integer> getCustomInteger(String key) {
        if (customIntegers.containsKey(key)) {
            return Optional.of(customIntegers.get(key));
        }
        return Optional.empty();
    }

    public void setCustomInteger(String key, int value) {
        customIntegers.put(key, value);
    }

    public Boolean get(PlayerProperty pp) {
        if (playerDefaults.containsKey(pp)) {
            return playerDefaults.get(pp);
        }
        return null;
    }

    public boolean get(LandFlag tp) {
        if (values.containsKey(tp)) {
            return values.get(tp);
        }
        return tp.getDefaultValue();
    }
}
