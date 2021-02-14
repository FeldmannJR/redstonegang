package dev.feldmann.redstonegang.wire.game.base.addons.both.clan.listeners;

import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanRole;
import dev.feldmann.redstonegang.wire.game.base.objects.BaseListener;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanMember;
import dev.feldmann.redstonegang.wire.game.base.events.player.PlayerJoinServerEvent;
import dev.feldmann.redstonegang.wire.permissions.events.PlayerChangeGroupEvent;
import dev.feldmann.redstonegang.wire.utils.items.PotionUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.potion.PotionEffect;

public class ClanListeners extends BaseListener {
    ClanAddon addon;

    public ClanListeners(ClanAddon addon) {
        this.addon = addon;
    }

    @EventHandler
    public void join(PlayerJoinServerEvent ev) {
        addon.scheduler().runAfter(() -> {
            if (ev.getPlayer().isOnline()) {
                ClanMember member = addon.getCache().getMember(ev.getPlayer());
                if (member.getClanTag() != null)
                    addon.getCache().updateLastActive(member.getClanTag());
            }
        }, 20 * 6);
    }


    @EventHandler
    public void sendLoginMessage(PlayerJoinServerEvent ev) {
        if (!ev.changedIndentifier()) return;
        ClanMember member = addon.getCache().getMember(ev.getPlayer());
        if (member.getClan() != null) {
            member.getClan().sendMessage("%% logou no servidor!", ev.getPlayer());
        }
    }

    @EventHandler
    public void updateSuffix(PlayerJoinServerEvent ev) {

    }

    @EventHandler
    public void damage(EntityDamageByEntityEvent ev) {

        Player d, e = null;
        d = null;

        if (ev.getDamager() instanceof Player) {

            d = (Player) ev.getDamager();

        }
        if (ev.getEntity() instanceof Player) {
            e = (Player) ev.getEntity();
        }
        if (ev.getDamager() instanceof Projectile) {
            Projectile proj = (Projectile) ev.getDamager();
            if (proj.getShooter() != null && proj.getShooter() instanceof Player) {
                d = (Player) proj.getShooter();
            }

        }
        if (d == null || e == null) {
            return;
        }
        if (!addon.canAttack(d, e)) {
            ev.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void potionSplash(PotionSplashEvent ev) {
        if (ev.isCancelled()) {
            return;
        }
        Projectile sp = ev.getEntity();
        if (sp.getShooter() instanceof Player) {
            Player shooter = (Player) sp.getShooter();

            for (LivingEntity tomou : ev.getAffectedEntities()) {
                if (tomou instanceof Player) {
                    Player pto = (Player) tomou;
                    //POÇÕES NATIVAS
                    for (PotionEffect pf : ev.getPotion().getEffects()) {
                        boolean ruim = PotionUtils.isNegative(pf.getType());
                        if (!ruim) {
//                            if (addon.sameClan(shooter, pto)) {
//                                pto.addPotionEffect(pf);
//                            }
                        } else {
                            if (addon.canAttack(shooter, pto)) {
                                pto.addPotionEffect(pf);
                            }
                        }
                    }
                }
            }
            ev.setCancelled(true);
        }
    }

    @EventHandler
    public void update(PlayerChangeGroupEvent ev) {
        ClanMember member = addon.getCache().getMember(ev.getPlayer().getId());
        if (member.getClanTag() != null) {
            if (member.getRole() == ClanRole.LEADER) {
                member.getClan().clearCache();
            }
        }
    }
}
