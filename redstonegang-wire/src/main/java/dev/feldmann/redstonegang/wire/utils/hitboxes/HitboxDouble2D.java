package dev.feldmann.redstonegang.wire.utils.hitboxes;

public class HitboxDouble2D {


    private double minX, minY, maxX, maxY;

    public HitboxDouble2D(double minX, double minY, double maxX, double maxY) {
        this.minX = Math.min(minX, maxX);
        this.minY = Math.min(minY, maxY);
        this.maxX = Math.max(minX, maxX);
        this.maxY = Math.max(minY, maxY);
    }

    public boolean isInside(double x, double y) {
        return x >= minX && x <= maxX && y >= minY && y <= maxY;
    }

    public double getMinY() {
        return minY;
    }

    public double getMinX() {
        return minX;
    }

    public double getMaxY() {
        return maxY;
    }

    public double getMaxX() {
        return maxX;
    }
}
