package dev.feldmann.redstonegang.wire.game.base.addons.server;

import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Tameable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class HorseProtection extends Addon {


    @EventHandler
    public void interage(PlayerInteractEntityEvent ev) {
        if (ev.getRightClicked() != null && ev.getRightClicked() instanceof Horse) {
            Horse h = (Horse) ev.getRightClicked();
            if ((h.getOwner() != null
                    && h.getOwner().getUniqueId() != null
                    && !h.getOwner().getUniqueId().equals(ev.getPlayer().getUniqueId()))) {
                ev.setCancelled(true);
                C.error(ev.getPlayer(), "Você não pode mecher em um cavalo que não é seu!");
            }
        }
    }

    @EventHandler
    public void poeColeira(PlayerLeashEntityEvent ev) {
        if (ev.getEntity() instanceof Tameable) {
            Tameable bixo = (Tameable) ev.getEntity();
            // o dono pode por coleira de boa
            if (bixo.getOwner() != null && bixo.getOwner().getName().equalsIgnoreCase(ev.getPlayer().getName())) {
                return;
            } else if (bixo.getOwner() != null) {
                ev.setCancelled(true);
                C.error(ev.getPlayer(), "Este animal não é seu!");
            }
        }
    }


}
