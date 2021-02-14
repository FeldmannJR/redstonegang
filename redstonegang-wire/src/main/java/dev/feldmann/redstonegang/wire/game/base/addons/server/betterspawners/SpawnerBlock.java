package dev.feldmann.redstonegang.wire.game.base.addons.server.betterspawners;

import dev.feldmann.redstonegang.wire.game.base.addons.both.customBlocks.BlockData;
import dev.feldmann.redstonegang.wire.game.base.addons.both.customBlocks.CustomBlocksAddon;
import dev.feldmann.redstonegang.wire.utils.items.InventoryHelper;
import dev.feldmann.redstonegang.wire.utils.location.BungeeBlock;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import com.google.gson.JsonObject;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class SpawnerBlock extends BlockData {

    int ownerId;
    int lvl;
    EntityType type;

    public SpawnerBlock() {
    }

    public SpawnerBlock(BungeeBlock loc, int ownerId, int lvl, EntityType type) {
        this.ownerId = ownerId;
        this.loc = loc;
        this.lvl = lvl;
        this.type = type;
    }

    @Override
    public void read(JsonObject jobj) {
        type = EntityType.valueOf(jobj.get("type").getAsString());
        lvl = jobj.get("lvl").getAsInt();
        ownerId = jobj.get("ownerId").getAsInt();
    }

    @Override
    public void write(JsonObject obj) {
        obj.addProperty("type", type.name());
        obj.addProperty("lvl", lvl);
        obj.addProperty("ownerId", ownerId);
    }

    @Override
    public Material getMaterial() {
        return Material.MOB_SPAWNER;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    @Override
    public void breakBlock(CustomBlocksAddon addon, BlockBreakEvent ev) {
        if (ev.getPlayer().getGameMode() == GameMode.CREATIVE) return;
        ev.getBlock().setType(Material.AIR);
        ev.setCancelled(true);

        SpawnerItem drop = SpawnerItems.getSpawner(type);
        int inv = 0;
        for (int x = 0; x < lvl; x++) {
            ItemStack it = drop.generateItem(1);
            if (InventoryHelper.getFreeSlots(ev.getPlayer()) >= 1) {
                ev.getPlayer().getInventory().addItem(it);
                inv++;
            } else {
                ev.getBlock().getWorld().dropItemNaturally(ev.getBlock().getLocation(), it);
            }
        }
        if (inv > 0) {
            C.info(ev.getPlayer(), "Foram adicionados %% spawners ao seu inventário!", inv);
        }

    }

    @Override
    public void interactBlock(CustomBlocksAddon addon, PlayerInteractEvent ev) {
        if (ev.getPlayer().getItemInHand() != null && ev.getPlayer().getItemInHand().getType() == Material.MOB_SPAWNER) {
            return;
        }
        C.info(ev.getPlayer(), "Este mobspawner é level %%", this.lvl);
    }
}
