package dev.feldmann.redstonegang.wire.game.base.addons.server.notificacoes;

import com.google.gson.JsonObject;
import net.md_5.bungee.api.chat.TextComponent;


public interface NotificationType {

    TextComponent[] process(JsonObject obj);

    String getType();

    JsonObject processAdd(JsonObject oldValues, JsonObject newValues);


}
