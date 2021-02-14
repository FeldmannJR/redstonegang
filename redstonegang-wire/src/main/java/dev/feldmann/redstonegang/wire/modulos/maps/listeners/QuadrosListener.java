package dev.feldmann.redstonegang.wire.modulos.maps.listeners;


import dev.feldmann.redstonegang.wire.game.base.events.player.PlayerJoinServerEvent;
import dev.feldmann.redstonegang.wire.game.base.objects.BaseListener;
import dev.feldmann.redstonegang.wire.modulos.maps.Quadro;
import dev.feldmann.redstonegang.wire.modulos.maps.QuadrosManager;
import dev.feldmann.redstonegang.wire.utils.hitboxes.RayCast;
import dev.feldmann.redstonegang.wire.utils.location.LocUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.player.*;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class QuadrosListener extends BaseListener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void sync(PlayerJoinServerEvent ev) {
        checkSend(ev.getPlayer());
    }

    @EventHandler
    public void teleport(PlayerTeleportEvent ev) {
        if (ev.getFrom().getWorld().equals(ev.getTo().getWorld())) {
            if (ev.getFrom().distance(ev.getTo()) > 10) {
                checkSend(ev.getPlayer());
            }
        }
    }

    @EventHandler
    public void move(PlayerMoveEvent ev) {
        if (!LocUtils.isSameBlock(ev.getFrom(), ev.getTo())) {
            checkSend(ev.getPlayer());
        }
    }


    public void checkSend(Player p) {
        for (Quadro q : QuadrosManager.quadroList) {
            if (QuadrosManager.getData(p).isSeeing(q)) continue;
            if (q.getStartLocation().getWorld() == p.getWorld()) {
                double distance = RayCast.distance(q.getHitBox(), p.getLocation().toVector());
                if (distance <= QuadrosManager.DISTANCE_TO_SEND) {
                    if (q.shouldRender(p)) {
                        q.sendPackets(p);
                        QuadrosManager.getData(p).setSeeing(q, true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void clickblock(PlayerInteractEvent ev) {
        Player p = ev.getPlayer();
        if (ev.getAction() != Action.PHYSICAL) {
            if (checkClicaQuadro(p)) {
                ev.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void removeItemFrame(EntityDamageByEntityEvent ev) {
        Entity e = ev.getEntity();
        if (e != null) {
            if (e instanceof ItemFrame) {
                if (QuadrosManager.isMapItemFrame((ItemFrame) e)) {
                    ev.setCancelled(true);
                }

            }
        }

    }

    @EventHandler
    public void quit(PlayerQuitEvent ev) {
        QuadrosManager.clearPlayer(ev.getPlayer());
    }


    @EventHandler
    public void changeWorld(PlayerChangedWorldEvent ev) {
        QuadrosManager.clearPlayer(ev.getPlayer());
        checkSend(ev.getPlayer());
    }

    @EventHandler
    public void leftClick(HangingBreakByEntityEvent ev) {
        if (ev.getRemover() instanceof Player) {
            if (ev.getEntity() instanceof ItemFrame) {
                if (checkClicaQuadro((Player) ev.getRemover())) {
                    ev.setCancelled(true);
                }
            }
        }

    }

    @EventHandler
    public void rightClick(PlayerInteractEntityEvent ev) {
        if (ev.getRightClicked() instanceof ItemFrame) {
            if (checkClicaQuadro(ev.getPlayer())) {
                ev.setCancelled(true);
            }

        }


    }


    double range = 10;

    public boolean checkClicaQuadro(Player p) {


        List<Quadro> quadros = new ArrayList();
        for (Quadro c : QuadrosManager.quadroList) {
            if (c.getStartLocation().getWorld() == p.getWorld()) {
                double d = RayCast.distance(c.getHitBox(), p.getEyeLocation().toVector());
                if (d <= range) {
                    quadros.add(c);
                }
            }
        }
        Collections.sort(quadros, new Comparator<Quadro>() {
            @Override
            public int compare(Quadro quadro, Quadro t1) {
                double d1 = RayCast.distance(quadro.getHitBox(), p.getEyeLocation().toVector());
                double d2 = RayCast.distance(t1.getHitBox(), p.getEyeLocation().toVector());
                if (d1 < d2) {
                    return -1;
                } else {
                    return 1;
                }

            }
        });

        for (Quadro c : quadros) {

            if (c.getStartLocation() != null && c.getStartLocation().getWorld() == p.getWorld()) {
                Vector bateu = RayCast.getHitPoint(p, 10, c.getHitBox());
                if (bateu != null) {
                    int[] ints = c.convertHitToPixel(bateu);
                    // ParticleEffect.FLAME.display(0, 0, 0, 0, 1, c.convertPixelToLocation(ints[0], ints[1]), p);
                    c.clickou(p, ints[0], ints[1]);
                    return true;
                }


            }
        }
        return false;

    }


}
