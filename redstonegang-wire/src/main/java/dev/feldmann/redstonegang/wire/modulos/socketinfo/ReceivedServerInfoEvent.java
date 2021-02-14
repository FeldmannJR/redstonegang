package dev.feldmann.redstonegang.wire.modulos.socketinfo;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ReceivedServerInfoEvent extends Event {
    private static HandlerList handlerList = new HandlerList();

    private ServerInfo info;

    public ReceivedServerInfoEvent(ServerInfo info) {
        this.info = info;
    }

    public ServerInfo getInfo() {
        return info;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }


}
