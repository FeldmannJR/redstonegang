package dev.feldmann.redstonegang.discord.app.cmds;

import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.discord.Discord;
import dev.feldmann.redstonegang.discord.permissoes.SyncUser;
import net.dv8tion.jda.core.entities.Member;

import java.util.List;

public class CmdSync extends ConsoleComando {
    public CmdSync() {
        super("sync", "Sincroniza uma conta do db!");
    }

    @Override
    public void exec(String[] strings) {
        if (strings.length != 1) {
            out("Uso : msg CanalDeTexto Mensagem");
            return;
        }
        List<Member> fe = Discord.instance().getGuild().getMembersByName(strings[0], false);
        if (!fe.isEmpty()) {
            User user = null;
            if (user != null) {
                SyncUser.sync(fe.get(0), user);
                out("Feito");
            }
        }

    }
}
