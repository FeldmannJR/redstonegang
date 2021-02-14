package dev.feldmann.redstonegang.discord.app.cmds;

import dev.feldmann.redstonegang.discord.Discord;

public class CmdPapo extends SendMessage {

    public CmdPapo() {
        super("papo", "Manda msg no bate papo");
    }

    @Override
    public long getChatId() {
        return Discord.instance().getConfig().getBatePapo();
    }
}
