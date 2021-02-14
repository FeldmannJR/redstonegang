package dev.feldmann.redstonegang.wire.game.base.addons.server.npcshops.cmds.subs;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.DoubleArgument;
import dev.feldmann.redstonegang.wire.game.base.addons.server.npcshops.LojaItem;
import dev.feldmann.redstonegang.wire.game.base.addons.server.npcshops.LojaNPC;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Compra extends LojaSubCmd {


    private static final DoubleArgument PRECO = new DoubleArgument("preco", false, 0.1, Double.MAX_VALUE);

    public Compra() {
        super("comprar", "bota o seu item na mão a loja comprar ele", true, PRECO);
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        NPC n = getSelected(sender);
        if (!getManager().hasLoja(n)) {
            C.error(sender, "Este npc não é uma loja, use o comando create!");
            return;
        }
        LojaNPC npc = getManager().getLoja(n);
        if (npc.getItens().size() >= npc.getSize()) {
            C.error(sender, "Não tem + espaço na loja!");
            return;
        }
        if (npc.editing) {
            C.error(sender, "Alguem já esta editando esta loja espere!");
            return;
        }
        Player p = (Player) sender;
        npc.getItens().add(new LojaItem(p.getItemInHand().clone(), null, args.get(PRECO), npc.firstEmpty()));
        C.info(p, "%% adicionado para a loja comprar.", p.getItemInHand().clone());
        npc.save();
    }
}
