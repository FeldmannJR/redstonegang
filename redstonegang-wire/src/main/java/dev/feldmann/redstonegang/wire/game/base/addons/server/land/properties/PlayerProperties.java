package dev.feldmann.redstonegang.wire.game.base.addons.server.land.properties;

import dev.feldmann.redstonegang.common.player.permissions.PermissionValue;

import dev.feldmann.redstonegang.wire.game.base.addons.server.land.LandAddon;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;

public class PlayerProperties {

    public static final boolean PLAYER = true;
    public static final boolean TERRENO = false;

    private HashMap<PlayerProperty, PermissionValue> values = new HashMap();

    int pId;
    int owner_id;
    boolean type;
    private LandAddon manager;

    public PlayerProperties(LandAddon manager, int pId, int owner_id, boolean type) {
        this.pId = pId;
        this.owner_id = owner_id;
        this.type = type;
        for (PlayerProperty prop : PlayerProperty.values()) {
            values.put(prop, PermissionValue.ALLOW);
        }
        this.manager = manager;
    }

    public static PlayerProperties fromJson(LandAddon manager, int pid, boolean type, int owner_id, String json) {
        JsonObject obj = new JsonParser().parse(json).getAsJsonObject();
        PlayerProperties props = new PlayerProperties(manager,pid, owner_id, type);
        for (PlayerProperty tp : PlayerProperty.values()) {
            if (obj.has(tp.name())) {
                try {
                    props.values.put(tp, PermissionValue.valueOf(obj.get(tp.name()).getAsString()));
                } catch (IllegalArgumentException ex) {

                }
            }
        }
        return props;

    }

    public String toJson() {
        JsonObject obj = new JsonObject();
        for (PlayerProperty property : values.keySet()) {
            obj.addProperty(property.name(), values.get(property).name());
        }
        return obj.toString();
    }

    public PermissionValue get(PlayerProperty tp) {
        if (!values.containsKey(tp)) {
            return PermissionValue.NONE;
        }
        return values.get(tp);
    }

    public boolean getType() {
        return type;
    }

    public int getPlayerId() {
        return pId;
    }

    public int getOwnerId() {
        return owner_id;
    }

    public void set(PlayerProperty prop, PermissionValue value) {
        if (value == PermissionValue.NONE) {
            values.remove(prop);
        } else {
            values.put(prop, value);
        }
        manager.getDB().savePlayerProperty(this);
    }
}
