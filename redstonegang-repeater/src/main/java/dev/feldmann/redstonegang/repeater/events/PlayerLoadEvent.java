package dev.feldmann.redstonegang.repeater.events;

import dev.feldmann.redstonegang.common.player.User;
import net.md_5.bungee.api.Callback;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.AsyncEvent;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Cancellable;
import net.md_5.bungee.api.plugin.Event;

/**
 * Executado após registrar/carregar um player do banco
 * Executado async
 */
public class PlayerLoadEvent extends Event implements Cancellable {

    private LoginEvent baseEvent;
    private User player;

    public PlayerLoadEvent(User player, LoginEvent event) {
        this.baseEvent = event;
        this.player = player;
    }

    public User getPlayer() {
        return player;
    }

    public void deny(String message) {
        deny(TextComponent.fromLegacyText(message));
    }

    public void deny(BaseComponent... message) {
        setCancelled(true);
        baseEvent.setCancelReason(message);
    }

    public LoginEvent getBaseEvent() {
        return baseEvent;
    }

    @Override
    public boolean isCancelled() {
        return baseEvent.isCancelled();
    }

    @Override
    public void setCancelled(boolean cancel) {
        baseEvent.setCancelled(cancel);
    }
}
