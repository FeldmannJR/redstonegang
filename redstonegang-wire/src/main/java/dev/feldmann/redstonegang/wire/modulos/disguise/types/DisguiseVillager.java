package dev.feldmann.redstonegang.wire.modulos.disguise.types;

import dev.feldmann.redstonegang.wire.modulos.disguise.annotations.SetAnnotation;
import dev.feldmann.redstonegang.wire.modulos.disguise.types.base.AgeableData;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class DisguiseVillager extends AgeableData {

    Profession prof = Profession.FARMER;

    public DisguiseVillager(Player p) {
        super(p);
    }

    public EntityType getEntityType() {
        return EntityType.VILLAGER;
    }

    @SetAnnotation(nome = "profession")
    public void setProfession(Profession prof) {
        this.prof = prof;
        watcher.add(16, prof.id);
    }

    public Profession getProfession() {
        return prof;
    }

    public enum Profession {
        FARMER(0),
        LIBRARIAN(1),
        PRIEST(2),
        BLACKSMITH(3),
        BUTCHER(4);

        int id;

        Profession(int id) {
            this.id = id;
        }
    }
}
