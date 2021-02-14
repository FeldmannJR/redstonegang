package dev.feldmann.redstonegang.discord.commands;

import dev.feldmann.redstonegang.common.utils.ObjectLoader;
import dev.feldmann.redstonegang.common.utils.formaters.StringUtils;
import dev.feldmann.redstonegang.discord.Discord;
import dev.feldmann.redstonegang.discord.commands.list.*;
import dev.feldmann.redstonegang.discord.commands.list.Pointer;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.List;

public class CommandManager extends ListenerAdapter {

    private List<DiscordCommand> commandList = new ArrayList();


    public CommandManager() {
        registerCommands();
    }

    public void registerCommands() {
        List<DiscordCommand> commands = ObjectLoader.load(Pointer.class.getPackage().getName(), DiscordCommand.class);
        for (DiscordCommand command : commands) {
            Discord.instance().log("Carregando comando " + command.getNome());
            command.setCommandManager(this);
            register(command);
        }
    }

    public void register(DiscordCommand command) {
        commandList.add(command);
    }

    public List<DiscordCommand> getCommandList() {
        return commandList;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (!event.isFromType(ChannelType.TEXT)) {
            return;
        }
        if (event.getAuthor().isBot()) {
            return;
        }
        if (!event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
            return;
        }
        String prefix = Discord.instance.getConfig().getCommandPrefix();
        String msg = event.getMessage().getContentDisplay();
        if (!msg.startsWith(prefix)) {
            return;
        }
        msg = msg.substring(prefix.length(), msg.length());
        String[] split = msg.split(" ");
        split = StringUtils.combineArgs(split);
        String cmdname = split[0];
        for (DiscordCommand cmd : commandList) {
            if (cmd.getNome().equalsIgnoreCase(cmdname) || (cmd.getAliases() != null && cmd.getAliases().contains(cmdname.toLowerCase()))) {
                if (cmd.getChannelTypes() != null) {
                    if (!cmd.getChannelTypes().contains(event.getChannelType())) {
                        event.getChannel().sendMessage("Este commando não pode ser executado aqui!").queue();
                        return;
                    }
                }
                String[] args = new String[split.length - 1];
                for (int x = 0; x < split.length - 1; x++) {
                    args[x] = split[x + 1];
                }
                if (cmd.getMinArgs() > split.length) {
                    Discord.instance().messages().commandError(event.getTextChannel(), "Uso: " + cmd.getNome() + " " + cmd.getUsage());
                    return;
                }
                cmd.execute(event, args);
                return;

            }
        }
        event.getChannel().sendMessage("Comando não encontrado! use '" + prefix + "ajuda' para ver os comandos!").queue();

    }
}

