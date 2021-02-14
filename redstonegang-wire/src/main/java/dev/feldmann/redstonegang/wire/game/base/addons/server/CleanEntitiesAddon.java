package dev.feldmann.redstonegang.wire.game.base.addons.server;

import dev.feldmann.redstonegang.common.utils.formaters.time.TimeUtils;
import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;

public class CleanEntitiesAddon extends Addon {

    private int tempoLimpar = 60 * 20;
    int tempo = 20;

    @Override
    public void onEnable() {


        scheduler().rodaRepeting(this::segundo, 20);

    }

    public void segundo() {
        if (tempo == 30 || tempo == 10) {
            if (!Bukkit.getOnlinePlayers().isEmpty())
                Bukkit.broadcastMessage("§bLimpando itens do chão em §f" + TimeUtils.millisToString(tempo * 1000L) + "§b!");
        }
        if (tempo == 0) {
            tempo = tempoLimpar;
            limpa();
        }

        tempo--;
    }


    public void limpa() {
        if (!Bukkit.getOnlinePlayers().isEmpty())
            Bukkit.broadcastMessage("§bLimpando itens do chão §b§lAGORA!");
        for (World w : Bukkit.getWorlds()) {
            for (Entity e : w.getEntities()) {
                if (e.hasMetadata("NPC")) {
                    continue;
                }
                if (e.getType() == EntityType.DROPPED_ITEM || e.getType() == EntityType.ARROW
                        || e.getType() == EntityType.EXPERIENCE_ORB
                        || e.getType() == EntityType.FALLING_BLOCK) {
                    e.remove();
                }

                if (e instanceof Monster) {
                    int cont = 0;
                    for (Entity ent : e.getNearbyEntities(20, 20, 20)) {
                        if (ent.getType() == EntityType.PLAYER) {
                            cont++;
                            break;
                        }
                    }
                    if (cont == 0) {
                        e.remove();
                    }

                }
            }
        }

    }
}
