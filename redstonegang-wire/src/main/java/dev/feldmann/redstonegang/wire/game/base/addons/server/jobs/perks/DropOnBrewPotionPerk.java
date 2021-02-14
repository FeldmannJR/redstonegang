package dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.perks;

import dev.feldmann.redstonegang.common.utils.Cooldown;
import dev.feldmann.redstonegang.wire.modulos.customevents.potion.PlayerBrewPotionEvent;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class DropOnBrewPotionPerk extends DropOnActionPerk<PlayerBrewPotionEvent> {

    public DropOnBrewPotionPerk(int level, int chance, String nome, ItemStack it) {
        super(level, chance, nome, it);
    }

    public DropOnBrewPotionPerk(int level, int chance, String nome, Cooldown cooldown, ItemStack it) {
        super(level, chance, nome, cooldown, it);
    }

    @Override
    public Location getLocation(PlayerBrewPotionEvent playerBrewPotionEvent) {
        if (playerBrewPotionEvent.getBrewer() != null) {
            return playerBrewPotionEvent.getBrewer().getLocation();
        }
        return playerBrewPotionEvent.getPlayer().getLocation();
    }


}
