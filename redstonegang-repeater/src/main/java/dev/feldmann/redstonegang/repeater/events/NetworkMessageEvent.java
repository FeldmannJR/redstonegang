package dev.feldmann.redstonegang.repeater.events;

import dev.feldmann.redstonegang.common.network.NetworkMessage;
import net.md_5.bungee.api.plugin.Event;


public class NetworkMessageEvent extends Event {


    private NetworkMessage msg;

    public NetworkMessageEvent(NetworkMessage msg) {
        this.msg = msg;
    }

    public boolean is(String id) {
        if (msg.has(0)) {
            if (msg.get(0).equals(id)) {
                return true;
            }
        }
        return false;
    }

    public String getId() {
        return msg.get(0);
    }

    public int getLength() {
        return msg.getLength() - 1;
    }


    public String get(int x) {
        return msg.get(x + 1);
    }

    public Integer getInt(int x) {
        return msg.getInt(x + 1).orElse(null);
    }

    public NetworkMessage getMsg() {
        return msg;
    }

}
