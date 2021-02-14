package dev.feldmann.redstonegang.discord.app.cmds;

import net.dv8tion.jda.core.managers.AudioManager;

public class CmdSair extends ConsoleComando {

    public CmdSair() {
        super("sair", "Sai de uma sala de chat!");
    }

    @Override
    public void exec(String[] args) {

        AudioManager audio = guild().getAudioManager();
        if (!audio.isConnected()) {
            return;
        }
        audio.closeAudioConnection();
        out("Saindo");
    }
}
