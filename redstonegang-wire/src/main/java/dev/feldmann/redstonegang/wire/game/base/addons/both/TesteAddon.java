package dev.feldmann.redstonegang.wire.game.base.addons.both;

import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

public class TesteAddon extends Addon {

    public boolean enabled = true;

    public TesteAddon() {
    }

    @EventHandler
    public void nullPointerTeste(PlayerInteractEvent ev) {
        if (ev.getPlayer().getItemInHand() != null) {
            if (ev.getPlayer().getItemInHand().getType() == Material.BLAZE_ROD) {
            }
        }
    }
}

