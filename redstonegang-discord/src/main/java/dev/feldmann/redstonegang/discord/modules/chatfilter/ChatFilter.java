package dev.feldmann.redstonegang.discord.modules.chatfilter;

import dev.feldmann.redstonegang.discord.Discord;
import net.dv8tion.jda.core.entities.*;

public abstract class ChatFilter {

    public abstract boolean filter(Message mensagem, Member member, TextChannel channel);


    public void sendPrivateMessage(Member member, String message) {
        Discord.instance.messages().sendWarning(member,message);
    }
}
