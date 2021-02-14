package dev.feldmann.redstonegang.common;

import dev.feldmann.redstonegang.common.api.RedstoneGangWebAPI;
import dev.feldmann.redstonegang.common.config.Config;
import dev.feldmann.redstonegang.common.config.ServerConfig;
import dev.feldmann.redstonegang.common.db.DatabaseManager;
import dev.feldmann.redstonegang.common.db.Databases;
import dev.feldmann.redstonegang.common.db.money.CurrencyManager;
import dev.feldmann.redstonegang.common.logs.Logs;
import dev.feldmann.redstonegang.common.network.NetworkClient;
import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.common.player.UserManager;
import dev.feldmann.redstonegang.common.servers.ServerManager;
import dev.feldmann.redstonegang.common.shop.ShopManager;
import com.google.gson.GsonBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;

import java.util.Random;
import java.util.UUID;
import java.util.logging.Logger;

public abstract class RedstoneGang {


    public String ENVIRONMENT;
    public boolean DEV;
    public boolean DEBUG;
    public String NAME;

    public static RedstoneGang instance;
    public static Random rnd;
    private Config config;

    private NetworkClient client;

    private DatabaseManager database;
    private Databases databases;
    private UserManager users;
    private RedstoneGangWebAPI webapi;
    private ServerManager servers;
    private CurrencyManager currencies;
    private ShopManager shop;


    private Logs logs;

    public RedstoneGang() {
        //Testando
        instance = this;
        rnd = new Random();

        this.config = new Config();
        // Api criada no laravel
        webapi = new RedstoneGangWebAPI(config.API_URL, config.API_TOKEN, "v1");
        webapi.showApiInfo();
        config.loadProvider();
        if (DEV) {
            alert("Servidor de desenvolvimento!");
        }
        ServerConfig config = this.config.getConfig();
        this.config.APP_BASE_URL = config.app_base_url;
        if (DEBUG) {
            getLogger().info("Network Host: " + config.network_host + " Network Port: " + config.network_port + " Network Token: " + config.network_token);
        }
        if (useNetwork()) {
            client = new NetworkClient(config.network_host, config.network_port, config.network_token);
        }
        if (useDatabase()) {
            database = new DatabaseManager(config.database_host, config.database_schema, config.database_user, config.database_password);
            databases = new Databases();
            if (useUsers()) {
                users = new UserManager();
                users.init();
            }
            logs = new Logs();
        }
        servers = new ServerManager();
        currencies = new CurrencyManager();
        shop = new ShopManager();
    }


    public static RedstoneGang instance() {
        return instance;
    }

    public Databases databases() {
        return databases;
    }

    public ServerManager servers() {
        return servers;
    }

    public ShopManager shop() {
        return shop;
    }

    public void debug(String msg) {
        if (DEBUG) {
            getLogger().info("DEBUG: " + msg);
        }
    }

    public void debugIf(String msg, boolean isTrue) {
        if (isTrue) {
            debug(msg);
        }
    }

    public void alert(String msg) {
        getLogger().warning("");
        getLogger().warning("|=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|");
        getLogger().warning("|");
        getLogger().warning("| " + msg);
        getLogger().warning("|");
        getLogger().warning("|=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|");
        getLogger().warning("");
    }


    public Logger getLogger() {
        return Logger.getGlobal();
    }


    public void onDisable() {
    }

    public void onJoin(UUID uid) {

    }

    public boolean useNetwork() {
        return true;
    }

    public boolean useDatabase() {
        return true;
    }

    public boolean useUsers() {
        return true;
    }

    public String getNomeServer() {
        return NAME;
    }

    public static User getPlayer(int id) {
        return instance.user().cache.get(id);
    }

    public static User getPlayer(UUID uid) {
        return instance.user().cache.get(uid);
    }

    public static User getPlayer(String name) {
        return instance.user().cache.get(name);
    }

    public RedstoneGangWebAPI webapi() {
        return webapi;
    }

    public DatabaseManager db() {
        return database;
    }

    public CurrencyManager currencies() {
        return currencies;
    }

    public NetworkClient network() {
        return client;
    }

    public UserManager user() {
        return users;
    }

    public Logs logs() {
        return logs;
    }

    public Config config() {
        return config;
    }

    public abstract ServerType getServerType();

    protected void sendUserMessageSocket(User user, TextComponent... textComponent) {
        String[] message = new String[2 + textComponent.length];
        message[0] = "sendmessage";
        message[1] = String.valueOf(user.getId());
        for (int i = 0; i < textComponent.length; i++) {
            message[i + 2] = ComponentSerializer.toString(textComponent);
        }
        network().sendMessageToType(ServerType.BUNGEE, (Object[]) message);
    }

    public void broadcastMessage(TextComponent... textComponent) {
        String[] message = new String[1 + textComponent.length];
        message[0] = "broadcastmessage";
        for (int i = 0; i < textComponent.length; i++) {
            message[i + 1] = ComponentSerializer.toString(textComponent);
        }
        network().sendMessageToType(ServerType.BUNGEE, (Object[]) message);
    }

    public void registerGsonTypes(GsonBuilder builder) {

    }

    /*
     *Tempo em ticks, 20 ticks = 1 s
     */
    public abstract void runRepeatingTask(Runnable r, int tempo);

    public abstract void runSync(Runnable r);

    public abstract void runAsync(Runnable r);

    public abstract void sendMessage(User player, TextComponent... txt);

    public abstract void shutdown(String cause);

}
