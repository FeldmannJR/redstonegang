package dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.redstone;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.common.player.permissions.Group;
import dev.feldmann.redstonegang.common.player.permissions.GroupOption;
import dev.feldmann.redstonegang.wire.RedstoneGangSpigot;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.Land;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.LandAddon;
import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import dev.feldmann.redstonegang.wire.game.base.objects.annotations.Dependencies;

import dev.feldmann.redstonegang.wire.utils.msgs.C;
import com.google.common.collect.ImmutableMap;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Arrays;
import java.util.List;

@Dependencies(hard = {LandAddon.class})
public class RedstoneAddon extends Addon {


    private static final String REDSTONE_POR_BLOCO = "redstone_redstonePorBloco";
    // private static final String FUNIL_POR_BLOCO = "funilPorBloco";

    private String byPassPermission;

    List<Material> blocked = Arrays.asList(Material.PISTON_BASE, Material.PISTON_STICKY_BASE, Material.PISTON_MOVING_PIECE, Material.PISTON_EXTENSION);

    private static ImmutableMap<Material, Integer> redstone = ImmutableMap.<Material, Integer>builder()
            .put(Material.REDSTONE_WIRE, 1)
            .put(Material.REDSTONE, 1)
            .put(Material.REDSTONE_COMPARATOR, 2)
            .put(Material.REDSTONE_COMPARATOR_ON, 2)
            .put(Material.REDSTONE_COMPARATOR_OFF, 2)
            .put(Material.DIODE, 2)
            .put(Material.DIODE_BLOCK_OFF, 2)
            .put(Material.DIODE_BLOCK_ON, 2)
            .put(Material.DISPENSER, 2)
            .put(Material.DROPPER, 2)
            .build();


    @Override
    public void onEnable() {
        addOption(new GroupOption("redstone por bloco²", REDSTONE_POR_BLOCO, "Quantos blocos de redstone pode botar no terreno!"));
        //addOption(new GroupOption("quantos funis por bloco²", FUNIL_POR_BLOCO, "Quantos blocos funis terreno!"));
    }


    public double getMaxRedstone(int pid) {
        User player = RedstoneGang.getPlayer(pid);
        Group group = player.permissions().getGroup();
        double max = 0;
        if (group != null) {
            max = group.getOptionOrElse(REDSTONE_POR_BLOCO, 0D);
        }
        return max;
    }

    /*public double getMaxHopper(int pid) {
        User player = RedstoneGang.getUser(pid);
        Group group = player.permissions().getMainGroup();
        double max = 0;
        if (group != null) {
            max = group.getOptionOrElse(FUNIL_POR_BLOCO, 0D);
        }
        return max;
    }
    */

    public double max(int pid, String terrenoOpt) {
        //if (terrenoOpt.equals("redstone")) {
        return getMaxRedstone(pid);
        //}
        //return getMaxHopper(pid);
    }

    public void checkLimit(BlockPlaceEvent ev, String terrenoOpt, String oq, int peso) {
        Land t = a(LandAddon.class).getTerreno(ev.getBlockPlaced());
        if (t != null) {
            if (byPassPermission != null && RedstoneGangSpigot.getPlayer(t.getOwnerId()).permissions().hasPermission(byPassPermission)) {
                return;
            }
            if (max(t.getOwnerId(), terrenoOpt) < 0) {
                return;
            }
            Integer atualNoTerreno = t.getFlags().getCustomInteger(terrenoOpt).orElse(0);
            int max = (int) (t.getRegion().getArea() * max(t.getOwnerId(), terrenoOpt));
            int atual = peso + atualNoTerreno;

            if (atual > max) {
                C.error(ev.getPlayer(), "Você atingiu o máximo de " + oq + " no seu terreno!", max);
                C.info(ev.getPlayer(), "Seu limite é %%!", max);
                ev.setCancelled(true);
                return;
            }
            t.getFlags().setCustomInteger(terrenoOpt, atual);
            t.saveProperties();
        }
    }

    public void removeLimit(Block b, String terrenoOpt, int peso) {
        Land t = a(LandAddon.class).getTerreno(b);
        if (t != null) {
            int redstones = t.getFlags().getCustomInteger(terrenoOpt).orElse(0);
            int atual = redstones - peso;
            t.getFlags().setCustomInteger(terrenoOpt, Math.max(atual, 0));
            t.saveProperties();
        }
    }


    @EventHandler(ignoreCancelled = true)
    public void place(BlockPlaceEvent ev) {
        Material type = ev.getBlockPlaced().getType();
        if (blocked.contains(type)) {
            C.error(ev.getPlayer(), "Bloco não permitido!");
            ev.setCancelled(true);
            return;
        }
        /*
        if (type == Material.HOPPER) {
            checkLimit(ev, "hopper", "funis", 1);
        }
        */
        if (redstone.containsKey(type)) {
            checkLimit(ev, "redstone", "redstone", redstone.get(type));
        }
    }

    @EventHandler
    public void breakBlock(BlockBreakEvent ev) {
        Material type = ev.getBlock().getType();
        Block up = ev.getBlock().getRelative(BlockFace.UP);
        Material upType = up.getType();

        if (redstone.containsKey(type)) {
            removeLimit(ev.getBlock(), "redstone", redstone.get(type));
        }
        if (redstone.containsKey(upType) && !upType.isSolid()) {
            removeLimit(up, "redstone", redstone.get(upType));
        }
        /*if (type == Material.HOPPER) {
            removeLimit(ev.getBlock(), "hopper", 1);
        }*/
    }
}
