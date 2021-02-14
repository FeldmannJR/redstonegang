package dev.feldmann.redstonegang.wire.modulos.maps.imagens;

import dev.feldmann.redstonegang.wire.modulos.maps.Quadro;
import dev.feldmann.redstonegang.wire.utils.hitboxes.Hitbox2D;
import org.bukkit.entity.Player;

import java.awt.image.BufferedImage;

public abstract class Imagem {

    int offsetX, offsetY;
    private Hitbox2D hitbox;

    public Imagem(int offsetX, int offsetY)
    {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public abstract BufferedImage getImage(Player p);

    public Hitbox2D getHitbox(BufferedImage im)
    {
        hitbox = new Hitbox2D(offsetX, offsetY, offsetX + im.getWidth(), offsetY + im.getHeight());
        return hitbox;
    }

    /*
     * Somente chamar este metodo dps que renderizou a imagem
     * */
    public Hitbox2D getHitbox()
    {
        return hitbox;
    }


    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    /*
    *  Chamado ao clickar na foto
    * */
    public boolean click(Player p, Quadro quadro)
    {
        return false;
    }
}
