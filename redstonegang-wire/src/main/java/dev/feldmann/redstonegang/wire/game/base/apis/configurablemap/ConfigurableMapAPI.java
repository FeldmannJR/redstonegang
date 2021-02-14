package dev.feldmann.redstonegang.wire.game.base.apis.configurablemap;

import dev.feldmann.redstonegang.wire.game.base.apis.configurablemap.cmds.MapConfigCmd;
import dev.feldmann.redstonegang.wire.game.base.apis.configurablemap.internal.ConfigurableBlock;
import dev.feldmann.redstonegang.wire.game.base.apis.configurablemap.internal.ConfigurableEntry;
import dev.feldmann.redstonegang.wire.game.base.apis.configurablemap.internal.ConfigurableNPC;
import dev.feldmann.redstonegang.wire.game.base.apis.configurablemap.internal.WorldFile;
import dev.feldmann.redstonegang.wire.game.base.events.ServerLoadedEvent;
import dev.feldmann.redstonegang.wire.game.base.objects.API;
import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import com.google.gson.JsonObject;
import net.citizensnpcs.api.event.CitizensEnableEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;

public class ConfigurableMapAPI extends API {


    HashMap<Class, Class<? extends ConfigurableEntry>> map = new HashMap<>();
    HashMap<String, ConfigurableEntry> configs = new HashMap<>();

    HashMap<String, Class<? extends ConfigurableEntry>> required = new HashMap<>();

    private HashMap<String, ConfigField> fields = new HashMap<>();

    WorldFile file;

    @Override
    public void onEnable() {
        loadTipos();
        File folder = Bukkit.getWorlds().get(0).getWorldFolder();
        if (!folder.exists()) {
            folder.mkdir();
        }
        File f = new File(folder, "addons.json");
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        file = new WorldFile(f, this);
        file.load();
        registerCommand(new MapConfigCmd(this));

    }

    @EventHandler
    public void loaded(ServerLoadedEvent ev) {
        Collection<Addon> addons = getServer().getAddons();
        for (Addon a : addons) {
            load(a);
        }
    }

    @EventHandler
    public void loadNpcs(CitizensEnableEvent ev) {
        for (String n : fields.keySet()) {
            ConfigField f = fields.get(n);
            if (f.f.getType() == NPC.class) {
                if (configs.containsKey(n) && configs.get(n).getClass() == ConfigurableNPC.class) {
                    try {
                        f.f.set(f.ad, configs.get(n).getValue());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    public void load(Addon ad) {
        String adname = ad.getClass().getSimpleName().toLowerCase();
        Field[] fs = ad.getClass().getDeclaredFields();
        for (Field f : fs) {
            f.setAccessible(true);
            MapConfigurable[] an = f.getDeclaredAnnotationsByType(MapConfigurable.class);
            if (an != null && an.length > 0) {
                if (map.containsKey(f.getType())) {
                    String cname = f.getName() + "_" + adname;
                    required.put(cname, map.get(f.getType()));
                    if (configs.containsKey(cname) && configs.get(cname).getClass() == map.get(f.getType())) {
                        try {
                            f.set(ad, configs.get(cname).getValue());
                            fields.put(cname, new ConfigField(ad, f, cname));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    } else {
                        fields.put(cname, new ConfigField(ad, f, cname));
                    }

                }
            }

        }
    }

    private void loadTipos() {
        registerTipo(NPC.class, ConfigurableNPC.class);
        registerTipo(Block.class, ConfigurableBlock.class);

    }

    private void registerTipo(Class value, Class<? extends ConfigurableEntry> en) {
        map.put(value, en);
    }


    public <T> void setConfig(String configName, Class<? extends ConfigurableEntry<T>> tipo, T valor) {
        try {
            ConfigurableEntry<T> entry = tipo.getConstructor(String.class).newInstance(configName);
            entry.setValue(valor);
            configs.put(configName, entry);
            file.set(entry);
            if (fields.containsKey(configName)) {
                ConfigField f = fields.get(configName);
                f.f.set(f.ad, valor);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public <T> T getConfig(String name, Class<? extends ConfigurableEntry<T>> tipo) {
        if (configs.containsKey(name)) {
            if (configs.get(name).getClass().equals(tipo)) {
                return (T) configs.get(name).getValue();
            }
        }
        return null;
    }

    public HashMap<String, Class<? extends ConfigurableEntry>> getRequired() {
        return required;
    }

    @Override
    public void onDisable() {

    }

    public Class getType(String type) {

        for (Class c : map.values()) {
            if (c.getSimpleName().equals(type)) {
                return c;
            }
        }
        return null;
    }

    public void load(String type, String key, JsonObject obj) {
        Class classe = getType(type);
        if (classe != null) {
            try {
                Constructor<? extends ConfigurableEntry> constructor = classe.getConstructor(String.class);
                ConfigurableEntry entry = constructor.newInstance(key);
                if (obj != null)
                    entry.deserialize(obj);
                configs.put(key, entry);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

    }

    public class ConfigField {
        Addon ad;
        Field f;
        String cname;

        public ConfigField(Addon ad, Field f, String cname) {
            this.ad = ad;
            this.f = f;
            this.cname = cname;
        }
    }
}
