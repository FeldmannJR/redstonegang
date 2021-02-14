package dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.imagemspawn;

import dev.feldmann.redstonegang.wire.modulos.maps.Quadro;
import dev.feldmann.redstonegang.wire.modulos.maps.QuadroUtils;
import dev.feldmann.redstonegang.wire.modulos.maps.imagens.Imagem;
import dev.feldmann.redstonegang.wire.utils.hitboxes.Vector2;
import org.bukkit.entity.Player;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BotaoImagem extends Imagem {

    private static final int tamanhoBotao = 40;
    private static final int tamanhoBotaoX = 150;

    String name;
    int slot;
    public BufferedImage im_ativo;
    public BufferedImage im_desativo;

    private TutorialQuadro quadro;

    public BotaoImagem(int offsetX, int offsetY, String name, int slot, TutorialQuadro quadro) {
        super(offsetX, offsetY);
        this.name = name;
        this.slot = slot;
        this.quadro = quadro;
        im_ativo = renderButton(true);
        im_desativo = renderButton(false);
    }

    @Override
    public BufferedImage getImage(Player p) {
        return null;
    }


    public BufferedImage renderButton(boolean ativo) {
        BufferedImage tmp = QuadroUtils.copyImage(ativo ? TutorialQuadro.botaoativo : TutorialQuadro.botao);
        String txt = name;
        int size = 16;
        Vector2 s = QuadroUtils.getSize(txt, size);

        int posTextoX = ((tamanhoBotaoX - s.getX()) / 2);
        int posTextoY = ((tamanhoBotao - s.getY()) / 2);
        QuadroUtils.drawMinecraftText(txt, ativo ? Color.GRAY : Color.BLACK, size, tmp, posTextoX + 2, posTextoY + 2);
        QuadroUtils.drawMinecraftText(txt, ativo ? Color.RED : Color.YELLOW, size, tmp, posTextoX, posTextoY);
        return tmp;

    }

    @Override
    public boolean click(Player p, Quadro quadro) {
        return this.quadro.clickMenu(p, slot);
    }
}
