package dev.feldmann.redstonegang.wire.game.base.addons.server.land;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.common.player.permissions.PermissionValue;

import dev.feldmann.redstonegang.wire.RedstoneGangSpigot;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.properties.PlayerProperties;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.properties.PlayerProperty;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.properties.LandFlagStorage;
import dev.feldmann.redstonegang.wire.utils.hitboxes.Hitbox2D;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.sql.Timestamp;
import java.util.HashMap;

public class Land {
    public static int lastId = 1;
    private int id;
    //0 == Servidor
    private int ownerId;
    //Id de quem ta alugando o terreno
    private int rentId;
    //Quando acaba a permissão do usuario
    private Timestamp finalRent;
    //Região
    private Hitbox2D region;
    //Mundo
    private String world;
    //Properties
    LandFlagStorage flags;

    //PerPlayerProperties
    HashMap<Integer, PlayerProperties> playerProps = null;

    private LandAddon manager;

    public Land(LandAddon manager, int id, int ownerId, Hitbox2D region, String world, LandFlagStorage props) {
        this.id = id;
        this.ownerId = ownerId;
        this.region = region;
        this.flags = props;
        this.world = world;
        this.manager = manager;
    }

    public boolean isInside(Location loc) {
        return region.isInside(loc.getBlockX(), loc.getBlockZ());
    }

    public Hitbox2D getRegion() {
        return region;
    }

    public LandFlagStorage getFlags() {
        return flags;
    }

    public int getRentId() {
        return rentId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    protected void setId(int id) {
        this.id = id;

    }

    public void setRegion(Hitbox2D region) {
        this.region = region;
        manager.getDB().updateRegion(this);
    }

    public void saveProperties() {
        if (this.getId() != -1) {
            manager.getDB().saveProperties(this);
        }
    }

    public User getOwner() {
        return RedstoneGangSpigot.getPlayer(getOwnerId());
    }

    public boolean playerCan(Player p, PlayerProperty prop) {
        int pid = RedstoneGang.getPlayer(p.getUniqueId()).getId();
        if (hasPlayerProperty(pid)) {
            PermissionValue val = getPlayerProperty(pid).get(prop);
            if (val != PermissionValue.NONE) {
                return val == PermissionValue.ALLOW;
            }
        }
        return false;
    }

    public void removePlayerPropery(int pId) {
        loadPlayerPropsIfNotLoaded();
        PlayerProperties remove = playerProps.remove(pId);
        if (remove != null) {
            manager.a(LandAddon.class).getDB().deletePlayerProperty(remove);
        }
    }

    public boolean hasPlayerProperty(int pId) {
        loadPlayerPropsIfNotLoaded();
        return playerProps.containsKey(pId);
    }

    public PlayerProperties getPlayerProperty(int pId) {
        loadPlayerPropsIfNotLoaded();
        if (!playerProps.containsKey(pId)) {
            PlayerProperties props = new PlayerProperties(manager,pId, getId(), false);
            playerProps.put(pId, props);
            manager.a(LandAddon.class).getDB().savePlayerProperty(props);
        }
        return playerProps.get(pId);
    }

    public World getWorld() {
        return Bukkit.getWorld(world);
    }

    public String getWorldName() {
        return world;
    }

    public int getId() {
        return id;
    }

    public void loadPlayerPropsIfNotLoaded() {
        if (playerProps == null)
            playerProps = manager.getDB().loadProperties(this);

    }

    public HashMap<Integer, PlayerProperties> getPlayerProps() {
        loadPlayerPropsIfNotLoaded();
        return playerProps;
    }
}
