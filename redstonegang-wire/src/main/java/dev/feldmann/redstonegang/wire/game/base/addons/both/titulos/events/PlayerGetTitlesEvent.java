package dev.feldmann.redstonegang.wire.game.base.addons.both.titulos.events;

import dev.feldmann.redstonegang.wire.game.base.addons.both.titulos.Titulo;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.HashMap;

public class PlayerGetTitlesEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private int pid;
    private HashMap<String, Titulo> titulos;

    public PlayerGetTitlesEvent(int pid, HashMap<String, Titulo> titulos) {
        this.pid = pid;
        this.titulos = titulos;
    }


    public void addTitle(Titulo t) {
        this.titulos.put(t.getTitulo(), t);
    }

    public int getOwner() {
        return pid;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
