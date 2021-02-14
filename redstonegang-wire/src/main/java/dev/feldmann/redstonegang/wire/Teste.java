package dev.feldmann.redstonegang.wire;


import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.db.Database;
import dev.feldmann.redstonegang.wire.modulos.maps.QuadroUtils;
import com.google.common.base.Charsets;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;


public class Teste {
    static long mp = 90;

    public static void main(String[] args) {
        System.out.println(getOfflineUUID("blabla", true));
    }

    public static String getOfflineUUID(String p, boolean Withdashes) {
        if (Withdashes) {
            return java.util.UUID.nameUUIDFromBytes(("OfflinePlayer:" + p).getBytes(Charsets.UTF_8)).toString();
        } else {
            return java.util.UUID.nameUUIDFromBytes(("OfflinePlayer:" + p).getBytes(Charsets.UTF_8)).toString().replaceAll("-", "");
        }
    }
//    public static void main(String[] args) {
//
//        System.out.println("com.mysql.jdbc.Driver".equalsIgnoreCase("com.mysql.jdbc.Driver"));
//    }

    private static double maxRate = 10;

    private static double mod(double rate, double highPercentage, double lowPercentage) {
        return 0;
    }

    public static int getLevelFromXp(long xp) {
        return (int) Math.floor(0.5 + (Math.sqrt(1d + ((8d * xp) / mp))) / 2d);
    }

    public static long getXpForLevel(int lvl) {
        return (long) Math.floor((((lvl * (lvl - 1))) * mp) / 2d);
    }

    public static void printColors() {
        Color[] mapColors = QuadroUtils.MAP_COLORS;
        List<Integer> jaFoi = new ArrayList<>();
        for (Color color : mapColors) {
            int rgb = color.getRGB() & 0x00FFFFFF;
            if (jaFoi.contains(rgb)) {
                continue;
            }
            jaFoi.add(rgb);
            //179   0   0   Untitled
            System.out.println(color.getRed() + "\t" + color.getGreen() + "\t" + color.getBlue() + "\tUntitled");
        }


    }

   /* public static void loadConfig() {
        YamlConfiguration config = new YamlConfiguration();
        try {
            config.load(new File("banner.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
        ConfigurationSection section = config.getConfigurationSection("patterns");
        Set<String> keys = section.getKeys(false);
        for (String key : keys) {
            char c = key.toUpperCase().charAt(0);
            List<Map<?, ?>> def = section.getConfigurationSection(key).getMapList("large");


            System.out.println("case '" + c + "':");
            System.out.print("  builder");
            for (Map<?, ?> map : def) {
                String tipo = (String) map.keySet().toArray()[0];
                Boolean cor = !(Boolean) map.values().toArray()[0];

                //  PatternShape type = PatternShape.getByDisplay(tipo);
                //System.out.print("\n        .pattern(" + type.getType().name() + "," + (cor ? "baseColor" : "charColor") + ")");

            }
            System.out.print(";");
            System.out.println("\n  break;");

        }
    }*/
}
