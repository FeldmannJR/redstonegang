package dev.feldmann.redstonegang.wire.game.base.addons.server.quests;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.Argument;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.game.base.objects.BaseListener;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class QuestHook extends BaseListener {


    public int questid;
    transient QuestManager manager;

    public void setManager(QuestManager manager) {
        this.manager = manager;
    }

    public QuestInfo getFazendo(Player p) {
        for (QuestInfo q : manager.getQuestPlayer(p).getActive(Hooks.getHook(this.getClass()))) {
            if (q.getQuest() != null) {
                if (q.getQuest().id == questid) {
                    return q;
                }
            }
        }
        return null;
    }

    public abstract ItemStack toItemStack();

    public abstract String suggestName();

    public abstract Argument[] getArgs();

    public abstract String toString();

    public abstract boolean createQuestHook(Player p, Arguments args);
}
