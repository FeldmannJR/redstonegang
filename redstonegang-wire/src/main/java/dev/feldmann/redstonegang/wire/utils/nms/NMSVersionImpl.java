package dev.feldmann.redstonegang.wire.utils.nms;

import com.mojang.authlib.GameProfile;
import dev.feldmann.redstonegang.wire.modulos.disguise.DisguiseWatcher;
import dev.feldmann.redstonegang.wire.modulos.disguise.types.base.DisguiseData;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

//Reflections é um saco melhor criar uma versão pra cada
public abstract class NMSVersionImpl {

    public abstract void sendPacket(Player p, Object packet);

    public abstract Object buildSpawnPacket(Player p, DisguiseData data);

    public abstract Object buildSpawnPlayer(Player p);

    public abstract Object buildDestroy(int... entityid);

    public abstract Object buildEquipment(int entityid, int slot, ItemStack item);

    public abstract Object buildMetadata(int entityid, DisguiseWatcher watcher);

    public abstract Object buildMount(int vehicle, int passanger);

    public abstract Object buildSpawnPacket(int id, EntityType type, Location l, DisguiseWatcher watcher);

    public abstract Object buildEntityStatus(int entityId, byte status);
    /*
     * Is @player seeing @p2?
     *
     * */

    public abstract boolean isPlayerSeeing(Player player, Entity p2);

    public abstract Object convertToNmsDatawatcher(DisguiseWatcher watcher, boolean all);

    public abstract DisguiseWatcher convertFromNmsDatawatcher(Object watcher);

    public int n(double d) {
        return (int) Math.floor(d * 32);
    }

    public int v(double f) {
        return (int) (8000D * Math.max(-3.9F, Math.min(f, 3.9D)));

    }

    public byte m(float f) {
        return (byte) d(f * 256F / 360F);
    }

    public static int d(float var0) {
        int var1 = (int) var0;
        return var0 < (float) var1 ? var1 - 1 : var1;
    }

    public abstract void reloadPlayer(Player p);

    public abstract Object removeFromTabList(UUID uid, String name);

    public abstract void addToTabList(Player p);

    public abstract GameProfile getGameProfile(Player p);
}
