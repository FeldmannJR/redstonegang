package dev.feldmann.redstonegang.wire.utils.items;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class InventoryHelper {

    public static boolean removeInventoryItems(Inventory inv, ItemStack it, int amount) {
        for (int slot = 0; slot < inv.getSize(); slot++) {
            ItemStack is = inv.getItem(slot);
            if (isSame(is, it)) {
                int newamount = is.getAmount() - amount;
                if (newamount > 0) {
                    is.setAmount(newamount);
                    break;
                } else {
                    inv.setItem(slot, null);
                    amount = -newamount;
                    if (amount == 0) {
                        break;
                    }
                }
            }
        }

        return false;
    }


    public static List<ItemStack> removeInventoryItemsAndReturn(Inventory inv, ItemStack it, int amount) {
        List<ItemStack> itens = new ArrayList<>();
        for (ItemStack is : inv.getContents()) {
            if (isSame(is, it)) {
                int slot = inv.first(is.getType());
                int newamount = is.getAmount() - amount;
                if (newamount > 0) {
                    ItemStack clone = is.clone();
                    clone.setAmount(amount);
                    itens.add(clone);
                    is.setAmount(newamount);
                    break;
                } else {
                    inv.setItem(slot, null);
                    ItemStack clone = is.clone();
                    itens.add(clone);
                    amount = -newamount;
                    if (amount == 0) {
                        break;
                    }
                }
            }
        }
        return combine(itens);
    }

    public static ArrayList<ItemStack> combine(List<ItemStack> items) {
        ArrayList<ItemStack> sorted = new ArrayList<ItemStack>();
        for (ItemStack item : items) {
            if (item == null) continue;
            boolean putInPlace = false;
            for (ItemStack sitem : sorted) {
                if (isSame(sitem, item)) {
                    if (item.getAmount() + sitem.getAmount() < sitem.getMaxStackSize()) {
                        sitem.setAmount(sitem.getAmount() + item.getAmount());
                        putInPlace = true;
                    } else {
                        item.setAmount(item.getAmount() - (sitem.getMaxStackSize() - sitem.getAmount()));
                        sitem.setAmount(sitem.getMaxStackSize());
                    }
                    break;
                }
            }
            if (!putInPlace) {
                sorted.add(item);
            }
        }
        return sorted;
    }

    public static boolean isSame(ItemStack item1, ItemStack item2) {
        if (item1 == null || item2 == null) {
            return false;
        }
        List<Enchantment> dif = new ArrayList<>();

        for (Enchantment en : item1.getEnchantments().keySet()) {
            dif.add(en);
        }
        for (Enchantment en : item2.getEnchantments().keySet()) {
            if (!dif.contains(en)) {
                dif.add(en);
            } else {
                dif.remove(en);
            }
        }
        if (!dif.isEmpty()) {
            return false;
        }
        for (Enchantment en : item1.getEnchantments().keySet()) {
            if (item2.getEnchantmentLevel(en) != item1.getEnchantmentLevel(en)) {
                return false;
            }
        }
        return item1.isSimilar(item2);
    }

    public static boolean inventoryContains(Inventory inventory, ItemStack item, int quantidade) {
        int count = 0;
        ItemStack[] items = inventory.getContents();
        for (int i = 0; i < items.length; i++) {
            ItemStack is = items[i];
            if (isSame(is, item)) {
                count += is.getAmount();
            }
            if (count >= quantidade) {
                return true;
            }
        }
        return false;
    }

    public static int getItemAmountInInventory(Inventory inventory, ItemStack item) {
        int count = 0;
        ItemStack[] items = inventory.getContents();
        for (int i = 0; i < items.length; i++) {
            ItemStack is = items[i];
            if (isSame(is, item)) {
                count += is.getAmount();
            }
        }
        return count;
    }

    public static int getFreeSlots(Player p) {
        return getFreeSlots(p.getInventory());
    }

    public static int getFreeSlots(Inventory inv) {
        int free = 0;
        for (int x = 0; x < inv.getSize(); x++) {
            ItemStack it = inv.getItem(x);
            if (it == null || it.getType() == Material.AIR) {
                free++;
            }
        }
        return free;
    }

    public static int getInventoryFreeSpace(Player p, ItemStack item) {
        return getInventoryFreeSpace(p.getInventory(), item);
    }

    public static int getInventoryFreeSpace(Inventory inv, ItemStack item) {
        int free = 0;
        for (ItemStack i : inv) {
            if (i == null) {
                free += item.getMaxStackSize();
            } else if (i.isSimilar(item)) {
                free += item.getMaxStackSize() - i.getAmount();
            }
        }
        return free;
    }


    public static void give(Player p, ItemStack it, int amount) {
        for (ItemStack itt : getItemAmount(it, amount)) {
            p.getInventory().addItem(itt);
        }
    }


    public static List<ItemStack> getItemAmount(ItemStack it, int amount) {
        List<ItemStack> l = new ArrayList<>();

        while (amount > it.getMaxStackSize()) {
            ItemStack c = it.clone();
            c.setAmount(it.getMaxStackSize());
            l.add(c);
            amount -= it.getMaxStackSize();
        }
        ItemStack c = it.clone();
        c.setAmount(amount);
        l.add(c);
        return l;
    }

}
