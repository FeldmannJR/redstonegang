package dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.perks;

import dev.feldmann.redstonegang.common.utils.Cooldown;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class DropOnKillSpecificPerk extends DropOnKillPerk implements SpecificAction<Entity, EntityType> {

    EntityType[] filters;

    public DropOnKillSpecificPerk(int level, int chance, String nome, ItemStack it, EntityType... filters) {
        super(level, chance, nome, it);
        this.filters = filters;
    }

    @Override
    public EntityType convert(Entity entity) {
        return entity.getType();
    }

    @Override
    public EntityType[] getFilters() {
        return filters;
    }

    @Override
    public Location getLocation(Entity entity) {
        return entity.getLocation();
    }


}
