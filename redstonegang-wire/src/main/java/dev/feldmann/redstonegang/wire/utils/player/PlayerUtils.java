package dev.feldmann.redstonegang.wire.utils.player;

import dev.feldmann.redstonegang.wire.utils.NMSUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class PlayerUtils {

    public static void limpa(Player player) {
        player.setSprinting(false);
        player.setSneaking(false);
        player.setFoodLevel(20);
        player.setSaturation(3f);
        player.setExhaustion(0f);
        player.setMaxHealth(20);
        player.setHealth(player.getMaxHealth());
        player.setFireTicks(0);
        player.setFallDistance(0);
        player.eject();
        player.leaveVehicle();
        player.setLevel(0);
        player.setExp(0F);
        player.getInventory().clear();
        player.getInventory().setArmorContents(new ItemStack[]{null, null, null, null});
        for (PotionEffect pt : player.getActivePotionEffects()) {
            player.removePotionEffect(pt.getType());
        }
        NMSUtils.setArrows(player, 0);
    }

}
