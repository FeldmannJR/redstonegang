package dev.feldmann.redstonegang.wire.game.base.addons.both.customItems;

import dev.feldmann.redstonegang.wire.utils.items.ItemUtils;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;


public abstract class CustomItem {


    private ItemStack i;
    private String name;
    private ItemRarity rarity;
    String identificator;
    private List<String> lore;
    private boolean glow = false;


    public CustomItem(ItemStack i, String name, String identificator, String... lore) {
        this(i, name, identificator, ItemRarity.COMMON, lore);
    }

    public CustomItem(ItemStack i, String name, String identificator, ItemRarity rarity, String... lore) {
        this.i = i;
        this.name = name;
        this.rarity = rarity;
        this.lore = new ArrayList<String>();
        this.identificator = identificator;
        if (lore != null) {
            for (String l : lore) {
                this.lore.add(l);
            }
        }

    }

    public ItemStack generateItem(int size) {
        ItemStack item = i.clone();
        item.setAmount(size);
        ItemUtils.setItemName(item, rarity.color + name);
        for (String l : lore) {
            ItemUtils.addLore(item, rarity.descColor + l);
        }
        ItemUtils.addLore(item, CustomItemsAddon.ITEM_IDENTIFICATOR + "" + identificator);
        if (glow) {
            item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        }
        return item;
    }

    public void interact(PlayerInteractEvent ev) {
    }

    public boolean interactEntity(PlayerInteractEntityEvent ev) {
        return false;
    }

    public boolean blockPlace(BlockPlaceEvent ev) {
        return false;
    }

    public void consomeItem(Player p) {

    }

}
