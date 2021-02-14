package dev.feldmann.redstonegang.wire.game.base.addons.both.shop.menus.edit;

import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.ShopClick;
import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.ShopAddon;
import dev.feldmann.redstonegang.wire.modulos.menus.Button;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.utils.items.ItemBuilder;
import dev.feldmann.redstonegang.wire.utils.items.ItemUtils;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class MenuEditSlots extends Menu {
    int pageid;
    ShopAddon addon;


    public MenuEditSlots(int pageid, ShopAddon addon) {
        super("Editando Shop", addon.getLines(pageid));
        int lines = addon.getLines(pageid);
        this.addon = addon;
        this.pageid = pageid;
        if (pageid != -1) {

            add((lines - 1) * 9, new Button(ItemBuilder.item(Material.DIODE).name("§e§lVoltar").build()) {
                @Override
                public void click(Player player, Menu menu, ClickType clickType) {
                    new MenuEditPacotes(addon.getShopPage(pageid), addon).open(player);
                }
            });

        }
        for (ShopClick click : addon.getPage(pageid)) {
            ItemStack it = click.buildItemStack();
            ItemUtils.addLore(it, "§cClique para remover!");
            add(click.slot, new Button(it) {
                @Override
                public void click(Player player, Menu menu, ClickType clickType) {
                    addNothing(click.slot);
                    click.slot = -1;
                    click.pageid = -1;
                    addon.save(click);


                }
            });

        }
        for (int x = 0; x < lines * 9; x++) {
            if (getButton(x) == null) {
                addNothing(x);
            }
        }
    }

    public void addNothing(int slot) {
        if (getButton(slot) != null) {
            removeButton(slot);
        }
        add(slot, new Button(ItemBuilder.item(Material.STAINED_GLASS_PANE).color(DyeColor.ORANGE).name("§e§lClique para adicionar").build()) {
            @Override
            public void click(Player player, Menu menu, ClickType clickType) {
                new MenuViewPacotes(addon) {
                    @Override
                    public void clickPacote(Player p, ShopClick click) {
                        click.slot = slot;
                        click.pageid = pageid;
                        addon.save(click);
                        new MenuEditSlots(pageid, addon).open(p);
                    }

                    @Override
                    public ItemStack trabalhaItem(ShopClick click, ItemStack it) {
                        ItemUtils.addLore(it, "§eClique para selecionar!");
                        return it;
                    }

                    @Override
                    public boolean shouldSkip(ShopClick click) {
                        if (click.slot != -1) {
                            return true;
                        }
                        return click.id == pageid;
                    }
                }.open(player);
            }
        });
    }
}
