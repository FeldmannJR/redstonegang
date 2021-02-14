package dev.feldmann.redstonegang.common.utils;

import java.util.concurrent.ConcurrentHashMap;

public class SyncUtils {

    private static ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>();

    public static Object getLock(String key) {
        return map.computeIfAbsent(key, x -> new Object());
    }

}
