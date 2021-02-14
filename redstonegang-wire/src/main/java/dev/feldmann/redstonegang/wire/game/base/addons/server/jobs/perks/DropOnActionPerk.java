package dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.perks;

import dev.feldmann.redstonegang.common.utils.Cooldown;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.JobsAddon;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class DropOnActionPerk<T> extends JobPerk {


    ItemStack it;

    public DropOnActionPerk(int level, int chance, String nome, ItemStack it) {
        super(level, nome, chance);
        this.it = it;
    }

    public DropOnActionPerk(int level, int chance, String nome, Cooldown cooldown, ItemStack it) {
        super(level, cooldown, nome, chance);
        this.it = it;
    }

    public ItemStack getItemStack() {
        return it.clone();
    }

    public ItemStack[] getDropItemStack(Player p, T ent) {
        return new ItemStack[]{it.clone()};
    }

    public void dropItemStack(ItemStack it, Player p, T ent) {
        Location l = getLocation(ent);
        l.getWorld().dropItemNaturally(l.clone().add(0.5, 0.5, 0.5), it);
    }

    public boolean onAction(Player p, T ent) {
        if (this instanceof SpecificAction) {
            SpecificAction<T, ?> s = (SpecificAction<T, ?>) this;
            if (!s.canDrop(ent)) {
                return false;
            }
        }
        ItemStack[] it = getDropItemStack(p, ent);
        if (it == null) return false;
        for (ItemStack itemStack : it) {
            dropItemStack(itemStack, p, ent);
        }
        C.send(p, JobsAddon.BONUS, "Você dropou %% extra!", it);
        return true;
    }

    public abstract Location getLocation(T t);
}
