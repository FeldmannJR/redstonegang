package dev.feldmann.redstonegang.discord.commands.list;

import dev.feldmann.redstonegang.discord.Discord;
import dev.feldmann.redstonegang.discord.commands.DiscordCommand;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.managers.AudioManager;

import java.util.Arrays;
import java.util.List;

public class Play extends DiscordCommand {
    @Override
    public void execute(MessageReceivedEvent ev, String[] args) {
        User author = ev.getAuthor();
        Member m = Discord.instance.getGuild().getMember(author);
        if (m.hasPermission(Permission.VOICE_MOVE_OTHERS)) {
            if (m.getVoiceState().getChannel() != null) {
                AudioManager audio = m.getGuild().getAudioManager();
                if (audio.isAttemptingToConnect()) {
                    return;
                }
                if (audio.isConnected()) {
                    if (audio.getConnectedChannel() != m.getVoiceState().getChannel()) {
                        audio.closeAudioConnection();
                    }
                }
                if (!audio.isConnected())
                    audio.openAudioConnection(m.getVoiceState().getChannel());

                Discord.instance.music.playYoutube(args[0],ev.getTextChannel());
                audio.setSendingHandler(Discord.instance.music);

            }else{
                ev.getChannel().sendMessage("Você precisa estar em um canal de voz pra chamar o BOT!").queue();
            }
        }
    }

    @Override
    public String getNome() {
        return "play";
    }

    @Override
    public String getDescription() {
        return "Bota alguma coisa pro bot tocar";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList();
    }

    @Override
    public List<ChannelType> getChannelTypes() {
        return Arrays.asList(ChannelType.TEXT);
    }
}
