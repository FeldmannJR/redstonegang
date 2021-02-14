package dev.feldmann.redstonegang.wire.utils.hitboxes;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class Hitbox {
    //min and max points of hit box
    public Vector max;
    public Vector min;


    public Hitbox(Vector min, Vector max) {
        this(min, max, false);
    }

    public Hitbox(Vector min, Vector max, boolean block) {
        this.min = new Vector(Math.min(min.getX(), max.getX()), Math.min(min.getY(), max.getY()), Math.min(min.getZ(), max.getZ()));
        this.max = new Vector(Math.max(min.getX(), max.getX()) + (block ? 1 : 0), Math.max(min.getY(), max.getY()) + (block ? 1 : 0), Math.max(min.getZ(), max.getZ()) + (block ? 1 : 0));
    }


    public Vector midPoint() {
        return max.clone().add(min).multiply(0.5);
    }

    public Vector getMin() {
        return min;
    }

    public Vector getMax() {
        return max;
    }

    /*
     * Expande a hitbox nas cordenadas que tu passa
     * Util pra querer verificar em uma hitbox maior ou menor
     * Não faz verificação se o max for menor que o min e o caralho
     * Use por risco próprio
     * */
    public Hitbox expand(int x, int y, int z) {
        Vector newmin = min.clone();
        Vector newmax = max.clone();
        if (x > 0) {
            newmax.setX(newmax.getX() + x);
        } else if (x < 0) {
            newmin.setX(newmin.getX() + x);
        }
        if (y > 0) {
            newmax.setY(newmax.getY() + y);
        } else if (y < 0) {
            newmin.setY(newmin.getY() + y);
        }
        if (z > 0) {
            newmax.setZ(newmax.getZ() + z);
        } else if (z < 0) {
            newmin.setZ(newmin.getZ() + z);
        }
        return new Hitbox(newmin, newmax);
    }

    public boolean isInside(Location l) {

        double x = l.getX();
        double y = l.getY();
        double z = l.getZ();

        Vector RegionMin = min;
        Vector RegionMax = max;
        return (x >= RegionMin.getBlockX()) && (x < RegionMax.getBlockX() + 1)
                && (y >= RegionMin.getBlockY()) && (y < RegionMax.getBlockY() + 1)
                && (z >= RegionMin.getBlockZ()) && (z < RegionMax.getBlockZ() + 1);

    }

    public boolean isInsideIgnoreY(Location l) {

        double x = l.getX();
        double y = l.getY();
        double z = l.getZ();

        Vector RegionMin = min;
        Vector RegionMax = max;
        return (x >= RegionMin.getBlockX()) && (x < RegionMax.getBlockX() + 1)
                && (z >= RegionMin.getBlockZ()) && (z < RegionMax.getBlockZ() + 1);

    }
}
