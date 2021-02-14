package dev.feldmann.redstonegang.wire.modulos.disguise.types.monsters;

import dev.feldmann.redstonegang.wire.modulos.disguise.annotations.SetAnnotation;
import dev.feldmann.redstonegang.wire.modulos.disguise.types.base.LivingData;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class DisguiseCreeper extends LivingData {

    boolean powered = false;//17
    boolean fuse = false;//16

    public DisguiseCreeper(Player p) {
        super(p);
    }

    @SetAnnotation(nome = "fuse")
    public void setFuse(boolean fuse) {
        this.fuse = fuse;
        watcher.add(16, (byte) (fuse ? 1 : -1));
        sendWatcher();
    }

    @SetAnnotation(nome = "powered")
    public void setPowered(boolean powered) {
        this.powered = powered;
        watcher.add(17, (byte) (powered ? 1 : 0));
    }

    public boolean isFuse() {
        return fuse;
    }

    public boolean isPowered() {
        return powered;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.CREEPER;
    }
}
