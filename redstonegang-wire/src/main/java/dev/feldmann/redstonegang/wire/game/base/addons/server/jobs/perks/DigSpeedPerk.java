package dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.perks;

import dev.feldmann.redstonegang.common.utils.Cooldown;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class DigSpeedPerk extends PotionEffectPerk {


    public DigSpeedPerk(int level, int chance, String nome, int duracao, int forca) {
        super(level, new Cooldown((duracao + 5) * 1000), nome, chance, new PotionEffect(PotionEffectType.FAST_DIGGING, duracao * 20, forca));
    }

    public DigSpeedPerk(int level, Cooldown cooldown, String nome, int chance, int duracao, int forca) {
        super(level, cooldown, nome, chance, new PotionEffect(PotionEffectType.FAST_DIGGING, duracao * 20, forca));
    }

    @Override
    public String getMensagem() {
        return "Você está quebrando blocos mais rapido!";
    }


    @Override
    public ItemStack getItemStack() {
        return new ItemStack(Material.SUGAR);
    }
}
