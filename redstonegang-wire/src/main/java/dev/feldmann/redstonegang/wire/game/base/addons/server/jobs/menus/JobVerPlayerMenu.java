package dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.menus;

import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.Job;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.JobPlayer;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.JobsAddon;
import dev.feldmann.redstonegang.wire.modulos.menus.Button;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.utils.items.CItemBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class JobVerPlayerMenu extends Menu {

    private JobsAddon addon;
    private JobPlayer pl;

    public JobVerPlayerMenu(JobsAddon addon, JobPlayer pl) {
        super("Jobs " + pl.getPlayer().getDisplayName(), 3);
        this.pl = pl;
        this.addon = addon;
        addInfos();
    }

    public void addInfos() {

        addBorder();
        removeButton(9);
        removeButton(17);
        add(13, getBorderItemStack());
        for (Job job : addon.getJobs()) {
            addNext(new Button(buildItemStack(job)) {
                @Override
                public void click(Player p, Menu m, ClickType click) {
                    new JobRanksMenu(job).open(p);
                }
            });
        }
    }

    public ItemStack buildItemStack(Job job) {
        int lvl = pl.getLevel(job);
        long xp = pl.getXp(job);
        return CItemBuilder
                .item(job.getIcone())
                .name(job.getNome())
                .desc("Level: %%", lvl)
                .desc("Restante: %%", pl.getRemaining(job))
                .desc("Total XP: %%", xp)
                .build();

    }


}
