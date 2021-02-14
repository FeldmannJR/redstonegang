package dev.feldmann.redstonegang.wire.modulos.disguise.types;

import dev.feldmann.redstonegang.wire.modulos.disguise.annotations.ActionAnnotation;
import dev.feldmann.redstonegang.wire.modulos.disguise.annotations.SetAnnotation;
import dev.feldmann.redstonegang.wire.modulos.disguise.types.base.AgeableData;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Rabbit;

public class DisguiseRabbit extends AgeableData {

    Rabbit.Type type = Rabbit.Type.BROWN;

    public DisguiseRabbit(Player p) {
        super(p);
    }

    public EntityType getEntityType() {
        return EntityType.RABBIT;
    }

    @SetAnnotation(nome = "rabittype")
    public void setType(Rabbit.Type type) {
        watcher.add(18, (byte) type.ordinal());
        this.type = type;
    }

    public Rabbit.Type getRabbitType() {
        return type;
    }

    @ActionAnnotation
    public void jump() {
        doStatus((byte) 1);
    }

}
