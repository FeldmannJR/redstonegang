package dev.feldmann.redstonegang.wire.game.base.addons.server.betterspawners;

import dev.feldmann.redstonegang.wire.game.base.addons.both.customBlocks.BlockData;
import dev.feldmann.redstonegang.wire.game.base.addons.both.customBlocks.CustomBlocksAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.customItems.CustomItem;
import dev.feldmann.redstonegang.wire.game.base.addons.both.customItems.ItemRarity;
import dev.feldmann.redstonegang.wire.modulos.language.lang.LanguageHelper;
import dev.feldmann.redstonegang.wire.utils.location.BungeeBlock;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class SpawnerItem extends CustomItem {
    EntityType type;
    BetterSpawnersAddon addon;

    public SpawnerItem(BetterSpawnersAddon addon, EntityType type) {
        super(
                new ItemStack(Material.MOB_SPAWNER),
                "Spawner de " + LanguageHelper.getEntityName(type),
                "mobspawner:" + type.name(),
                ItemRarity.PAID,
                "Um spawner de " + LanguageHelper.getEntityName(type)
        );
        this.type = type;
        this.addon = addon;
    }

    @Override
    public void interact(PlayerInteractEvent ev) {
        if (ev.getPlayer().isSneaking()) return;
        if (ev.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (ev.getClickedBlock().getType() == Material.MOB_SPAWNER) {
                BlockData block = addon.a(CustomBlocksAddon.class).get(ev.getClickedBlock());
                if (block instanceof SpawnerBlock) {
                    if (((SpawnerBlock) block).lvl >= addon.maxLvl) {
                        C.error(ev.getPlayer(), "Este mobspawner ja é nivel 5!");
                        ev.setCancelled(true);
                        return;
                    }
                    if (((SpawnerBlock) block).type != this.type) {
                        C.error(ev.getPlayer(), "Você só pode juntar mobspawners do mesmo tipo!");
                        ev.setCancelled(true);
                        return;
                    }
                    consomeItem(ev.getPlayer());
                    ((SpawnerBlock) block).setLvl(((SpawnerBlock) block).lvl + 1);
                    addon.a(CustomBlocksAddon.class).save(block);
                    addon.setLvl((CreatureSpawner) ev.getClickedBlock().getState(), ((SpawnerBlock) block).lvl);
                    C.info(ev.getPlayer(), "Você melhorou seu mobspawner agora ele é level %%!", ((SpawnerBlock) block).lvl);
                    ev.setCancelled(true);
                }
            }
        }

    }

    @Override
    public boolean blockPlace(BlockPlaceEvent ev) {
        Block place = ev.getBlockPlaced();
        addon.a(CustomBlocksAddon.class).save(new SpawnerBlock(BungeeBlock.fromBlock(place), addon.getPlayerId(ev.getPlayer()), 1, type));
        CreatureSpawner spawner = (CreatureSpawner) ev.getBlockPlaced().getState();
        spawner.setSpawnedType(type);
        addon.setLvl(spawner, 1);
        return true;
    }

}
