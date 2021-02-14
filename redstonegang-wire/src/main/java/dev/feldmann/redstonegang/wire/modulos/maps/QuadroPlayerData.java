package dev.feldmann.redstonegang.wire.modulos.maps;

import java.util.HashSet;

public class QuadroPlayerData {

    HashSet<Integer> seeing = new HashSet<>();

    public boolean isSeeing(Quadro c) {
        return seeing.contains(c.id);
    }

    public void setSeeing(Quadro c, boolean b) {
        seeing.remove(c.id);
        if (b) {
            seeing.add(c.id);
        }
    }


}
