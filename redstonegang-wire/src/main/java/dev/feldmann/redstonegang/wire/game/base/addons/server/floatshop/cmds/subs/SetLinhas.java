package dev.feldmann.redstonegang.wire.game.base.addons.server.floatshop.cmds.subs;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.IntegerArgument;
import dev.feldmann.redstonegang.wire.game.base.addons.server.floatshop.FloatItem;
import dev.feldmann.redstonegang.wire.game.base.addons.server.floatshop.FloatShop;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Player;

public class SetLinhas extends ShopSubCmd {


    private static final IntegerArgument LINHAS = new IntegerArgument("linhas", 1, 6);

    public SetLinhas() {
        super("setlinhas", "edita os itens da loja", true, LINHAS);
    }

    @Override
    public void command(Player p, Arguments args) {
        NPC selected = getSelected(p);
        if (!getManager().hasShop(selected)) {
            C.error(p, "Este npc não tem uma float shop nele!");
            return;
        }
        FloatShop shop = getManager().getShop(selected);
        int oldLinhas = shop.getLinhas();
        int newLinhas = args.get(LINHAS);
        if (oldLinhas > newLinhas) {
            for (FloatItem item : shop.getItens()) {
                item.setSlot(null);
            }
        }
        shop.setLinhas(newLinhas);
        shop.saveItens(getManager());
        getManager().saveShop(shop);
        C.info(p, "Setado linhas para %%!", newLinhas);
    }
}
