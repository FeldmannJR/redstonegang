package dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.perks;

import dev.feldmann.redstonegang.common.utils.Cooldown;
import dev.feldmann.redstonegang.wire.utils.items.ItemUtils;
import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DropOnFishPerk extends DropOnActionPerk<Item> {

    public DropOnFishPerk(int level, int chance, String nome, ItemStack it) {
        super(level, chance, nome, it);
    }

    public DropOnFishPerk(int level, int chance, String nome, Cooldown cooldown, ItemStack it) {
        super(level, chance, nome, cooldown, it);
    }

    @Override
    public Location getLocation(Item item) {
        return item.getLocation();
    }

    @Override
    public void dropItemStack(ItemStack it, Player p, Item ent) {
        ItemUtils.dropItemToPlayerLikeFishing(p, ent.getLocation(), it);
    }
}
