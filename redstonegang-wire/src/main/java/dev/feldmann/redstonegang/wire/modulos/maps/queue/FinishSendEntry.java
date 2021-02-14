package dev.feldmann.redstonegang.wire.modulos.maps.queue;

import dev.feldmann.redstonegang.wire.modulos.maps.Quadro;
import org.bukkit.entity.Player;

public class FinishSendEntry extends QueueEntry {

    public Quadro quadro;

    public FinishSendEntry(Player p, Quadro quadro) {
        super(p);
        this.quadro = quadro;
    }
}
