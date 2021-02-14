package dev.feldmann.redstonegang.wire.modulos.maps.imagens;

import dev.feldmann.redstonegang.wire.utils.hitboxes.Hitbox2D;
import org.bukkit.entity.Player;

import java.awt.image.BufferedImage;

public class HitboxImagem extends Imagem {

    Hitbox2D hit;

    public HitboxImagem(Hitbox2D hit) {
        super(0, 0);
        this.hit = hit;
    }

    @Override
    public Hitbox2D getHitbox() {
        return hit;
    }

    @Override
    public Hitbox2D getHitbox(BufferedImage im) {
        return hit;
    }

    @Override
    public BufferedImage getImage(Player p) {
        return null;
    }
}
