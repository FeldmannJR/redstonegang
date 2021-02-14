package dev.feldmann.redstonegang.wire.game.base.addons.server.quests.hooks.bixos;


import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.QuestInfo;
import dev.feldmann.redstonegang.wire.utils.items.SpawnEggs;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class QuestMataBixo extends QuestAlgoBixo {


    @EventHandler(priority = EventPriority.MONITOR)
    public void killBixo(EntityDeathEvent ev) {
        if (ev.getEntity().getKiller() == null) return;
        Player p = ev.getEntity().getKiller();
        QuestInfo faz = getFazendo(p);
        if (faz != null) {
            for (EntityType e : ents) {
                if (ev.getEntity().getType() == e) {
                    faz.faz();
                    return;
                }
            }
        }
    }

    public ItemStack toItemStack() {
        ItemStack it;
        if (ents != null && ents.length >= 1) {
            if (ents[0] == EntityType.PLAYER) {
                return new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
            }
            it = SpawnEggs.getItemStack(ents[0]);
        } else {
            it = new ItemStack(Material.MONSTER_EGG);
        }
        return it;

    }


    @Override
    public String getNome() {
        return "Matar";
    }


}
