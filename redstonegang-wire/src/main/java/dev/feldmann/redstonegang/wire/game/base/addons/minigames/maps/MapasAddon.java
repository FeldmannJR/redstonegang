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

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.api.Response;
import dev.feldmann.redstonegang.common.api.maps.responses.MapResponse;
import dev.feldmann.redstonegang.common.utils.FileUtils;
import dev.feldmann.redstonegang.wire.Wire;
import dev.feldmann.redstonegang.wire.game.base.Games;
import dev.feldmann.redstonegang.wire.game.base.Server;
import dev.feldmann.redstonegang.wire.game.base.addons.both.npcs.NPCAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.npcs.RedstoneNPC;
import dev.feldmann.redstonegang.wire.game.base.objects.Addon;

import dev.feldmann.redstonegang.wire.game.base.objects.ServerEntry;
import dev.feldmann.redstonegang.wire.game.base.objects.maps.MapConfigEntry;
import dev.feldmann.redstonegang.wire.game.base.objects.maps.SaveOption;
import dev.feldmann.redstonegang.wire.game.base.objects.maps.SaveType;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Carlos
 */
public class MapasAddon extends Addon {

    private static HashMap<String, Mapa> loaded = new HashMap();

    private MapFileManager fileManager;

    @Override
    public void onEnable() {
        fileManager = new MapFileManager();
        plugin().redstonegang().runRepeatingTask(new Runnable() {
            @Override
            public void run() {

                for (Mapa m : getLoadedMaps()) {
                    if (m.getWorld() != null) {
                        if (m.getTime() != -1) {
                            m.getWorld().setTime(m.getTime());
                        }
                    }
                }

            }
        }, 10);
    }

    public Collection<Mapa> getLoadedMaps() {
        return loaded.values();
    }


    public void save(Mapa m) {
        if (!loaded.containsKey(m.getKey())) {
            return;
        }
        try {
            UnloadWorld(m.getWorld(), true);
            fileManager.uploadFromBukkit(m.getId(), m.getBukkitFolder(), hasNeededConfigs(m));
            loaded.remove(m.getKey());
        } catch (IOException ex) {
            Logger.getLogger(MapasAddon.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void saveConfig(Mapa m) {
        try {
            fileManager.updateConfig(m.getId(), m.getBukkitFolder(), hasNeededConfigs(m));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public List<String> getFolders() {
        return fileManager.games();
    }

    public MapResponse create(String game, String nome) {
        Response<MapResponse> response = RedstoneGang.instance().webapi().maps().findOrCreate(nome, game);
        if (response.hasFailed()) return null;
        return response.collect();
    }

    public List<String> getAllFolders() {
        List<String> games = new ArrayList<>();
        for (Games game : Games.values()) {
            if (game.getEntry().getMapsFolder() != null)
                games.add(game.getEntry().getMapsFolder());
        }
        games.add("random");
        return games;
    }

    public boolean exists(MapResponse response) {
        return fileManager.exists(response);
    }

    @Override
    public void onDisable() {

        for (Mapa m : getLoadedMaps()) {
            if (m.getTipo() == MapaType.STATIC) {
                continue;
            }
            server().tpDefault();
            unloadMapa(m);
        }
    }

    public void unloadMapa(Mapa map) {
        if (map == null) {
            return;
        }
        UnloadWorld(map.getWorld(), false);
        FileUtils.deleteDirectory(map.getBukkitFolder());
        loaded.remove(map.getKey());
    }

    private World loadWorldBukkit(String nome) {

        World ww = WorldCreator.
                name(nome).
                type(org.bukkit.WorldType.FLAT).
                generateStructures(false).
                generator(new VoidGenerator()).
                createWorld();

        ww.setSpawnFlags(true, true);
        ww.setKeepSpawnInMemory(false);
        ww.setAutoSave(false);
        ww.setPVP(true);

        List<Entity> remove = new ArrayList();
        for (Entity et : ww.getEntities()) {
            if (et instanceof LivingEntity) {
                if (et instanceof ArmorStand) {
                    ArmorStand ar = (ArmorStand) et;
                    if (ar.isVisible()) {
                        continue;
                    }
                }
                remove.add(et);
            }
        }
        for (Entity re : remove) {
            re.remove();
        }
        return ww;
    }

    public MapResponse find(String game, String name) {
        return RedstoneGang.instance().webapi().maps().find(game, name);
    }

    public MapResponse anyFromGame() {
        Server server = Wire.instance.game().getServer();
        String mapsfolder = server.getGames().getEntry().getMapsFolder();
        return anyFromGame(mapsfolder);
    }

    public MapResponse anyFromGame(String game) {
        Response<MapResponse[]> response = RedstoneGang.instance().webapi().maps().list(game);
        if (!response.hasFailed()) {
            for (MapResponse map : response.collect()) {
                if (map.valid) {
                    return map;
                }
            }
        }
        return null;
    }

    public boolean hasNeededConfigs(Mapa m) {

        MapConfig config = m.getConfig();
        ServerEntry entry = m.getEntry();
        if (entry == null) return true;
        List<MapConfigEntry> entries = entry.getMapConfigs();
        for (MapConfigEntry en : entries) {
            if (en.getOption() != SaveOption.REQUIRED) continue;
            if (en.getTipo() == SaveType.REGION) {
                if (!config.getKeysRegions().contains(en.getNome())) {
                    return false;
                }
            } else if (en.getTipo() == SaveType.STRING) {
                if (!config.getKeysConfigs().contains(en.getNome())) {
                    return false;
                }
            } else {
                if (!config.getKeysLocations().contains(en.getNome())) {
                    return false;
                }
            }
        }
        return true;


    }

    public MapFileManager getFileManager() {
        return fileManager;
    }

    public Mapa loadVerifiying(MapResponse map, MapaType type) {
        if (type == MapaType.GAME && !map.valid) {
            server().broadcast("Mapa não configurado!!!");
            Wire.instance.game().loadGame(Games.ESPERA.getEntry());
            return null;
        }
        return load(map, type);
    }

    public Mapa load(String game, String name) {
        return load(game, name, MapaType.GAME);
    }

    public Mapa load(String game, String nome, MapaType type) {

        MapResponse res = find(game, nome);
        if (res != null) {
            return this.load(res, type);
        }
        return null;
    }

    public Mapa load(MapResponse response, MapaType type) {
        Mapa m = new Mapa(response, type);

        if (loaded.containsKey(m.getKey())) {
            return loaded.get(m.getKey());
        }
        File create = m.getBukkitFolder();
        if (create.exists()) {
            create.delete();
        }
        try {
            fileManager.copyMap(response.id, m.getBukkitFolder());
        } catch (IOException e) {
            e.printStackTrace();
        }
        loadWorldBukkit(m.getBukkitName());
        m.config.loadConfig();
        if (m.getTime() != -1) {
            m.getWorld().setTime(m.getTime());
        }
        log("Mapa " + m.getKey() + " carregado!");
        loaded.put(m.getKey(), m);
        return m;
    }

    public void UnloadWorld(World world, boolean save) {
        if (server().hasAddon(NPCAddon.class)) {
            List<RedstoneNPC> toremove = new ArrayList();
            for (RedstoneNPC npc : server().a(NPCAddon.class).npcs) {
                if (npc != null && npc.getCitizens() != null) {
                    if (npc.getCitizens().getStoredLocation() != null && npc.getCitizens().getStoredLocation().getWorld() != null) {
                        if (npc.getCitizens().getStoredLocation().getWorld() == world) {
                            toremove.add(npc);
                        }
                    }
                }
            }
            for (RedstoneNPC n : toremove) {
                server().a(NPCAddon.class).removerNPC(n);
                server().a(NPCAddon.class).npcs.remove(n);
            }
        }
        for (Chunk c : world.getLoadedChunks()) {
            c.unload(save);
        }
        Bukkit.getServer().unloadWorld(world, true);

    }
}
