package dev.feldmann.redstonegang.repeater.modules;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.repeater.RedstoneGangBungee;
import dev.feldmann.redstonegang.repeater.Repeater;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.event.AsyncEvent;
import net.md_5.bungee.api.plugin.Listener;

public class Module implements Listener {


    public Repeater plugin() {
        return Repeater.getInstance();
    }

    public RedstoneGang redstonegang() {
        return RedstoneGangBungee.instance();
    }

    public void complete(AsyncEvent ev) {
        ev.completeIntent(plugin());
    }

    public void register(AsyncEvent ev) {
        ev.registerIntent(plugin());
    }
    public ProxyServer proxy(){
        return plugin().getProxy();
    }

    public void onDisable() {

    }

    public void onEnable() {

    }

}
