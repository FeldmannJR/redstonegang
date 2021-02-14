package dev.feldmann.redstonegang.wire.modulos.disguise.types.base;

import dev.feldmann.redstonegang.wire.modulos.disguise.DisguiseModule;
import dev.feldmann.redstonegang.wire.utils.nms.NMSVersionImpl;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class DisguiseData {

    UUID uid;
    int playerId;
    Player player;
    protected boolean disguised = false;

    public DisguiseData(Player p) {
        this.player = p;
        this.uid = p.getUniqueId();
        this.playerId = p.getEntityId();

    }

    public List<Player> getSeeing() {
        List<Player> seeing = new ArrayList();
        for (Player pl: Bukkit.getOnlinePlayers()) {
            if (pl != player && pl.getWorld() == player.getWorld()) {
                if (DisguiseModule.nms.isPlayerSeeing(pl, player)) {
                    seeing.add(pl);
                }
            }
        }
        return seeing;

    }

    public NMSVersionImpl nms() {
        return DisguiseModule.nms;

    }

    public boolean isDisguised() {
        return disguised;
    }

    public Player getPlayer() {
        return player;
    }

    public abstract void restore(DisguiseData nextDisguise);

    public abstract void disguise(DisguiseData lastDisguise);

}
