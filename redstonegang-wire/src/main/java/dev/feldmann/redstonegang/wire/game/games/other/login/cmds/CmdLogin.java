package dev.feldmann.redstonegang.wire.game.games.other.login.cmds;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.StringArgument;
import dev.feldmann.redstonegang.wire.base.cmds.types.PlayerCmd;
import dev.feldmann.redstonegang.wire.game.games.other.login.Login;
import org.bukkit.entity.Player;

public class CmdLogin extends PlayerCmd {
    private static StringArgument PASSWORD = new StringArgument("senha", false);

    private Login login;

    public CmdLogin(Login login) {
        super("login", "Loga no servidor", PASSWORD);
        this.login = login;
    }

    @Override
    public void command(Player player, Arguments args) {
        if (login.getData(player).logging) {
            player.sendMessage("§cVocê já está logando aguarde!");
            return;
        }
        String senha = args.get(PASSWORD);
        login.login(player, senha);

    }
}
