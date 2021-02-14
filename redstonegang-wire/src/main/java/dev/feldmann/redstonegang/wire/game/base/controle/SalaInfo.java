package dev.feldmann.redstonegang.wire.game.base.controle;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SalaInfo {
    SalaType type = SalaType.OFICIAL;
    /**
     * Id dos mods no DB
     */

    List<Integer> mods = new ArrayList<>();
    /**
     * Id do dono no DB
     */
    Integer owner = null;
    /*
     * Whitelist da sala somente quem está nessa lista ou é mod pode entrar
     * */
    List<Integer> whitelist = new ArrayList<>();


    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public void setType(SalaType type) {
        this.type = type;
    }

    public Integer getOwner() {
        return owner;
    }

    public SalaType getType() {
        return type;
    }

    public boolean isMod(int id) {
        return owner == id || mods.contains(id);
    }

    public boolean isOwner(int id) {
        return owner == id;
    }

    public boolean isMod(Player p) {
        return false;
    }

    public boolean isWhitelisted(Integer i) {
        return whitelist.contains(i);
    }
}
