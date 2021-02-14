package dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.ferreiro.menus;

import dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.ferreiro.FerreiroAddon;
import dev.feldmann.redstonegang.wire.modulos.language.lang.LanguageHelper;
import dev.feldmann.redstonegang.wire.modulos.menus.Button;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.modulos.menus.buttons.BackButton;
import dev.feldmann.redstonegang.wire.utils.items.CItemBuilder;
import dev.feldmann.redstonegang.wire.utils.items.InventoryHelper;
import dev.feldmann.redstonegang.wire.utils.items.ItemUtils;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class SelectEnchantItem extends Menu {


    FerreiroAddon addon;
    private ItemStack item;

    public SelectEnchantItem(Player p, FerreiroAddon addon, ItemStack item) {
        super("Selecionar Encantamento", 4);
        this.addon = addon;
        this.item = item;
        addItens(p);
        add(27, new BackButton() {
            @Override
            public void click(Player p, Menu m, ClickType click) {
                new SelectItemDisenchant(p, addon).open(p);
            }
        });

    }


    public void addItens(Player p) {
        if (item != null && item.getType() != Material.AIR) {
            ItemStack clone = item.clone();
            if (!clone.getEnchantments().isEmpty()) {

                for (Enchantment enchantment : clone.getEnchantments().keySet()) {
                    final int level = clone.getEnchantmentLevel(enchantment);
                    final String nome = LanguageHelper.getEnchantmentDisplayName(enchantment, level);
                    addNext(new Button(
                            CItemBuilder.item(
                                    ItemUtils.getEnchantmentBook(enchantment, level))
                                    .desc("Clique aqui para remover")
                                    .desc("o encantamento %% !", nome)
                                    .build()) {
                        @Override
                        public void click(Player p, Menu m, ClickType click) {
                            for (ItemStack item : p.getInventory().getContents()) {
                                if (InventoryHelper.isSame(item, clone)) {
                                    item.removeEnchantment(enchantment);
                                    C.info(p, "Removido encantamento %% !", nome);
                                    new SelectItemDisenchant(p, addon).open(p);
                                    return;
                                }
                            }
                            C.error(p, "Ocorreu um erro!");
                            p.closeInventory();
                        }
                    });
                }
            }

        }

    }
}
