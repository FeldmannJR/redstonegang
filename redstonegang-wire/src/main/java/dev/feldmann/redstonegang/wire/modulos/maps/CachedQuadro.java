package dev.feldmann.redstonegang.wire.modulos.maps;

import org.bukkit.entity.Player;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class CachedQuadro extends Quadro {

    public CachedQuadro(BufferedImage background)
    {
        super(background);
    }

    //Um cache pra geral
    byte[] cacheglobal;

    //Um cache
    HashMap<Player, byte[]> cacheplayer = new HashMap();

    @Override
    public byte[] getData(Player p)
    {
        if (isGlobal()) {
            if (cacheglobal == null) {
                cacheglobal = renderQuadro(p);
            }
            return cacheglobal;

        } else {
            if (!cacheplayer.containsKey(p)) {
                cacheplayer.put(p, renderQuadro(p));
            }
            return cacheplayer.get(p);
        }


    }


}
