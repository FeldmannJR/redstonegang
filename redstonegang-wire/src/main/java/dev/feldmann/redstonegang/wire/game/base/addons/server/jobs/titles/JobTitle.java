package dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.titles;

import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.Job;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.JobsAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.titulos.Titulo;
import dev.feldmann.redstonegang.wire.game.base.addons.both.titulos.TituloCor;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;

public class JobTitle extends Titulo {

    private static final int TITULO_ID = -5000;


    Job job;

    public JobTitle(Job job, int owner, int y) {
        super(owner, "job_" + job.getDbId());
        this.job = job;

        int x = y * 50;
        int lvl = getLevel();
        for (Integer t : JobsAddon.coresTitulos.keySet()) {
            if (lvl >= t) {
                ChatColor c = JobsAddon.coresTitulos.get(t);
                TituloCor tcor = new TituloCor(TITULO_ID - x, this, c.toString(), null);
                tcor.setDesc("Obtido ao atingir level " + t + " no job " + job.getNome() + ".");
                this.cor.put(c.toString(), tcor);
            }
            x++;
        }

    }

    public int getLevel() {
        return job.getAddon().getJobPlayer(getOwner()).getLevel(job);
    }

    @Override
    public String getProcessedName() {
        return job.getTitulo() + "  Level " + getLevel();
    }

    @Override
    public void addCor(TituloCor cor) {

    }

    @Override
    public Material getMaterial() {
        return job.getIcone().getType();
    }

    @Override
    public boolean isVirtual() {
        return true;
    }
}
