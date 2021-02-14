package dev.feldmann.redstonegang.wire.integrations;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.StringFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class CustomFlags {

    public static final StringFlag GREETING_TITLE = new StringFlag("title-greeting", (String) null);
    public static final StringFlag FAREWELL_TITLE = new StringFlag("title-farewell", (String) null);
    public static final StateFlag CAN_BUY_LAND = new StateFlag("buy-land", false);
    public static final StringFlag BUY_LAND_PERMISSION = new StringFlag("buy-land-permission", (String) null);


    WorldGuardPlugin wgPlugin;

    public void onLoad() {
        Plugin pl = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
        if (pl instanceof WorldGuardPlugin) {
            wgPlugin = WorldGuardPlugin.inst();
            FlagRegistry flag = wgPlugin.getFlagRegistry();
            flag.register(GREETING_TITLE);
            flag.register(FAREWELL_TITLE);
            flag.register(CAN_BUY_LAND);
            flag.register(BUY_LAND_PERMISSION);
        }
    }

    public void onEnable() {
        wgPlugin.getSessionManager().registerHandler(TitleGreetingHandler.FACTORY, null);
        wgPlugin.getSessionManager().registerHandler(TitleFarewellHandler.FACTORY, null);

    }

    public void onDisable() {


    }
}
