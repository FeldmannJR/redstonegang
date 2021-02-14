package dev.feldmann.redstonegang.wire.game.base.controle;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.wire.Wire;
import dev.feldmann.redstonegang.wire.game.base.Games;
import dev.feldmann.redstonegang.wire.game.base.objects.ServerEntry;

import java.util.ArrayList;

public class ServerControler {

    //Tipo da sala
    SalaInfo info = new SalaInfo();
    //Fila
    ArrayList<Games> fila = new ArrayList();
    int index = -1;

    public SalaInfo getInfo() {
        return info;
    }

    public ServerEntry next() {
        index++;
        if (fila.size() == 0) {
            if (Wire.instance.GAME_NAME != null) {
                try {
                    Games games = Games.valueOf(Wire.instance.GAME_NAME);
                    fila.add(games);
                } catch (IllegalArgumentException ex) {

                }
            }else{
                return null;
            }

        }
        if (index >= fila.size()) {
            index = 0;
        }
        return fila.get(index).getEntry();
    }


}
