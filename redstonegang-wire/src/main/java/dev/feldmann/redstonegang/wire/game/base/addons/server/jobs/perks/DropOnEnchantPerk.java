package dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.perks;

import dev.feldmann.redstonegang.common.utils.Cooldown;
import org.bukkit.Location;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.inventory.ItemStack;

public class DropOnEnchantPerk extends DropOnActionPerk<EnchantItemEvent> {

    public DropOnEnchantPerk(int level, int chance, String nome, ItemStack it) {
        super(level, chance, nome, it);
    }

    public DropOnEnchantPerk(int level, int chance, String nome, Cooldown cooldown, ItemStack it) {
        super(level, chance, nome, cooldown, it);
    }

    @Override
    public Location getLocation(EnchantItemEvent enchantItemEvent) {
        return enchantItemEvent.getEnchanter().getLocation();
    }


}
