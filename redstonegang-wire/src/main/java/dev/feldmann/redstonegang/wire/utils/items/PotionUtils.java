package dev.feldmann.redstonegang.wire.utils.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

public class PotionUtils {
    public static boolean isNegative(PotionEffectType ef) {
        int p = ef.getId();
        if (p == PotionEffectType.ABSORPTION.getId()) {
            return false;
        } else if (p == PotionEffectType.BLINDNESS.getId()) {
            return true;
        } else if (p == PotionEffectType.CONFUSION.getId()) {
            return true;
        } else if (p == PotionEffectType.DAMAGE_RESISTANCE.getId()) {
            return false;
        } else if (p == PotionEffectType.FAST_DIGGING.getId()) {
            return false;
        } else if (p == PotionEffectType.FIRE_RESISTANCE.getId()) {
            return false;
        } else if (p == PotionEffectType.HARM.getId()) {
            return true;
        } else if (p == PotionEffectType.HEAL.getId()) {
            return false;
        } else if (p == PotionEffectType.HEALTH_BOOST.getId()) {
            return false;
        } else if (p == PotionEffectType.HUNGER.getId()) {
            return true;
        } else if (p == PotionEffectType.INCREASE_DAMAGE.getId()) {
            return false;
        } else if (p == PotionEffectType.INVISIBILITY.getId()) {
            return false;
        } else if (p == PotionEffectType.JUMP.getId()) {
            return false;
        } else if (p == PotionEffectType.NIGHT_VISION.getId()) {
            return false;
        } else if (p == PotionEffectType.POISON.getId()) {
            return true;
        } else if (p == PotionEffectType.REGENERATION.getId()) {
            return false;
        } else if (p == PotionEffectType.SATURATION.getId()) {
            return false;
        } else if (p == PotionEffectType.SLOW.getId()) {
            return true;
        } else if (p == PotionEffectType.SLOW_DIGGING.getId()) {
            return true;
        } else if (p == PotionEffectType.SPEED.getId()) {
            return false;
        } else if (p == PotionEffectType.WATER_BREATHING.getId()) {
            return false;
        } else if (p == PotionEffectType.WEAKNESS.getId()) {
            return true;
        } else return p == PotionEffectType.WITHER.getId();
    }

    public static ItemStack buildAwkward(int quantidade) {
        return new ItemStack(Material.POTION, quantidade, (short) 16);

    }
}
