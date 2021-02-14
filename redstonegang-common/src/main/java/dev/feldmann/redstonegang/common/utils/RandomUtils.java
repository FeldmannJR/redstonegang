package dev.feldmann.redstonegang.common.utils;

import java.util.List;
import java.util.Random;

public class RandomUtils {
    private static Random rnd = new Random();

    public static int randomInt(int min, int max) {
        return min + rnd.nextInt(max - min + 1);
    }

    public static double randDouble(double min, double max) {
        return min + (rnd.nextDouble() * (max - min));
    }

    public static boolean oneIn(int quantity) {
        return rnd.nextInt(quantity) == 0;
    }

    public static boolean randomBoolean() {
        return rnd.nextBoolean();
    }

    public static <T> T getRandom(List<T> list) {
        int index = rnd.nextInt(list.size());
        return list.get(index);
    }

    public static <T> T getRandom(T[] list) {
        int index = rnd.nextInt(list.length);
        return list[index];
    }
}
