package dev.feldmann.redstonegang.wire.game.base.apis.configurablemap.internal;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;

import java.util.UUID;

public class ConfigurableNPC extends ConfigurableEntry<NPC> {


    UUID uid;

    public ConfigurableNPC(String keyName) {
        super(keyName);
    }

    @Override
    public void deserialize(JsonObject s) {
        if (s != null) {
            if (s.has("uuid")) {
                JsonElement el = s.get("uuid");
                if (el.getAsString() != null) {
                    uid = UUID.fromString(el.getAsString());
                    NPC npc = CitizensAPI.getNPCRegistry().getByUniqueIdGlobal(UUID.fromString(el.getAsString()));
                    this.value = npc;
                }
            }
        }
    }

    @Override
    public NPC getValue() {
        if (uid != null)
            return CitizensAPI.getNPCRegistry().getByUniqueId(uid);
        return null;
    }

    @Override
    public void setValue(NPC npc) {
        super.setValue(npc);
        this.uid = npc.getUniqueId();
    }

    @Override
    public JsonObject serialize() {
        if (getValue() == null) {
            return null;
        }
        JsonObject obj = new JsonObject();
        if (getValue() != null) {
            obj.addProperty("uuid", getValue().getUniqueId().toString());
        }
        return obj;
    }
}
