package dev.feldmann.redstonegang.wire.game.base.addons.server.quests.hooks.rnd;


import dev.feldmann.redstonegang.wire.base.cmds.arguments.Argument;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.QuestInfo;
import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.hooks.QuestContador;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.inventory.ItemStack;

public class QuestPegaXp extends QuestContador {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void pickup(PlayerExpChangeEvent ev) {
        int a = ev.getAmount();
        if (a > 0) {
            QuestInfo info = getFazendo(ev.getPlayer());
            if (info != null) {
                info.faz(a);
            }
        }
    }

    @Override
    public ItemStack toItemStack() {
        return new ItemStack(Material.EXP_BOTTLE);
    }

    @Override
    public String suggestName() {
        return "Colete " + contador + " XP";
    }

    @Override
    public Argument[] getArgs() {
        return new Argument[]{QUANTIDADE};
    }

    @Override
    public String toString() {
        return contador + " xp";
    }

    @Override
    public boolean createQuestHook(Player p, Arguments args) {
        this.contador = args.get(QUANTIDADE);
        return true;
    }
}
