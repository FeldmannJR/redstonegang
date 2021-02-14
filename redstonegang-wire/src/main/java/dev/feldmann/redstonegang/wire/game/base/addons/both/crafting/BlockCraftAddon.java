package dev.feldmann.redstonegang.wire.game.base.addons.both.crafting;

import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.PrepareItemCraftEvent;

import java.util.ArrayList;
import java.util.List;

public class BlockCraftAddon extends Addon {

    private List<Material> blocked = new ArrayList();

    public BlockCraftAddon(Material... blocked) {
        if (blocked != null) {
            for (Material m : blocked) {
                this.blocked.add(m);
            }
        }
    }

    @EventHandler
    public void craft(PrepareItemCraftEvent ev) {
        if (ev.getRecipe().getResult() != null) {
            if (blocked.contains(ev.getRecipe().getResult().getType())) {
                ev.getInventory().setResult(null);
            }
        }
    }
}
