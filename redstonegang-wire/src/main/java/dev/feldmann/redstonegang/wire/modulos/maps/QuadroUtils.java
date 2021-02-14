package dev.feldmann.redstonegang.wire.modulos.maps;


import dev.feldmann.redstonegang.common.api.youtube.YoutubeAPI;

import dev.feldmann.redstonegang.wire.utils.hitboxes.Vector2;
import com.google.gson.Gson;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.map.MapFont;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Base64;
import java.util.Map;

public class QuadroUtils {
    protected static byte matchColor(int rgb) {
        int index = 0;
        double best = -1.0D;
        for (int i = 4; i < MAP_COLORS.length; i++) {
            double distance = getDistance(rgb, MAP_COLORS[i].getRGB());
            if (distance < best || best == -1.0D) {
                best = distance;
                index = i;
            }
        }
        return (byte) (index < 128 ? index : -129 + index - 127);
    }

    protected static byte matchColor(Color color) {
        if (color.getAlpha() < 128) {
            return 0;
        }
        int index = 0;
        double best = -1.0D;
        for (int i = 4; i < MAP_COLORS.length; i++) {
            double distance = getDistance(color, MAP_COLORS[i]);
            if (distance < best || best == -1.0D) {
                best = distance;
                index = i;
            }
        }
        return (byte) (index < 128 ? index : -129 + index - 127);
    }

    protected static double getDistance(int r1, int g1, int b1, int r2, int g2, int b2) {
        double rmean = (r1 + r2) / 2.0D;
        double r = r1 - r2;
        double g = g1 - g2;
        int b = b1 - b2;
        double weightR = 2.0D + rmean / 256.0D;
        double weightG = 4.0D;
        double weightB = 2.0D + (255.0D - rmean) / 256.0D;
        return weightR * r * r + weightG * g * g + weightB * b * b;
    }


    protected static double getDistance(int rgb1, int rgb2) {
        int r1 = (rgb1 >> 16) & 0x000000FF;
        int g1 = (rgb1 >> 8) & 0x000000FF;
        int b1 = (rgb1) & 0x000000FF;

        int r2 = (rgb2 >> 16) & 0x000000FF;
        int g2 = (rgb2 >> 8) & 0x000000FF;
        int b2 = (rgb2) & 0x000000FF;

        return getDistance(r1, g1, b1, r2, g2, b2);
    }

    protected static double getDistance(Color c1, Color c2) {
        return getDistance(c1.getRed(), c1.getGreen(), c1.getBlue(), c2.getRed(), c2.getGreen(), c2.getBlue());
    }


    public static final Color[] MAP_COLORS = new Color[]{
            c(0, 0, 0),
            c(0, 0, 0),
            c(0, 0, 0),
            c(0, 0, 0),
            c(89, 125, 39),
            c(109, 153, 48),
            c(127, 178, 56),
            c(67, 94, 29),
            c(174, 164, 115),
            c(213, 201, 140),
            c(247, 233, 163),
            c(130, 123, 86),
            c(140, 140, 140),
            c(171, 171, 171),
            c(199, 199, 199),
            c(105, 105, 105),
            c(180, 0, 0),
            c(220, 0, 0),
            c(255, 0, 0),
            c(135, 0, 0),
            c(112, 112, 180),
            c(138, 138, 220),
            c(160, 160, 255),
            c(84, 84, 135),
            c(117, 117, 117),
            c(144, 144, 144),
            c(167, 167, 167),
            c(88, 88, 88),
            c(0, 87, 0),
            c(0, 106, 0),
            c(0, 124, 0),
            c(0, 65, 0),
            c(180, 180, 180),
            c(220, 220, 220),
            c(255, 255, 255),
            c(135, 135, 135),
            c(115, 118, 129),
            c(141, 144, 158),
            c(164, 168, 184),
            c(86, 88, 97),
            c(106, 76, 54),
            c(130, 94, 66),
            c(151, 109, 77),
            c(79, 57, 40),
            c(79, 79, 79),
            c(96, 96, 96),
            c(112, 112, 112),
            c(59, 59, 59),
            c(45, 45, 180),
            c(55, 55, 220),
            c(64, 64, 255),
            c(33, 33, 135),
            c(100, 84, 50),
            c(123, 102, 62),
            c(143, 119, 72),
            c(75, 63, 38),
            c(180, 177, 172),
            c(220, 217, 211),
            c(255, 252, 245),
            c(135, 133, 129),
            c(152, 89, 36),
            c(186, 109, 44),
            c(216, 127, 51),
            c(114, 67, 27),
            c(125, 53, 152),
            c(153, 65, 186),
            c(178, 76, 216),
            c(94, 40, 114),
            c(72, 108, 152),
            c(88, 132, 186),
            c(102, 153, 216),
            c(54, 81, 114),
            c(161, 161, 36),
            c(197, 197, 44),
            c(229, 229, 51),
            c(121, 121, 27),
            c(89, 144, 17),
            c(109, 176, 21),
            c(127, 204, 25),
            c(67, 108, 13),
            c(170, 89, 116),
            c(208, 109, 142),
            c(242, 127, 165),
            c(128, 67, 87),
            c(53, 53, 53),
            c(65, 65, 65),
            c(76, 76, 76),
            c(40, 40, 40),
            c(108, 108, 108),
            c(132, 132, 132),
            c(153, 153, 153),
            c(81, 81, 81),
            c(53, 89, 108),
            c(65, 109, 132),
            c(76, 127, 153),
            c(40, 67, 81),
            c(89, 44, 125),
            c(109, 54, 153),
            c(127, 63, 178),
            c(67, 33, 94),
            c(36, 53, 125),
            c(44, 65, 153),
            c(51, 76, 178),
            c(27, 40, 94),
            c(72, 53, 36),
            c(88, 65, 44),
            c(102, 76, 51),
            c(54, 40, 27),
            c(72, 89, 36),
            c(88, 109, 44),
            c(102, 127, 51),
            c(54, 67, 27),
            c(108, 36, 36),
            c(132, 44, 44),
            c(153, 51, 51),
            c(81, 27, 27),
            c(17, 17, 17),
            c(21, 21, 21),
            c(25, 25, 25),
            c(13, 13, 13),
            c(176, 168, 54),
            c(215, 205, 66),
            c(250, 238, 77),
            c(132, 126, 40),
            c(64, 154, 150),
            c(79, 188, 183),
            c(92, 219, 213),
            c(48, 115, 112),
            c(52, 90, 180),
            c(63, 110, 220),
            c(74, 128, 255),
            c(39, 67, 135),
            c(0, 153, 40),
            c(0, 187, 50),
            c(0, 217, 58),
            c(0, 114, 30),
            c(91, 60, 34),
            c(111, 74, 42),
            c(129, 86, 49),
            c(68, 45, 25),
            c(79, 1, 0),
            c(96, 1, 0),
            c(112, 2, 0),
            c(59, 1, 0)};

    public static void drawText(int x, int y, MapFont font, String text, BufferedImage im) {
        int xStart = x;
        byte color = 44;
        if (!font.isValid(text)) {
            throw new IllegalArgumentException("text contains invalid characters");
        }
        int i = 0;
        while (i < text.length()) {
            block10:
            {
                char ch = text.charAt(i);
                if (ch == '\n') {
                    x = xStart;
                    y += font.getHeight() + 1;
                } else {
                    int j;
                    if (ch == '\u00a7' && (j = text.indexOf(59, i)) >= 0) {
                        try {
                            color = Byte.parseByte(text.substring(i + 1, j));
                            i = j;
                            break block10;
                        } catch (NumberFormatException numberFormatException) {
                        }
                    }
                    MapFont.CharacterSprite sprite = font.getChar(text.charAt(i));
                    int r = 0;
                    while (r < font.getHeight()) {
                        int c = 0;
                        while (c < sprite.getWidth()) {
                            if (sprite.get(r, c)) {
                                im.setRGB(x + c, y + r, color);
                            }
                            ++c;
                        }
                        ++r;
                    }
                    x += sprite.getWidth() + 1;
                }
            }
            ++i;
        }
    }

    public static byte[] drawImage(BufferedImage image) {
        int h = image.getHeight();
        int w = image.getWidth();

        byte[] data = new byte[w * h];
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                data[(y * w) + x] = QuadroUtils.matchColor(new Color(image.getRGB(x, y), true));
            }
        }

        return data;
    }

    public static BufferedImage copyImage(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    public void altera(BufferedImage i, Player p) {
        Graphics g = i.getGraphics();
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Monospaced", Font.PLAIN, 20));
        g.drawString(p.getName(), 10, 100);
        GameProfile profile = ((CraftPlayer) p).getProfile();
        Property property = profile.getProperties().get("textures").iterator().next();
        String texture = property.getValue();
        texture = new String(Base64.getDecoder().decode(texture));

        Map json = new Gson().fromJson(texture, Map.class);
        String fota = (String) ((Map) ((Map) json.get("textures")).get("SKIN")).get("url");

        BufferedImage skin = i(fota);
        skin = skin.getSubimage(8, 8, 8, 8);
        skin = resize(skin, 8);

        g.drawImage(skin, 32, 32, null);

    }


    static BufferedImage resize(BufferedImage before, float s) {
        int newHeight = (int) (before.getHeight() * s);
        int newWidth = (int) (before.getWidth() * s);

        BufferedImage resized = new BufferedImage(newWidth, newHeight, before.getType());
        Graphics2D g = resized.createGraphics();
        g.drawImage(before, 0, 0, newWidth, newHeight, 0, 0, before.getWidth(),
                before.getHeight(), null);
        g.dispose();
        return resized;
    }

    public static BufferedImage i(String url) {
        try {
            return ImageIO.read(new URL(url));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BufferedImage getHead(Player p) {
        GameProfile profile = ((CraftPlayer) p).getProfile();
        Property property = profile.getProperties().get("textures").iterator().next();
        String texture = property.getValue();
        texture = new String(Base64.getDecoder().decode(texture));

        Map json = new Gson().fromJson(texture, Map.class);
        String fota = (String) ((Map) ((Map) json.get("textures")).get("SKIN")).get("url");

        BufferedImage skin = i(fota);
        skin = skin.getSubimage(8, 8, 8, 8);
        return skin;
    }

    public static InputStream readFromResources(String img) {
        InputStream fis = QuadroUtils.class.getResourceAsStream(img);
        return fis;
    }

    public static Font minecraftFont = null;

    public static Vector2 getSize(String texto, int tamanho) {
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2d.setFont(getMinecraftFont().deriveFont(Font.LAYOUT_LEFT_TO_RIGHT, tamanho + 0.0f));
        FontMetrics fm = g2d.getFontMetrics();
        int width = fm.stringWidth(texto);
        int height = fm.getHeight();
        g2d.dispose();
        return new Vector2(width, height);
    }

    public static void drawMinecraftText(String text, Color c, int tamanho, BufferedImage img, int x, int y) {
        Graphics2D g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        Font f = getMinecraftFont().deriveFont(Font.LAYOUT_LEFT_TO_RIGHT, tamanho + 0.0f);
        g2d.setFont(f);
        g2d.setColor(c);
        g2d.drawString(text, x, y + g2d.getFontMetrics().getAscent());
    }

    public static Font getMinecraftFont() {
        try {
            if (minecraftFont == null) {
                minecraftFont = Font.createFont(Font.TRUETYPE_FONT, readFromResources("/resources/font/minecraft.ttf"));
            }
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return minecraftFont;
    }

    public static BufferedImage drawText(String text, Color c, String fonte, int tamanho) {

        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        Font font = new Font(fonte, Font.PLAIN, tamanho);
        g2d.setFont(font);
        FontMetrics fm = g2d.getFontMetrics();
        int width = fm.stringWidth(text);
        int height = fm.getHeight();
        g2d.dispose();

        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2d.setFont(font);
        fm = g2d.getFontMetrics();
        g2d.setColor(c);
        g2d.drawString(text, 0, fm.getAscent());
        return img;
    }

    /*
        public static void borderIn(Imagem im, Quadro c, Player p, ParticleEffect ef)
        {
            Hitbox2D h = im.getHitbox();
            int maxx = h.getMaxX() - 1;
            int maxy = h.getMaxY() - 1;
            for (int x = Math.max(h.getMinX() - 3, 0); x < h.getMaxX(); x++) {
                for (int y = Math.max(h.getMinY() - 3, 0); y < h.getMaxY(); y++) {

                    if (x == h.getMinX() || x == maxx || y == h.getMinY() || y == maxy) {
                        if (x == h.getMinX() || x == maxx) {
                            if (y % 24 != 0) {
                                continue;

                            }

                        }
                        if (y == h.getMinY() || y == maxy) {
                            if (x % 24 != 0) {
                                continue;
                            }
                        }
                        ef.display(0, 0, 0, 0, 1, c.convertPixelToLocation(x, y), p);
                    }
                }

            }
        }

        public static void cornersIn(Imagem im, Quadro c, Player p, ParticleEffect ef)
        {
            Hitbox2D h = im.getHitbox();
            ef.display(0, 0, 0, 0, 1, c.convertPixelToLocation(h.getMinX(), h.getMinY()), p);
            ef.display(0, 0, 0, 0, 1, c.convertPixelToLocation(h.getMinX(), h.getMaxY()), p);
            ef.display(0, 0, 0, 0, 1, c.convertPixelToLocation(h.getMaxX(), h.getMinY()), p);
            ef.display(0, 0, 0, 0, 1, c.convertPixelToLocation(h.getMaxX(), h.getMaxY()), p);
        }
        */
    public static BufferedImage getImageFromColor(Color c, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(c);
        g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
        return image;

    }


    public static BufferedImage getYoutubeThumb(YoutubeAPI.VideoInfo info) {
        if (info == null) {
            return null;
        }
        try {
            BufferedImage thumb = ImageIO.read(info.getThumb());
            thumb = QuadroUtils.resize(thumb, 0.75f);

            int height = thumb.getHeight();
            int width = thumb.getWidth();


            BufferedImage dislikes = QuadroUtils.drawText(info.getDeslikes(), Color.RED, "Arial", 16);
            BufferedImage likes = QuadroUtils.drawText(info.getLikes(), Color.GREEN, "Arial", 16);
            BufferedImage channel = getChannel(info.getChannel(), width);
            BufferedImage title = getTitle(info.getNome(), width);
            BufferedImage fundo = new BufferedImage(width, channel.getHeight() + title.getHeight() + 4 + height + likes.getHeight() + 4, BufferedImage.TYPE_INT_ARGB);

            draw(channel, fundo, 0, 0);
            draw(thumb, fundo, 0, channel.getHeight() + title.getHeight() + 4);
            draw(likes, fundo, width - dislikes.getWidth() - likes.getWidth() - 20, channel.getHeight() + title.getHeight() + 4 + height + 3);
            draw(dislikes, fundo, width - dislikes.getWidth(), channel.getHeight() + title.getHeight() + 4 + height + 3);
            draw(title, fundo, 0, channel.getHeight());
            draw(QuadroUtils.drawText(info.getViews() + " views.", Color.YELLOW, "Arial", 16), fundo, 0, channel.getHeight() + title.getHeight() + 4 + height + 3);
            return fundo;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    private static BufferedImage getChannel(String nome, int width) {
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        int size = pickOptimalFontSize((Graphics2D) img.getGraphics(), nome, width, 30, 16);
        if (size == -1) {
            nome = nome.substring(0, Math.min(30, nome.length())) + "...";
            return drawText(nome, Color.white, "Arial", 12);
        }
        return drawText(nome, Color.WHITE, "Arial", size);

    }

    private static BufferedImage getTitle(String nome, int width) {
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        int size = pickOptimalFontSize((Graphics2D) img.getGraphics(), nome, width, 30, 12);
        if (size == -1) {
            nome = nome.substring(0, Math.min(30, nome.length())) + "...";
            return drawText(nome, Color.white, "Arial", 12);
        }
        return drawText(nome, Color.WHITE, "Arial", size);

    }

    private static int pickOptimalFontSize(Graphics2D g, String title, int width, int maxSize, int minSize) {
        Rectangle2D rect = null;

        int fontSize = maxSize + 1; //initial value
        do {
            fontSize--;
            if (fontSize < minSize) {
                return -1;
            }
            Font font = new Font("Arial", Font.PLAIN, fontSize);
            rect = getStringBoundsRectangle2D(g, title, font);
        } while (rect.getWidth() >= width);

        return fontSize;
    }

    public static Rectangle2D getStringBoundsRectangle2D(Graphics g, String title, Font font) {
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics();
        Rectangle2D rect = fm.getStringBounds(title, g);
        return rect;
    }

    private static void draw(BufferedImage image, BufferedImage to, int offsetx, int offsety) {
        to.getGraphics().drawImage(image, offsetx, offsety, null);

    }

    private static Color c(int r, int g, int b) {
        return new Color(r, g, b);
    }
}
