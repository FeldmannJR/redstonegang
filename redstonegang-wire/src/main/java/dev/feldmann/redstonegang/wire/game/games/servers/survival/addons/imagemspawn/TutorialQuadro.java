package dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.imagemspawn;

import dev.feldmann.redstonegang.common.utils.Cooldown;
import dev.feldmann.redstonegang.wire.modulos.maps.Quadro;
import dev.feldmann.redstonegang.wire.modulos.maps.QuadroUtils;
import dev.feldmann.redstonegang.wire.modulos.maps.imagens.Imagem;
import dev.feldmann.redstonegang.wire.utils.hitboxes.Vector2;
import org.bukkit.entity.Player;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.UUID;

public class TutorialQuadro extends Quadro {

    Cooldown cd = new Cooldown(2500);
    public static BufferedImage bg;
    public static BufferedImage botao;
    public static BufferedImage botaoativo;
    public static TutorialPage[] pages;
    private byte[][] preRender;

    public HashMap<UUID, Integer> playerViews = new HashMap<>();

    static {

        bg = readFromResources("/resources/survival/tutorial/bg.png");
        botao = readFromResources("/resources/survival/tutorial/botao.png");
        botaoativo = readFromResources("/resources/survival/tutorial/botaoativo.png");
        pages = new TutorialPage[]{
                new TutorialPage("Bem Vindos", readTutorial("1.png")),
                new TutorialPage("Chat", readTutorial("2.png")),
                new TutorialPage("Economia", readTutorial("3.png")),
                new TutorialPage("Terrenos", readTutorial("4.png")),
                new TutorialPage("Clans", readTutorial("5.png")),
                new TutorialPage("Quests", readTutorial("6.png")),
                new TutorialPage("Comandos", readTutorial("7.png")),
        };

    }

    int offsetX = 20;
    int offsetY = 20;
    int tamanhoBotao = 40;
    Vector2 contentPosition = new Vector2(190, 20);
    //tam 686 344


    public void addButtons() {
        for (int x = 0; x < pages.length; x++) {
            int posY = offsetY + (x * (tamanhoBotao + 10));
            pages[x].botao = (BotaoImagem) addImagem(new BotaoImagem(offsetX, posY, pages[x].name, x, this));
        }
    }


    public static BufferedImage readFromResources(String img) {
        InputStream fis = TutorialQuadro.class.getResourceAsStream(img);
        try {
            return ImageIO.read(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BufferedImage readTutorial(String img) {
        return readFromResources("/resources/survival/tutorial/" + img);
    }

    public TutorialQuadro() {
        super(bg);
        addButtons();
        showPage();
        preRender = new byte[pages.length][];
        for (int x = 0; x < pages.length; x++) {
            preRender[x] = preRender(x);
        }
    }

    @Override
    public byte[] getData(Player p) {
        return preRender[getPage(p)];
    }

    public byte[] preRender(int page) {
        BufferedImage bg = QuadroUtils.copyImage(TutorialQuadro.bg);
        for (TutorialPage p : pages) {
            BotaoImagem i = p.botao;
            if (page == i.slot) {
                bg.getGraphics().drawImage(i.im_ativo, i.getOffsetX(), i.getOffsetY(), null);
            } else {
                bg.getGraphics().drawImage(i.im_desativo, i.getOffsetX(), i.getOffsetY(), null);
            }
            i.getHitbox(i.im_ativo);
        }
        bg.getGraphics().drawImage(pages[page].image, contentPosition.getX(), contentPosition.getY(), null);
        return QuadroUtils.drawImage(bg);


    }

    public void showPage() {
        addImagem(new Imagem(contentPosition.getX(), contentPosition.getY()) {
            @Override
            public BufferedImage getImage(Player p) {
                return null;
            }
        });
    }


    public boolean clickMenu(Player p, int item) {
        if (this.isRendering(p)) {
            return false;
        }
        if (cd.isCooldown(p.getUniqueId())) {
            return false;
        }
        if (item == getPage(p)) {
            return false;
        }
        cd.addCooldown(p.getUniqueId());
        playerViews.put(p.getUniqueId(), item);
        p.sendMessage("Atualizando para a pagina " + pages[item].name);
        sendPackets(p);
        return true;
    }

    public int getPage(Player p) {
        if (playerViews.containsKey(p.getUniqueId())) {
            return playerViews.get(p.getUniqueId());
        }
        return 0;
    }

}
