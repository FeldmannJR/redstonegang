package dev.feldmann.redstonegang.discord.commands.list.admin;

import dev.feldmann.redstonegang.discord.Discord;
import dev.feldmann.redstonegang.discord.commands.DiscordCommand;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Regras extends DiscordCommand {
    @Override
    public void execute(MessageReceivedEvent ev, String[] args) {

        TextChannel regras = ev.getGuild().getTextChannelById(Discord.instance().getConfig().getRulesId());
        if (regras != null) {
            for (Message message : regras.getIterableHistory()) {
                if (message.getAuthor().getId().equals(Discord.getJda().getSelfUser().getId())) {
                    message.delete().queue();
                }
            }
            regras.sendMessage(new MessageBuilder().setContent(ev.getMessage().getContentRaw().replace(Discord.instance.getConfig().getCommandPrefix() + "regras", "")).build()).queue(m -> {
                m.pin().queue((a) -> info(ev, ":ok_hand: Regras atualizadas!"));
            });
        } else {
            error(ev, "Canal de regras não encontrado!");
        }
    }

    @Override
    public String getNome() {
        return "regras";
    }

    @Override
    public String getDescription() {
        return "Seta as regras do servidor!";
    }


}
