package dev.feldmann.redstonegang.discord.app.cmds;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.VoiceChannel;

import java.util.List;

public class CmdMover extends ConsoleComando {

    public CmdMover() {
        super("movertodos", "Move todos os usuarios online para uma sala especificada!");
    }

    @Override
    public void exec(String[] args) {
        if (args.length != 1) {
            out("Use: movertodos SALA");
            return;
        }
        out("Guild:" + guild());

        List<VoiceChannel> channels = guild().getVoiceChannelsByName(args[0], true);
        if (channels.size() != 1) {
            out("Existem nenhum ou mais que um canal com este nome");
            return;
        }
        for (Member m : guild().getMembers()) {
            if (m.getVoiceState().inVoiceChannel()) {
                guild().getController().moveVoiceMember(m, channels.get(0)).queue();
            }


        }


    }
}
