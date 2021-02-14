package dev.feldmann.redstonegang.wire.modulos.disguise.types.monsters;

import dev.feldmann.redstonegang.wire.modulos.disguise.annotations.SetAnnotation;
import dev.feldmann.redstonegang.wire.modulos.disguise.types.base.EquipmentData;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class DisguiseZombie extends EquipmentData {

    private boolean baby = false;
    private boolean villager = false;

    public DisguiseZombie(Player p) {
        super(p);
    }

    @SetAnnotation(nome = "baby")
    public void setBaby(boolean baby) {
        this.baby = baby;
        watcher.add(12, (byte) (baby ? 1 : 0));
    }

    @SetAnnotation(nome = "villager")
    public void setVillager(boolean villager) {
        this.villager = villager;
        watcher.add(13, (byte) (villager ? 1 : 0));
    }

    public EntityType getEntityType() {
        return EntityType.ZOMBIE;
    }

}
