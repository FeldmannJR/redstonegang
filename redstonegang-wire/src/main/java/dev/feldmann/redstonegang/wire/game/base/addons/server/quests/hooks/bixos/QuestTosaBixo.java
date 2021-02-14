package dev.feldmann.redstonegang.wire.game.base.addons.server.quests.hooks.bixos;


import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.QuestInfo;
import dev.feldmann.redstonegang.wire.game.base.addons.server.tosar.PlayerShearAnimalEvent;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.inventory.ItemStack;

public class QuestTosaBixo extends QuestAlgoBixo {


    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void captura(PlayerShearAnimalEvent ev) {
        Player p = ev.getPlayer();
        QuestInfo faz = getFazendo(p);
        if (faz != null) {
            for (EntityType e : ents) {
                if (ev.getTosado().getType() == e) {
                    faz.faz();
                    return;
                }
            }
        }

    }

    public ItemStack toItemStack() {
        return new ItemStack(Material.SHEARS);

    }


    @Override
    public String getNome() {
        return "Tose";
    }
}
