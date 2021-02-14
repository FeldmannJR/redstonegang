package dev.feldmann.redstonegang.wire.game.base.addons.both.simplecmds.cmds.staff;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.api.Response;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.game.base.addons.both.simplecmds.SimpleCmd;
import dev.feldmann.redstonegang.wire.modulos.menus.menus.SelectCustomSkinMenu;
import dev.feldmann.redstonegang.wire.utils.items.InventoryHelper;
import dev.feldmann.redstonegang.wire.utils.items.ItemUtils;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Heads extends SimpleCmd {
    public Heads() {
        super("heads", "Pega uma cabeça que foi dado upload.", "heads");
    }


    @Override
    public void command(Player player, Arguments a) {

        Response<String[]> skins = RedstoneGang.instance().webapi().skins().custom();
        if (skins.hasFailed()) {
            C.error(player, "Ocorreu um erro ao baixar as skins!");
            return;
        }
        new SelectCustomSkinMenu(skins.collect()) {
            @Override
            public void selectSkin(Player p, String skin) {
                ItemStack head = ItemUtils.getHead("rg-" + skin);
                if (InventoryHelper.getInventoryFreeSpace(player, head) > 0) {
                    C.info(p, "Cabeça %% adicionada ao inventario!", skin);
                    p.getInventory().addItem(head);
                    return;
                }
                C.error(p, "Você não tem espaço no inventário!");

            }
        }.open(player);
    }

    @Override
    public boolean canOverride() {
        return true;
    }

}

