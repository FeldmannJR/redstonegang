package dev.feldmann.redstonegang.wire.modulos.disguise;

import dev.feldmann.redstonegang.wire.modulos.disguise.types.base.DisguiseData;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class DisguiseAPI {

    private static HashMap<UUID, DisguiseData> disguises = new HashMap();

    public static DisguiseData getDisguise(Player p) {
        return getDisguise(p.getUniqueId());

    }

    public static void setDisguise(Player p, DisguiseData data) {
        DisguiseData datao = disguises.remove(p.getUniqueId());
        if (datao != null) {
            datao.restore(data);
        }
        if (data != null) {
            disguises.put(p.getUniqueId(), data);
            data.disguise(datao);
        }
    }

    public static DisguiseData getDisguise(UUID uid) {
        if (disguises.containsKey(uid)) {
            return disguises.get(uid);
        }
        return null;
    }

    public static void removeDisguise(UUID uid) {
        disguises.remove(uid);
    }
}
