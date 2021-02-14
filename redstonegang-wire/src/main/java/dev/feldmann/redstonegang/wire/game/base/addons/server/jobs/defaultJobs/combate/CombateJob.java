package dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.defaultJobs.combate;

import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.Job;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class CombateJob extends Job {

    public CombateJob() {
        super("Guerreiro", "Guerreiro", new ItemStack(Material.IRON_SWORD));
    }

    @Override
    public String getTitulo() {
        return "Guerreiro";
    }

    @Override
    public String getDesc() {
        return null;
    }
}
