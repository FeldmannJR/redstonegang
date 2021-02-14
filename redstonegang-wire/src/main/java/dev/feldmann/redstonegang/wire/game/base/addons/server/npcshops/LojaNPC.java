package dev.feldmann.redstonegang.wire.game.base.addons.server.npcshops;

import dev.feldmann.redstonegang.common.utils.formaters.numbers.NumberUtils;
import dev.feldmann.redstonegang.wire.utils.items.ItemUtils;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LojaNPC {

    public static final String LORE_VENDA = "§fPreco Venda: §a";
    public static final String LORE_COMPRA = "§cPreco Compra: §e";


    private UUID uuid;
    private List<LojaItem> itens = new ArrayList();
    private int size;
    private String permission;
    public boolean editing = false;
    protected NPCShopAddon manager;

    public LojaNPC(UUID uuid, int size, String permission) {
        this.uuid = uuid;
        this.size = size;
        this.permission = permission;
    }

    public static LojaNPC fromItems(UUID uid, String permission, ItemStack[] itens) {
        LojaNPC npc = new LojaNPC(uid, itens.length, permission);
        npc.updateFromItemstack(itens);
        return npc;
    }

    //Isso aqui é gambiarra obvia, mas o tempo nao ajuda
    public void updateFromItemstack(ItemStack[] itens) {
        this.itens.clear();
        for (int x = 0; x < itens.length; x++) {
            ItemStack it = itens[x];
            if (it != null) {
                Double venda = getPrecoVenda(it);
                Double compra = getPrecoCompra(it);
                it = it.clone();
                ItemUtils.removeLoreStartsWith(it, LORE_COMPRA, false);
                ItemUtils.removeLoreStartsWith(it, LORE_VENDA, false);
                LojaItem item = new LojaItem(it, venda, compra, x);
                this.itens.add(item);
            }

        }
    }

    public ItemStack[] toItemStackArray() {
        ItemStack[] itns = new ItemStack[size];
        for (LojaItem it : itens) {
            itns[it.getSlot()] = it.generateWithPrice();
        }
        return itns;
    }

    private static Double getPrecoVenda(ItemStack it) {
        String lore = ItemUtils.findLore(it, LORE_VENDA, false);
        if (lore != null) {
            String n = lore.replace(LORE_VENDA, "");
            return NumberUtils.doubleFromString(n);
        }
        return null;
    }

    public int firstEmpty() {
        SLOT:
        for (int x = 0; x < getSize(); x++) {
            for (LojaItem it : getItens()) {
                if (it.getSlot() == x) {
                    continue SLOT;
                }
            }
            return x;
        }
        return -1;
    }

    public String getPermission() {
        return permission;
    }

    private static Double getPrecoCompra(ItemStack it) {
        String lore = ItemUtils.findLore(it, LORE_COMPRA, false);
        if (lore != null) {
            String n = lore.replace(LORE_COMPRA, "");
            return NumberUtils.doubleFromString(lore);
        }
        return null;
    }

    public NPC getNPC() {
        return CitizensAPI.getNPCRegistry().getByUniqueId(uuid);
    }

    public int getSize() {
        return size;
    }

    public UUID getUUID() {
        return uuid;
    }

    public List<LojaItem> getItens() {
        return itens;
    }

    public void save() {
        manager.save(this);
    }
}
