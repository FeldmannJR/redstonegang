package dev.feldmann.redstonegang.wire.modulos.disguise.types.monsters;

import dev.feldmann.redstonegang.wire.modulos.disguise.annotations.SetAnnotation;
import dev.feldmann.redstonegang.wire.modulos.disguise.types.base.LivingData;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class DisguiseSlime extends LivingData {

    byte size = 0;

    public DisguiseSlime(Player p) {
        super(p);
    }

    public EntityType getEntityType() {
        return EntityType.SLIME;
    }

    @SetAnnotation(nome = "size")
    public void setSize(byte size) {
        this.size = size;
        watcher.add(16, size);
    }

    public byte getSize() {
        return size;
    }

}
