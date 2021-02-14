package dev.feldmann.redstonegang.wire.utils.items;

import dev.feldmann.redstonegang.wire.utils.msgs.C;
import dev.feldmann.redstonegang.common.utils.msgs.MsgType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class CItemBuilder extends ItemBuilder {

    protected CItemBuilder(ItemStack it) {
        super(it);
    }


    public static CItemBuilder item(Material m) {
        return new CItemBuilder(new ItemStack(m));
    }

    public static CItemBuilder item(ItemStack it) {
        return new CItemBuilder(it);
    }

    public static CItemBuilder head(String pname) {
        CItemBuilder b = new CItemBuilder(new ItemStack(Material.SKULL_ITEM));
        b.playerSkull(pname);
        return b;
    }


    @Override
    public ItemBuilder name(String name) {
        super.name(C.item(name));
        return this;
    }


    @Override
    public ItemBuilder lore(String... lore) {
        if (lore != null) {
            for (int x = 0; x < lore.length; x++) {
                lore[x] = C.itemDesc(lore[x]);
            }
        }
        super.lore(lore);
        return this;
    }
}
