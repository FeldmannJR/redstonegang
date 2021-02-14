package dev.feldmann.redstonegang.wire.game.base.addons.server.floatshop.cmds.subs;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.game.base.addons.server.floatshop.menu.edit.EditShopMenu;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Player;

public class Editar extends ShopSubCmd {


    public Editar() {
        super("editar", "edita os itens da loja", true);
    }

    @Override
    public void command(Player p, Arguments args) {
        NPC selected = getSelected(p);
        if (!getManager().hasShop(selected)) {
            C.error(p, "Este npc não tem uma float shop nele!");
            return;
        }
        new EditShopMenu(getManager(), getManager().getShop(selected)).open(p);
    }
}
