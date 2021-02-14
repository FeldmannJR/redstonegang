package dev.feldmann.redstonegang.discord.modules.chatfilter;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public interface ChatFilterRule {

    boolean shouldFilter(MessageReceivedEvent ev);
}
