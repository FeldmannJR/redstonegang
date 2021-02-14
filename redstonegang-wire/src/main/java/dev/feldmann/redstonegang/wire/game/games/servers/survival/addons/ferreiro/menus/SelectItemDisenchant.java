package dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.ferreiro.menus;

import dev.feldmann.redstonegang.common.utils.formaters.time.TimeUtils;
import dev.feldmann.redstonegang.common.utils.msgs.MsgType;
import dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.ferreiro.FerreiroAddon;
import dev.feldmann.redstonegang.wire.modulos.menus.Button;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.modulos.menus.buttons.BackButton;
import dev.feldmann.redstonegang.wire.utils.items.CItemBuilder;
import dev.feldmann.redstonegang.wire.utils.items.InventoryHelper;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class SelectItemDisenchant extends Menu {


    FerreiroAddon addon;

    public SelectItemDisenchant(Player p, FerreiroAddon addon) {
        super("Selecionar Item", 4);
        this.addon = addon;
        addItens(p);
        add(27, new BackButton() {
            @Override
            public void click(Player p, Menu m, ClickType click) {
                new FerreiroMenu(p, addon).open(p);
            }
        });

    }


    public void addItens(Player p) {
        for (ItemStack it : p.getInventory()) {
            if (it != null && it.getType() != Material.AIR) {

                ItemStack clone = it.clone();
                if (!clone.getEnchantments().isEmpty()) {

                    addNext(new Button(CItemBuilder.item(clone.clone()).descBreak("Clique aqui para remover um encantamento deste item!").build()) {
                        @Override
                        public void click(Player p, Menu m, ClickType click) {
                            if (!InventoryHelper.inventoryContains(p.getInventory(), clone, clone.getAmount())) {
                                C.error(p, "Ocorreu um erro!");
                                p.closeInventory();
                                return;
                            }

                            new SelectEnchantItem(p, addon, clone).open(p);
                        }
                    });
                }
            }
        }

    }
}
