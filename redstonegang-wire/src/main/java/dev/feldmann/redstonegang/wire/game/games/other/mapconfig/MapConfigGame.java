package dev.feldmann.redstonegang.wire.game.games.other.mapconfig;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.api.Response;
import dev.feldmann.redstonegang.common.api.maps.responses.MapResponse;
import dev.feldmann.redstonegang.wire.game.base.addons.both.effects.EffectsAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.holograms.Hologram;
import dev.feldmann.redstonegang.wire.game.base.addons.minigames.maps.Mapa;
import dev.feldmann.redstonegang.wire.game.base.addons.minigames.maps.MapaType;
import dev.feldmann.redstonegang.wire.game.base.addons.both.npcs.NPCAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.npcs.RedstoneNPC;
import dev.feldmann.redstonegang.wire.game.base.events.player.PlayerJoinServerEvent;
import dev.feldmann.redstonegang.wire.game.base.objects.ServerEntry;
import dev.feldmann.redstonegang.wire.game.base.Server;
import dev.feldmann.redstonegang.wire.game.base.objects.maps.MapConfigEntry;
import dev.feldmann.redstonegang.wire.game.base.objects.maps.SaveType;
import dev.feldmann.redstonegang.wire.game.games.other.mapconfig.addons.PlayerConfigAddon;
import dev.feldmann.redstonegang.wire.game.games.other.mapconfig.cmds.CmdMapa;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.util.*;
import java.util.stream.Collectors;

public class MapConfigGame extends Server {

    Mapa configurando = null;
    List<RedstoneNPC> locNpcs = new ArrayList();
    List<Hologram> holos = new ArrayList();
    List<MapResponse> maps;

    @Override
    public void enable() {
        super.enable();
        addAddon(
                new NPCAddon(),
                new PlayerConfigAddon(this),
                new EffectsAddon()
        );
        registerListener(new MapGameListener());
        registerCommand(new CmdMapa(this));
    }

    public List<MapResponse> getMaps() {
        if (maps == null) {
            Response<MapResponse[]> list = RedstoneGang.instance().webapi().maps().list();
            if (!list.hasFailed()) {
                this.maps = Arrays.asList(list.collect());
            }
        }
        return maps;
    }

    public void saveCurrent() {
        if (configurando != null) {
            removeLocNpcs();
            tpDefault();
            mapas().save(configurando);
            configurando = null;
        }
    }

    public void setConfigurando(MapResponse response) {
        if (configurando != null) {
            removeLocNpcs();
            tpDefault();
            mapas().unloadMapa(configurando);
            configurando = null;
        }
        if (response != null) {
            if (mapas().exists(response)) {
                configurando = mapas().load(response, MapaType.GAME);
                config().trocaMapa(configurando);
                update();
                tpGeral(configurando.getSpawn());

            }
        }

    }

    @EventHandler
    public void join(PlayerJoinServerEvent ev) {
        Player p = ev.getPlayer();
        p.setGameMode(GameMode.CREATIVE);
        if (getConfigurando() != null) {
            p.teleport(getConfigurando().getSpawn());
        }
    }

    public Mapa getConfigurando() {
        return configurando;
    }


    public List<MapConfigEntry> getEntries() {
        if (configurando == null) return null;
        List<MapConfigEntry> entries = new ArrayList<>();
        ServerEntry entry = configurando.getEntry();
        if (entry != null) {
            entries = entry.getMapConfigs();
        }
        return entries;
    }

    public List<MapConfigEntry> filter(SaveType tipo) {
        if (configurando == null) return null;
        List<MapConfigEntry> entries = getEntries();
        Iterator<MapConfigEntry> it = entries.iterator();
        while (it.hasNext()) {
            if (it.next().getTipo() != tipo) {
                it.remove();
            }
        }
        return entries;
    }

    public List<String> getMapGames() {
        List<MapResponse> maps = getMaps();
        List<String> games = new ArrayList<>();
        maps.forEach((e) -> {
            if (games.contains(e.game)) games.add(e.game);
        });
        return games;
    }

    public List<MapResponse> getMaps(String game) {
        return getMaps().stream().filter((e) -> e.game.equals(game)).collect(Collectors.toList());
    }

    public String getNextName(Set<String> keys, String opcional) {
        int x = 1;
        for (String s : keys) {
            if (s.startsWith(opcional)) {
                String[] split = s.split("_");
                String last = split[split.length - 1];
                int numero = Integer.valueOf(last);
                if (numero > x) {
                    x = numero;
                }
            }
        }
        int usa = 0;
        for (int z = 1; z <= x + 1; z++) {
            if (!keys.contains(opcional + "_" + z)) {
                usa = z;
                break;
            }
        }
        if (usa == 0) {
            usa = x + 1;
        }
        return opcional + "_" + usa;
    }

    public boolean isBlock(String loc) {

        for (MapConfigEntry entr : getEntries()) {
            if (entr.getNome().equalsIgnoreCase(loc) || (loc.startsWith(entr.getNome()) && loc.contains("_"))) {
                if (entr.getTipo() == SaveType.BLOCK) {
                    return true;
                }
            }
        }
        return false;
    }

    public PlayerConfigAddon config() {
        return a(PlayerConfigAddon.class);
    }


    public void update() {
        updateLocNpcs();
        updateBlocks();
    }

    private void updateLocNpcs() {
        removeLocNpcs();
        if (configurando != null) {
            Set<String> keysLocations = configurando.getConfig().getKeysLocations();
            for (String loc : keysLocations) {
                if (isBlock(loc)) continue;
                Location worldLoc = configurando.getConfig().getWorldLocation(loc);
                RedstoneNPC npc = new RedstoneNPC(worldLoc, EntityType.PLAYER, "Loc: " + loc);
                npc.setSkin("MHF_Exclamation");
                npc.setFly(true);
                locNpcs.add(npc);
                a(NPCAddon.class).criar(npc);
            }
        }
    }

    private void updateBlocks() {
        removeBlocks();
        if (configurando != null) {
            Set<String> keysLocations = configurando.getConfig().getKeysLocations();
            for (String loc : keysLocations) {
                if (!isBlock(loc)) continue;
                Location worldLoc = configurando.getConfig().getWorldLocation(loc);
                Hologram h = hologram().createHolo(worldLoc.clone().add(0.5, 1, 0.5), "§f" + loc);
                holos.add(h);
            }
        }
    }

    public void removeBlocks() {
        for (Hologram h : holos) {
            hologram().removeHologram(h);
        }
        holos.clear();
    }

    public void removeLocNpcs() {
        for (RedstoneNPC npc : locNpcs) {
            a(NPCAddon.class).removerNPC(npc);
        }
        locNpcs.clear();
    }


}
