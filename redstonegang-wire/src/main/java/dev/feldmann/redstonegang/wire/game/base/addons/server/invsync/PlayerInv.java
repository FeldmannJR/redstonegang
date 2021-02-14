package dev.feldmann.redstonegang.wire.game.base.addons.server.invsync;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.Collection;

public class PlayerInv {

    ItemStack[] invContents;
    ItemStack[] armorContents;
    ItemStack[] enderContents;
    Integer selectedItem;

    Collection<PotionEffect> potions;
    Double healthScale;
    //
    Integer foodLevel;
    Float foodExhaustion;
    Float foodSaturation;

    Float exp;
    Integer level;

    Player p;

    public PlayerInv(){

    }
    public PlayerInv(Player p) {
        this.p = p;
        //Inv
        invContents = p.getInventory().getContents();
        armorContents = p.getInventory().getArmorContents();
        enderContents = p.getEnderChest().getContents();
        selectedItem = p.getInventory().getHeldItemSlot();
        //Potions
        potions = p.getActivePotionEffects();
        //Food
        foodSaturation = p.getSaturation();
        foodExhaustion = p.getExhaustion();
        foodLevel = p.getFoodLevel();
        //Xp
        exp = p.getExp();
        level = p.getLevel();
        //Vida
        healthScale = p.getHealthScale();


    }

}
