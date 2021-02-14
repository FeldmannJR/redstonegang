package dev.feldmann.redstonegang.wire.plugin.events;

import dev.feldmann.redstonegang.common.network.NetworkMessage;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;


public class NetworkMessageEvent extends Event {

    private static HandlerList handler = new HandlerList();

    private NetworkMessage msg;

    public NetworkMessageEvent(NetworkMessage msg) {
        this.msg = msg;
    }

    public String getId() {
        return msg.get(0);
    }

    public String get(int x) {
        return msg.get(x + 1);
    }

    public boolean is(String id) {
        if (msg.has(0)) {
            return msg.get(0).equals(id);
        }
        return false;
    }

    public Integer getInt(int x) {
        return msg.getInt(x + 1).orElse(null);
    }

    public NetworkMessage getMsg() {
        return msg;
    }

    @Override
    public HandlerList getHandlers() {
        return handler;
    }

    public static HandlerList getHandlerList() {
        return handler;
    }
}
