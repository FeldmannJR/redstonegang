package dev.feldmann.redstonegang.wire.game.base.addons.server.pvp;

import dev.feldmann.redstonegang.common.player.config.types.BooleanConfig;
import dev.feldmann.redstonegang.common.utils.Cooldown;
import dev.feldmann.redstonegang.wire.Wire;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.staffcommands.StaffCommandsAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.pvp.event.PlayerAfterStartFreePvpEvent;
import dev.feldmann.redstonegang.wire.game.base.addons.server.pvp.event.PlayerStartFreePvpEvent;
import dev.feldmann.redstonegang.wire.game.base.addons.server.pvp.event.PlayerStopFreePvpEvent;
import dev.feldmann.redstonegang.wire.game.base.events.player.PlayerJoinServerEvent;
import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import dev.feldmann.redstonegang.wire.plugin.events.update.UpdateEvent;
import dev.feldmann.redstonegang.wire.plugin.events.update.UpdateType;
import dev.feldmann.redstonegang.wire.utils.player.ActionBar;
import dev.feldmann.redstonegang.wire.utils.player.Title;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PvPAddon extends Addon {
    private Cooldown logger = new Cooldown(15000);
    private BooleanConfig DESLOGOU_PVP;
    public HashMap<UUID, Integer> freePvp = new HashMap<UUID, Integer>();

    @Override
    public void onEnable() {
        DESLOGOU_PVP = new BooleanConfig("ad_pvp_" + getServer().getIdentifier() + "_deslogou", false);
        addConfig(DESLOGOU_PVP);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void quit(PlayerQuitEvent ev) {
        if (Wire.instance.restarting) {
            return;
        }
        Player p = ev.getPlayer();
        if (freePvp.containsKey(ev.getPlayer().getUniqueId())) {
            Bukkit.getScheduler().cancelTask(freePvp.remove(ev.getPlayer().getUniqueId()));
        }
        if (logger.isCooldown(p.getUniqueId())) {
            for (Entity e : ev.getPlayer().getNearbyEntities(7, 7, 7)) {
                if (e instanceof Player) {
                    e.sendMessage("§a" + ev.getPlayer().getName() + " saiu no meio do PvP e perdeu os itens!");
                }
            }
            ev.getPlayer().damage(40000);
            getUser(p).setConfig(DESLOGOU_PVP, true);
            ev.getPlayer().setHealth(ev.getPlayer().getMaxHealth());

        }
    }


    @EventHandler
    public void update(UpdateEvent ev) {
        if (ev.getType() == UpdateType.TICK) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (logger.isCooldown(p.getUniqueId())) {
                    if (logger.remaining(p.getUniqueId()) <= 50) {
                        p.sendMessage("§c§l[PvPLogger] §e§lVocê pode deslogar agora!");
                        Title.sendTitle(p, "", "§aVocê pode sair e usar comandos agora!", 0, 40, 5);
                        ActionBar.sendActionBar(p, "§aAgora você pode sair!");
                        p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);
                        logger.removeCooldown(p.getUniqueId());
                    } else {
                        ActionBar.sendActionBar(p, "§c§lNão saia do servidor, caso saia irá morrer!!!");
                    }
                }
            }
        }
    }

    public void freePvp(final Player p) {
        if (Wire.callEvent(new PlayerStartFreePvpEvent(p))) {
            return;
        }
        if (freePvp.containsKey(p.getUniqueId())) {
            Bukkit.getScheduler().cancelTask(freePvp.remove(p.getUniqueId()));
        } else {
            p.sendMessage("");
            p.sendMessage("§4§lALERTA: ");
            p.sendMessage("§a§lSeu nome está vermelho qualquer um pode bater em você em qualquer lugar!");
            p.sendMessage("");
            p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1, 1);
        }

        int id = scheduler().runAfter(() -> {
            p.sendMessage("");
            p.sendMessage("§2§lSEGURO: ");
            p.sendMessage("§cSeu nome não está mais vermelho! Agora você está livre!");
            p.sendMessage("");
            p.playSound(p.getLocation(), Sound.VILLAGER_YES, 1, 1);
            stopFreePvp(p);
        }, 20 * 5);
        freePvp.put(p.getUniqueId(), id);
        Wire.callEvent(new PlayerAfterStartFreePvpEvent(p));
    }

    public void stopFreePvp(Player p) {
        if (p == null) {
            return;
        }
        if (freePvp.containsKey(p.getUniqueId())) {
            int x = freePvp.get(p.getUniqueId());
            freePvp.remove(p.getUniqueId());
            scheduler().cancelTask(x);
            Wire.callEvent(new PlayerStopFreePvpEvent(p));
        }
    }

    @EventHandler
    public void death(PlayerDeathEvent ev) {
        stopFreePvp(ev.getEntity());
        logger.removeCooldown(ev.getEntity().getUniqueId());
    }

    @EventHandler
    public void flag(PlayerJoinServerEvent ev) {
        Boolean deslogou = ev.getUser().getConfig(DESLOGOU_PVP);
        if (deslogou) {
            ev.getUser().setConfig(DESLOGOU_PVP, false);
            ev.getPlayer().sendMessage("            ");
            ev.getPlayer().sendMessage("            ");
            ev.getPlayer().sendMessage("§c§l[PvPLogger] §b§lVocê saiu no meio do PvP e perdeu seus itens!");
            ev.getPlayer().sendMessage("            ");
            ev.getPlayer().sendMessage("            ");
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void damage(EntityDamageByEntityEvent ev) {
//        if (Servidores.getServer() == Servidores.EVENTOS) {
//            return;
//        }
        if (ev.getDamager() instanceof Player || ev.getDamager() instanceof Projectile) {
            if (ev.getEntity() instanceof Player) {
                Player a = null;
                if (ev.getDamager() instanceof Projectile) {
                    if (((Projectile) ev.getDamager()).getShooter() != null) {
                        if (((Projectile) ev.getDamager()).getShooter() instanceof Player) {
                            a = (Player) ((Projectile) ev.getDamager()).getShooter();
                        }
                    }
                    if (a == null) {
                        return;
                    }
                } else {
                    a = (Player) ev.getDamager();
                }
                Player t = (Player) ev.getEntity();
//                if (EventoManager.getEvento() != null) {
//                    return;
//                }
                // Se quem tomou dano tem godmode ignora
                if (this.hasAddon(StaffCommandsAddon.class)) {
                    boolean godmode = this.a(StaffCommandsAddon.class).isGodMode(t);
                    if (godmode) {
                        return;
                    }
                }
                if (!ev.isCancelled()) {
                    if (!freePvp.containsKey(t.getUniqueId())) {
                        if (ev.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK || ev.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
                            freePvp(a);
                        }
                    }
                    logger.addCooldown(ev.getEntity().getUniqueId());
                } else if (freePvp.containsKey(t.getUniqueId()) && (!hasAddon(ClanAddon.class) || a(ClanAddon.class).canAttack(t, a))) {
                    ev.setCancelled(false);
                    logger.addCooldown(ev.getEntity().getUniqueId());
                    if (ev.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK || ev.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
                        freePvp(a);
                        freePvp(t);
                    }
                }
            }
        }
    }

    public static List<String> bloqueados = Arrays.asList("top", "enderchest", "home", "minerar", "evento", "lixo", "terreno", "warp", "spawn");

    @EventHandler
    public void PlayerCommand(PlayerCommandPreprocessEvent ev) {
        String first = ev.getMessage().split(" ")[0];
        first = first.substring(1).toLowerCase();
        if (bloqueados.contains(first)) {
            if (logger.isCooldown(ev.getPlayer().getUniqueId())) {
                ev.getPlayer().sendMessage("§c§lEspere um pouco para usar /" + first + " você acabou de tomar dano!");
                ev.setCancelled(true);
                return;
            }
        }
    }

    public boolean isFreePvp(Player p) {
        return freePvp.containsKey(p.getUniqueId());
    }

    public boolean isLogged(Player p) {
        return this.logger.isCooldown(p.getUniqueId());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void damage2(EntityDamageByEntityEvent ev) {
        if (ev.getEntity() instanceof Player) {
            boolean can = false;
            if (ev.getDamager() instanceof Player) {
                can = true;
            }
            if (ev.getDamager() instanceof Projectile) {
                Projectile p = (Projectile) ev.getDamager();
                if (p.getShooter() != null && p.getShooter() instanceof Player) {
                    can = true;
                }
            }
            if (can) {
                Player p = (Player) ev.getEntity();
//if (EventoManager.getEvento() != null && !EventoManager.getEvento().usePvPLogger() && EventoManager.getEvento().isJogador(p.getUniqueId())) {
//return;
//}
                if (!isLogged((Player) ev.getEntity())) {
                    ev.getEntity().sendMessage("§c§l[PvPLogger] §eVocê tomou dano não saia do servidor ou perderá seus itens!");
                    ActionBar.sendActionBar(p, "§c§l[PvPLogger]§e Não saia do servidor, caso saia irá morrer!!!");
                }
                logger.addCooldown(ev.getEntity().getUniqueId());
            }
        }
    }
}
