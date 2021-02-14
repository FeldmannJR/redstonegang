package dev.feldmann.redstonegang.wire.game.base.addons.server.notificacoes;

import dev.feldmann.redstonegang.wire.utils.json.RGson;
import com.google.gson.JsonObject;

public class Notification {
    int owner;
    NotificationType type;
    JsonObject vars;

    public Notification(int owner, NotificationType type, JsonObject vars) {
        this.owner = owner;
        this.type = type;
        this.vars = vars;
    }

    public Notification(int owner, NotificationType type, Object vars) {
        this.owner = owner;
        this.type = type;
        this.vars = RGson.gson().toJsonTree(vars).getAsJsonObject();
    }


}
