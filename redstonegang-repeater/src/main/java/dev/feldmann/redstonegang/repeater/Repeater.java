package dev.feldmann.redstonegang.repeater;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.network.NetworkMessage;
import dev.feldmann.redstonegang.common.player.permissions.PermissionManager;
import dev.feldmann.redstonegang.common.player.permissions.PermissionServer;
import dev.feldmann.redstonegang.common.utils.ObjectLoader;
import dev.feldmann.redstonegang.repeater.commands.RegisterCommand;
import dev.feldmann.redstonegang.repeater.events.NetworkMessageEvent;
import dev.feldmann.redstonegang.repeater.modules.Module;
import dev.feldmann.redstonegang.repeater.modules.ServerManager;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.List;

public class Repeater extends Plugin {

    private static Repeater instance;
    private RedstoneGang redstonegang;
    private List<Module> modules;

    @Override
    public void onEnable() {
        Repeater.instance = this;
        this.redstonegang = new RedstoneGangBungee();
        this.redstonegang.network().addHandler(this::handleNetwork);
        PermissionManager.defaultServer = PermissionServer.GERAL;
        log("ON!");
        loadModules();
        this.getProxy().getPluginManager().registerCommand(this, new RegisterCommand());

    }

    private void handleNetwork(NetworkMessage message) {
        log(message.toString());
        this.getProxy().getPluginManager().callEvent(new NetworkMessageEvent(message));
    }

    private void loadModules() {
        modules = ObjectLoader.load(Module.class.getPackage().getName(), Module.class, (jar) -> {
            return !jar.getName().endsWith("/Module.class");
        });
        for (Module module : modules) {
            log("Loading module: " + module.getClass().getSimpleName());
            getInstance().getProxy().getPluginManager().registerListener(Repeater.getInstance(), module);
            module.onEnable();
        }
    }

    @Override
    public void onDisable() {
        for (Module module : modules) {
            module.onDisable();
        }
        log("OFF!");
    }

    public static Repeater getInstance() {
        return instance;
    }

    public static void log(String log) {
        Repeater.getInstance().getLogger().info(log);
    }

    public static void runSync(Runnable r) {

    }

    public ServerManager servers() {
        return getModule(ServerManager.class);
    }

    public <T extends Module> T getModule(Class<T> classe) {
        for (Module module : modules) {
            if (module.getClass() == classe) {
                return (T) module;
            }
        }
        return null;
    }

    public static void runAsync(Runnable r) {
        getInstance().getProxy().getScheduler().runAsync(instance, r);
    }

    public void sendToDefault(String server) {

    }

}
