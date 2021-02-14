package dev.feldmann.redstonegang.wire.game.games.other.hub;

import dev.feldmann.redstonegang.common.api.maps.responses.MapResponse;
import dev.feldmann.redstonegang.wire.game.base.Server;
import dev.feldmann.redstonegang.wire.game.base.addons.minigames.maps.Mapa;
import dev.feldmann.redstonegang.wire.game.base.addons.minigames.maps.MapaType;
import dev.feldmann.redstonegang.wire.game.base.addons.both.npcs.NPCAddon;


public class Hub extends Server {

    Mapa mapaHub;

    @Override
    public void enable() {
        super.enable();
        addAddon(
                new NPCAddon(),
                new NPCs()
        );

    }

    @Override
    public void lateEnable() {
        super.lateEnable();
        MapResponse res = mapas().find("hub", "1");
        if (res != null) {
            this.mapas().load(res, MapaType.GAME);
        }
        if (isInvalid()) return;
    }
}
