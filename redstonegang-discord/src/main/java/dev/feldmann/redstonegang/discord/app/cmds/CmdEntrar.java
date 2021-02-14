package dev.feldmann.redstonegang.discord.app.cmds;

import net.dv8tion.jda.core.entities.VoiceChannel;

public class CmdEntrar extends ConsoleComando {

    public CmdEntrar() {
        super("entrar", "Entra em uma sala de chat");
    }

    @Override
    public void exec(String[] args) {
        if (args.length != 2) {
            out("Use: entrar Video Sala");
            return;
        }
        VoiceChannel v = null;
        for (VoiceChannel vc : guild().getVoiceChannels()) {
            if (vc.getName().contains(getRemainArguments(args, 1))) {
                v = vc;
            }
        }

        if (v == null) {
            out("Existem nenhum ou mais que um canal com este nome");
            return;
        }
    }
}
