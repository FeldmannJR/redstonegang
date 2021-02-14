package dev.feldmann.redstonegang.common.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.function.Function;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ObjectLoader {

    public static <T> List<T> load(String pacote, Class<T> type) {
        return load(pacote, type, null);
    }

    public static <T> List<T> load(String pacote, Class<T> type, Function<JarEntry, Boolean> filter) {
        File f = new File(ObjectLoader.class.getProtectionDomain().getCodeSource().getLocation().getFile().replaceAll("%20", " ")).getAbsoluteFile();
        JarFile jf;
        try {
            jf = new JarFile(f);
        } catch (IOException ex) {
            ex.printStackTrace();
            return Arrays.asList();
        }
        Enumeration en = jf.entries();
        List<T> entries = new ArrayList<>();
        while (en.hasMoreElements()) {
            Object entry = en.nextElement();
            T addon = load(entry, pacote, type, filter);
            if (addon != null) {
                entries.add(addon);
            }
        }
        return entries;
    }

    private static <T> T load(Object ne, String pacote, Class<T> type, Function<JarEntry, Boolean> filter) {
        JarEntry entry = (JarEntry) ne;
        String name = entry.getName();
        name = name.replaceAll("/", ".");
        if (!name.endsWith(".class")) {
            return null;
        }
        name = name.replace(".class", "");
        if (!name.startsWith(pacote)) {
            return null;
        }
        if (filter!=null && !filter.apply(entry)) {
            return null;
        }
        Class c;
        try {
            c = Class.forName(name);
        } catch (ClassNotFoundException ex) {

            ex.printStackTrace();
            return null;
        }
        if (type.isAssignableFrom(c)) {
            try {
                T w = (T) c.newInstance();
                return w;
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }
        return null;
    }
}

