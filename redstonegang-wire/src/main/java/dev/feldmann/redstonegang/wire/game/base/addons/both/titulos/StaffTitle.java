package dev.feldmann.redstonegang.wire.game.base.addons.both.titulos;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.sql.Timestamp;

public class StaffTitle extends Titulo {
    public StaffTitle(int owner) {
        super(owner, "staff_title");
        int x = -100;
        for (ChatColor c : ChatColor.values()) {
            if (c.isColor()) {
                this.cor.put(c.toString(), new TituloCor(x, this, c.toString(), new Timestamp(System.currentTimeMillis())));
                x--;
                this.cor.put(c.toString()+ChatColor.BOLD, new TituloCor(x, this, c.toString() + ChatColor.BOLD, new Timestamp(System.currentTimeMillis())));
                x--;

            }
        }
    }


    @Override
    public String getProcessedName() {
        return "STAFF";
    }

    @Override
    public void addCor(TituloCor cor) {

    }

    @Override
    public Material getMaterial() {
        return Material.SPONGE;
    }

    @Override
    public boolean isVirtual() {
        return true;
    }
}
