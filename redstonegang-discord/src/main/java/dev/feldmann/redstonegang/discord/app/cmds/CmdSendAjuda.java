package dev.feldmann.redstonegang.discord.app.cmds;

import dev.feldmann.redstonegang.discord.Discord;

public class CmdSendAjuda extends SendMessage {

    public CmdSendAjuda() {
        super("sendajuda", "Manda msg no bate papo");
    }
    
    @Override
    public long getChatId() {
        return Discord.instance().getConfig().getAjudaChannel();
    }
}
