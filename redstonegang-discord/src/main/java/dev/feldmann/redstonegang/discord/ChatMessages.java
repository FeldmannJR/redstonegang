package dev.feldmann.redstonegang.discord;


import dev.feldmann.redstonegang.discord.utils.Cooldown;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.SelfUser;
import net.dv8tion.jda.core.entities.TextChannel;

import java.awt.*;

public class ChatMessages {


    Cooldown warning;

    public ChatMessages() {
        warning = new Cooldown(5000);
    }

    public void sendWarning(Member member, String message) {
        if (warning.can(member.getUser().getIdLong())) {
            member.getUser().openPrivateChannel().queue((privateChannel -> {
                EmbedBuilder builder = new EmbedBuilder()
                        .setColor(Color.RED)
                        .setTitle("Atenção!")
                        .setFooter("Por favor leias as regras!", null)
                        .addField("Whoops...", message, false);

                privateChannel.sendMessage(builder.build()).queue();
            }));
        }
    }

    public void sendWelcome(Member m) {
        TextChannel channel = Discord.instance().getGuild().getTextChannelById(Discord.instance().getConfig().getWelcomeId());

        EmbedBuilder builder = baseBuilder(m.getGuild())
                .setThumbnail(m.getUser().getEffectiveAvatarUrl())
                .setAuthor(m.getUser().getAsTag(), null, m.getUser().getEffectiveAvatarUrl())
                .setTitle(":comet: Bem-Vindo(a) ao servidor!")
                .setDescription("Olá <@" + m.getUser().getId() + ">, fique a vontade para conversar e fazer amigos!")
                .setFooter("", Discord.getJda().getSelfUser().getEffectiveAvatarUrl());
        channel.sendMessage(new MessageBuilder().append(m).setEmbed(builder.build()).build()).queue();
    }

    public void commandInfo(TextChannel channel, String message) {
        SelfUser selfUser = Discord.getJda().getSelfUser();
        EmbedBuilder builder = baseBuilder(channel.getGuild())
                .setAuthor(selfUser.getName(), null, selfUser.getEffectiveAvatarUrl())
                .setDescription(message);
        channel.sendMessage(builder.build()).queue();

    }

    public void commandError(TextChannel channel, String message) {
        SelfUser selfUser = Discord.getJda().getSelfUser();
        EmbedBuilder builder = baseBuilder(channel.getGuild())
                .setAuthor(selfUser.getName(), null, selfUser.getEffectiveAvatarUrl())
                .addField("Whoops...", message, false);

        channel.sendMessage(builder.build()).queue();

    }

    private EmbedBuilder baseBuilder(Guild g) {
        return new EmbedBuilder()
                .setColor(getColor(g));
    }

    private Color getColor(Guild g) {
        SelfUser selfUser = Discord.getJda().getSelfUser();
        Color color = g.getMember(selfUser).getColor();
        return color == null ? Color.red : color;
    }


}
