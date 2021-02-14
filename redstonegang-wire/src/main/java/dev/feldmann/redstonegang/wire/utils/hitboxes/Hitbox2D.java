package dev.feldmann.redstonegang.wire.utils.hitboxes;

import org.bukkit.block.BlockFace;

public class Hitbox2D {


    private int minX, minY, maxX, maxY;

    public Hitbox2D(int minX, int minY, int maxX, int maxY) {
        this.minX = Math.min(minX, maxX);
        this.minY = Math.min(minY, maxY);
        this.maxX = Math.max(minX, maxX);
        this.maxY = Math.max(minY, maxY);
    }

    public boolean isInside(int x, int y) {
        return x >= minX && x <= maxX && y >= minY && y <= maxY;
    }

    public boolean isInside(Hitbox2D hit) {
        return isInside(hit.getMinX(), hit.getMinY()) && isInside(hit.getMaxX(), hit.getMaxY());
    }

    public int getMinX() {
        return minX;
    }

    public int getMinY() {
        return minY;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    public int getHeight() {

        return (maxY - minY) + 1;
    }

    public double[] getMidPoint() {
        return new double[]{minX + (getWidth() / 2), minY + (getHeight() / 2)};

    }

    /*
     * Expande a hitbox pra incluir o ponto X,Y
     * */
    public void includePoint(int x, int y) {
        if (minX > x) {
            this.minX = x;
        }
        if (maxX < x) {
            this.maxX = x;
        }
        if (minY > y) {
            minY = y;
        }
        if (maxY < y) {
            maxY = y;
        }
    }

    public int getWidth() {
        return (maxX - minX) + 1;
    }

    public Hitbox2D expands(int qtd) {
        return new Hitbox2D(minX - qtd, minY - qtd, maxX + qtd, maxY + qtd);
    }

    public void expandsInternaly(int qtd) {
        minX -= qtd;
        minY -= qtd;
        maxX += qtd;
        maxY += qtd;
    }

    public Hitbox2D shrink(int i) {
        return new Hitbox2D(minX + i, minY + i, maxX - i, maxY - i);
    }

    public Hitbox2D expands(BlockFace face, int qtd) {
        int minX = this.minX;
        int maxX = this.maxX;
        int minY = this.minY;
        int maxY = this.maxY;
        if (face.getModX() > 0) {
            maxX += qtd;
        }
        if (face.getModX() < 0) {
            minX -= qtd;
        }
        if (face.getModZ() > 0) {
            maxY += qtd;
        }
        if (face.getModZ() < 0) {
            minY -= qtd;
        }
        return new Hitbox2D(minX, minY, maxX, maxY);


    }

    public boolean collides(Hitbox2D hit) {
        int tw = this.getWidth();
        int th = this.getHeight();
        int rw = hit.getWidth();
        int rh = hit.getHeight();
        if (rw <= 0 || rh <= 0 || tw <= 0 || th <= 0) {
            return false;
        }
        int tx = this.getMinX();
        int ty = this.getMinY();
        int rx = hit.getMinX();
        int ry = hit.getMinY();
        rw += rx;
        rh += ry;
        tw += tx;
        th += ty;
        //      overflow || intersect
        return ((rw < rx || rw > tx) &&
                (rh < ry || rh > ty) &&
                (tw < tx || tw > rx) &&
                (th < ty || th > ry));
    }

    public int getArea() {
        return getHeight() * getWidth();
    }


}
