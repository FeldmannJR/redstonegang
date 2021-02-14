package dev.feldmann.redstonegang.wire.game.games.servers.survival.minerar;

import dev.feldmann.redstonegang.wire.game.base.addons.both.customItems.CustomItem;
import dev.feldmann.redstonegang.wire.game.base.addons.both.customItems.CustomItemsAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.simplecmds.SimpleCmds;
import dev.feldmann.redstonegang.wire.game.base.addons.both.simplecmds.cmds.staff.SetSpawn;
import dev.feldmann.redstonegang.wire.game.base.addons.both.simplecmds.cmds.staff.tps.Tp;
import dev.feldmann.redstonegang.wire.game.base.addons.server.betterspawners.SpawnerItem;
import dev.feldmann.redstonegang.wire.game.base.addons.server.home.events.PlayerSetHomeEvent;
import dev.feldmann.redstonegang.wire.game.base.addons.server.oreGenerator.OreGenerator;
import dev.feldmann.redstonegang.wire.game.games.servers.survival.Survival;
import dev.feldmann.redstonegang.wire.game.games.servers.survival.SyncTimeAddon;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.entity.Creeper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;

public class SurvivalMinerar extends Survival {


    private String worldName = "minerar";

    @Override
    public void enable() {
        super.enable();
        addAddon(new SyncTimeAddon(false));
        addAddon(new MinerarAddon(worldName), new OreGenerator(), new WorldResetAddon(worldName), new MinerarMobsAddon());
        addAddon(new SimpleCmds(database, SetSpawn.class, Tp.class));
    }

    @Override
    public void lateEnable() {
        super.lateEnable();

    }

    @EventHandler
    public void explode(EntityExplodeEvent ev) {
        if (ev.getEntity() instanceof Creeper) return;
        ev.blockList().clear();
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    public void place(BlockPlaceEvent ev) {
        ItemStack item = ev.getPlayer().getItemInHand();
        if (item != null) {
            CustomItem custom = a(CustomItemsAddon.class).getCustomItemFromItemstack(item);
            if (custom != null) {
                if (custom instanceof SpawnerItem) {
                    C.error(ev.getPlayer(), "Você não pode colocar mobspawners aqui!");
                    ev.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void sethome(PlayerSetHomeEvent ev) {
        ev.setCancelled(true);
        C.error(ev.getPlayer(), "Você não pode setar home no minerar!");
    }
}
