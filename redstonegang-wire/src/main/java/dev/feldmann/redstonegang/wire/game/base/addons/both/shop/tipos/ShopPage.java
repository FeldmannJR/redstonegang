package dev.feldmann.redstonegang.wire.game.base.addons.both.shop.tipos;

import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.ShopClick;
import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.menus.edit.MenuEditPacotes;
import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.menus.edit.MenuEditSlots;
import dev.feldmann.redstonegang.wire.modulos.menus.Button;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.modulos.menus.buttons.SelectIntegerButton;
import dev.feldmann.redstonegang.wire.utils.items.ItemBuilder;
import dev.feldmann.redstonegang.wire.utils.items.ItemUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class ShopPage extends ShopClick {

    public int lines = 6;


    @Override
    public void click(Player p) {
        addon.openPlayer(p, id);
    }

    @Override
    public ItemStack buildItemStack() {
        ItemStack it = super.buildItemStack();
        ItemUtils.addLore(it, "§a=>§f Clique para abrir a pagina!");
        return it;
    }

    @Override
    public void editPacket(Menu m) {
        super.editPacket(m);
        ShopPage page = this;
        m.addNext(new Button(ItemBuilder.item(Material.GLASS).name("§c§lSetar slots").build()) {
            @Override
            public void click(Player player, Menu menu, ClickType clickType) {
                new MenuEditSlots(page.id, addon).open(player);
            }
        });

        m.addNext(new SelectIntegerButton(ItemBuilder.item(Material.WORKBENCH).name("§aSetar Linhas").lore("§f" + lines).build(), 1, 6) {


            @Override
            public void accept(Integer integer, Player p) {
                if (integer != null) {
                    page.lines = integer;
                    addon.save(page);
                    for (ShopClick c : addon.getPage(id)) {
                        int max = integer * 9;
                        int voltar = max - 9;
                        if (c.slot >= max || c.slot == voltar) {
                            c.slot = -1;
                            c.pageid = -1;
                            addon.save(c);
                        }
                    }
                }
                new MenuEditPacotes(page, addon).open(p);
            }
        });


    }
}
