package dev.feldmann.redstonegang.wire.game.base.events;

import dev.feldmann.redstonegang.wire.game.base.addons.minigames.state.GameState;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameStateChangeEvent extends Event {

    private static HandlerList handlers = new HandlerList();

    private GameState newState;

    public GameStateChangeEvent(GameState gamestate) {
        this.newState = gamestate;
    }

    public GameState getNewState() {
        return newState;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
