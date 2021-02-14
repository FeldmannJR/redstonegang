package dev.feldmann.redstonegang.wire;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.ServerType;
import dev.feldmann.redstonegang.common.currencies.Currency;
import dev.feldmann.redstonegang.common.db.money.CurrencyChangeType;
import dev.feldmann.redstonegang.common.db.money.CurrencyHook;
import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.wire.plugin.events.NetworkMessageEvent;
import dev.feldmann.redstonegang.wire.plugin.events.PlayerCurrencyChangeEvent;
import com.google.gson.GsonBuilder;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.util.UUIDTypeAdapter;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;
import java.util.logging.Logger;

public class RedstoneGangSpigot extends RedstoneGang implements CurrencyHook {

    public RedstoneGangSpigot() {
        super();
        network().addHandler(msg ->
        {
            Bukkit.getPluginManager().callEvent(new NetworkMessageEvent(msg));
        });
        currencies().addHook(this);
    }

    @Override
    public ServerType getServerType() {
        return ServerType.SPIGOT;
    }


    @Override
    public void runRepeatingTask(Runnable r, int tempo) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Wire.instance, r, tempo, tempo);
    }

    @Override
    public void runSync(Runnable r) {
        Bukkit.getScheduler().runTask(Wire.instance, r);
    }

    @Override
    public void runAsync(Runnable r) {
        Bukkit.getScheduler().runTaskAsynchronously(Wire.instance, r);
    }

    @Override
    public void sendMessage(User rgPlayer, TextComponent... textComponent) {
        Player p = getOnlinePlayer(rgPlayer.getId());
        if (p != null && p.isOnline()) {
            for (TextComponent component : textComponent) {
                p.spigot().sendMessage(component);
            }
        } else {
            sendUserMessageSocket(rgPlayer, textComponent);
        }
    }


    @Override
    public void shutdown(String s) {
        getLogger().info("Shutdown: " + s);
        Bukkit.shutdown();
    }

    @Override
    public Logger getLogger() {
        return Wire.instance.getLogger();
    }


    public static Player getOnlinePlayer(User user) {
        return user == null ? null : getOnlinePlayer(user.getId());
    }

    public static Player getOnlinePlayer(int pid) {
        User pl = getPlayer(pid);
        if (pl != null) {
            Player online = Bukkit.getPlayer(pl.getUuid());
            if (online != null && online.isOnline())
                return online;
        }
        return null;
    }

    public static User getPlayer(Player p) {
        return getPlayer(p.getUniqueId());
    }

    @Override
    public void registerGsonTypes(GsonBuilder builder) {
        builder.registerTypeAdapter(PropertyMap.class, new PropertyMap.Serializer());
        try {
            Class<?> clazz = Class.forName(YggdrasilAuthenticationService.class.getName() + "$GameProfileSerializer");
            Constructor<?> cons = clazz.getDeclaredConstructor();
            cons.setAccessible(true);
            Object o = cons.newInstance();
            builder.registerTypeAdapter(GameProfile.class, o);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        builder.registerTypeAdapter(UUID.class, new UUIDTypeAdapter());
    }

    @Override
    public void handleCurrencyChange(int playerId, Currency currency, CurrencyChangeType currencyChangeType) {
        Bukkit.getPluginManager().callEvent(new PlayerCurrencyChangeEvent(playerId, currency, currencyChangeType));

    }
}
