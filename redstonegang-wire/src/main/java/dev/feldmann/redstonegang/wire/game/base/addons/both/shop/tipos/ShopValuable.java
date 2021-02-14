package dev.feldmann.redstonegang.wire.game.base.addons.both.shop.tipos;

import dev.feldmann.redstonegang.wire.RedstoneGangSpigot;
import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.ShopClick;

import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.menus.edit.MenuEditPacotes;
import dev.feldmann.redstonegang.wire.modulos.menus.Button;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.modulos.menus.buttons.SelectIntegerButton;
import dev.feldmann.redstonegang.wire.modulos.menus.menus.SellMenu;
import dev.feldmann.redstonegang.wire.utils.items.ItemBuilder;
import dev.feldmann.redstonegang.wire.utils.items.ItemUtils;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import dev.feldmann.redstonegang.common.utils.msgs.MsgType;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public abstract class ShopValuable extends ShopClick {

    public int preco = -1;

    @Override
    public ItemStack buildItemStack() {
        ItemStack it = super.buildItemStack();
        ItemUtils.addLore(it, "§f§lPreço: §a§l" + addon.currency.getValue(preco) + "!");
        ItemUtils.addLore(it, "§7Clique para ver!");
        return it;
    }


    public SellMenu getVenda(Player p) {
        ShopValuable valuable = this;

        return new SellMenu(nome, addon.currency, preco, getIcone(), (backPlayer) -> {
            valuable.click(backPlayer);
        }, false) {
            @Override
            public boolean compra(Player p, Menu m) {
                return compraPacote(p);
            }

            @Override
            public void quit(Player p, QuitType type) {
                valuable.click(p);
            }

        };
    }

    public ItemStack getComprarIcone() {
        return ItemBuilder.item(Material.EMERALD_BLOCK).name("§a§lComprar").lore("§fClique para comprar", "§fpor " + addon.currency.getValue(preco) + "!").build();
    }

    public Button getComprarButton() {
        ShopValuable valuable = this;
        return new Button(getComprarIcone()) {
            @Override
            public void click(Player player, Menu menu, ClickType clickType) {
                getVenda(player).open(player);
            }
        };

    }

    public void sendMessageComprou(Player p) {
        p.sendMessage("§a[Shop] §eVocê comprou §9" + nome + "§e por §a" + preco + " gemas!");
        p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);
        if (RedstoneGangSpigot.getPlayer(p).getConfig(addon.SHOW_BUY_MESSAGES)) {
            addon.broadcast(C.msgText(MsgType.BUY, "%% comprou %% por %%", p, nome, addon.currency.getValue(preco)));
        }
    }

    public void comprou(Player p) {
        sendMessageComprou(p);
    }

    @Override
    public void editPacket(Menu m) {
        super.editPacket(m);
        ShopValuable click = this;

        m.addNext(new SelectIntegerButton(ItemBuilder.item(Material.DOUBLE_PLANT).name("§aSetar Preco").lore("§f" + preco).build(), 0) {

            @Override
            public void accept(Integer integer, Player p) {
                if (integer != null) {
                    click.preco = integer;
                    addon.save(click);
                }
                new MenuEditPacotes(click, addon).open(p);
            }
        });
    }

    public abstract boolean compraPacote(Player p);
}
