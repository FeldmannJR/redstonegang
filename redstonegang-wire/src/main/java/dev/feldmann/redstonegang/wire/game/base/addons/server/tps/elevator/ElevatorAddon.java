package dev.feldmann.redstonegang.wire.game.base.addons.server.tps.elevator;

import dev.feldmann.redstonegang.common.utils.Cooldown;
import dev.feldmann.redstonegang.wire.Wire;
import dev.feldmann.redstonegang.wire.game.base.addons.both.customBlocks.BlockData;
import dev.feldmann.redstonegang.wire.game.base.addons.both.customBlocks.CustomBlocksAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.customItems.CustomItemsAddon;
import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.tps.elevator.events.PlayerUseElevatorEvent;
import dev.feldmann.redstonegang.wire.game.base.objects.annotations.Dependencies;
import dev.feldmann.redstonegang.wire.utils.location.BungeeBlock;
import dev.feldmann.redstonegang.wire.utils.player.Title;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.spigotmc.redstonegang.event.PlayerJumpEvent;

import java.util.*;

@Dependencies(hard = {CustomItemsAddon.class, CustomBlocksAddon.class})
public class ElevatorAddon extends Addon {


    public static ElevatorItem ITEM;
    public static final Integer LIMIT = 20;

    Cooldown cd = new Cooldown(333);


    @Override
    public void onEnable() {
        ITEM = new ElevatorItem(this);
        a(CustomItemsAddon.class).registerItem(ITEM);
        a(CustomBlocksAddon.class).registerType("ELEVATOR", ElevatorBlock.class);
    }

    @Override
    public void onStart() {
    }


    @EventHandler(ignoreCancelled = true)
    public void rightClickBlock(PlayerInteractEvent ev) {
        if (ev.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (ev.getPlayer().getGameMode() != GameMode.SURVIVAL) return;

            Block clickedBlock = ev.getClickedBlock();
            CustomBlocksAddon custom = a(CustomBlocksAddon.class);
            BlockData data = custom.get(clickedBlock);
            if (data != null && data instanceof ElevatorBlock) {
                Wire.callEvent(new BlockBreakEvent(clickedBlock, ev.getPlayer()));
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void breakBlock(BlockBreakEvent ev) {
    /*    CustomBlocksAddon custom = a(CustomBlocksAddon.class);
        BlockData data = custom.get(ev.getBlock());
        if (data != null && data instanceof ElevatorBlock) {
            ev.setCancelled(true);
            ev.getBlock().setType(Material.AIR);
            custom.remove(data);
            if (ev.getUser().getGameMode() == GameMode.SURVIVAL)
                ev.getBlock().getWorld().dropItemNaturally(ev.getBlock().getLocation().clone().add(0.5, 0.5, 0.5), ITEM.generateItem(1));
        }
        */
    }

    @EventHandler
    public void sneak(PlayerToggleSneakEvent ev) {
        if (ev.isSneaking()) {
            if (cd.isCooldown(ev.getPlayer().getUniqueId())) {
                return;
            }
            Block down = ev.getPlayer().getLocation().getBlock();
            if (down.getType() != Material.ENDER_PORTAL_FRAME) {
                down = down.getRelative(BlockFace.DOWN);
            }
            if (down.getType() == Material.ENDER_PORTAL_FRAME) {
                if (!isElevador(down)) {
                    return;
                }
                ElevatorBlock bl = (ElevatorBlock) a(CustomBlocksAddon.class).get(down);

                Block up = getElevator(down, false, bl.infiniteRange);
                if (up == null) {
                    return;
                }
                if (Wire.callEvent(new PlayerUseElevatorEvent(ev.getPlayer(), down))) {
                    return;
                }
                Location to = ev.getPlayer().getLocation().clone();
                to.setY(up.getY() + 1.2);
                teleportAfter(ev.getPlayer(), to, 1);
                effects(ev.getPlayer());
                Title.sendTitle(ev.getPlayer(), "§c§lDESCENDO", "", 0, 15, 5);
            }
        }
    }

    public void effects(Player p) {
        if (p.hasPotionEffect(PotionEffectType.BLINDNESS)) {
            p.removePotionEffect(PotionEffectType.BLINDNESS);
        }
        p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 30, 2));
        cd.addCooldown(p.getUniqueId());
    }

    @EventHandler
    public void player(PlayerAnimationEvent ev) {

    }

    @EventHandler
    public void move(PlayerJumpEvent ev) {
        Block down = ev.getPlayer().getLocation().getBlock();
        if (down.getType() != Material.ENDER_PORTAL_FRAME) {
            down = down.getRelative(BlockFace.DOWN);
        }

        if (cd.isCooldown(ev.getPlayer().getUniqueId())) {
            return;
        }
        if (down.getType() == Material.ENDER_PORTAL_FRAME) {
            if (!isElevador(down)) {
                return;
            }
            ElevatorBlock bl = (ElevatorBlock) a(CustomBlocksAddon.class).get(down);
            Block up = getElevator(down, true, bl.infiniteRange);
            if (up == null) {
                return;
            }
            if (Wire.callEvent(new PlayerUseElevatorEvent(ev.getPlayer(), down))) {
                return;
            }
            Location to = ev.getPlayer().getLocation().clone();
            to.setY(up.getY() + 1.2);
            teleportAfter(ev.getPlayer(), to, 1);
            effects(ev.getPlayer());
            Title.sendTitle(ev.getPlayer(), "§a§lSUBINDO", "", 0, 15, 5);
        }
    }


    public boolean isElevador(Block b) {
        BlockData data = custom().get(b);
        return data != null && data instanceof ElevatorBlock;
    }

    public CustomBlocksAddon custom() {
        return a(CustomBlocksAddon.class);

    }


    public Block getElevator(Block b, boolean up, boolean ignoreLimit) {
        BungeeBlock ploc = BungeeBlock.fromBlock(b);
        List<BlockData> data = new ArrayList<>(custom().getBlocks());

        Iterator<BlockData> it = data.iterator();
        while (it.hasNext()) {
            BlockData next = it.next();
            if (next instanceof ElevatorBlock) {
                if (next.getLoc().isSameIgnoreY(ploc)) {
                    if ((up && next.getLoc().getY() > ploc.getY()) || (!up && next.getLoc().getY() < ploc.getY())) {
                        if (ignoreLimit || Math.abs(next.getLoc().getY() - ploc.getY()) <= LIMIT) {
                            continue;
                        }
                    }
                }
            }
            it.remove();
        }
        Comparator c = Comparator.comparingInt((BlockData b2) -> b2.getLoc().getY());
        if (!up) {
            c = c.reversed();
        }
        Collections.sort(data, c);
        if (data.size() == 0) {
            return null;
        }

        return data.get(0).getLoc().toBlock();
    }


}
