package dev.feldmann.redstonegang.wire.game.base;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.api.maps.responses.MapResponse;
import dev.feldmann.redstonegang.common.player.config.ConfigType;
import dev.feldmann.redstonegang.wire.Wire;
import dev.feldmann.redstonegang.wire.game.base.addonconfigs.AddonConfigManager;
import dev.feldmann.redstonegang.wire.game.base.addons.both.changeserver.ChangeServerAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.damageinfo.DamageInfoAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.stats.StatAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.minigames.maps.Mapa;
import dev.feldmann.redstonegang.wire.game.base.addons.minigames.maps.MapaType;
import dev.feldmann.redstonegang.wire.game.base.objects.BaseListener;
import dev.feldmann.redstonegang.wire.game.base.addons.both.SchedulerAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.holograms.HologramAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.customItems.CustomItemsAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.minigames.maps.MapasAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.world.WorldControlAddon;
import dev.feldmann.redstonegang.wire.game.base.events.player.PlayerTeleportToServerEvent;
import dev.feldmann.redstonegang.wire.game.base.objects.API;
import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import dev.feldmann.redstonegang.wire.game.base.objects.ServerListener;
import dev.feldmann.redstonegang.wire.game.base.objects.annotations.Dependencies;
import dev.feldmann.redstonegang.wire.game.base.objects.exceptions.CyclicalDependencyException;
import dev.feldmann.redstonegang.wire.game.base.objects.exceptions.HardDependencyNotFoundException;
import dev.feldmann.redstonegang.wire.utils.location.BungeeLocation;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Server extends BaseListener {

    private HashMap<Class<? extends Addon>, Addon> addons = new HashMap<>();
    private HashMap<Class<? extends API>, API> apis = new HashMap<>();

    private boolean invalid = false;

    private Mapa mapa = null;
    /*
     * Funções de ligar e desligar
     * */

    public void enable() {
        addAddon(
                new DefaultAddon(),
                new SchedulerAddon(),
                new HologramAddon(),
                new MapasAddon(),
                new WorldControlAddon(),
                new CustomItemsAddon(),
                new DamageInfoAddon(),
                new StatAddon(),
                new ChangeServerAddon()
        );
    }

    public void _enable() throws HardDependencyNotFoundException, CyclicalDependencyException {
        Bukkit.getPluginManager().registerEvents(this, Wire.instance);
        registerListener(new ServerListener(this));
        enable();
        checkHardDependencies();
        checkCircularDependencies();
        try {
            enableAddons();
        } catch (Exception ex) {
            ex.printStackTrace();
            Bukkit.shutdown();
        }
    }

    public String getIdentifier() {
        return "geral";
    }


    public void disable() {
        disableAddons();
        apis.values().forEach((a) -> a.onDisable());
        HandlerList.unregisterAll(this);
        unregisterCmdsAndListeners(true);

    }

    public void lateEnable() {

    }

    public void _lateEnable() {
        lateEnable();
        startAddons();

    }


    public void setInvalid(boolean invalid) {
        this.invalid = invalid;
    }

    public boolean isInvalid() {
        return invalid;
    }


    private void checkHardDependencies() throws HardDependencyNotFoundException {
        for (Addon ad : addons.values()) {
            Class<? extends Addon>[] depen = getDependencies(ad.getClass(), false);
            for (Class<? extends Addon> d : depen) {
                if (!addons.containsKey(d)) {
                    throw new HardDependencyNotFoundException(ad.getClass(), d);
                }
            }
        }
    }

    private void checkCircularDependencies() throws CyclicalDependencyException {
        for (Addon ad : addons.values()) {
            if (!checkCircularDependencies(ad)) {
                throw new CyclicalDependencyException(ad.getClass());
            }
        }
    }

    private boolean checkCircularDependencies(Addon ad) {
        List<Class<? extends Addon>> depen = getAllDependencies(ad.getClass());
        return !depen.contains(ad.getClass());
    }


    public void disableAddons() {
        List<Addon> desligar = new ArrayList<>(addons.values());
        while (!desligar.isEmpty()) {
            OUT:
            for (Addon ad : new ArrayList<>(desligar)) {
                for (Addon ad2 : desligar) {
                    if (isDependencyOf(ad.getClass(), ad2.getClass())) {
                        continue OUT;
                    }
                }
                for (ConfigType cfg : ad.getPlayerConfigs()) {
                    RedstoneGang.instance.user().getConfig().removeConfig(cfg);
                }
                ad.desliga();
                try {
                    Wire.instance.game().addonsConfigs.saveVariables(ad);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                desligar.remove(ad);
            }
        }

        RedstoneGang.instance.user().getConfig().reload();
        addons.clear();
    }

    public boolean isDependencyOf(Class c1, Class c2) {
        return getAllDependencies(c2).contains(c1);
    }

    private void enableAddons() {
        for (Class<? extends Addon> ad : addons.keySet()) {
            enableAddon(ad);
        }
    }

    private void startAddons() {
        for (Class<? extends Addon> ad : addons.keySet()) {
            startAddon(ad);
            for (ConfigType cfg : addons.get(ad).getPlayerConfigs()) {
                RedstoneGang.instance.user().getConfig().registerConfig(cfg);
            }
        }
        RedstoneGang.instance.user().getConfig().reload();


    }

    private void enableAddon(Class<? extends Addon> ad) {
        Addon adon = a(ad);
        if (adon.enabled) {
            return;
        }

        List<Class<? extends Addon>> deps = getAllDependencies(ad);
        for (Class<? extends Addon> dep : deps) {
            if (a(dep) != null)
                enableAddon(dep);
        }
        try {
            long start = System.currentTimeMillis();
            loadApis(ad);
            adon.setServer(this);
            adon.onEnable();
            adon.enabled = true;
            this.registerListener(adon);
            adon.enabledTime = System.currentTimeMillis() - start;
            RedstoneGang.instance().debugIf("Addon " + adon.getClass().getSimpleName() + " took " + adon.enabledTime + " to enable!", adon.enabledTime > 250);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void loadApis(Class<? extends Addon> ad) {
        List<Class<? extends API>> cs = getApis(ad);
        if (cs != null) {
            for (Class<? extends API> c : cs) {
                if (!apis.containsKey(c)) {
                    try {
                        loadApi(c.newInstance());
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

    private void startAddon(Class<? extends Addon> ad) {
        Addon adon = a(ad);
        if (adon.started) {
            return;
        }
        List<Class<? extends Addon>> deps = getAllDependencies(ad);
        for (Class<? extends Addon> dep : deps) {
            if (this.addons.containsKey(dep)) {
                startAddon(dep);
            } else {

            }

        }
        long start = System.currentTimeMillis();
        adon.onStart();
        adon.started = true;
        adon.startedTime = System.currentTimeMillis() - start;
        RedstoneGang.instance().debugIf("Addon " + adon.getClass().getSimpleName() + " took " + adon.startedTime + " to start!", adon.startedTime > 250);

    }

    private List<Class<? extends Addon>> getAllDependencies(Class<? extends Addon> ad) {
        List<Class<? extends Addon>> dependencies = getDependencies(ad);
        for (Class<? extends Addon> depen : new ArrayList<>(dependencies)) {
            dependencies.addAll(getDependencies(depen));
        }
        return dependencies;

    }

    private List<Class<? extends Addon>> getDependencies(Class<? extends Addon> addon) {
        Class<? extends Addon>[] hard = getDependencies(addon, false);
        Class<? extends Addon>[] soft = getDependencies(addon, true);
        List<Class<? extends Addon>> both = new ArrayList<>();

        for (int x = 0; x < hard.length; x++) {
            both.add(hard[x]);
        }
        for (int x = 0; x < soft.length; x++) {
            both.add(soft[x]);
        }
        return both;

    }

    private Class<? extends Addon>[] getDependencies(Class<? extends Addon> addon, boolean soft) {
        Dependencies depen = addon.getAnnotation(Dependencies.class);
        if (depen != null) {
            if (soft) {
                return depen.soft();
            } else {
                return depen.hard();
            }
        }
        return new Class[0];
    }

    private List<Class<? extends API>> getApis(Class<? extends Addon> addon) {
        List<Class<? extends API>> apis = new ArrayList<>();


        while (!addon.getClass().equals(Addon.class)) {
            Dependencies depen = addon.getAnnotation(Dependencies.class);
            if (depen != null) {
                for (Class<? extends API> a : depen.apis()) {
                    apis.add(a);
                }
            }
            if (Addon.class.isAssignableFrom(addon.getSuperclass())) {
                addon = (Class<? extends Addon>) addon.getSuperclass();
            } else {
                break;
            }
        }

        return apis;
    }

    private void loadApi(API api) {
        if (!apis.containsKey(api.getClass())) {
            apis.put(api.getClass(), api);
            api.setSv(this);
            api.onEnable();
            registerListener(api);

        }
    }
    // ==============================================

    /*
     * Funções de carregar/remover addons
     */

    public void addAddon(Addon... addon) {
        if (addons == null) {
            addons = new HashMap<>();
        }
        if (addon != null) {
            for (Addon ad : addon) {
                if (!addons.containsKey(ad.getClass())) {
                    addons.put(ad.getClass(), ad);
                    AddonConfigManager configs = Wire.instance.game().addonsConfigs;
                    try {
                        configs.loadVariables(ad, getIdentifier());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    public void removeAddon(Class<? extends Addon> addon) {
        Addon ad = addons.remove(addon);
        if (ad != null) {
            ad.desliga();
            HandlerList.unregisterAll(ad);
            ad.unregisterCmdsAndListeners(true);
        }
    }


    public <T extends Addon> T getAddonInstanceOf(Class<T> classe) {
        if (addons.containsKey(classe)) {
            return (T) addons.get(classe);
        }
        for (Addon addon : addons.values()) {
            if (classe.isInstance(addon)) {
                return (T) addon;
            }
        }
        return null;
    }

    public <T extends Addon> T getAddon(Class<T> addon) {
        if (!addons.containsKey(addon)) {
            return null;
        }
        return (T) addons.get(addon);
    }

    public <T extends Addon> T a(Class<T> addon) {
        return getAddon(addon);
    }

    public boolean hasAddon(Class<? extends Addon> addon) {
        return addons.containsKey(addon);
    }

    public <T extends API> T getApi(Class<T> api) {
        return (T) apis.get(api);
    }

    /*
     * Shortcuts para addons uteis
     */

    public SchedulerAddon scheduler() {
        return a(SchedulerAddon.class);
    }

    public HologramAddon hologram() {
        return a(HologramAddon.class);
    }

    public MapasAddon mapas() {
        return a(MapasAddon.class);
    }


    public RedstoneGang api() {
        return Wire.instance.redstonegang();
    }


    /*
     * Funções de uso constante
     */

    /**
     * Teleporta todos os jogadores online para o mundo @param mundo
     */
    public void tpGeral(World w) {
        tpGeral(w.getSpawnLocation());
    }

    /**
     * Teleporta todos jogadores online para o mundo default
     */
    public void tpDefault() {
        tpGeral(Bukkit.getWorlds().get(0));
    }

    /**
     * Teleporta todo mundo para uma @param loc
     */
    public void tpGeral(Location loc) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.teleport(loc);
        }
    }

    public Collection<Addon> getAddons() {
        return addons.values();
    }

    public Games getGames() {
        for (Games g : Games.values()) {
            if (g.getEntry().getGameClass() == this.getClass()) {
                return g;
            }
        }
        return null;
    }

    public void teleport(Player p, BungeeLocation loc) {
        if (loc.isCurrentServer()) {
            p.teleport(loc.toLocation());
        } else {
            teleportToServer(p, loc.getServer(), loc);
        }
    }

    public boolean teleportToServer(Player p, String sv) {
        return teleportToServer(p, sv, null);
    }

    public boolean teleportToServer(Player p, String sv, BungeeLocation loc) {
        if (loc == null) {
            if (Wire.callEvent(new PlayerTeleportToServerEvent(p, sv))) {
                return false;
            }
        } else {
            if (Wire.callEvent(new PlayerTeleportToServerEvent(p, loc))) {
                return false;
            }
        }
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        try {
            out.writeUTF("Connect");
            out.writeUTF(sv); // Target Server
        } catch (IOException e) {
            // Can never happen
        }
        p.sendPluginMessage(Wire.instance, "BungeeCord", b.toByteArray());
        return true;

    }

    public void broadcast(String s) {
        broadcast(new TextComponent(TextComponent.fromLegacyText(s)));
    }

    public void broadcast(TextComponent text, String permission) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasPermission(permission)) {
                p.spigot().sendMessage(text);
            }
        }
        Bukkit.getConsoleSender().sendMessage(text.toLegacyText());
    }

    public void broadcast(TextComponent text) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.spigot().sendMessage(text);
        }
        Bukkit.getConsoleSender().sendMessage(text.toLegacyText());
    }

    public void loadMapa(MapResponse map) {
        if (this.mapa != null) {
            this.mapas().unloadMapa(mapa);
            this.mapa = null;
        }
        this.mapa = this.mapas().load(map, MapaType.GAME);
    }

    public Mapa getMapa() {
        return mapa;
    }
}
