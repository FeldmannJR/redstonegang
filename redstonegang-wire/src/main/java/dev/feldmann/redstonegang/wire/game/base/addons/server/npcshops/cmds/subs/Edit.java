package dev.feldmann.redstonegang.wire.game.base.addons.server.npcshops.cmds.subs;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.game.base.addons.server.npcshops.LojaNPC;
import dev.feldmann.redstonegang.wire.game.base.addons.server.npcshops.menus.adm.EditLojaMenu;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Edit extends LojaSubCmd {


    public Edit() {
        super("editar", "edita os icones no inventário", true);
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        NPC n = getSelected(sender);
        if (!getManager().hasLoja(n)) {
            C.error(sender, "Este npc não é uma loja, use o comando create!");
            return;
        }
        LojaNPC npc = getManager().getLoja(n);
        if (npc.editing) {
            C.error(sender, "Alguem já esta editando esta loja espere!");
            return;
        }
        new EditLojaMenu(npc).open((Player) sender);

    }
}
