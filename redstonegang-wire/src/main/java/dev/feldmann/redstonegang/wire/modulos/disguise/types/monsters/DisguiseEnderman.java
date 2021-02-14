package dev.feldmann.redstonegang.wire.modulos.disguise.types.monsters;

import dev.feldmann.redstonegang.wire.modulos.disguise.annotations.ActionAnnotation;
import dev.feldmann.redstonegang.wire.modulos.disguise.annotations.SetAnnotation;
import dev.feldmann.redstonegang.wire.modulos.disguise.types.base.LivingData;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

public class DisguiseEnderman extends LivingData {

    boolean screaming = false;
    MaterialData bloco;

    public DisguiseEnderman(Player p) {
        super(p);
    }

    public boolean isScreaming() {
        return screaming;
    }

    @SetAnnotation(nome = "screaming")
    public void setScreaming(boolean screaming) {
        watcher.add(18, screaming ? (byte) 1 : (byte) 0);
        this.screaming = screaming;
    }

    public void setBloco(MaterialData bloco) {
        watcher.add(16, (short) bloco.getItemTypeId());
        watcher.add(17, bloco.getData());
        this.bloco = bloco;
    }

    @ActionAnnotation
    public void bloco() {
        setBloco(new MaterialData(Material.DIAMOND_BLOCK));
    }

    public MaterialData getBloco() {
        return bloco;
    }

    public EntityType getEntityType() {
        return EntityType.ENDERMAN;
    }
}
