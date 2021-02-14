package dev.feldmann.redstonegang.wire.modulos.permissions;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.common.player.Skin;
import dev.feldmann.redstonegang.wire.RedstoneGangSpigot;
import dev.feldmann.redstonegang.wire.base.modulos.Modulo;
import dev.feldmann.redstonegang.wire.modulos.skins.CustomSkin;
import dev.feldmann.redstonegang.wire.permissions.PermissionBaseRG;
import dev.feldmann.redstonegang.wire.utils.Reflections;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

public class PlayerLoader extends Modulo implements Listener {
    private PermissibleInjector i;

    @Override
    public void onEnable() {
        register(this);
        i = new PermissibleInjector.ClassPresencePermissibleInjector(Reflections.getCBClassName("entity.CraftHumanEntity"), "perm", true);
    }

    @Override
    public void onDisable() {

    }

    @EventHandler
    public void preJoin(AsyncPlayerPreLoginEvent ev) {
        //Removendo o cache
        RedstoneGang.instance.user().cache.clearCache(ev.getUniqueId());
        //Puxando dnv no banco

        User rgplayer = RedstoneGangSpigot.getPlayer(ev.getUniqueId());
        //Se não ta cadastrado kicka
        if (rgplayer == null) {
            ev.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, "Você não está cadastrado nos ervidor!");
            return;
        }
        if (rgplayer.getDisguiseName() != null) {
            Skin s = CustomSkin.getSkin(rgplayer.getDisguiseName());
            CustomSkin.putWithoutReload(ev.getUniqueId(), ev.getName(), rgplayer.getDisguiseName(), s);
        }
        if (RedstoneGang.instance().DEV && !RedstoneGang.instance().ENVIRONMENT.equals("local")) {
            if (!rgplayer.permissions().hasPermission("redstonegang.developer")) {
                ev.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, "Servidor em modo de desenvolvimento!");
                return;
            }
        }
    }

    @EventHandler
    public void join(PlayerJoinEvent ev) {
        ev.setJoinMessage(null);
        User pl = RedstoneGangSpigot.getPlayer(ev.getPlayer());
        if (pl.getDisguiseName() != null) {
        }

    }

    @EventHandler
    public void quit(PlayerQuitEvent ev) {
        ev.setQuitMessage(null);
    }

    private void inject(Player p) {
        PermissionBaseRG base = new PermissionBaseRG(p);
        try {
            i.inject(p, base);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void join(PlayerLoginEvent ev) {
        inject(ev.getPlayer());
        User rgplayer = RedstoneGangSpigot.getPlayer(ev.getPlayer());
        if (rgplayer.getDisguiseName() != null) {
            ev.getPlayer().setDisplayName(rgplayer.getDisplayName());
        }
    }

}
