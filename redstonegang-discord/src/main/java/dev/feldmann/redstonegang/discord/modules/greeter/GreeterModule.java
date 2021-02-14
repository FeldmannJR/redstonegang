package dev.feldmann.redstonegang.discord.modules.greeter;

import dev.feldmann.redstonegang.discord.Discord;
import dev.feldmann.redstonegang.discord.modules.Module;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;

public class GreeterModule extends Module {


    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        Discord.instance().messages().sendWelcome(event.getMember());
    }
}
