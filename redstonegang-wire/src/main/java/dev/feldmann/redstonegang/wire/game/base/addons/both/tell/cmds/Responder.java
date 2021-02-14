package dev.feldmann.redstonegang.wire.game.base.addons.both.tell.cmds;

import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.RemainStringsArgument;
import dev.feldmann.redstonegang.wire.base.cmds.types.PlayerCmd;
import dev.feldmann.redstonegang.wire.game.base.addons.both.tell.TellAddon;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.entity.Player;


public class Responder extends PlayerCmd {

    private static RemainStringsArgument MENSAGEM = new RemainStringsArgument("mensagem", false);
    private TellAddon addon;

    public Responder(TellAddon addon) {
        super("responder", "Responde a ultima mensagem privada!", MENSAGEM);
        this.addon = addon;
        setAlias("r");
    }

    @Override
    public boolean canOverride() {
        return true;
    }

    @Override
    public void command(Player player, Arguments args) {
        User target = addon.getLastReceived(addon.getUser(player));
        if (target == null) {
            C.error(player, "Ninguém mandou mensagem para você!");
            return;
        }
        addon.sendTell(player, target, args.get(MENSAGEM));
    }
}
