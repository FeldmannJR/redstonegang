package dev.feldmann.redstonegang.wire.game.base.addons.server.floatshop.cmds.subs;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.game.base.addons.server.floatshop.menu.edit.SortShopMenu;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Player;

public class Organizar extends ShopSubCmd {


    public Organizar() {
        super("organizar", "organiza os itens na loja", true);
    }

    @Override
    public void command(Player p, Arguments args) {
        NPC selected = getSelected(p);
        if (!getManager().hasShop(selected)) {
            C.error(p, "Este npc não tem uma float shop nele!");
            return;
        }
        new SortShopMenu(getManager(), getManager().getShop(selected)).open(p);
    }
}
