package dev.feldmann.redstonegang.wire.modulos.skins;

import dev.feldmann.redstonegang.common.player.Skin;
import dev.feldmann.redstonegang.wire.modulos.disguise.DisguiseModule;
import dev.feldmann.redstonegang.wire.utils.nms.NMS;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerSkin {


    UUID uuid;
    String name;

    GameProfile cache;
    GameProfile selfCache;

    public String disguiseName = null;
    private UUID randUUID = null;
    private Skin s;


    public PlayerSkin(UUID uuid, String name, String disguiseName, Skin s) {
        this.uuid = uuid;
        this.name = name;
        this.disguiseName = disguiseName;
        if (disguiseName != null)
            this.randUUID = UUID.randomUUID();
        this.s = s;
    }

    public UUID getRandUUID() {
        return randUUID;
    }

    private PropertyMap getSkin() {
        PropertyMap map = new PropertyMap();
        map.put("textures", new Property("textures", s.getTexture(), s.getSignature()));
        return map;
    }

    public void remove(boolean next) {
        removeFromTab(true, this);
        if (!next) {
            NMS.current.addToTabList(getPlayer());
            respawn();
        }
    }

    public void set(PlayerSkin prev) {
        if (prev == null) {
            removeFromTab(false, this);
        }
        NMS.current.addToTabList(getPlayer());
        respawn();
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }


    public void removeFromTab(boolean check, PlayerSkin prev) {
        Object self = NMS.current.removeFromTabList(uuid,disguiseName != null ? disguiseName : name);
        Object other;

        if (!check) {
            other = self;
        } else {
            if (prev != null && prev.disguiseName != null) {
                other = NMS.current.removeFromTabList(prev.randUUID, disguiseName != null ? disguiseName : name);
            } else {
                other = self;
            }
        }

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getUniqueId().equals(uuid)) {
                NMS.current.sendPacket(p, self);
            } else {
                NMS.current.sendPacket(p, other);
            }
        }
    }


    public void respawn() {
        Object destroy = NMS.current.buildDestroy(getPlayer().getEntityId());
        Object spawn = NMS.current.buildSpawnPlayer(getPlayer());
        for (Player p : getSeeing()) {
            NMS.current.sendPacket(p, destroy);
            NMS.current.sendPacket(p, spawn);
        }
        NMS.current.reloadPlayer(getPlayer());
    }

    public List<Player> getSeeing() {
        List<Player> seeing = new ArrayList();
        for (Player pl : Bukkit.getOnlinePlayers()) {
            if (pl != getPlayer() && pl.getWorld() == getPlayer().getWorld()) {
                if (DisguiseModule.nms.isPlayerSeeing(pl, getPlayer())) {
                    seeing.add(pl);
                }
            }
        }
        return seeing;

    }

    public GameProfile create(boolean self) {
        if (!self) {
            if (cache != null) {
                return cache;
            }
        } else {
            if (selfCache != null) {
                return selfCache;
            }
        }
        String name = this.disguiseName != null ? this.disguiseName : this.name;
        UUID uuid = this.randUUID != null && !self ? this.randUUID : this.uuid;
        GameProfile profile = new GameProfile(uuid, name);

        try {
            Field fProperties = profile.getClass().getDeclaredField("properties");
            fProperties.setAccessible(true);
            try {

                fProperties.set(profile, getSkin());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        if (!self) {
            cache = profile;
        } else {
            selfCache = profile;
        }

        return profile;
    }
}
