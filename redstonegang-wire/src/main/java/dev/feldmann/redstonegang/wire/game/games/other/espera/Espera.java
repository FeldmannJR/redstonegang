package dev.feldmann.redstonegang.wire.game.games.other.espera;


import dev.feldmann.redstonegang.common.api.maps.responses.MapResponse;
import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.wire.RedstoneGangSpigot;
import dev.feldmann.redstonegang.wire.game.base.addons.minigames.maps.Mapa;
import dev.feldmann.redstonegang.wire.game.base.addons.minigames.maps.MapaType;
import dev.feldmann.redstonegang.wire.game.base.events.player.PlayerJoinServerEvent;
import dev.feldmann.redstonegang.wire.game.base.Server;

import org.bukkit.event.EventHandler;

public class Espera extends Server {

    Mapa mapa;

    @Override
    public void lateEnable() {
        super.lateEnable();
        MapResponse res = mapas().find("espera", "Apex");
        if (res != null) {
            this.mapas().load(res, MapaType.GAME);
        }
    }

    @Override
    public void disable() {
        super.disable();
    }

    @EventHandler
    public void join(PlayerJoinServerEvent ev) {

        User rgpl = RedstoneGangSpigot.getPlayer(ev.getPlayer());

        /* TODO */
        //RedstoneGangSpigot.instance.user().getPunishment();

        /*ev.getUser().teleport(mapa.getSpawn());
        redstonegang().player().loadPlayer(ev.getUser().getUniqueId(), (pl) -> {
            if (pl == null) {
                ev.getUser().sendMessage("DEU PAU");
                return;
            }
            ScoreboardManager.addToTeam(ev.getUser(), ev.getUser().getName(), ev.getUser().getName(), pl.getGroup().getMinecraftPrefix(), pl.getGroup().getMinecraftSuffix(), false, NameTagVisibility.NEVER);
            pl.addPermission(Permissions.VIP.name(), Permission.ALLOW);
        });
        ev.getUser().sendMessage("Teste");*/
    }

}
