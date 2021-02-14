package dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.respawn;

import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.common.player.config.types.EnumConfig;
import dev.feldmann.redstonegang.wire.game.base.addons.both.damageinfo.PlayerCustomDeathEvent;
import dev.feldmann.redstonegang.wire.game.base.addons.server.home.HomeAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.home.HomeEntry;
import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import dev.feldmann.redstonegang.wire.game.games.servers.survival.Survival;
import dev.feldmann.redstonegang.wire.game.base.addons.server.invsync.InvSync;
import dev.feldmann.redstonegang.wire.utils.location.BungeeLocation;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerRespawnEvent;


public class SurvivalRespawnAddon extends Addon {
    public EnumConfig<RespawnLocation> RESPAWN_AT_HOME = new EnumConfig<RespawnLocation>("ad_survival_respawn_location", RespawnLocation.Spawn);

    @Override
    public void onEnable() {
        addConfig(RESPAWN_AT_HOME);
    }


    @EventHandler
    public void death(PlayerCustomDeathEvent ev) {
    }


    @EventHandler
    public void respawn(PlayerRespawnEvent ev) {
        User pl = getUser(ev.getPlayer());
        Boolean home = pl.getConfig(RESPAWN_AT_HOME) == RespawnLocation.Casa;
        BungeeLocation location = null;
        if (home) {
            HomeEntry homeEntry = a(HomeAddon.class).getDefault(ev.getPlayer());
            if (homeEntry != null) {
                location = homeEntry.getLocation();

            }
        } else {
            Survival survival = (Survival) getServer();
            BungeeLocation spawn = survival.getSpawn();
            if (spawn != null) {
                location = spawn;
            }
        }
        if (location != null) {
            if (location.isCurrentServer()) {
                ev.setRespawnLocation(location.toLocation());
            } else {
                BungeeLocation finalLocation = location;
                scheduler().runAfter(() -> {
                    if (ev.getPlayer() != null && ev.getPlayer().isOnline() && !a(InvSync.class).isWaitingLoad(ev.getPlayer())) {
                        getServer().teleport(ev.getPlayer(), finalLocation);
                    }
                }, 1);
            }
            return;
        }

        return;
    }


}
