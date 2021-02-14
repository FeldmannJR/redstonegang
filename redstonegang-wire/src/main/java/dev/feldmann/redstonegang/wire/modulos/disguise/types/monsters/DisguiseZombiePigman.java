package dev.feldmann.redstonegang.wire.modulos.disguise.types.monsters;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class DisguiseZombiePigman extends DisguiseZombie {

    public DisguiseZombiePigman(Player p) {
        super(p);
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.PIG_ZOMBIE;
    }
}
