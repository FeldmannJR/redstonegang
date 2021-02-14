package dev.feldmann.redstonegang.wire.game.base.addons.server;

import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.common.player.permissions.PermissionDescription;
import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.SignChangeEvent;

public class SignColorAddon extends Addon {

    private PermissionDescription USE_PERMISSION;

    @Override
    public void onEnable() {
        USE_PERMISSION = new PermissionDescription("Placas Coloridas", generatePermission("use"), "Usar cores em placas");
        addOption(USE_PERMISSION);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void signChangeEVent(SignChangeEvent ev) {
        User user = getUser(ev.getPlayer());
        if (!user.hasPermission(USE_PERMISSION)) {
            return;
        }
        for (int i = 0; i < ev.getLines().length; i++) {
            String line = ev.getLine(i);
            if (line != null) {
                ev.setLine(i, ChatColor.translateAlternateColorCodes('&', line));
            }
        }
    }

}
