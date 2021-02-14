package dev.feldmann.redstonegang.wire.game.base.addons.both.customItems;

import dev.feldmann.redstonegang.wire.game.base.addons.both.customItems.cmds.CmdCustomItem;
import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import dev.feldmann.redstonegang.wire.utils.items.ItemUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CustomItemsAddon extends Addon {

    public static final String ITEM_IDENTIFICATOR = "§0§m§n⺭";

    private List<CustomItem> loaded = new ArrayList<>();

    @Override
    public void onEnable() {
        registerCommand(new CmdCustomItem(this));
    }

    public CustomItem getCustomItemFromItemstack(ItemStack itemStack) {
        if (itemStack == null) return null;

        String lore = ItemUtils.findLore(itemStack, ITEM_IDENTIFICATOR, false);
        if (lore == null) {
            return null;
        }
        lore = lore.substring(ITEM_IDENTIFICATOR.length());

        for (CustomItem item : loaded) {
            if (item.identificator.equals(lore)) {
                return item;
            }
        }
        return null;
    }


    public void registerItem(CustomItem item) {
        loaded.add(item);
    }

    public void loadStaticItems(Class classe) {
        for (Field f : classe.getDeclaredFields()) {
            f.setAccessible(true);
            if (CustomItem.class.isAssignableFrom(f.getType())) {
                try {
                    CustomItem o = (CustomItem) f.get(null);
                    loaded.add(o);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public List<CustomItem> getItens() {
        return loaded;
    }

    boolean entity = false;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void right(PlayerInteractEntityEvent ev) {
        if (ev.isCancelled()) return;
        CustomItem item = getCustomItemFromItemstack(ev.getPlayer().getItemInHand());
        if (item != null) {
            if (item.interactEntity(ev)) {
                entity = true;
            }

        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void interact(PlayerInteractEvent ev) {
        if (ev.isCancelled()) return;

        if (entity) {
            entity = false;
            return;
        }
        CustomItem item = getCustomItemFromItemstack(ev.getPlayer().getItemInHand());
        if (item != null) {
            item.interact(ev);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void blockPlace(BlockPlaceEvent ev) {
        if (ev.isCancelled()) return;
        CustomItem item = getCustomItemFromItemstack(ev.getPlayer().getItemInHand());
        if (item != null) {
            item.blockPlace(ev);
        }
    }

}
