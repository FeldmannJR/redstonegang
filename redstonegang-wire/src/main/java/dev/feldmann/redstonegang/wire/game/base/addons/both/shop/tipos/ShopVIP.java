package dev.feldmann.redstonegang.wire.game.base.addons.both.shop.tipos;


import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.ShopClick;
import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.menus.edit.MenuEditPacotes;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.modulos.menus.buttons.SelectStringButton;
import dev.feldmann.redstonegang.wire.utils.items.ItemBuilder;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ShopVIP extends ShopClick {

    public String link = null;

    @Override
    public void editPacket(Menu m) {
        super.editPacket(m);
        ShopVIP click = this;
        m.addNext(new SelectStringButton(ItemBuilder.item(Material.SIGN).name("§aSetar Link").lore("§f" + link).build()) {
            @Override
            public void accept(String integer, Player p) {
                if (integer != null) {
                    click.link = integer;
                    addon.save(click);
                    C.info(p, "Pacote salvo!");
                }
                new MenuEditPacotes(click, addon).open(p);
            }
        });
    }

    @Override
    public void click(Player p) {
        C.blank(p, 2);
        C.send(p, null, "§6Para ver informações sobre o VIP §f" + this.nome + "§6 acesse o link:");
        C.send(p, null, "%url%", "§f§n" + this.link);
        p.closeInventory();
    }


}
