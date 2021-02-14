package dev.feldmann.redstonegang.wire.game.base.addons.server.safetime;

import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.common.player.config.types.BooleanConfig;
import dev.feldmann.redstonegang.common.utils.formaters.time.TimeUtils;
import dev.feldmann.redstonegang.wire.Wire;
import dev.feldmann.redstonegang.wire.game.base.addons.server.safetime.commands.CmdSeguro;
import dev.feldmann.redstonegang.wire.game.base.addons.server.safetime.event.PlayerSafeTimeEndEvent;
import dev.feldmann.redstonegang.wire.game.base.addons.server.safetime.event.PlayerSafeTimeStartEvent;
import dev.feldmann.redstonegang.wire.game.base.database.addons.tables.records.SafetimeRecord;
import dev.feldmann.redstonegang.wire.game.base.events.player.PlayerJoinServerEvent;
import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import dev.feldmann.redstonegang.wire.plugin.events.update.UpdateEvent;
import dev.feldmann.redstonegang.wire.plugin.events.update.UpdateType;
import dev.feldmann.redstonegang.wire.utils.EventUtils;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import dev.feldmann.redstonegang.wire.utils.player.ActionBar;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashMap;

public class SafeTimeAddon extends Addon {

    public BooleanConfig GAVE;

    HashMap<Integer, SafetimeRecord> safeTimes = new HashMap<>();
    SafeTimeDatabase db;

    public SafeTimeAddon(String database) {
        db = new SafeTimeDatabase(database);
    }

    @Override
    public void onEnable() {
        GAVE = new BooleanConfig("ad_safetime_" + getServer().getIdentifier() + "_gave", false);
        addConfig(GAVE);
        registerCommand(new CmdSeguro(this));
    }

    @EventHandler
    public void update(UpdateEvent ev) {
        if (ev.getType() == UpdateType.SEC_1) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                SafetimeRecord safe = getSafetime(p);
                if (safe != null) {
                    long diff = safe.getEnd().getTime() - System.currentTimeMillis();
                    ActionBar.sendActionBar(p, "§fModo §a§lSEGURO§f acaba em §a" + TimeUtils.millisToHoursAndMinutes(diff) + "§f !");
                }
            }
        }
    }


    @EventHandler
    public void join(PlayerJoinServerEvent ev) {
        User user = ev.getUser();
        if (!user.getConfig(GAVE)) {
            int horas = 3;
            setSafeTime(ev.getPlayer(), 60 * horas);
            C.info(ev.getPlayer(), "Você tem %% horas em modo seguro! Nenhum jogador pode matar você!", horas);
            user.setConfig(GAVE, true);
        }
    }


    @EventHandler
    public void quit(PlayerQuitEvent ev) {
        safeTimes.remove(getPlayerId(ev.getPlayer()));
    }

    public void setSafeTime(Player p, int minutos) {
        User user = getUser(p);
        SafetimeRecord safe = getSafetime(p);
        if (safe == null) {
            safe = db.createSafeTime(user.getId());
        }
        safe.setStart(new Timestamp(System.currentTimeMillis()));
        safe.setEnd(new Timestamp(System.currentTimeMillis() + ((long) minutos * 1000L * 60L)));
        safe.store();
        safeTimes.put(user.getId(), safe);
        Wire.callEvent(new PlayerSafeTimeStartEvent(p));
    }

    public boolean isInSafetime(Player p) {
        return getSafetime(p) != null;
    }

    private void safeTimeEnded(Player p) {
        Wire.callEvent(new PlayerSafeTimeEndEvent(p));
        ActionBar.sendActionBar(p, "§e!!! §fSeu tempo §cSEGURO ACABOU §e!!!");
        C.info(p, "Seu tempo SEGURO acabou !!!");
    }

    public SafetimeRecord getSafetime(Player p) {
        if (p.hasMetadata("NPC")) return null;
        User user = getUser(p);
        boolean ended = false;
        if (safeTimes.containsKey(user.getId())) {
            SafetimeRecord safetimeRecord = safeTimes.get(user.getId());
            if (safetimeRecord != null && !isValid(safetimeRecord)) {
                // Acabou
                ended = true;
                safeTimes.put(user.getId(), null);
            }
        } else {
            SafetimeRecord safetime = db.getSafeTime(user);
            safeTimes.put(user.getId(), safetime);
        }
        if (ended) safeTimeEnded(p);
        return safeTimes.get(user.getId());
    }

    public void removeSafeTime(Player p) {
        db.removeSafeTime(getUser(p).getId());
        safeTimes.put(getPlayerId(p), null);
        safeTimeEnded(p);
    }

    public boolean isValid(SafetimeRecord rec) {
        return rec.getStart().before(new Timestamp(System.currentTimeMillis())) &&
                rec.getEnd().after(new Time(System.currentTimeMillis()));

    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void protect(EntityDamageByEntityEvent ev) {
        if (!(ev.getEntity() instanceof Player)) {
            return;
        }
        Player damager = EventUtils.getDamager(ev);
        Player damaged = (Player) ev.getEntity();
        if (damager != null) {
            if (isInSafetime(damager)) {
                C.info(damager, "Você não pode atacar em modo %%! Espere acabar ou use %cmd% !", "SEGURO", "/seguro");
                ev.setCancelled(true);
                return;
            }
            if (isInSafetime(damaged)) {
                C.info(damager, "Este jogador está em modo %% você não pode atacar ele! Você pode identificar o modo seguro pelo nome verde!", "SEGURO");
                ev.setCancelled(true);
                return;
            }
        }

    }

}
