package dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.menus;

import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.Job;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.perks.JobPerk;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.modulos.menus.buttons.DummyButton;
import dev.feldmann.redstonegang.wire.utils.items.CItemBuilder;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class JobPerksMenu extends Menu {

    private Job job;

    public JobPerksMenu(Job job) {
        super("Bonus " + job.getNome(), Menu.calcSize(job.getPerks().size()));
        this.job = job;
        addVantagens();
    }

    public void addVantagens() {
        List<JobPerk> ordered = new ArrayList<>(job.getPerks());
        ordered.sort(Comparator.comparingInt(j -> j.getLevel()));
        for (JobPerk perk : ordered) {
            addNext(new DummyButton(buildItem(perk)));
        }
    }


    public ItemStack buildItem(JobPerk perk) {
        return CItemBuilder.item(perk.getItemStack())
                .name(perk.getNome())
                .desc("Level: %%", perk.getLevel())
                .build();

    }
}
