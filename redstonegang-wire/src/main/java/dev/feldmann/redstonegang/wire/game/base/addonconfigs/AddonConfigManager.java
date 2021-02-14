package dev.feldmann.redstonegang.wire.game.base.addonconfigs;

import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import dev.feldmann.redstonegang.wire.game.base.objects.annotations.AConfig;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.UUID;

public class AddonConfigManager {

    AddonConfigDatabase database;

    public AddonConfigManager() {
        database = new AddonConfigDatabase();
    }


    public void loadVariables(Addon ad, String sv) throws IllegalAccessException {
        HashMap<String, String> vars = database.loadConfigs(ad.getClass().getSimpleName(), sv);
        for (Field f : ad.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            AConfig[] ano = f.getDeclaredAnnotationsByType(AConfig.class);
            if (ano != null && ano.length > 0) {
                String name = f.getName();
                if (vars.containsKey(name)) {
                    String value = vars.get(name);
                    if (f.getType() == String.class) {
                        f.set(ad, value);
                    } else if (f.getType() == int.class) {
                        try {
                            f.set(ad, Integer.valueOf(value));
                        } catch (NumberFormatException ex) {

                        }
                    } else if (f.getType() == UUID.class) {
                        f.set(ad, UUID.fromString(value));
                    }

                }

            }
        }
    }

    public void saveVariables(Addon ad) throws IllegalAccessException {
        HashMap<String, String> vars = new HashMap<>();
        for (Field f : ad.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            AConfig[] ano = f.getDeclaredAnnotationsByType(AConfig.class);
            if (ano != null && ano.length > 0) {
                Object valor = f.get(ad);
                if (valor != null) {
                    vars.put(f.getName(), valor.toString());
                }
            }
        }
        database.save(vars, ad.getClass().getSimpleName(), ad.getServer().getIdentifier());
    }
}
