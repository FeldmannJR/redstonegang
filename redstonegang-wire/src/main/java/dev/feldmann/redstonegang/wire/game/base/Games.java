package dev.feldmann.redstonegang.wire.game.base;

import dev.feldmann.redstonegang.wire.game.base.objects.ServerEntry;
import dev.feldmann.redstonegang.wire.game.games.other.espera.EsperaEntry;
import dev.feldmann.redstonegang.wire.game.games.other.hub.HubEntry;
import dev.feldmann.redstonegang.wire.game.games.other.login.LoginEntry;
import dev.feldmann.redstonegang.wire.game.games.other.mapconfig.MapConfigGameEntry;
import dev.feldmann.redstonegang.wire.game.games.servers.build.BuildEntry;
import dev.feldmann.redstonegang.wire.game.games.servers.survival.minerar.SurvivalMinerarEntry;
import dev.feldmann.redstonegang.wire.game.games.servers.survival.terrenos.SurvivalTerrenosEntry;
import dev.feldmann.redstonegang.wire.game.games.servers.worldgen.WorldGenBlueprint;

public enum Games {

    LOGIN(new LoginEntry()),
    ESPERA(new EsperaEntry()),
    HUB(new HubEntry()),
    MAPCONFIG(new MapConfigGameEntry()),
    SURVIVAL_TERRENOS(new SurvivalTerrenosEntry()),
    SURVIVAL_MINERAR(new SurvivalMinerarEntry()),
    BUILD(new BuildEntry()),
    WORLDGEN(new WorldGenBlueprint());

    ServerEntry entry;

    Games(ServerEntry entry) {
        this.entry = entry;
    }

    public ServerEntry getEntry() {
        return entry;
    }
}
