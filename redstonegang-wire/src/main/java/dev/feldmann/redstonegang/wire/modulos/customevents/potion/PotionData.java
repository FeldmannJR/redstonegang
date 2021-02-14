package dev.feldmann.redstonegang.wire.modulos.customevents.potion;

import org.bukkit.potion.PotionEffectType;

public class PotionData {
    private PotionEffectType type;
    private PlayerBrewPotionEvent.UpdatePotionType update;

    public PotionData(PotionEffectType type, PlayerBrewPotionEvent.UpdatePotionType update) {
        this.type = type;
        this.update = update;
    }

    public PotionEffectType getType() {
        return type;
    }

    public PlayerBrewPotionEvent.UpdatePotionType getUpdate() {
        return update;
    }
}
