package dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.perks;

import dev.feldmann.redstonegang.common.utils.Cooldown;
import dev.feldmann.redstonegang.common.utils.RandomUtils;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class JobPerk {
    int level;
    Cooldown cooldown = null;
    String nome;
    private int chance;


    public JobPerk(int level, Cooldown cooldown, String nome, int chance) {
        this.level = level;
        this.cooldown = cooldown;
        this.nome = nome;
        this.chance = chance;
    }

    public JobPerk(int level, String nome, int chance) {
        this.level = level;
        this.nome = nome;
        this.chance = chance;
    }

    public boolean roll() {
        return RandomUtils.randomInt(0, chance) == 0;
    }

    public int getChance() {
        return chance;
    }

    public void addCooldown(Player p) {
        if (cooldown != null) {
            cooldown.addCooldown(p.getUniqueId());
        }
    }

    public boolean isCooldown(Player p) {
        if (cooldown != null) {
            return cooldown.isCooldown(p.getUniqueId());
        }
        return false;
    }

    public String getNome() {
        return nome;
    }

    public int getLevel() {
        return level;
    }

    public abstract ItemStack getItemStack();
}
