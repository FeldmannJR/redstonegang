package dev.feldmann.redstonegang.wire.game.base.addons.both.shop.tipos;


import dev.feldmann.redstonegang.common.currencies.Currencies;
import dev.feldmann.redstonegang.common.player.permissions.PermissionManager;
import dev.feldmann.redstonegang.common.player.permissions.PermissionValue;
import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.menus.edit.MenuEditPacotes;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.modulos.menus.buttons.SelectStringButton;
import dev.feldmann.redstonegang.wire.modulos.menus.menus.SellMenu;
import dev.feldmann.redstonegang.wire.utils.items.ItemBuilder;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ShopPermission extends ShopValuable {

    public String permission = null;

    @Override
    public void editPacket(Menu m) {
        super.editPacket(m);
        ShopPermission click = this;
        m.addNext(new SelectStringButton(ItemBuilder.item(Material.DOUBLE_PLANT).name("§aSetar Permissão").lore("§f" + permission).build()) {
            @Override
            public void accept(String integer, Player p) {
                if (integer != null) {
                    click.permission = integer;
                    addon.save(click);
                    C.info(p, "Pacote salvo");
                }
                new MenuEditPacotes(click, addon).open(p);
            }
        });
    }

    @Override
    public void click(Player p) {
        if (p.hasPermission(permission)) {
            C.error(p, "Você já tem esta permissão!");
            return;
        }
        new SellMenu(nome, Currencies.CASH, preco, getIcone()) {
            @Override
            public boolean compra(Player p, Menu m) {
                if (p.hasPermission(permission)) {
                    C.error(p, "Você já tem esta permissão!");
                    return false;
                }
                sendMessageComprou(p);
                addon.getUser(p).permissions().addPermission(PermissionManager.defaultServer, permission, PermissionValue.ALLOW);
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
