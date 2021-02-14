package dev.feldmann.redstonegang.wire.game.base.addons.server.quests.hooks.rnd;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.Argument;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.IntegerArgument;
import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.QuestInfo;
import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.hooks.QuestContador;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.inventory.ItemStack;

public class QuestEncante extends QuestContador {

    private static IntegerArgument LVL_MINIMO = new IntegerArgument("lvlminimo", 0, 30);

    int lvlminimo;


    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void pickup(EnchantItemEvent ev) {
        int xpcost = ev.getExpLevelCost();
        if (xpcost >= lvlminimo) {
            QuestInfo fa = getFazendo(ev.getEnchanter());
            if (fa != null) {
                fa.faz();
            }
        }
    }

    @Override
    public String toString() {
        String s = "Encante " + contador + " lvl minimo:" + lvlminimo;

        return s;
    }


    @Override
    public ItemStack toItemStack() {
        return new ItemStack(Material.ENCHANTMENT_TABLE);
    }

    @Override
    public String suggestName() {
        return "Encante " + contador + " itens com level minimo " + lvlminimo;
    }

    @Override
    public Argument[] getArgs() {
        return new Argument[]{QUANTIDADE};
    }

    @Override
    public boolean createQuestHook(Player p, Arguments args) {
        this.contador = args.get(QUANTIDADE);
        this.lvlminimo = args.get(LVL_MINIMO);
        return true;
    }
}
