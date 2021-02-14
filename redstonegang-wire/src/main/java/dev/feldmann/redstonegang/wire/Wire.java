package dev.feldmann.redstonegang.wire;

import dev.feldmann.redstonegang.RGSpigot;
import dev.feldmann.redstonegang.common.utils.EnvHelper;
import dev.feldmann.redstonegang.wire.base.cmds.CmdRegistrator;
import dev.feldmann.redstonegang.wire.base.modulos.ModuloManager;
import dev.feldmann.redstonegang.wire.game.base.GameManager;
import dev.feldmann.redstonegang.wire.integrations.CitizensWireIntegration;
import dev.feldmann.redstonegang.wire.plugin.events.update.Updater;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import net.citizensnpcs.Citizens;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;


public class Wire extends JavaPlugin implements PluginMessageListener {


    public String GAME_NAME;
    public static Wire instance;

    private RedstoneGangSpigot redstonegang;
    private CmdRegistrator cmds;
    private ModuloManager modulos;
    private GameManager game;
//    private CancellationDetector detector = new CancellationDetector<EntityDamageByEntityEvent>(EntityDamageByEntityEvent.class);

    public boolean restarting = false;

    @Override
    public void onLoad() {
        C.initC();
        modulos = new ModuloManager();
        modulos.onLoad();
    }

    @Override
    public void onEnable() {
        instance = this;
        logToFile();
        registerBungeeChannels();
        GAME_NAME = EnvHelper.get("SPIGOT_GAME_NAME", null);
        try {
            this.redstonegang = new RedstoneGangSpigot();
        } catch (Exception ex) {
            ex.printStackTrace();
            Bukkit.shutdown();
            return;
        }
        cmds = new CmdRegistrator();
        cmds.registerDefaults();
        game = new GameManager();
        modulos.onEnable();
        game.load();
        if (game.getServer() != null) {
            redstonegang.servers().serverTurnOn(true, game.getServer().getGames().name());
        }
        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Updater(), 1, 1);
        CitizensWireIntegration integration = new CitizensWireIntegration();
        Citizens.integration = integration;
        RGSpigot.implementation = integration;
//        detector.addListener((plugin, event) -> {
//            System.out.println(event.getEventName() + " cancelled by " + plugin.getName());
//        });
    }


    public void logToFile() {
        ((Logger) LogManager.getRootLogger()).addFilter(new WireLogFilter());
    }

    @Override
    public void onDisable() {
        restarting = true;
        if (game != null) {
            game.unload();
        }
        if (modulos != null) {
            modulos.onDisable();
        }
        redstonegang.servers().serverTurnOff();
        if (!Bukkit.getOnlinePlayers().isEmpty()) {
            try {
                // Da tempo pro bungee teleportar os negos se precisar
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void registerBungeeChannels() {
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);
    }

    public CmdRegistrator cmds() {
        return cmds;
    }

    public RedstoneGangSpigot redstonegang() {
        return redstonegang;
    }

    public GameManager game() {
        return game;
    }

    public static void log(String s) {
        instance.getLogger().info(s);
    }

    public static boolean callEvent(Event ev) {
        Bukkit.getPluginManager().callEvent(ev);
        if (ev instanceof Cancellable) {
            return ((Cancellable) ev).isCancelled();
        }
        return false;
    }


    @Override
    public void onPluginMessageReceived(String s, Player player, byte[] bytes) {

    }
}
