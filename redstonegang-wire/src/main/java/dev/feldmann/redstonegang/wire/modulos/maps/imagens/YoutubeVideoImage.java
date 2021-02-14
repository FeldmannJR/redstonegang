package dev.feldmann.redstonegang.wire.modulos.maps.imagens;


import dev.feldmann.redstonegang.common.api.youtube.YoutubeAPI;
import dev.feldmann.redstonegang.wire.modulos.maps.Quadro;
import dev.feldmann.redstonegang.wire.modulos.maps.QuadroUtils;
import org.bukkit.entity.Player;

public class YoutubeVideoImage extends GlobalImagem {

    YoutubeAPI.VideoInfo info;

    public YoutubeVideoImage(int offsetX, int offsetY, YoutubeAPI.VideoInfo info) {
        super(offsetX, offsetY, QuadroUtils.getYoutubeThumb(info));
        this.info = info;
    }


    @Override
    public boolean click(Player p, Quadro quadro) {
        p.sendMessage("§f§n" + info.getLink());
        return true;
    }
}
