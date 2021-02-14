package dev.feldmann.redstonegang.wire.game.base.addons.server.chestshops.menus;

import dev.feldmann.redstonegang.common.utils.formaters.numbers.NumberUtils;
import dev.feldmann.redstonegang.wire.game.base.addons.server.chestshops.ChestShop;
import dev.feldmann.redstonegang.wire.game.base.addons.server.chestshops.ChestShopAddon;
import dev.feldmann.redstonegang.wire.modulos.anvilgui.AnvilGUI;
import dev.feldmann.redstonegang.wire.modulos.menus.Button;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.utils.items.CItemBuilder;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import dev.feldmann.redstonegang.wire.utils.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class EditShop extends Menu {

    ChestShop shop;

    public EditShop(ChestShopAddon manager, ChestShop shop) {
        super("Editando Shop", 2);
        this.shop = shop;
        add(4, new Button(ItemBuilder.item(Material.CHEST).name(C.item("Itens do Bau")).build()) {
            @Override
            public void click(Player p, Menu m, ClickType click) {
                if (!shop.deleted) {
                    new SelectItemFromChest(manager, shop).open(p);

                } else {
                    p.closeInventory();
                }
            }
        });

        add(15, new Button(
                CItemBuilder.item(Material.CARROT_ITEM)
                        .name("Preço Compra")
                        .descBreak("Qual preço você irá comprar o item dos outros jogadores!")
                        .desc("Preço Atual: %%", (shop.getPrecoCompra() != 0 ? shop.getPrecoCompra() : "Não setado"))
                        .build()) {
            @Override
            public void click(Player p, Menu m, ClickType click) {
                if (!shop.deleted) {
                    new AnvilGUI(p, shop.getPrecoCompra() != null ? shop.getPrecoCompra() + "" : "", (ap, s) -> {
                        Integer integer = NumberUtils.convertFromString(s);
                        if (integer == null || integer < 0) {
                            C.error(p, "Preço inválido!");
                            return null;
                        }
                        if (!validateInteger(p, integer)) {
                            return null;
                        }
                        if (!shop.deleted) {
                            shop.setPrecoCompra(integer);
                            manager.save(shop);
                            C.info(p, "Setado preço de compra para: %% ", integer);
                        }
                        return null;
                    }, 60);
                }
            }
        });
        add(11, new Button(CItemBuilder.item(Material.EMERALD).name("Preço Venda")
                .descBreak("Qual preço você irá vender o item para os outros jogadores!")
                .desc("Preço Atual: %%", (shop.getPrecoVenda() != 0 ? shop.getPrecoVenda() : "Não setado"))
                .build()) {
            @Override
            public void click(Player p, Menu m, ClickType click) {
                if (!shop.deleted) {
                    new AnvilGUI(p, shop.getPrecoVenda() != null ? shop.getPrecoVenda() + "" : "", (ap, s) -> {
                        Integer integer = NumberUtils.convertFromString(s);
                        if (integer == null || integer < 0) {
                            C.error(p, "Preço inválido!");
                            return null;
                        }
                        if (!validateInteger(p, integer)) {
                            return null;
                        }
                        if (!shop.deleted) {
                            shop.setPrecoVenda(integer);

                            manager.save(shop);
                            C.info(p, "Setado preço de venda para: %%", integer);
                        }
                        return null;
                    }, 60);
                }
            }
        });
        add(13, new Button(
                ItemBuilder.item(Material.GOLD_INGOT)
                        .name(C.item("Quantidade"))
                        .lore(C.itemDesc("Quantidade: %%", (shop.getItem() != null ? shop.getItem().getAmount() : "Não setado")))
                        .build()) {
            @Override
            public void click(Player p, Menu m, ClickType click) {
                if (shop.getItem() == null) {
                    C.error(p, "Primeiro configure um item!");
                    return;
                }
                if (!shop.deleted) {
                    new AnvilGUI(p, shop.getItem() != null ? shop.getItem().getAmount() + "" : "", (ap, s) -> {
                        Integer integer = NumberUtils.convertFromString(s);
                        if (integer == null || integer <= 0) {
                            C.error(p, "Quantidade inválida!");
                            return null;
                        }
                        if (integer > shop.getItem().getMaxStackSize()) {
                            C.error(p, "Quantidade maxima do item é %%", shop.getItem().getMaxStackSize());
                            return null;
                        }


                        if (!shop.deleted) {
                            shop.setAmount(integer);
                            manager.save(shop);
                            C.info(p, "Quantidade setada para %%: ", integer);
                        }
                        return null;
                    }, 60);
                }
            }
        });


    }


    public boolean validateInteger(Player p, int x) {
        if (x > 100000) {
            if (x % 1000 != 0) {
                C.error(p, "Valores acima de 100k precisam ser exatos multiplos de 1000!");
                return false;
            }
        }
        return true;
    }
}
