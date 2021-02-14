package dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.perks;

import dev.feldmann.redstonegang.common.utils.Cooldown;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.JobsAddon;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class PotionEffectPerk extends OnXpPerk {

    PotionEffect ef;


    public PotionEffectPerk(int level, int chance, String nome, PotionEffect ef) {
        super(level, nome, chance);
        this.ef = ef;
    }

    public PotionEffectPerk(int level, Cooldown cooldown, String nome, int chance, PotionEffect ef) {
        super(level, cooldown, nome, chance);
        this.ef = ef;
    }

    public PotionEffect getEffect() {
        return ef;
    }

    public boolean gainXp(Player p) {
        C.send(p, JobsAddon.BONUS, getMensagem());
        p.addPotionEffect(ef);
        return true;
    }

    public String getMensagem() {
        return "Você está quebrando blocos mais rapido!";
    }

    @Override
    public ItemStack getItemStack() {
        return new ItemStack(Material.POTION);
    }
}
