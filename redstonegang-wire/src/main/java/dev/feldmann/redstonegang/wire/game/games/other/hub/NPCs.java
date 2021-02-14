package dev.feldmann.redstonegang.wire.game.games.other.hub;

import dev.feldmann.redstonegang.wire.game.base.Games;
import dev.feldmann.redstonegang.wire.game.base.addons.both.npcs.NPCAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.npcs.RedstoneNPC;
import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

public class NPCs extends Addon {

    @Override
    public void onStart() {
        for (Games g : Games.values()) {
            Location loc = getHub().mapaHub.getConfig().getWorldLocation("npc_" + g.name());
            RedstoneNPC npc = new RedstoneNPC(loc, EntityType.PLAYER, "§c" + g.getEntry().getNome());
            a(NPCAddon.class).criar(npc);
        }
    }

    public Hub getHub() {
        return (Hub) server();
    }
}
