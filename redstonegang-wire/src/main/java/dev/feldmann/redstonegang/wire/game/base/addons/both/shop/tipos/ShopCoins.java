package dev.feldmann.redstonegang.wire.game.base.addons.both.shop.tipos;


import dev.feldmann.redstonegang.common.currencies.Currencies;
import dev.feldmann.redstonegang.wire.game.base.addons.server.economy.EconomyAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.menus.edit.MenuEditPacotes;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.modulos.menus.buttons.SelectIntegerButton;
import dev.feldmann.redstonegang.wire.modulos.menus.menus.SellMenu;
import dev.feldmann.redstonegang.wire.utils.items.ItemBuilder;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ShopCoins extends ShopValuable {

    public int coins = 0;

    @Override
    public void editPacket(Menu m) {
        super.editPacket(m);
        ShopCoins click = this;
        m.addNext(new SelectIntegerButton(ItemBuilder.item(Material.DOUBLE_PLANT).name("§aSetar Coins").lore("§f" + coins).build()) {
            @Override
            public void accept(Integer integer, Player p) {
                if (integer != null) {
                    click.coins = preco;
                    addon.save(click);
                    C.info(p, "Pacote salvo");
                }
                new MenuEditPacotes(click, addon).open(p);
            }
        });
    }

    @Override
    public void click(Player p) {
        new SellMenu(nome, Currencies.CASH, preco, getIcone()) {
            @Override
            public boolean compra(Player p, Menu m) {
                addon.a(EconomyAddon.class).add(addon.getPlayerId(p), coins);
                sendMessageComprou(p);
                return true;
            }

            @Override
            public void quit(Player p, QuitType type) {
                if (type == QuitType.SUCCESS) {
                    p.closeInventory();
                } else {
                    addon.openPlayer(p, pageid);
                }
            }

        }.open(p);
    }

    @Override
    public boolean compraPacote(Player p) {
        return false;

    }


}
