package dev.feldmann.redstonegang.wire.game.base.addons.server.floatshop.menu.edit;

import dev.feldmann.redstonegang.wire.game.base.addons.server.floatshop.FloatItem;
import dev.feldmann.redstonegang.wire.game.base.addons.server.floatshop.FloatShopAddon;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.modulos.menus.buttons.*;
import dev.feldmann.redstonegang.wire.utils.items.CItemBuilder;
import dev.feldmann.redstonegang.wire.utils.items.ItemUtils;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import dev.feldmann.redstonegang.wire.modulos.menus.buttons.*;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class EditItemMenu extends Menu {

    FloatItem item;

    FloatShopAddon addon;

    public EditItemMenu(FloatShopAddon addon, FloatItem item) {
        super("Edit Item", 3);
        this.item = item;
        this.addon = addon;
        addItems();
    }

    private void addItems() {
        add(4, new DummyButton(generate(item)));

        int slot = 11;
        add((slot++), new SelectIntegerButton(
                CItemBuilder.item(Material.EMERALD)
                        .name("Base Preço Compra")
                        .lore("Atual: " + item.getBuyPrice())
                        .build(), "" + (int) item.getBuyPrice()) {
            @Override
            public void accept(Integer integer, Player p) {
                if (integer == null) {
                    reopen(p);
                    return;
                }
                if (integer < 0) {
                    C.error(p, "Valor Invalido!");
                    return;
                }
                item.setBuyPrice(integer);
                C.info(p, "Preço de compra setado para %%!", integer);
                reopen(p);
            }
        });
        add((slot++), new SelectIntegerButton(
                CItemBuilder.item(Material.RABBIT)
                        .name("Base Preço Venda")
                        .lore("Atual: " + item.getSellPrice())
                        .build(), (int) item.getSellPrice() + "") {
            @Override
            public void accept(Integer integer, Player p) {
                if (integer == null) {
                    reopen(p);
                    return;
                }
                if (integer < 0) {
                    C.error(p, "Valor Invalido!");
                    return;
                }
                item.setSellPrice(integer);
                C.info(p, "Preço de venda setado para %%!", integer);
                reopen(p);
            }
        });
        String current = (int) (item.getLowPercentage() * 100) + "";
        add((slot++), new SelectIntegerButton(
                CItemBuilder.item(Material.BROWN_MUSHROOM)
                        .name("Porcentagem em baixa")
                        .lore("Qual o valor minimo do item em porcentagem do valor base!", "Atual: " + current).build(), current) {
            @Override
            public void accept(Integer integer, Player p) {
                if (integer == null) {
                    reopen(p);
                    return;
                }
                if (integer > 100 || integer <= 0) {
                    C.error(p, "Valor Invalido!");
                    return;
                }
                double percentage = (double) integer / 100;
                item.setLowPercentage(percentage);
                C.info(p, "Porcentagem em baixa setado para %%!", integer);
                reopen(p);
            }
        });
        current = (int) (item.getHighPercentage() * 100) + "";
        add((slot++), new SelectIntegerButton(
                CItemBuilder.item(Material.RED_MUSHROOM)
                        .name("Porcentagem em alta")
                        .lore("Qual o valor maximo do item em porcentagem do valor base!", "Atual: " + current).build(), current) {
            @Override
            public void accept(Integer integer, Player p) {
                if (integer == null) {
                    reopen(p);
                    return;
                }
                if (integer < 100 || integer > 200) {
                    C.error(p, "Valor Invalido!");
                    return;
                }
                double percentage = (double) integer / 100.0d;
                item.setHighPercentage(percentage);
                C.info(p, "Porcentagem em alta setado para %%!", integer);
                reopen(p);
            }
        });
        add((slot++), new SelectIntegerButton(
                CItemBuilder.item(Material.ENDER_CHEST)
                        .name("Maximo items para variação!")
                        .split("Qual a quantidade maxima de itens que influencia a variação do preço!")
                        .desc("Após comprar/vender X itens o preço não vai mudar mais!")
                        .desc("Atual: %%", item.getMaxDifference())
                        .build(), item.getMaxDifference() + "") {
            @Override
            public void accept(Integer integer, Player p) {
                if (integer == null) {
                    reopen(p);
                    return;
                }
                if (integer < 5) {
                    C.error(p, "Valor precisa ser no minimo 5!");
                    reopen(p);
                    return;
                }
                item.setMaxDifference(integer);
                C.info(p, "Setado maximo items para variação %%!", integer);
                reopen(p);
            }
        });
        add((slot++), new ConfirmarButton(
                CItemBuilder.item(Material.STONE)
                        .name("Deixar o preço fixo!")
                        .desc("Seta o valor em alta e baixa para 100%(sem variação)")
                        .build(), "Deixar preço fixo", "Deixa a porcentagem em alta e baixa igual a 100(não varia)") {
            @Override
            public void confirmar(Player p) {
                item.setLowPercentage(1);
                item.setHighPercentage(1);
                C.info(p, "Item setado como fixo!");
                reopen(p);
            }

            @Override
            public void recusar(Player p) {
                reopen(p);
            }
        });
        slot = 21;
        add((slot++), new SelectIntegerButton(
                CItemBuilder.item(Material.CHEST)
                        .name("Maximo de items em estoque!")
                        .split("Quantos items a lojá terá no máximo em estoque! Obs: se a quantidade do item na loja for maior que um(ex 64) o estoque conta somente 1!")
                        .desc("Se setado para 0 a loja não usará estoque!")
                        .desc("Atual: %%", item.getMaxAvailable())
                        .build(), item.getMaxAvailable() + "") {
            @Override
            public void accept(Integer integer, Player p) {
                if (integer == null) {
                    reopen(p);
                    return;
                }
                if (integer < 0) {
                    C.error(p, "Valor precisa ser no minimo 0!");
                    reopen(p);
                    return;
                }
                item.setMaxAvailable(integer);
                C.info(p, "Setado maximo de itens no estoque para  %%!", integer);
                reopen(p);
            }
        });
        add((slot++), new SelectDoubleButton(
                CItemBuilder.item(Material.WATCH)
                        .name("Regeneração de itens!")
                        .split("Quantos itens a loja irá regenerar automaticamente por minuto! Se setado para 0 não irá regenerar nada e o estoque só irá aumentar com vendas de jogadores!")
                        .split("Pode usar números quebrados, ex 0.1 vai gerar um item a cada 10 minutos!")
                        .desc("Atual: %%", item.getPerMinuteRegen())
                        .build(), item.getPerMinuteRegen() + "") {
            @Override
            public void accept(Double integer, Player p) {
                if (integer == null) {
                    reopen(p);
                    return;
                }
                if (integer < 0) {
                    C.error(p, "Valor precisa ser no minimo 0!");
                    reopen(p);
                    return;
                }
                double v = integer.doubleValue();
                item.setPerMinuteRegen(v);
                C.info(p, "Setado regen de %% a cada minuto!", v);
                reopen(p);
            }
        });
        add((slot++), new SelectDoubleButton(
                CItemBuilder.item(Material.NAME_TAG)
                        .name("Estoque!")
                        .split("Quantos itens a loja tem atualmente!")
                        .desc("Atual: %%", item.getAvailable())
                        .build(), item.getAvailable() + "") {
            @Override
            public void accept(Double integer, Player p) {
                if (integer == null) {
                    reopen(p);
                    return;
                }
                if (integer < 0) {
                    C.error(p, "Valor precisa ser no minimo 0!");
                    reopen(p);
                    return;
                }
                item.setAvailable(integer);
                C.info(p, "Setado estoque para %% !", integer);
                reopen(p);
            }
        });


        add(0, new BackButton() {
            @Override
            public void click(Player p, Menu m, ClickType click) {
                back(p);
            }
        });

        add(8, new ConfirmarButton(
                CItemBuilder.item(Material.BARRIER)
                        .name("Deletar")
                        .build(), "Deletar o item", "Deletar o item do shop") {
            @Override
            public void confirmar(Player p) {
                addon.deleteItem(item);
                C.info(p, "Item deletado!");
                back(p);
            }

            @Override
            public void recusar(Player p) {
                reopen(p);
            }
        });
    }

    public void back(Player p) {
        new EditShopMenu(addon, addon.getShop(item.getShopId())).open(p);
    }

    public void reopen(Player p) {
        new EditItemMenu(addon, item).open(p);
    }


    private static ItemStack generate(FloatItem item) {
        double available = item.getAvailable();
        double buy = item.calculateBuyPrice();
        double sell = item.calculateSellPrice();
        ItemStack i = item.getItem().clone();
        ItemUtils.addLore(i, "");
        ItemUtils.addLore(i, C.buy("Preço Comprar: %%", buy));
        ItemUtils.addLore(i, C.sell("Preço Vender: %%", sell));
        ItemUtils.addLore(i, "");
        if (item.isItemFloat()) {
            ItemUtils.addLore(i, C.itemDesc(item.getVarianceString()));
        } else {
            ItemUtils.addLore(i, C.itemDesc("Preço normal!"));
        }
        ItemUtils.addLore(i, "");
        if (item.useStock()) {
            ItemUtils.addLore(i, C.itemDesc("Em Estoque: %%", available));
            ItemUtils.addLore(i, "");
        }

        return i;

    }

}
