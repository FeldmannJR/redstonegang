package dev.feldmann.redstonegang.wire.game.base.addons.server.tps.elevator;

import dev.feldmann.redstonegang.wire.game.base.addons.both.customBlocks.BlockData;
import dev.feldmann.redstonegang.wire.game.base.addons.both.customBlocks.CustomBlocksAddon;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import com.google.gson.JsonObject;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class ElevatorBlock extends BlockData {

    public boolean infiniteRange = false;
    public boolean breakable = true;

    @Override
    public void read(JsonObject obj) {
        if (obj.has("infiniteRange")) {
            this.infiniteRange = obj.get("infiniteRange").getAsBoolean();
        }
        if (obj.has("breakable")) {
            this.breakable = obj.get("breakable").getAsBoolean();
        }
    }


    @Override
    public void write(JsonObject obj) {
        obj.addProperty("infiniteRange", infiniteRange);
        obj.addProperty("breakable", breakable);
    }

    @Override
    public Material getMaterial() {
        return Material.ENDER_PORTAL_FRAME;
    }

    @Override
    public void breakBlock(CustomBlocksAddon addon, BlockBreakEvent ev) {
        ev.setCancelled(true);
        if (!breakable && ev.getPlayer().getGameMode() != GameMode.CREATIVE) {
            C.error(ev.getPlayer(), "Você não pode quebrar este bloco!");
            return;
        }
        ev.getBlock().setType(Material.AIR);
        addon.remove(this);
        if (ev.getPlayer().getGameMode() == GameMode.SURVIVAL)
            ev.getBlock().getWorld().dropItemNaturally(ev.getBlock().getLocation().clone().add(0.5, 0.5, 0.5), ElevatorAddon.ITEM.generateItem(1));
    }

    @Override
    public void interactBlock(CustomBlocksAddon addon, PlayerInteractEvent ev) {
        if (ev.getPlayer().getItemInHand() != null && ev.getPlayer().getItemInHand().getType() == Material.EYE_OF_ENDER) {
            if (ev.getAction() == Action.RIGHT_CLICK_BLOCK) {
                ev.setCancelled(true);
            }
        }
    }
}
