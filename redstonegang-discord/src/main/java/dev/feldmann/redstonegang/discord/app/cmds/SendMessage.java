package dev.feldmann.redstonegang.discord.app.cmds;

import net.dv8tion.jda.core.entities.TextChannel;

public abstract class SendMessage extends ConsoleComando {


    public SendMessage(String cmd, String help) {
        super(cmd, help);
    }

    @Override
    public void exec(String[] strings) {
        if (strings.length <= 0) {
            out("Uso : " + getName() + " Mensagem");
            return;
        }

        String msg = getRemainArguments(strings, 0);

        TextChannel channel = guild().getTextChannelById(getChatId());
        if (channel == null) {
            out("Canal não encontrado!");
            return;
        }
        channel.sendMessage(msg).queue();
        out("Mensagem enviada");

    }

    public abstract long getChatId();
}
