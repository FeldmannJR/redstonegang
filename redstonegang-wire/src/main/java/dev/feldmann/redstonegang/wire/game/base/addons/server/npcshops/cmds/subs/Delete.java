package dev.feldmann.redstonegang.wire.game.base.addons.server.npcshops.cmds.subs;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.game.base.addons.server.npcshops.LojaNPC;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.command.CommandSender;

public class Delete extends LojaSubCmd {


    public Delete() {
        super("deletar", "delete a loja selecionada", true);
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        NPC n = getSelected(sender);
        if (!getManager().hasLoja(n)) {
            C.error(sender, "Este npc não é uma loja!!");
            return;
        }
        LojaNPC npc = getManager().getLoja(n);
        if (npc.editing) {
            C.error(sender, "Alguem já esta editando esta loja espere!");
            return;
        }
        getManager().deleteLoja(npc);
        C.info(sender, "Loja Deletada!");
    }
}
