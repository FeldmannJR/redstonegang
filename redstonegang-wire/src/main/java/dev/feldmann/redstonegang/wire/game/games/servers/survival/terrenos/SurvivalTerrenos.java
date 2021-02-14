package dev.feldmann.redstonegang.wire.game.games.servers.survival.terrenos;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.player.permissions.GroupOption;
import dev.feldmann.redstonegang.common.player.permissions.PermissionDescription;
import dev.feldmann.redstonegang.common.player.permissions.PermissionServer;
import dev.feldmann.redstonegang.common.utils.RandomUtils;
import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.perm.Permission;
import dev.feldmann.redstonegang.wire.game.base.addons.both.simplecmds.SimpleCmds;
import dev.feldmann.redstonegang.wire.game.base.addons.both.simplecmds.cmds.staff.tps.Tp;
import dev.feldmann.redstonegang.wire.game.base.addons.server.oreGenerator.OreGenerator;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.LandAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.floatshop.FloatShopAddon;
import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import dev.feldmann.redstonegang.wire.game.games.servers.survival.Survival;
import dev.feldmann.redstonegang.wire.game.games.servers.survival.SyncTimeAddon;
import dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.imagemspawn.ImagemSpawn;
import dev.feldmann.redstonegang.wire.game.base.addons.server.invsync.events.PlayerSyncLocationEvent;
import dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.redstone.RedstoneAddon;
import dev.feldmann.redstonegang.wire.game.games.servers.survival.terrenos.cmds.CmdMinerar;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.ArrayList;
import java.util.List;

public class SurvivalTerrenos extends Survival {

    @Override
    public void enable() {
        super.enable();
        addAddon(new LandAddon(database));
        addAddon(new RedstoneAddon());
        addAddon(new ImagemSpawn());
        addAddon(new OreGenerator());
        addAddon(new FloatShopAddon(database));
        addAddon(new SimpleCmds(database, Tp.class));
        addAddon(new SyncTimeAddon(true));
        registerCommand(new CmdMinerar(this));


    }

    @Override
    public void lateEnable() {
        super.lateEnable();
        writePermissions();
    }

    @EventHandler
    public void explode(EntityExplodeEvent ev) {
        ev.blockList().clear();
    }

    @EventHandler
    public void sync(PlayerSyncLocationEvent ev) {
        if (ev.isOverrideTeleportLocation()) {
            return;
        }
        if (ev.getTeleportLocation() != null && ev.getTeleportLocation().isCurrentServer()) {
            ev.setSpawnLocation(ev.getTeleportLocation().toLocation());
            return;
        }

        if (ev.getSavedLocation() != null && ev.getSavedLocation().isCurrentServer()) {
            ev.setSpawnLocation(ev.getSavedLocation().toLocation());
        } else {
            ev.setSpawnLocation(getSpawn().toLocation());
        }
    }


    public void writePermissions() {
        List<GroupOption> options = new ArrayList<>();
        getAddons().forEach((a) -> options.addAll(a.getOptions()));

        for (Addon ad : getAddons()) {
            List<RedstoneCmd> cmds = ad.getCommands();
            CMD:
            for (RedstoneCmd cmd : cmds) {
                if (cmd.getExecutePerm() instanceof Permission) {
                    for (GroupOption op : options) {
                        if (op.getKey().equals(((Permission) cmd.getExecutePerm()).getPermission())) {
                            continue CMD;
                        }
                    }
                    options.add(new PermissionDescription("Comando " + cmd.getName(), ((Permission) cmd.getExecutePerm()).getPermission(), "permissão para executar o comando " + cmd.getName() + " do addon " + ad.getClass().getSimpleName()));
                }
            }
        }
        RedstoneGang.instance.user().getPermissions().getDb().setPermissionsDesc(options, PermissionServer.SV);
    }
}
