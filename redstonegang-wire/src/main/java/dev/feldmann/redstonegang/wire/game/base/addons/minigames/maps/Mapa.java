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

import dev.feldmann.redstonegang.common.api.maps.responses.MapResponse;
import dev.feldmann.redstonegang.wire.game.base.Games;
import dev.feldmann.redstonegang.wire.game.base.objects.ServerEntry;
import dev.feldmann.redstonegang.wire.utils.hitboxes.Hitbox;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.File;

/**
 * @author Carlos André Feldmann Júnior
 */
public class Mapa {

    private static int lastId = 0;

    private String bukkitName;
    private MapResponse map;
    public MapConfig config;
    private MapaType tipo;

    public boolean loaded = false;

    public String getNome() {
        return map.name;
    }

    public Mapa(MapResponse response, MapaType type) {
        this.map = response;
        this.tipo = type;
        lastId++;
        bukkitName = "mundo" + lastId;
        config = new MapConfig(this);

    }


    public File getBukkitFolder() {
        return new File(Bukkit.getWorldContainer().getAbsolutePath() + "/" + bukkitName + "/");

    }


    public String getBukkitName() {
        return bukkitName;
    }


    public String getKey() {
        return map.game + "/" + getNome();
    }

    public MapaType getTipo() {
        return tipo;
    }

    public Location getSpawn() {
        if (config.getKeysLocations().contains("spawn")) {
            return config.getWorldLocation("spawn");
        } else {
            return getWorld().getSpawnLocation();
        }

    }

    public Hitbox getRegionMapa() {
        return config.getRegion("mapa");
    }

    public int getTime() {
        if (config.getKeysConfigs().contains("time")) {
            try {
                int time = Integer.valueOf(config.getConfig("time"));

                return time;
            } catch (NumberFormatException ex) {

                return -1;
            }
        }

        return -1;
    }

    public String getBuilders() {
        if (config.getConfig("builders") != null) {
            return config.getConfig("builders");
        }
        return "Desconhecido";
    }

    public ServerEntry getEntry() {
        if (this.map.game == null) {
            return null;
        }
        for (Games g : Games.values()) {
            if (this.map.game.equals(g.getEntry().getMapsFolder())) {
                return g.getEntry();
            }
        }
        return null;
    }

    public MapConfig getConfig() {
        return config;
    }

    public World getWorld() {
        for (World level : Bukkit.getWorlds()) {
            if (level.getName().equalsIgnoreCase(bukkitName)) {
                return level;
            }
        }
        return null;
    }

    public int getId() {
        return map.id;
    }
}
