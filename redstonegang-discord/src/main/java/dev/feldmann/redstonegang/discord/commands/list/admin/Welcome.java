package dev.feldmann.redstonegang.discord.commands.list.admin;

import dev.feldmann.redstonegang.discord.Discord;
import dev.feldmann.redstonegang.discord.commands.DiscordCommand;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.Arrays;
import java.util.List;

public class Welcome extends DiscordCommand {


    @Override
    public void execute(MessageReceivedEvent ev, String[] args) {
        Message message = ev.getMessage();
        List<Member> members = message.getMentionedMembers();
        if (members.size() != 1) {
            error(ev, "Você só pode marcar um membro!");
            return;
        }
        Member member = members.get(0);
        Discord.instance().messages().sendWelcome(member);
    }


    @Override
    public String getNome() {
        return "welcome";
    }

    @Override
    public String getDescription() {
        return "Testa welcome";
    }

    @Override
    public List<ChannelType> getChannelTypes() {
        return Arrays.asList(ChannelType.TEXT);
    }
}
