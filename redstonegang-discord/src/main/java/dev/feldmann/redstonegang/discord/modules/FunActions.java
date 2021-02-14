package dev.feldmann.redstonegang.discord.modules;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.utils.formaters.numbers.NumberUtils;
import dev.feldmann.redstonegang.discord.Discord;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.events.ReadyEvent;

public class FunActions extends Module {

    int index = 0;

    @Override
    public void onReady(ReadyEvent event) {
        nextMessage();
        RedstoneGang.instance().runRepeatingTask(this::nextMessage, 20 * 60);
    }

    private Game[] getMessages() {
        return new Game[]{
                Game.of(Game.GameType.DEFAULT, "Minecraft"),
                Game.of(Game.GameType.WATCHING, "Forever Mapa"),
                Game.watching("a casa automática"),
                Game.listening("" + NumberUtils.convertToString(Discord.instance().getGuild().getMembers().size()) + " membros!")
        };
    }

    private void nextMessage() {
        Game[] msgs = getMessages();
        index++;
        if (index >= msgs.length) {
            index = 0;
        }
        Discord.getJda().getPresence().setGame(msgs[index]);

    }


}
