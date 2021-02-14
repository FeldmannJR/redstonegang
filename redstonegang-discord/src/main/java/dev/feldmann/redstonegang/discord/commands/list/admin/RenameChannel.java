package dev.feldmann.redstonegang.discord.commands.list.admin;

import dev.feldmann.redstonegang.discord.commands.DiscordCommand;
import dev.feldmann.redstonegang.discord.utils.DiscordUtils;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.managers.ChannelManager;

import java.lang.reflect.Field;

public class RenameChannel extends DiscordCommand {


    @Override
    public void execute(MessageReceivedEvent ev, String[] args) {
        String oldName = args[0];
        String newName = this.getRemainArguments(args, 1).replace(' ', '\u2005');
        if (oldName.startsWith("#")) {
            oldName = oldName.substring(1);
        }
        final String finalOldName = oldName;
        TextChannel channel = DiscordUtils.findTextChannel(ev.getGuild(), oldName);
        if (channel == null) {
            error(ev, "Canal ``" + oldName + "`` não encontrado!");
            return;
        }

        ChannelManager manager = channel.getManager();
        try {
            Field name = manager.getClass().getDeclaredField("name");
            manager.setName("temp");
            name.setAccessible(true);
            name.set(manager, newName);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        manager.queue((v) -> {
            info(ev, "Nome do canal ``" + finalOldName+ "`` alterado para ``" + newName + "`` !");
        });
    }

    @Override
    public int getMinArgs() {
        return 2;
    }

    @Override
    public String getUsage() {
        return "<canal> <novoNome>";
    }

    @Override
    public String getNome() {
        return "renamechannel";
    }

    @Override
    public String getDescription() {
        return "Renomeia um canal";
    }

    @Override
    public Permission executePermission() {
        return Permission.ADMINISTRATOR;
    }
}
