package dev.feldmann.redstonegang.wire.utils.items;

import dev.feldmann.redstonegang.common.utils.formaters.StringUtils;
import dev.feldmann.redstonegang.common.utils.msgs.MsgType;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder {

    protected ItemStack item;

    public static ItemBuilder item(Material m) {
        return new ItemBuilder(m);
    }

    public static ItemBuilder item(MaterialData data) {
        return new ItemBuilder(data.toItemStack(1));
    }

    public static ItemBuilder item(ItemStack it) {
        return new ItemBuilder(it);
    }


    public ItemStack build() {
        return item;
    }

    private ItemBuilder(Material m) {
        this.item = new ItemStack(m);
    }


    protected ItemBuilder(ItemStack it) {
        ItemMeta meta = it.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        it.setItemMeta(meta);
        this.item = it;
    }

    public ItemBuilder amount(int amnt) {
        this.item.setAmount(amnt);
        return this;
    }

    public ItemBuilder split(String lore) {
        List<String> quebra = StringUtils.quebra(30, lore);
        for (String s : quebra) {
            desc(s);
        }
        return this;
    }

    public ItemBuilder desc(String lore, Object... values) {
        ItemUtils.addLore(item, C.itemDesc(lore, values));
        return this;
    }


    public ItemBuilder descBreak(String desc) {
        for (String string : StringUtils.quebra(30, desc)) {
            desc(string);
        }
        return this;
    }

    public ItemBuilder name(MsgType msgType, String text, Object... vars) {
        this.name(C.msg(msgType, text, vars));
        return this;
    }

    public ItemBuilder lore(MsgType msgType, String text, Object... vars) {
        this.lore(C.msg(msgType, text, vars));
        return this;
    }


    public ItemBuilder lore(String... lore) {
        ItemMeta meta = item.getItemMeta();
        List<String> lo = meta.getLore() == null ? new ArrayList() : meta.getLore();
        for (String s : lore) {
            lo.add(s);
        }
        meta.setLore(lo);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder name(String name) {

        ItemMeta meta = item.getItemMeta();
        if (name != null) {
            meta.setDisplayName(name);
        }
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder data(byte data) {
        this.item.setDurability(data);
        return this;
    }

    public ItemBuilder glow() {
        item.addUnsafeEnchantment(Enchantment.LUCK, 1);
        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder color(DyeColor cor) {
        byte data;
        if (this.item.getType() == Material.INK_SACK) {
            data = cor.getDyeData();
        } else {
            data = cor.getWoolData();
        }

        return data(data);
    }

    public ItemBuilder playerSkull(String nick) {
        item.setDurability((short) 3);
        ItemMeta meta = item.getItemMeta();
        if (meta instanceof SkullMeta) {
            if (nick != null) {
                ((SkullMeta) meta).setOwner(nick);
            }
        }
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder skull(SkullType type) {
        return this;
    }

    public ItemBuilder lore(List<String> lore) {
        return this.lore(lore.toArray(new String[lore.size()]));
    }
}
