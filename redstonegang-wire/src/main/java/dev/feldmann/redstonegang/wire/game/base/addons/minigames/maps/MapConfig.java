/*
 *  _   __   _   _____   _____       ___       ___  ___   _____
 * | | |  \ | | /  ___/ |_   _|     /   |     /   |/   | /  ___|
 * | | |   \| | | |___    | |      / /| |    / /|   /| | | |
 * | | | |\   | \___  \   | |     / / | |   / / |__/ | | | |
 * | | | | \  |  ___| |   | |    / /  | |  / /       | | | |___
 * |_| |_|  \_| /_____/   |_|   /_/   |_| /_/        |_| \_____|
 *
 * Classe criada por Carlos Andre Feldmann Junior
 * Apoio: Isaias Finger, Gabriel Slomka, Gabriel Augusto Souza
 * Skype: junior.feldmann
 * GitHub: https://github.com/feldmannjr
 * Facebook: https://www.facebook.com/carlosandre.feldmannjunior
 */
package dev.feldmann.redstonegang.wire.game.base.addons.minigames.maps;

import dev.feldmann.redstonegang.wire.utils.hitboxes.Hitbox;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.util.Vector;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Carlos
 */
public class MapConfig {

    private HashMap<String, Hitbox> regions = new HashMap();
    private HashMap<String, CustomLocation> locations = new HashMap();
    private HashMap<String, String> configs = new HashMap();

    private File databaseFile;
    private Mapa mapa;


    public MapConfig(Mapa mapa) {
        this.mapa = mapa;
        databaseFile = new File(mapa.getBukkitFolder().getAbsolutePath() + "/config.yml");
    }

    public void loadConfig() {
        regions.clear();
        locations.clear();
        configs.clear();
        if (!databaseFile.exists()) {
            try {
                databaseFile.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Mapa.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        YamlConfiguration config = new YamlConfiguration();
        try {
            config.load(databaseFile);
        } catch (IOException ex) {
            Logger.getLogger(MapConfig.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidConfigurationException ex) {
            Logger.getLogger(MapConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        ConfigurationSection regionsc = config.getConfigurationSection("region");
        if (regionsc != null) {
            for (String s : regionsc.getKeys(false)) {
                int minx = config.getInt("region." + s + ".min.x");
                int miny = config.getInt("region." + s + ".min.y");
                int minz = config.getInt("region." + s + ".min.z");
                int maxx = config.getInt("region." + s + ".max.x");
                int maxy = config.getInt("region." + s + ".max.y");
                int maxz = config.getInt("region." + s + ".max.z");
                regions.put(s, new Hitbox(new Vector(minx, miny, minz), new Vector(maxx, maxy, maxz)));
            }
        }
        ConfigurationSection locationsc = config.getConfigurationSection("location");
        if (locationsc != null) {
            for (String s : locationsc.getKeys(false)) {
                double x = config.getDouble("location." + s + ".x");
                double y = config.getDouble("location." + s + ".y");
                double z = config.getDouble("location." + s + ".z");
                double pitch = config.getDouble("location." + s + ".pitch");
                double yaw = config.getDouble("location." + s + ".yaw");
                locations.put(s, new CustomLocation(x, y, z, (float) yaw, (float) pitch));
            }
        }
        ConfigurationSection configsc = config.getConfigurationSection("config");
        if (configsc != null) {
            for (String s : configsc.getKeys(false)) {
                configs.put(s, config.getString("config." + s + ""));
            }
        }
    }

    public void addLocation(String nome, Location l) {
        try {
            nome = nome.toLowerCase();
            if (!databaseFile.exists()) {
                databaseFile.createNewFile();
            }
            YamlConfiguration config = new YamlConfiguration();
            config.load(databaseFile);
            config.set("location." + nome + ".x", l.getX());
            config.set("location." + nome + ".y", l.getY());
            config.set("location." + nome + ".z", l.getZ());
            config.set("location." + nome + ".yaw", l.getYaw());
            config.set("location." + nome + ".pitch", l.getPitch());
            config.save(databaseFile);
            locations.put(nome, new CustomLocation(l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch()));
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InvalidConfigurationException ex) {
            Logger.getLogger(MapConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public void addRegion(String nome, Hitbox r) {
        try {
            nome = nome.toLowerCase();
            if (!databaseFile.exists()) {
                databaseFile.createNewFile();
            }
            YamlConfiguration config = new YamlConfiguration();
            config.load(databaseFile);
            config.set("region." + nome + ".max.x", r.getMax().getBlockX());
            config.set("region." + nome + ".max.y", r.getMax().getBlockY());
            config.set("region." + nome + ".max.z", r.getMax().getBlockZ());
            config.set("region." + nome + ".min.x", r.getMin().getBlockX());
            config.set("region." + nome + ".min.y", r.getMin().getBlockY());
            config.set("region." + nome + ".min.z", r.getMin().getBlockZ());
            config.save(databaseFile);
            regions.put(nome, r);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InvalidConfigurationException ex) {
            Logger.getLogger(MapConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addConfig(String nome, String valor) {
        try {
            nome = nome.toLowerCase();
            if (!databaseFile.exists()) {
                databaseFile.createNewFile();
            }
            YamlConfiguration config = new YamlConfiguration();
            config.load(databaseFile);
            config.set("config." + nome, valor);
            config.save(databaseFile);
            configs.put(nome, valor);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InvalidConfigurationException ex) {
            Logger.getLogger(MapConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public Set<String> getKeysRegions() {
        return regions.keySet();
    }

    public Set<String> getKeysLocations() {
        return locations.keySet();
    }

    public Set<String> getKeysConfigs() {
        return configs.keySet();
    }


    public CustomLocation getLocation(String nome) {
        nome = nome.toLowerCase();
        return locations.get(nome);
    }

    public Location getWorldLocation(String nome) {
        return getLocation(nome).toLocation(mapa.getWorld());
    }

    public Hitbox getRegion(String nome) {
        nome = nome.toLowerCase();
        return regions.get(nome);
    }

    public String getConfig(String nome) {
        nome = nome.toLowerCase();
        return configs.get(nome);
    }

    public Set<CustomLocation> getLocationsStartsWith(String prefix) {
        Set<CustomLocation> locs = new HashSet();
        for (String loc : this.getKeysLocations()) {
            if (loc.startsWith(prefix)) {
                locs.add(this.getLocation(loc));
            }
        }
        return locs;
    }

    public void removeConfig(String nome) {
        nome = nome.toLowerCase();
        if (!configs.containsKey(nome)) {
            return;
        }
        remove("config", nome);
        configs.remove(nome);
    }


    public void removeLocation(String nome) {
        nome = nome.toLowerCase();
        if (!locations.containsKey(nome)) {
            return;
        }
        remove("location", nome);
        locations.remove(nome);
    }

    public void removeRegion(String nome) {
        nome = nome.toLowerCase();
        if (!regions.containsKey(nome)) {
            return;
        }
        remove("region", nome);
        regions.remove(nome);
    }

    private void remove(String prefix, String nome) {
        try {
            nome = nome.toLowerCase();
            if (!databaseFile.exists()) {
                databaseFile.createNewFile();
            }
            YamlConfiguration config = new YamlConfiguration();
            config.load(databaseFile);
            config.getConfigurationSection(prefix).set(nome, null);
            config.save(databaseFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InvalidConfigurationException ex) {
            Logger.getLogger(MapConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
