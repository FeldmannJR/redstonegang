package dev.feldmann.redstonegang.wire.game.base.addons.both.shop.menus.edit;


import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.ShopClick;
import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.ShopAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.menus.ShopMenuBase;
import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.tipos.ShopItemStack;
import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.tipos.ShopPacoteAleatorio;
import dev.feldmann.redstonegang.wire.modulos.menus.Button;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.utils.items.ItemBuilder;
import dev.feldmann.redstonegang.wire.utils.items.ItemUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Iterator;

public class MenuSelecionaKits extends Menu implements ShopMenuBase {

    boolean add;

    public MenuSelecionaKits(ShopPacoteAleatorio ale, ShopAddon addon) {
        super("Selecionando", 6);
        ale.valida();
        for (int x : ale.pacotes) {
            ShopItemStack it = (ShopItemStack) addon.getClickById(x);
            ItemStack item = it.buildItemStack();
            ItemUtils.addLore(item, "§cClique para remover!");
            addNext(new Button(it.buildItemStack()) {
                @Override
                public void click(Player player, Menu menu, ClickType clickType) {
                    Iterator<Integer> iter = ale.pacotes.iterator();
                    while (iter.hasNext()) {
                        int y = iter.next();
                        if (y == x) {
                            iter.remove();
                        }
                    }
                    addon.save(ale);
                }
            });
        }
        if (ale.pacotes.size() < 45)
            add(49, new Button(ItemBuilder.item(Material.EMERALD).name("§aAdicionar pacote").build()) {
                @Override
                public void click(Player player, Menu menu, ClickType clickType) {
                    new MenuViewPacotes(addon) {
                        @Override
                        public void clickPacote(Player p, ShopClick click) {
                            ale.pacotes.add(click.id);
                            addon.save(ale);
                            new MenuSelecionaKits(ale, addon).open(p);
                        }

                        @Override
                        public ItemStack trabalhaItem(ShopClick click, ItemStack it) {
                            ItemUtils.addLore(it, "§a§lClique para adicionar!");
                            return it;
                        }

                        @Override
                        public boolean shouldSkip(ShopClick click) {
                            if (click.getClass() == ShopItemStack.class) {
                                return ale.pacotes.contains(click.id);
                            }
                            return true;
                        }
                    }.open(player);
                }
            });
        add(45, new Button(ItemBuilder.item(Material.DIODE).name("§e§lVoltar").build()) {
            @Override
            public void click(Player player, Menu menu, ClickType clickType) {
                new MenuEditPacotes(ale, addon).open(player);
            }
        });
    }
}
