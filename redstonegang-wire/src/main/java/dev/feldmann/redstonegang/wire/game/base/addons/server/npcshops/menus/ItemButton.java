package dev.feldmann.redstonegang.wire.game.base.addons.server.npcshops.menus;

import dev.feldmann.redstonegang.common.utils.formaters.numbers.NumberUtils;
import dev.feldmann.redstonegang.wire.game.base.addons.server.economy.EconomyAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.npcshops.NPCShopAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.npcshops.LojaItem;
import dev.feldmann.redstonegang.wire.modulos.menus.Button;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import dev.feldmann.redstonegang.wire.utils.items.InventoryHelper;
import dev.feldmann.redstonegang.wire.utils.items.ItemUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class ItemButton extends Button {

    LojaItem item;

    public ItemButton(LojaItem item) {
        super(generate(item));
        this.item = item;
    }


    @Override
    public void click(Player p, Menu m, ClickType click) {

        if (item.getPrecoVenda() != null && item.getPrecoCompra() == null) {
            buy(p, click.isShiftClick());
        }
        if (item.getPrecoVenda() == null && item.getPrecoCompra() != null) {
            sell(p, click.isShiftClick());
        }
    }

    public void sell(Player p, boolean click) {
        int qtd = click ? 10 : 1;
        int iqtd = qtd * item.getItem().getAmount();
        if (InventoryHelper.inventoryContains(p.getInventory(), item.getItem(), iqtd)) {
            InventoryHelper.removeInventoryItems(p.getInventory(), item.getItem(), iqtd);
            double preco = qtd * item.getPrecoCompra();
            NPCShopAddon.instance.a(EconomyAddon.class).add(p, preco);
            C.info(p, "Você vendeu %% %% por ## !", iqtd + "x", ItemUtils.clone(item.getItem(), 1), preco);
            p.updateInventory();
        } else {
            C.error(p, "Você não tem %% %% para vender !", iqtd + "x", ItemUtils.clone(item.getItem(), 1));
        }
    }

    public void buy(Player p, boolean shift) {
        int qtd = shift ? 10 : 1;
        int slots = qtd * item.getItem().getAmount();
        if (InventoryHelper.getInventoryFreeSpace(p, item.getItem()) < slots) {
            C.error(p, "Você não tem espaço no inventário!");
            return;
        }
        double preco = item.getPrecoVenda() * qtd;
        if (!NPCShopAddon.instance.a(EconomyAddon.class).hasWithMessage(p, preco)) {
            return;
        }

        NPCShopAddon.instance.a(EconomyAddon.class).remove(p, preco);

        for (int x = 0; x < qtd; x++) {
            p.getInventory().addItem(item.getItem().clone());
        }
        p.updateInventory();
        C.info(p, "Você comprou %% %% por ## !", slots + "x", ItemUtils.clone(item.getItem(), 1), preco);

    }

    private static ItemStack generate(LojaItem item) {
        ItemStack i = item.getItem().clone();
        ItemUtils.addLore(i, "");

        if (item.getPrecoVenda() != null && item.getPrecoCompra() == null) {
            ItemUtils.addLore(i, C.buy("%%: ##", "Comprar por", NumberUtils.convertToString(item.getPrecoVenda())));
            ItemUtils.addLore(i, C.itemDesc("Clique para comprar um"));
            ItemUtils.addLore(i, C.itemDesc("Shift + click para"));
            ItemUtils.addLore(i, C.itemDesc("comprar dez!"));
        }
        if (item.getPrecoCompra() != null && item.getPrecoVenda() == null) {
            ItemUtils.addLore(i, C.sell("%%: ##", "Vender por", NumberUtils.convertToString(item.getPrecoCompra())));
            ItemUtils.addLore(i, C.itemDesc("Clique para vender um"));
            ItemUtils.addLore(i, C.itemDesc("Shift + click para"));
            ItemUtils.addLore(i, C.itemDesc("vender 10x!"));
        }
        return i;

    }
}
