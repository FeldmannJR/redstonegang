/*
 *  _   __   _   _____   _____       ___       ___  ___   _____
 * | | |  \ | | /  ___/ |_   _|     /   |     /   |/   | /  ___|
 * | | |   \| | | |___    | |      / /| |    / /|   /| | | |
 * | | | |\   | \___  \   | |     / / | |   / / |__/ | | | |
 * | | | | \  |  ___| |   | |    / /  | |  / /       | | | |___
 * |_| |_|  \_| /_____/   |_|   /_/   |_| /_/        |_| \_____|
 *
 * Classe criada por Carlos Andre Feldmann Junior
 * Apoio: Isaias Finger, Gabriel Slomka, Gabriel Augusto Souza
 * Skype: junior.feldmann
 * GitHub: https://github.com/feldmannjr
 * Facebook: https://www.facebook.com/carlosandre.feldmannjunior
 */
package dev.feldmann.redstonegang.wire.game.base.addons.both.npcs;


import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.npc.skin.SkinnableEntity;
import net.citizensnpcs.trait.Gravity;
import net.citizensnpcs.trait.LookClose;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;


import java.util.HashSet;
import java.util.function.BiConsumer;

/**
 * @author Carlos
 */
public class NPCAddon extends Addon {

    public HashSet<RedstoneNPC> npcs = new HashSet();
    NPCRegistry reg;

    @Override
    public void onEnable() {
        reg = CitizensAPI.getNPCRegistry();
        reg.deregisterAll();
    }

    @Override
    public void onDisable() {
        for (RedstoneNPC npc : npcs) {
            removerNPC(npc);
        }
        npcs.clear();
        reg.deregisterAll();
    }

    public net.citizensnpcs.api.npc.NPC criar(RedstoneNPC npc) {
        net.citizensnpcs.api.npc.NPC ci = reg.createNPC(npc.getType(), npc.getNome());
        if (npc.voa) {
            ci.getTrait(Gravity.class).gravitate(true);
        }
        npc.setCitizens(ci);
        ci.spawn(npc.loc);
        npcs.add(npc);
        if (npc.et == EntityType.PLAYER) {
            SkinnableEntity skinnable = ci.getEntity() instanceof SkinnableEntity ? (SkinnableEntity) ci.getEntity()
                    : null;
            if (skinnable != null) {
                if (npc.getSkin() != null) {
                    skinnable.setSkinName(npc.getSkin());
                } else {
                    skinnable.setSkinName("Steve");
                }
            }
        } else {
            if (npc.et != EntityType.ENDER_DRAGON) {
                //     this.A(ArmorStandAddon.class).criar(ci.getEntity(), npc.getNome());
            }
        }
        if (npc.realLook) {
            // RealisticLooking
            ci.getTrait(LookClose.class).toggle();
            ci.getTrait(LookClose.class).setRealisticLooking(true);
        }
        return ci;
    }

    public void removerNPC(RedstoneNPC npc) {
        npc.citizens.despawn();
        npc.citizens.destroy();
        reg.deregister(npc.citizens);
    }

    @EventHandler
    public void interactnpc(PlayerInteractEntityEvent ev) {
        RedstoneNPC who = this.whoisNPC(ev.getRightClicked());
        if (who != null) {
            for (BiConsumer<Player, RedstoneNPC> c : who.clickEvents) {
                c.accept(ev.getPlayer(), who);
            }
        }
    }

    public RedstoneNPC whoisNPC(Entity who) {
        RedstoneNPC sel = null;
        for (RedstoneNPC npc : npcs) {
            if (npc.citizens != null && npc.citizens.getEntity() != null) {
                if (npc.citizens.getEntity() == who) {
                    sel = npc;
                    break;
                }
            }
        }
        return sel;
    }
}
