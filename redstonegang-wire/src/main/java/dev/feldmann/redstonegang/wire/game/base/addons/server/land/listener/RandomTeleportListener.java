package dev.feldmann.redstonegang.wire.game.base.addons.server.land.listener;

import dev.feldmann.redstonegang.common.utils.Cooldown;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.LandAddon;
import dev.feldmann.redstonegang.wire.game.base.objects.BaseListener;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class RandomTeleportListener extends BaseListener {


    private LandAddon addon;
    private Cooldown useTeleportCd = new Cooldown(60000);
    private Cooldown sendMessageCd = new Cooldown(2000);

    public RandomTeleportListener(LandAddon addon) {
        this.addon = addon;
    }

    public void efeito(Player p) {
        p.getWorld().playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0f, 1);
        p.getWorld().spigot().playEffect(p.getLocation(), Effect.PORTAL);
        p.getWorld().playEffect(p.getLocation(), Effect.ENDER_SIGNAL, 1, 1);

    }

    public void tryToTeleport(Player p) {
        if (useTeleportCd.isCooldown(p.getUniqueId())) {
            if (!sendMessageCd.isCooldown(p.getUniqueId())) {
                sendMessageCd.addCooldown(p.getUniqueId());
                int x = -1;
                int z = -1;
                p.setVelocity(new Vector(x, 1.5, z).normalize().multiply(1.5));
                p.sendMessage("§cEspere para usar o teleporte novamente!");
            }

            return;
        }
        Location loc = addon.getEmptyLocation(p, -4950, 4950, 10);
        if (loc == null) {
            C.error(p, "Teleport falhou tente novamente!");
            useTeleportCd.addCooldown(p.getUniqueId(), 10000);
            sendMessageCd.addCooldown(p.getUniqueId());
            return;
        }
        efeito(p);
        p.setFallDistance(0);
        p.teleport(loc);
        efeito(p);
        useTeleportCd.addCooldown(p.getUniqueId());
        C.info(p, "Você foi teleportado!");

    }

    @EventHandler
    public void move(PlayerMoveEvent ev) {
        Location to = ev.getTo();
        if (to.getBlock().getType() == Material.ENDER_PORTAL) {
            Block down = to.getBlock().getRelative(BlockFace.DOWN);
            if (down.getType() == Material.SPONGE) {
                tryToTeleport(ev.getPlayer());
                return;
            }

        }
    }
}
