package dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.imagemspawn;

import dev.feldmann.redstonegang.common.api.youtube.YoutubeAPI;
import dev.feldmann.redstonegang.wire.game.base.apis.configurablemap.ConfigurableMapAPI;
import dev.feldmann.redstonegang.wire.game.base.apis.configurablemap.MapConfigurable;
import dev.feldmann.redstonegang.wire.game.base.events.ServerLoadedEvent;
import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import dev.feldmann.redstonegang.wire.game.base.objects.annotations.Dependencies;
import dev.feldmann.redstonegang.wire.modulos.maps.Quadro;
import dev.feldmann.redstonegang.wire.modulos.maps.QuadroUtils;
import dev.feldmann.redstonegang.wire.modulos.maps.QuadrosManager;
import dev.feldmann.redstonegang.wire.modulos.maps.imagens.YoutubeVideoImage;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import java.awt.*;

@Dependencies(apis = ConfigurableMapAPI.class)
public class ImagemSpawn extends Addon {


    @MapConfigurable
    Block start;

    @MapConfigurable
    Block lastVideo;

    @MapConfigurable
    Block lastVideoKnadez;

    @Override
    public void onEnable() {
    }

    public void startLastVideo() {
        if (lastVideo != null) {
            Quadro c = new Quadro(QuadroUtils.getImageFromColor(Color.BLACK, 128 * 2, 128 * 2));
            c.addImagem(new YoutubeVideoImage(8, 0, YoutubeAPI.getLastVideoFromChannel(YoutubeAPI.getChannelId("https://www.youtube.com/channel/UCcn0BFXHCtkqJl8CAPDj9og"))));
            QuadrosManager.setQuadro(lastVideo.getLocation(), c);
        }
        if (lastVideoKnadez != null) {
            Quadro c = new Quadro(QuadroUtils.getImageFromColor(new Color(125, 0, 16), 128 * 2, 128 * 2));
            c.addImagem(new YoutubeVideoImage(8, 0, YoutubeAPI.getLastVideoFromChannel(YoutubeAPI.getChannelId("https://www.youtube.com/channel/UCj_eXjocAsyFCzCwapNPaFA"))));
            QuadrosManager.setQuadro(lastVideoKnadez.getLocation(), c);

        }

    }

    public void startHelp() {
        if (start != null) {
            boolean b = QuadrosManager.setQuadro(start.getLocation(), new TutorialQuadro());
            if (!b) {
                System.out.println("Não botei");
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void serverLoaded(ServerLoadedEvent ev) {
        startLastVideo();
        startHelp();
    }
}
