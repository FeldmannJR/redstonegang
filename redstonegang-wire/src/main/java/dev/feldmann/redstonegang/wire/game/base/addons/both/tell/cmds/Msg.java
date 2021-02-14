package dev.feldmann.redstonegang.wire.game.base.addons.both.tell.cmds;

import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.PlayerNameArgument;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.RemainStringsArgument;
import dev.feldmann.redstonegang.wire.base.cmds.types.PlayerCmd;
import dev.feldmann.redstonegang.wire.game.base.addons.both.tell.TellAddon;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.entity.Player;


public class Msg extends PlayerCmd {

    private static PlayerNameArgument JOGADOR = new PlayerNameArgument("jogador", false, false);
    private static RemainStringsArgument MENSAGEM = new RemainStringsArgument("mensagem", true);
    private TellAddon addon;

    public Msg(TellAddon addon) {
        super("mensagem", "Manda uma mensagem privada para alguem!", JOGADOR, MENSAGEM);
        this.addon = addon;
        setAlias("tell", "msg");
    }

    @Override
    public boolean canOverride() {
        return true;
    }

    @Override
    public void command(Player player, Arguments args) {
        User target = args.get(JOGADOR);
        if (!target.isOnline()) {
            C.error(player, "Jogador não está online!");
            return;
        }
        if (args.isPresent(MENSAGEM)) {
            addon.sendTell(player, target, args.get(MENSAGEM));
        } else {
            addon.joinChannel(player, target);
        }

    }
}
