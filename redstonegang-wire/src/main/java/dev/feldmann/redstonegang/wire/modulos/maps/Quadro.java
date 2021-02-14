package dev.feldmann.redstonegang.wire.modulos.maps;

import dev.feldmann.redstonegang.wire.Wire;
import dev.feldmann.redstonegang.wire.modulos.maps.imagens.Imagem;
import dev.feldmann.redstonegang.wire.modulos.maps.imagens.PerPlayerImagem;
import dev.feldmann.redstonegang.wire.modulos.maps.queue.FinishSendEntry;
import dev.feldmann.redstonegang.wire.modulos.maps.queue.MapQueueEntry;
import dev.feldmann.redstonegang.wire.modulos.maps.queue.SendMapsQueue;
import dev.feldmann.redstonegang.wire.utils.hitboxes.Hitbox;
import dev.feldmann.redstonegang.wire.utils.hitboxes.Hitbox2D;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.awt.image.BufferedImage;
import java.util.*;

public class Quadro {

    private static int lastid = 1;
    public static short maxMapId = 15000;
    int id;
    public BlockFace facing;

    BufferedImage bg;

    Hitbox hitbox;

    Location startLocation;

    List<Imagem> imagemList = new ArrayList();


    public HashSet<UUID> sending = new HashSet<>();

    private static byte[] empty;


    public HashMap<Integer, QuadroSlot> quadros = new HashMap<>();


    public int width;
    public int height;


    public Quadro(BufferedImage background) {
        if (empty == null) {
            empty = new byte[128 * 128];
            for (int x = 0; x < 128 * 128; x++) {
                empty[x] = 0;
            }

        }
        id = lastid++;
        this.width = background.getWidth();
        this.height = background.getHeight();

        //Precisa ser multiplo de 128
        if (width % 128 != 0 || height % 128 != 0) {
            return;
        }
        for (int x = 0; x < getQuadrosX(); x++) {
            for (int y = 0; y < getQuadrosY(); y++) {
                int slot = y * getQuadrosX() + x;
                QuadroSlot qslot = new QuadroSlot();
                qslot.x = x;
                qslot.y = y;
                quadros.put(slot, qslot);
            }
        }
        this.bg = background;

    }


    public int getQuadrosY() {
        return height / 128;
    }

    public int getQuadrosX() {
        return width / 128;
    }

    //Pega do cache se tem, caso contrario chama o render
    public byte[] getData(Player p) {
        return renderQuadro(p);
    }

    public int getTamanhoData() {
        return getQuadrosX() * getQuadrosY();

    }

    double hitboxProfundidade = 0;

    public void createBoundingBox(Location start) {
        this.startLocation = start;
        Vector min, max;
        min = new Vector(start.getX(), start.getBlockY() - getQuadrosY() + 1, start.getBlockZ());
        if (facing == BlockFace.NORTH || facing == BlockFace.SOUTH) {

            double maxz = min.getZ();
            if (facing == BlockFace.NORTH) {
                min.setZ(min.getZ() + 1);
                maxz = maxz + 1 + hitboxProfundidade;
            } else {
                maxz = maxz - hitboxProfundidade;
            }
            max = new Vector(start.getX() + getQuadrosX(), start.getBlockY() + 1, maxz);
        } else {

            double maxx = min.getX();
            if (facing == BlockFace.WEST) {
                min.setX(min.getX() + 1);
                maxx = maxx + 1 + hitboxProfundidade;
            } else {
                maxx = maxx - hitboxProfundidade;
            }
            max = new Vector(maxx, start.getBlockY() + 1, start.getBlockZ() + getQuadrosX());

        }

        hitbox = new Hitbox(min, max);
    }

    public int[] convertHitToPixel(Vector hit) {
        //Tamanho pixel= tamanho hitbox
        //x = Hit
        double p = (getQuadrosX() * 128);
        double x;
        if (facing == BlockFace.NORTH || facing == BlockFace.SOUTH) {
            x = hit.getX() - hitbox.min.getX();

        } else {
            x = hit.getZ() - hitbox.min.getZ();
        }
        x = x * 128;
        if (facing == BlockFace.NORTH || facing == BlockFace.EAST) {
            x = (128 * getQuadrosX()) - x - 1;
        }

        double y = (hit.getY() - hitbox.min.getY()) * 128;
        y = (128 * getQuadrosY()) - y - 1;
        return new int[]{(int) x, (int) y};

    }

    public Location convertPixelToLocation(int x, int y) {
        double off = 0.07;
        double locy = Double.valueOf(y) / 128;
        double locx = Double.valueOf(x) / 128;

        double fy = getHitBox().max.getY() - locy;
        double fx;
        double fz;
        if (facing == BlockFace.NORTH || facing == BlockFace.SOUTH) {
            fz = getHitBox().min.getZ();
            if (facing == BlockFace.NORTH) {
                fx = getHitBox().max.getX() - locx;
                fz -= off;
            } else {
                fx = locx * getHitBox().min.getX() + locx;
                fz += off;
            }
        } else {
            fx = getHitBox().min.getX();
            if (facing == BlockFace.EAST) {
                fz = getHitBox().max.getZ() - locx;
                fx += off;
            } else {
                fz = getHitBox().min.getZ() + locx;
                fx -= off;
            }
        }
        return new Location(startLocation.getWorld(), fx, fy, fz);

    }


    long start;

    public void loga(String s) {
        System.out.println(s + " " + (System.currentTimeMillis() - start));
        start = System.currentTimeMillis();
    }

    protected byte[] renderQuadro(Player p) {
        start = System.currentTimeMillis();
        BufferedImage fundo = QuadroUtils.copyImage(bg);
        int x = 0;
        for (Imagem im : imagemList) {
            BufferedImage bm = im.getImage(p);
            if (bm != null) {
                Hitbox2D h = im.getHitbox(bm);
                fundo.getGraphics().drawImage(bm, h.getMinX(), h.getMinY(), null);
            }
        }
        byte[] b = QuadroUtils.drawImage(fundo);
        return b;
    }


    public boolean isGlobal() {
        for (Imagem im : imagemList) {
            if (im instanceof PerPlayerImagem) {
                return false;
            }

        }
        return true;
    }

    public int getId() {
        return id;
    }

    public QuadroSlot getSlot(int x, int y) {
        return quadros.get(y * getQuadrosX() + x);
    }

    public Imagem addImagem(Imagem im) {
        imagemList.add(im);
        return im;
    }

    public byte[] getSubImage(byte data[], int x, int y) {

        int minX = x * 128;
        int minY = y * 128;

        byte[] ndata = new byte[128 * 128];
        for (int xf = 0; xf < 128; xf++) {
            for (int yf = 0; yf < 128; yf++) {
                int oldy = minY + yf;
                int oldx = minX + xf;
                ndata[yf * 128 + xf] = data[(oldy * width) + oldx];
            }
        }
        return ndata;

    }


    public HashMap<UUID, byte[]> lastSent = new HashMap();

    public void calculateReset(Hitbox2D h, int x, int y) {
        h.includePoint(x, y);
    }

    public void sendPackets(Player p) {

        final Quadro q = this;

        Bukkit.getScheduler().runTaskAsynchronously(Wire.instance, new Runnable() {
            @Override
            public void run() {
                byte[] data = getData(p);
                final byte[] last = lastSent.get(p.getUniqueId());
                lastSent.put(p.getUniqueId(), data);
                Bukkit.getScheduler().scheduleSyncDelayedTask(Wire.instance, new Runnable() {
                    @Override
                    public void run() {
                        int sum = 0;
                        QUADRO:
                        for (QuadroSlot qs : quadros.values()) {
                            qs.sendUpdatePacket(p);
                            byte[] sub = getSubImage(data, qs.x, qs.y);
                            byte[] old = null;
                            if (last != null) {
                                old = getSubImage(last, qs.x, qs.y);
                            }
                            int minY = 0;
                            int maxY = 128;
                            for (int x = 0; x < (128 / SendMapsQueue.colunaPixels); x++) {
                                if (old != null) {
                                    minY = -1;
                                    maxY = -1;
                                    boolean difere = false;
                                    for (int y = 0; y < 128; y++) {
                                        for (int xx = x; xx < x + SendMapsQueue.colunaPixels; xx++) {
                                            int s = xx + (y * 128);
                                            byte b1 = sub[s];
                                            byte b2 = old[s];
                                            if (b1 != b2) {
                                                difere = true;
                                                if (minY == -1 || minY > y) {
                                                    minY = y;
                                                }
                                                if (maxY == -1 || maxY < y) {
                                                    maxY = y;
                                                }
                                            }
                                        }
                                    }
                                    if (!difere) {
                                        continue;
                                    }
                                    maxY++;
                                }

                                sum++;
                                MapQueueEntry en = new MapQueueEntry(p, q, qs.getMapId(p), SendMapsQueue.colunaPixels * x, minY, SendMapsQueue.colunaPixels, (maxY - minY), sub);
                                QuadrosManager.queue.add(en);

                            }
                        }
                        sending.add(p.getUniqueId());
                        QuadrosManager.queue.add(new FinishSendEntry(p, q));
                    }
                });
            }
        });


    }

    public Location getStartLocation() {
        return startLocation;
    }

    public Hitbox getHitBox() {
        return hitbox;
    }

    public boolean shouldRender(Player p) {
        return true;
    }

    public void clickou(Player p, int x, int y) {
        for (Imagem i : imagemList) {
            if (i.getHitbox() == null) {
                break;
            }

            if (i.getHitbox().isInside(x, y)) {
                if (i.click(p, this)) {
                    break;
                }
            }

        }

    }

    public boolean isRendering(Player p) {
        return sending.contains(p.getUniqueId());
    }

    public Collection<QuadroSlot> getSlots() {
        return quadros.values();
    }


    public static class QuadroSlot {
        public ItemFrame itemframe;
        public Block blocoframe;
        public HashMap<Player, Integer> mapids = new HashMap<>();
        public int x, y;
        public static HashMap<UUID, Integer> used = new HashMap();


        public int getMapId(Player p) {
            if (mapids.containsKey(p)) {
                return mapids.get(p);
            }
            if (!used.containsKey(p.getUniqueId())) {
                used.put(p.getUniqueId(), (int) maxMapId);
                mapids.put(p, (int) maxMapId);
                return maxMapId;
            }

            int u = used.get(p.getUniqueId()) - 1;
            used.put(p.getUniqueId(), u);
            mapids.put(p, u);
            return u;

        }


        public void setItemFrame(ItemFrame item) {
            this.itemframe = item;
            blocoframe = item.getLocation().getBlock().getRelative(item.getAttachedFace());

        }

        public void sendUpdatePacket(Player p) {
            short id = (short) getMapId(p);
             QuadrosManager.queue.add(new MapQueueEntry(p, ItemFrameManager.setItemFrameMap(itemframe, (short) getMapId(p))));
        }
        public void clear(Player p){
            QuadrosManager.queue.add(new MapQueueEntry(p, ItemFrameManager.clear(itemframe)));
        }


    }


}
