package dev.feldmann.redstonegang.discord.commands.list;

import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.common.player.permissions.Group;
import dev.feldmann.redstonegang.discord.commands.DiscordCommand;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Info extends DiscordCommand {
    @Override
    public void execute(MessageReceivedEvent ev, String[] args) {
        User user = null;
        String msg;
        if (user == null) {
            msg = "Conta não linkada com o discord!";
        } else {
            msg = " ID: " + user.getId() + "\n";
            msg += "UUID: " + user.getUuid() + "\n";
            msg += "NAME: " + user.getName() + "\n";

            Group grupo = user.permissions().getGroup();
            msg += "GRUPO " + " : ";
            if (grupo != null) {
                msg += grupo.getNome();
            } else {
                msg += "Não tem!";
            }
            msg += '\n';


        }
        ev.getChannel().sendMessage(msg).queue();
    }

    @Override
    public String getNome() {
        return "info";
    }

    @Override
    public String getDescription() {
        return "Ve seu uuid";
    }


}
