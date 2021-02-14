package dev.feldmann.redstonegang.wire.modulos.disguise.types.monsters;

import dev.feldmann.redstonegang.wire.modulos.disguise.annotations.SetAnnotation;
import dev.feldmann.redstonegang.wire.modulos.disguise.types.base.EquipmentData;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class DisguiseSkeleton extends EquipmentData {

    boolean wither = false;

    public DisguiseSkeleton(Player p) {
        super(p);
    }

    @SetAnnotation(nome = "wither")
    public void setWither(boolean wither) {
        this.wither = wither;
        watcher.add(13, (byte) (wither ? 1 : 0));
    }

    public EntityType getEntityType() {
        return EntityType.SKELETON;
    }

}
