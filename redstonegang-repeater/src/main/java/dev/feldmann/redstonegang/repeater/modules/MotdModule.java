package dev.feldmann.redstonegang.repeater.modules;

import dev.feldmann.redstonegang.common.RedstoneGang;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.event.EventHandler;


public class MotdModule extends Module {

    @EventHandler
    public void motd(ProxyPingEvent ev) {
        ServerPing response = ev.getResponse();
        String motd = "               &f| &4&lRedstone&f&lGang &r&f|\n            &cwww.redstonegang.com.br";
        if (RedstoneGang.instance().DEV) {
            motd = "               &f| &4&lRedstone&f&lGang |\n         &e&lEM DESENVOLVIMENTO";
        }
        motd = motd.replace("&", "§");
        response.setDescriptionComponent(new TextComponent(TextComponent.fromLegacyText(motd)));

    }

}
