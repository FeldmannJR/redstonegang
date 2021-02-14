package dev.feldmann.redstonegang.discord.modules.chatfilter;

import dev.feldmann.redstonegang.discord.modules.Module;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChatFilterModule extends Module {

    List<ChatFilter> filters = new ArrayList();

    public ChatFilterModule() {
        addFilter(
        );
    }

    public void addFilter(ChatFilter... filters) {
        this.filters.addAll(Arrays.asList(filters));
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (!event.isFromType(ChannelType.TEXT)) {
            return;
        }
        if (event.getMember() == null) {
            return;
        }
        if (event.getAuthor().isBot()) {
            return;
        }
        if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
            return;
        }
        for (ChatFilter f : filters) {
            if (f instanceof ChatFilterRule) {
                if (!((ChatFilterRule) f).shouldFilter(event)) {
                    continue;
                }
            }
            if (f.filter(event.getMessage(), event.getMember(), event.getTextChannel())) {
                event.getMessage().delete().queue();
                return;
            }
        }

    }
}
