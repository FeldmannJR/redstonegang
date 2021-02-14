package dev.feldmann.redstonegang.wire.game.base.addons.server.quests.hooks.itens;

import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.QuestInfo;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

public class QuestPesque extends QuestAlgoItem {


    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void fish(PlayerFishEvent ev) {
        if (ev.getCaught() != null && ev.getCaught().getType() == EntityType.DROPPED_ITEM) {
            QuestInfo fazendo = getFazendo(ev.getPlayer());
            if (fazendo != null) {
                Item it = (Item) ev.getCaught();
                if (isValid(it.getItemStack())) {
                    fazendo.faz();
                }
            }

        }
    }

    @Override
    public ItemStack toItemStack() {
        return new ItemStack(Material.FISHING_ROD);
    }

    @Override
    public String getNome() {
        return "Pesque";
    }
}
