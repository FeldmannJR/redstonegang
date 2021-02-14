package dev.feldmann.redstonegang.wire.game.games.servers.build;

import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.common.player.permissions.Group;
import dev.feldmann.redstonegang.wire.RedstoneGangSpigot;
import dev.feldmann.redstonegang.wire.game.base.Server;
import dev.feldmann.redstonegang.wire.game.base.addons.both.customBlocks.CustomBlocksAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.simplecmds.SimpleCmds;
import dev.feldmann.redstonegang.wire.game.base.addons.server.economy.EconomyAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.home.HomeAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.tps.elevator.ElevatorAddon;
import dev.feldmann.redstonegang.wire.game.base.events.player.PlayerJoinServerEvent;
import dev.feldmann.redstonegang.wire.game.games.servers.build.cmds.CreateVoid;
import dev.feldmann.redstonegang.wire.modulos.scoreboard.ScoreboardManager;
import dev.feldmann.redstonegang.wire.permissions.CmdPerm;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

public class Build extends Server {


    private static String buildDatabase = "redstonegang_build";

    @Override
    public void enable() {
        super.enable();
        addAddon(new CustomBlocksAddon(buildDatabase, "customBlocks"));
        addAddon(new HomeAddon(buildDatabase));
        addAddon(new EconomyAddon(buildDatabase, "money"));
        addAddon(new ElevatorAddon());
        addAddon(new SimpleCmds(buildDatabase));
        registerCommand(new CreateVoid());
        registerCommand(new CmdPerm());
    }

    @EventHandler
    public void join(PlayerJoinServerEvent ev) {
        User pl = RedstoneGangSpigot.getPlayer(ev.getPlayer().getUniqueId());
        Group gr = pl.permissions().getGroup();
        if (gr != null) {
            ScoreboardManager.addToTeam(ev.getPlayer().getName(), ev.getPlayer().getName(), gr.getPrefix() != null ? gr.getPrefix() : "", gr.getSuffix() != null ? gr.getSuffix() : "", true);
        }
    }

    @EventHandler
    public void damage(EntityDamageEvent ev) {
        if (ev.getEntity() instanceof Player) {
            ev.setCancelled(true);
            if (ev.getCause() == EntityDamageEvent.DamageCause.VOID) {
                ev.getEntity().teleport(ev.getEntity().getWorld().getSpawnLocation());
            }
        }
    }

    @Override
    public String getIdentifier() {
        return "build";
    }
}
