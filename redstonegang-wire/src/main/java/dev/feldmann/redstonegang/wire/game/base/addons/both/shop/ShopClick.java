package dev.feldmann.redstonegang.wire.game.base.addons.both.shop;

import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.menus.Excluir;
import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.menus.edit.MenuEditIcon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.menus.edit.MenuEditPacotes;
import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.menus.edit.MenuListPacotes;
import dev.feldmann.redstonegang.wire.modulos.anvilgui.AnvilGUI;
import dev.feldmann.redstonegang.wire.modulos.menus.Button;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.utils.items.ItemBuilder;
import dev.feldmann.redstonegang.wire.utils.items.ItemUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;


public abstract class ShopClick {

    @Excluir
    public transient ShopAddon addon;

    @Excluir
    public int id = -1;
    @Excluir
    public int slot = -1;
    @Excluir
    public int pageid = -1;
    @Excluir
    public boolean invalid = false;


    public String nome = null;
    public ItemStack icone = null;


    public ItemStack getIcone() {
        if (icone != null) {
            return icone.clone();
        }
        return new ItemStack(Material.PAPER);
    }

    public ItemStack buildItemStack() {
        ItemStack icone = getIcone();
        ItemUtils.setItemName(icone, "§e§l" + nome);
        return icone;
    }

    public void setItemStack(ItemStack item) {
        this.icone = item;
    }

    public void editPacket(Menu m) {
        ShopClick click = this;
        m.addNext(new Button(ItemBuilder.item(Material.NAME_TAG).name("§aSetar nome").lore("§f" + nome).build()) {
            @Override
            public void click(Player player, Menu menu, ClickType clickType) {
                new AnvilGUI(player, nome, (p, string) -> {
                    if (invalid) return null;
                    click.nome = string;
                    addon.save(click);
                    new MenuEditPacotes(click, addon).open(p);
                    return null;
                }, 60);
            }
        });
        m.addNext(new Button(ItemBuilder.item(Material.FISHING_ROD).name("§aSetar Icone").lore("§f").build()) {
            @Override
            public void click(Player player, Menu menu, ClickType clickType) {
                new MenuEditIcon(icone) {
                    @Override
                    public void seleciona(ItemStack itemStack) {
                        click.icone = itemStack;
                        addon.save(click);
                        new MenuEditPacotes(click, addon).open(player);


                    }
                }.open(player);
            }
        });
        m.addNext(new Button(ItemBuilder.item(Material.BARRIER).name("§c§lDELETAR").build()) {
            @Override
            public void click(Player player, Menu menu, ClickType clickType) {
                addon.delete(click);
                new MenuListPacotes(addon).open(player);
            }
        });


    }

    public abstract void click(Player p);

}
