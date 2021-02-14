package dev.feldmann.redstonegang.wire.game.base.addons.server.floatshop.cmds.subs;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.IntegerArgument;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.StringArgument;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Player;

public class Create extends ShopSubCmd {

    private static final IntegerArgument LINHAS = new IntegerArgument("linhas", true, 3, 1, 6);
    private static final StringArgument PERMISSION = new StringArgument("permission", true);


    public Create() {
        super("criar", "cria uma loja para o npc selecionado, somente pode abrir a loja quem tem a permissão", true, LINHAS, PERMISSION);
    }

    @Override
    public void command(Player sender, Arguments args) {
        int linhas = args.get(LINHAS);
        NPC n = getSelected(sender);
        if (getManager().hasShop(n)) {
            C.error(sender, "Este npc já tem uma loja!");
            return;
        }
        getManager().createShop(n, linhas, args.get(PERMISSION));
        C.info(sender, "Loja criada!");


    }
}
