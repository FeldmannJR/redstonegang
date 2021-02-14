package dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.menus;

import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.wire.RedstoneGangSpigot;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.Job;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.ranks.JobRankInfo;
import dev.feldmann.redstonegang.wire.modulos.menus.Button;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.utils.items.CItemBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class JobRanksMenu extends Menu {

    private Job job;

    public JobRanksMenu(Job job) {
        super("Top " + job.getNome(), 6);
        this.job = job;
        addTops();

    }

    public void addTops() {
        JobRankInfo info = job.getAddon().getRanks().getInfo(job);
        HashMap<Integer, Long> top = info.getTop();
        List<Integer> ordered = new ArrayList<>(top.keySet());
        ordered.sort(Comparator.comparingLong((i) -> top.get(i)).reversed());

        int pos = 1;
        for (Integer pId : ordered) {
            User pl = RedstoneGangSpigot.getPlayer(pId);
            addNext(new Button(CItemBuilder.head(pl.getDisplayName()).name(pos + " - " + pl.getDisplayName()).desc("Level: %%", job.getLevel(top.get(pId))).desc("Total XP: %%", top.get(pId)).build()) {
                @Override
                public void click(Player p, Menu m, ClickType click) {
                    job.getAddon().openPlayer(p, pl);
                }
            });
            pos++;
        }
    }
}
