package dev.feldmann.redstonegang.wire.game.base.addons.server.jobs;

import org.bukkit.Material;
import org.bukkit.material.MaterialData;

public class ExactMaterialData extends MaterialData {
    public ExactMaterialData(Material type, byte data) {
        super(type, data);
    }
}
