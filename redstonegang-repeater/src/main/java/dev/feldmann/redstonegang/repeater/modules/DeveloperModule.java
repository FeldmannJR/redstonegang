package dev.feldmann.redstonegang.repeater.modules;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.repeater.events.PlayerLoadEvent;
import net.md_5.bungee.event.EventHandler;

public class DeveloperModule extends Module {

    @EventHandler
    public void login(PlayerLoadEvent event) {
        User player = event.getPlayer();
        if (RedstoneGang.instance().DEV && !RedstoneGang.instance().ENVIRONMENT.equals("local")) {
            if (!player.permissions().hasPermission("redstonegang.developer")) {
                event.deny("§c§lServidor em desenvolvimento!");
            }
        }
    }
}
