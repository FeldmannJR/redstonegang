package dev.feldmann.redstonegang.wire.game.base.objects;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.network.NetworkClient;
import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.common.player.config.ConfigType;
import dev.feldmann.redstonegang.common.player.permissions.GroupOption;
import dev.feldmann.redstonegang.wire.Wire;
import dev.feldmann.redstonegang.wire.RedstoneGangSpigot;
import dev.feldmann.redstonegang.wire.game.base.Server;
import dev.feldmann.redstonegang.wire.game.base.addons.both.SchedulerAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.invsync.InvSync;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Addon extends BaseListener {

    private List<GroupOption> options = new ArrayList();
    List<ConfigType> playerConfigs = new ArrayList<>();

    Server server = null;

    public boolean enabled = false;
    public boolean started = false;
    public boolean disabled = false;

    public long enabledTime = 0, startedTime = 0;


    /**
     * Chamado ao adicionar o addon ao jogo
     */
    public void onEnable() {

    }

    /**
     * Chamado após iniciar todos os addons
     */
    public void onStart() {
    }

    /**
     * Chamado ao remover o addon ou fechar o jogo
     */
    public void onDisable() {
    }

    public void addOption(GroupOption... op) {
        for (GroupOption option : op) {
            options.add(option);
        }
    }

    public void setServer(Server s) {
        this.server = s;
    }

    public Server server() {
        return server;
    }

    public void desliga() {
        if (!enabled) {
            return;
        }
        enabled = false;
        started = false;
        disabled = true;
        this.onDisable();
    }

    public Server getServer() {
        return server;
    }

    public Wire plugin() {
        return Wire.instance;
    }

    public SchedulerAddon scheduler() {
        return a(SchedulerAddon.class);
    }

    public void log(String oq) {
        System.out.println("[" + getClass().getSimpleName() + "] " + oq);
    }

    public <T extends Addon> T aOf(Class<T> addon) {
        return server().getAddonInstanceOf(addon);
    }

    public <T extends Addon> T a(Class<T> addon) {
        return server().getAddon(addon);
    }

    public boolean hasAddon(Class<? extends Addon> addon) {
        return a(addon) != null;
    }


    public <T extends API> T api(Class<T> api) {
        return server.getApi(api);
    }


    public int getPlayerId(Player p) {
        return RedstoneGang.getPlayer(p.getUniqueId()).getId();
    }

    public User getUser(Player p) {
        return RedstoneGang.getPlayer(p.getUniqueId());
    }

    public User getUser(int id) {
        return RedstoneGang.getPlayer(id);
    }

    public Player getOnlinePlayer(int pid) {
        return Bukkit.getPlayer(RedstoneGangSpigot.getPlayer(pid).getUuid());
    }

    public NetworkClient network() {
        return RedstoneGang.instance.network();
    }

    public List<GroupOption> getOptions() {
        return options;
    }

    public void broadcast(TextComponent text) {
        getServer().broadcast(text);
    }

    public void addConfig(ConfigType... config) {
        Collections.addAll(playerConfigs, config);
    }

    public List<ConfigType> getPlayerConfigs() {
        return playerConfigs;
    }

    public <U> void runAsync(Supplier<U> func, Consumer<U> sync) {
        RedstoneGangSpigot.instance.runAsync(() -> {
            U get = func.get();
            RedstoneGangSpigot.instance.runSync(() -> {
                if (!disabled) {
                    sync.accept(get);
                }
            });
        });
    }

    public void teleportAfter(Player p, Location l, int ticks) {
        scheduler().runAfter(() -> {
            if (p != null && p.isOnline()) {
                if (a(InvSync.class) != null) {
                    if (a(InvSync.class).isWaitingLoad(p)) return;
                }
                p.teleport(l);
            }
        }, ticks);
    }


    private String getAddonSlug() {
        return this.getClass().getSimpleName().toLowerCase().replace("addon", "");
    }

    public String generateConfigName(String configName) {
        return "ad_" + getAddonSlug() + "_" + getServer().getIdentifier() + "_" + configName;
    }

    public String generateConfigNameWithoutIdentifier(String configName) {
        return "ad_" + getAddonSlug() + "_" + configName;
    }


    public String generatePermission(String what) {
        return "rg." + getAddonSlug() + "." + what;
    }

    public String generatePermissionStaff(String what) {
        return "rg." + getAddonSlug() + ".staff." + what;
    }

    public void runAsync(Runnable r) {
        scheduler().rodaAsync(r);
    }

    public void runSync(Runnable sync) {
        scheduler().runSync(sync);
    }
}
