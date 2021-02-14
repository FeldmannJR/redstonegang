package dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.extrachests;

import dev.feldmann.redstonegang.common.utils.msgs.MsgType;
import dev.feldmann.redstonegang.wire.RedstoneGangSpigot;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import dev.feldmann.redstonegang.wire.utils.items.ItemUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.jooq.Field;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

public enum ChestList {
    ENDERCHEST(27, ExtraChests.ENDERCHEST_PERMISSION, null, "Enderchest", null),
    VIP_1(54, ExtraChests.EXTRA_CHEST_VIP, new MaterialData(Material.DIAMOND), "Baú VIP", 2),
    EXTRA_1(54, ExtraChests.EXTRA_CHEST_1, new MaterialData(Material.CHEST), "Baú Extra 1", 4),
    EXTRA_2(54, ExtraChests.EXTRA_CHEST_2, new MaterialData(Material.CHEST), "Baú Extra 2", 5),
    EXTRA_3(54, ExtraChests.EXTRA_CHEST_3, new MaterialData(Material.CHEST), "Baú Extra 3", 6);

    private int size;
    private String permission;

    private MaterialData item;
    private String nome;
    private Integer slot;

    ChestList(int size, String permission, MaterialData item, String nome, Integer slot) {
        this.size = size;
        this.permission = permission;
        this.item = item;
        this.nome = nome;
        this.slot = slot;
    }

    public Integer getSlot() {
        return slot;
    }

    public Field<byte[]> getField() {
        return DSL.field(name().toLowerCase(), SQLDataType.BLOB.nullable(true));

    }

    public String getNome() {
        return nome;
    }

    public String getPermission() {
        return permission;
    }

    public boolean hasPermission(int pId) {
        return permission == null || RedstoneGangSpigot.getPlayer(pId).permissions().hasPermission(permission);
    }

    public boolean hasPermission(Player p) {
        return permission == null || p.hasPermission(permission);
    }

    public ItemStack buildItemstack(int pid) {
        ItemStack it = item.toItemStack(1);
        if (permission == null || RedstoneGangSpigot.getPlayer(pid).permissions().hasPermission(permission)) {
            ItemUtils.setItemName(it, C.msg(MsgType.ITEM_CAN, nome));
        } else {
            it = new ItemStack(Material.BARRIER);
            ItemUtils.setItemName(it, C.msg(MsgType.ITEM_CANT, "Bloqueado!"));
            ItemUtils.addLore(it, C.msg(MsgType.ITEM_CANT_DESC, "Você não pode usar este bau!"));
        }
        return it;
    }

    public int getSize() {
        return size;
    }
}
