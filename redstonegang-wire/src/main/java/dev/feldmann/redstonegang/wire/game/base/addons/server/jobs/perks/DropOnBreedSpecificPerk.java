package dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.perks;

import dev.feldmann.redstonegang.common.utils.Cooldown;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class DropOnBreedSpecificPerk extends DropOnBreakPerk implements SpecificAction<Entity, EntityType> {

    EntityType[] filter;

    public DropOnBreedSpecificPerk(int level, int chance, String nome, ItemStack it, EntityType... filter) {
        super(level, chance, nome, it);
        this.filter = filter;
    }

    @Override
    public EntityType convert(Entity entity) {
        return entity.getType();
    }

    @Override
    public EntityType[] getFilters() {
        return filter;
    }
}
