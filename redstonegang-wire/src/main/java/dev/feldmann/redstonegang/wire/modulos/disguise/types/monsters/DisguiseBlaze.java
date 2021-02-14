package dev.feldmann.redstonegang.wire.modulos.disguise.types.monsters;

import dev.feldmann.redstonegang.wire.modulos.disguise.annotations.SetAnnotation;
import dev.feldmann.redstonegang.wire.modulos.disguise.types.base.LivingData;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class DisguiseBlaze extends LivingData {

    boolean onfire = false;

    public DisguiseBlaze(Player p) {
        super(p);
    }

    @SetAnnotation(nome = "fire")
    public void setFire(boolean onfire) {
        this.onfire = onfire;
        watcher.add(16, onfire ? (byte) 1 : (byte) 0);
    }

    public boolean isOnfire() {
        return onfire;
    }

    public EntityType getEntityType() {
        return EntityType.BLAZE;
    }

}
