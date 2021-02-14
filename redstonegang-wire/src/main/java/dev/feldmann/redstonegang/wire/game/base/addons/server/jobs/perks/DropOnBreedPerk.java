package dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.perks;

import dev.feldmann.redstonegang.common.utils.Cooldown;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

public class DropOnBreedPerk extends DropOnActionPerk<Entity> {


    public DropOnBreedPerk(int level, int chance, String nome, ItemStack it) {
        super(level, chance, nome, it);
    }

    public DropOnBreedPerk(int level, int chance, String nome, Cooldown cooldown, ItemStack it) {
        super(level, chance, nome, cooldown, it);
    }

    @Override
    public Location getLocation(Entity entity) {
        return entity.getLocation();
    }
}
