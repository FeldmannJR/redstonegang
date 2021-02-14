package dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.perks;

import dev.feldmann.redstonegang.common.utils.Cooldown;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.ExactMaterialData;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public class DropOnBreakSpecificPerk extends DropOnBreakPerk implements SpecificAction<Block, MaterialData> {

    MaterialData[] filters;

    public DropOnBreakSpecificPerk(int level, int chance, String nome, ItemStack it, MaterialData... filters) {
        super(level, chance, nome, it);
        this.filters = filters;
    }

    public DropOnBreakSpecificPerk(int level, int chance, String nome, Cooldown cooldown, ItemStack it, MaterialData... filters) {
        super(level, chance, nome, cooldown, it);
        this.filters = filters;
    }

    @Override
    public MaterialData convert(Block block) {
        return new MaterialData(block.getType(), block.getData());
    }

    @Override
    public MaterialData[] getFilters() {
        return filters;
    }

    @Override
    public boolean apply(MaterialData entity, MaterialData filter) {
        if (entity.getItemType() == filter.getItemType()) {
            if (filter instanceof ExactMaterialData) {
                return filter.getData() == entity.getData();
            }
            return true;
        }
        return false;
    }

    @Override
    public Location getLocation(Block block) {
        return block.getLocation();
    }
}
