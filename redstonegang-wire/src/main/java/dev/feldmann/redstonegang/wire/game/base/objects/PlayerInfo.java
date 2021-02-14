package dev.feldmann.redstonegang.wire.game.base.objects;

import org.bukkit.entity.Player;

public class PlayerInfo {
    private Player player;

    //Pode ser espectador e estar no jogo
    boolean spectator = false;

    //Ta no jogo ainda
    boolean valid = true;

    public PlayerInfo(Player p) {
        this.player = p;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isSpectator() {
        return spectator;
    }

    public boolean isValid() {
        return valid;
    }

    public void setSpectator(boolean spectator) {
        this.spectator = spectator;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
