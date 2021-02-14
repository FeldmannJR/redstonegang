package dev.feldmann.redstonegang.discord.commands;

import dev.feldmann.redstonegang.discord.Discord;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.Arrays;
import java.util.List;

public abstract class DiscordCommand {

    private CommandManager commandManager;

    public abstract void execute(MessageReceivedEvent ev, String[] args);

    public abstract String getNome();

    public abstract String getDescription();


    public List<String> getAliases() {
        return null;
    }

    public List<ChannelType> getChannelTypes() {
        return Arrays.asList(ChannelType.TEXT);
    }

    public Permission executePermission() {
        return Permission.ADMINISTRATOR;
    }

    protected CommandManager getCommandManager() {
        return commandManager;
    }

    void setCommandManager(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    public String getUsage() {
        return null;
    }

    public int getMinArgs() {
        return 0;
    }

    public void error(MessageReceivedEvent ev, String msg) {
        Discord.instance().messages().commandError(ev.getTextChannel(), msg);
    }

    public void info(MessageReceivedEvent ev, String msg) {
        Discord.instance().messages().commandInfo(ev.getTextChannel(), msg);
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
