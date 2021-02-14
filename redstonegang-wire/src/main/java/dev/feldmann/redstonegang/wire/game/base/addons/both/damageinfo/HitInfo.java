package dev.feldmann.redstonegang.wire.game.base.addons.both.damageinfo;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.wire.RedstoneGangSpigot;
import dev.feldmann.redstonegang.wire.modulos.language.lang.LanguageHelper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class HitInfo {
    Entity damager;
    HitType type;
    ItemStack item = null;
    double amount;
    long when;

    Integer damagerId = null;

    public HitInfo(Entity damager, HitType type, double amount, long when) {
        this.damager = damager;
        this.type = type;
        this.when = when;
        this.amount = amount;
        if (damager instanceof Player) {
            damagerId = RedstoneGangSpigot.getPlayer((Player) damager).getId();
        }
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public HitType getType() {
        return type;
    }

    public ItemStack getItem() {
        return item;
    }

    public String getKillerDisplayname() {
        return RedstoneGang.getPlayer(damagerId).getDisplayName();
    }

    public double getAmount() {
        return amount;
    }

    public long getTimestamp() {
        return when;
    }

    public Entity getDamager() {
        return damager;
    }

    public String getItemName() {
        if (item != null) {
            return LanguageHelper.getItemName(item);
        }

        return "punhos";
    }

    public boolean isPlayerDamager() {
        return damagerId != null;
    }

    public User getUserDamager() {
        if (this.damagerId != null) {
            return RedstoneGang.getPlayer(this.damagerId);
        }
        return null;
    }

    public Player getPlayerDamager() {
        if (damager instanceof Player) {
            return (Player) damager;
        } else {
            return null;
        }
    }
}
