package dev.feldmann.redstonegang.wire.game.base;

import dev.feldmann.redstonegang.wire.game.base.addons.minigames.state.GameState;
import dev.feldmann.redstonegang.wire.game.base.addons.minigames.state.states.PreGameStateController;
import dev.feldmann.redstonegang.wire.game.base.objects.PlayerInfo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Game extends Server {

    int minJogadres;
    int maxJogadores;
    GameState state;
    List<PlayerInfo> infos = new ArrayList();

    @Override
    public void enable() {
        super.enable();
        addAddon(new PreGameStateController(this));
    }

    public int getMinJogadres() {
        return minJogadres;
    }

    public int getMaxJogadores() {
        return maxJogadores;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }



    public List<PlayerInfo> getPlayers(boolean spectators) {
        if (spectators) {
            return infos;
        }
        List<PlayerInfo> without = new ArrayList(infos);
        Iterator<PlayerInfo> it = without.iterator();
        while (it.hasNext()) {
            if (it.next().isSpectator()) {
                it.remove();
            }
        }
        return without;
    }
}
