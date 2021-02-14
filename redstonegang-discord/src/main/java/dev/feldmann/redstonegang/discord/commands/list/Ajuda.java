package dev.feldmann.redstonegang.discord.commands.list;

import dev.feldmann.redstonegang.discord.Discord;
import dev.feldmann.redstonegang.discord.commands.DiscordCommand;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.Arrays;
import java.util.List;

public class Ajuda extends DiscordCommand {

    public Ajuda() {
    }

    @Override
    public void execute(MessageReceivedEvent ev, String[] args) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("Segue os comandos do bot:", MessageBuilder.Formatting.BOLD);
        for (DiscordCommand cmd : this.getCommandManager().getCommandList()) {
            builder.appendCodeBlock(Discord.instance.getConfig().getCommandPrefix() + cmd.getNome() + " - " + cmd.getDescription(), "");
        }
        ev.getChannel().sendMessage(builder.build()).queue();


    }

    @Override
    public String getNome() {
        return "ajuda";
    }

    @Override
    public String getDescription() {
        return "Mostra todos os comandos do bot";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("help");
    }

    @Override
    public List<ChannelType> getChannelTypes() {
        return Arrays.asList(ChannelType.TEXT);
    }
}
