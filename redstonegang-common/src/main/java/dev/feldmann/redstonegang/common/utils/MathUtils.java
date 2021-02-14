package dev.feldmann.redstonegang.common.utils;

public class MathUtils {


    public static double lerp(double min, double max, float pct) {
        return min + ((max - min) * pct);
    }

    public static int lerp(int min, int max, float pct) {
        return (int) (min + ((max - min) * pct));
    }
}
