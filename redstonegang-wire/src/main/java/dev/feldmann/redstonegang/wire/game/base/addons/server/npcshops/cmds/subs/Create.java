package dev.feldmann.redstonegang.wire.game.base.addons.server.npcshops.cmds.subs;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.*;
import dev.feldmann.redstonegang.wire.game.base.addons.server.npcshops.LojaNPC;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.IntegerArgument;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.StringArgument;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

public class Create extends LojaSubCmd {

    private static final IntegerArgument LINHAS = new IntegerArgument("linhas", true, 3, 1, 6);
    private static final StringArgument PERMISSION = new StringArgument("permission", true);


    public Create() {
        super("criar", "cria uma loja para o npc selecionado, somente pode abrir a loja quem tem a permissão", true, LINHAS, PERMISSION);
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        int linhas = args.get(LINHAS);
        NPC n = getSelected(sender);
        if (getManager().hasLoja(n)) {
            C.error(sender, "Este npc já tem uma loja!");
            return;
        }
        LojaNPC npc = LojaNPC.fromItems(n.getUniqueId(), args.get(PERMISSION), new ItemStack[linhas * 9]);
        getManager().addLoja(npc);
        C.info(sender, "Loja criada com o npc selecionado!");

    }
}
