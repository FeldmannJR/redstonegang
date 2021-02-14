package dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.perks;

import dev.feldmann.redstonegang.common.utils.Cooldown;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

public class DropOnKillPerk extends DropOnActionPerk<Entity> {


    public DropOnKillPerk(int level, int chance, String nome, ItemStack it) {
        super(level, chance, nome, it);
    }

    @Override
    public Location getLocation(Entity entity) {
        return entity.getLocation();
    }
}
