package dev.feldmann.redstonegang.wire.game.base;

import dev.feldmann.redstonegang.wire.game.base.objects.PlayerInfo;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class Team {

    String nome;
    ChatColor cor = ChatColor.WHITE;
    List<PlayerInfo> players = new ArrayList();


    public Team(String nome) {
        this.nome = nome;
    }

    public void addPlayer(PlayerInfo info) {
        this.players.add(info);
    }

    public boolean isValid() {
        for (PlayerInfo info : players) {
            if (info.isValid()) {
                return true;
            }
        }
        return false;
    }


}
