package dev.feldmann.redstonegang.discord.app.cmds;

import dev.feldmann.redstonegang.discord.Discord;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;

public class CmdRegras extends ConsoleComando {
    public CmdRegras() {
        super("regras", "copia as regras");
    }

    @Override
    public void exec(String[] strings) {
        Guild guild = Discord.instance().getGuild();
        TextChannel regras = guild.getTextChannelsByName("regras", true).get(0);
        Message message = regras.getPinnedMessages().complete().get(0);
        String raw = message.getContentRaw();
        Message m = new MessageBuilder().setContent(raw).build();
        regras.sendMessage(m).complete();
    }
}
