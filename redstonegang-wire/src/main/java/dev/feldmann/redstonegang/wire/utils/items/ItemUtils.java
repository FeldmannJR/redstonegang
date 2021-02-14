package dev.feldmann.redstonegang.wire.utils.items;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class ItemUtils {

    public static ItemStack getHead(String name) {
        ItemStack is = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
        SkullMeta meta = (SkullMeta) is.getItemMeta();
        meta.setOwner(name);
        is.setItemMeta(meta);
        return is;
    }

    public static ItemStack setItemName(ItemStack i, String name) {
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(name);
        i.setItemMeta(im);
        return i;
    }


    public static ItemStack setNbtInt(ItemStack it, String key, int value) {
        net.minecraft.server.v1_8_R3.ItemStack nms = CraftItemStack.asNMSCopy(it);
        if (!nms.hasTag()) {
            nms.setTag(new NBTTagCompound());
        }
        nms.getTag().setInt(key, value);
        return CraftItemStack.asBukkitCopy(nms);
    }

    public static Integer getNbtInt(ItemStack it, String key) {
        net.minecraft.server.v1_8_R3.ItemStack nms = CraftItemStack.asNMSCopy(it);
        if (nms.hasTag() && nms.getTag().hasKey(key)) {
            if (nms.getTag().hasKeyOfType(key, 3)) {
                return nms.getTag().getInt(key);
            }
        }
        return null;
    }

    public static String getItemName(ItemStack i) {
        if (i == null) {
            return null;
        }
        String itemname = "";
        ItemMeta im = i.getItemMeta();
        if (im == null) {
            return null;
        }
        if (!i.hasItemMeta()) {
            itemname = i.getType().name();
        } else if (im.getDisplayName() != null) {
            itemname = im.getDisplayName();
        } else {
            itemname = i.getType().name();
        }
        return itemname;
    }

    public static ItemStack addLore(ItemStack i, String... lore) {
        if (lore == null || lore.length == 0) {
            return i;
        }
        ItemMeta im = i.getItemMeta();
        List<String> aux = new ArrayList();
        if (im.getLore() != null) {
            aux = im.getLore();
        }
        for (String l : lore) {
            aux.add(l);
        }
        im.setLore(aux);
        i.setItemMeta(im);
        return i;
    }

    public static String findLore(ItemStack it, String startsWith, boolean ignoreColor) {
        if (it != null) {
            if (it.getItemMeta() != null && it.getItemMeta().getLore() != null) {
                for (String lore : it.getItemMeta().getLore()) {
                    if (ignoreColor) {
                        lore = ChatColor.stripColor(lore);
                    }
                    if (lore.startsWith(startsWith)) {
                        return lore;
                    }
                }
            }
        }
        return null;
    }

    public static void removeLoreStartsWith(ItemStack it, String startsWith, boolean ignoreColor) {
        if (it != null) {
            if (it.getItemMeta() != null && it.getItemMeta().getLore() != null) {
                List<String> lore = it.getItemMeta().getLore();
                for (String linha : new ArrayList<String>(lore)) {
                    String tmp = linha;
                    if (ignoreColor) {
                        tmp = ChatColor.stripColor(linha);
                    }
                    if (tmp.startsWith(startsWith)) {
                        lore.remove(linha);
                    }
                }
                setLore(it, lore);

            }
        }

    }


    public static List<String> getLore(ItemStack i) {
        ItemMeta im = i.getItemMeta();
        return im.getLore();
    }

    public static ItemStack setLore(ItemStack i, List<String> l) {
        ItemMeta im = i.getItemMeta();
        im.setLore(l);
        i.setItemMeta(im);
        return i;
    }

    public static ItemStack clearLore(ItemStack i) {
        List<String> aux = new ArrayList();
        ItemMeta im = i.getItemMeta();
        im.setLore(aux);
        i.setItemMeta(im);
        return i;
    }

    public static ItemStack clone(ItemStack it, int qtd) {
        ItemStack clone = it.clone();
        clone.setAmount(qtd);
        return clone;
    }

    public static ItemStack addColor(ItemStack item, Color cor) {
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        meta.setColor(cor);
        item.setItemMeta(meta);
        return item;
    }

    public static void dropItemToPlayerLikeFishing(Player p, Location loc, ItemStack it) {
        Item item = loc.getWorld().dropItem(loc, it);
        double d5 = p.getLocation().getX() - loc.getX();
        double d6 = p.getLocation().getY() - loc.getY();
        double d7 = p.getLocation().getZ() - loc.getZ();
        double d8 = Math.sqrt(d5 * d5 + d6 * d6 + d7 * d7);
        double d9 = 0.1D;
        item.setVelocity(new Vector(d5 * d9, d6 * d9 + Math.sqrt(d8) * 0.08D, d7 * d9));
    }

    public static ItemStack getEnchantmentBook(Enchantment ench, int lvl) {
        ItemStack it = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta m = (EnchantmentStorageMeta) it.getItemMeta();
        m.addStoredEnchant(ench, lvl, true);
        return it;
    }
}
