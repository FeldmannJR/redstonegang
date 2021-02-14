package dev.feldmann.redstonegang.discord.app.cmds;


import dev.feldmann.redstonegang.app.cmds.Comando;
import dev.feldmann.redstonegang.discord.Discord;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;

public abstract class ConsoleComando extends Comando {

    public ConsoleComando(String cmd, String help) {
        super(cmd, help);
    }

    public JDA jda() {
        return Discord.instance.jda;
    }

    public Guild guild() {
        return Discord.instance.getGuild();
    }

    public String getRemainArguments(String[] args, int start) {
        String arg = "";
        for (int x = start; x < args.length; x++) {
            if (x != start) {
                arg += " ";
            }
            arg += args[x];
        }
        return arg;
    }
}
