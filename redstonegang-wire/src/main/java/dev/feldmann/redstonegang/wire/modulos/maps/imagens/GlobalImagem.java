package dev.feldmann.redstonegang.wire.modulos.maps.imagens;

import org.bukkit.entity.Player;

import java.awt.image.BufferedImage;

public class GlobalImagem extends Imagem {
    BufferedImage imagem;


    public GlobalImagem(int offsetX, int offsetY, BufferedImage im)
    {
        super(offsetX, offsetY);
        this.imagem = im;
    }

    @Override
    public BufferedImage getImage(Player p)
    {
        return imagem;
    }


}
