package dev.feldmann.redstonegang.discord.utils;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;

import java.util.List;

public class DiscordUtils {


    public static TextChannel findTextChannel(Guild g, String name) {
        List<TextChannel> channels = g.getTextChannelsByName(name, false);
        if (channels.size() == 0) {
            return null;
        }
        return channels.get(0);
    }
}
