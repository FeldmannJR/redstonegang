package dev.feldmann.redstonegang.wire.game.base.addons.server.quests.hooks.itens;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.Argument;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.RemainItemsArgument;
import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.hooks.QuestContador;

import dev.feldmann.redstonegang.wire.modulos.language.lang.LanguageHelper;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.util.List;

public abstract class QuestAlgoItem extends QuestContador {

    protected static RemainItemsArgument ITEMS = new RemainItemsArgument("items", false);


    public MaterialData[] md;


    public abstract String getNome();


    @Override
    public Argument[] getArgs() {
        return new Argument[]{QUANTIDADE, ITEMS};
    }


    @Override
    public String suggestName() {
        if (md.length == 1) {
            return getNome() + " " + contador + " " + LanguageHelper.getItemName(md[0].toItemStack());
        }
        String quebrar = getNome() + " " + contador + " ";

        for (int x = 0; x < md.length; x++) {
            if (x > 0) {
                quebrar += " ou ";
            }
            quebrar += LanguageHelper.getItemName(md[x].toItemStack());

        }
        return quebrar;
    }

    public boolean isValid(ItemStack it) {

        for (MaterialData md : md) {
            if (it.getType() == md.getItemType() && md.getData() == it.getDurability()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        String s = getNome() + " " + contador + " ";
        for (MaterialData m : md) {
            s += "|" + m.getItemType().getId() + ":" + m.getData();
        }
        return s;
    }

    @Override
    public boolean createQuestHook(Player p, Arguments args) {
        int qts = args.get(QUANTIDADE);

        List<MaterialData> mds = args.get(ITEMS);
        this.contador = qts;
        MaterialData[] ma = new MaterialData[mds.size()];
        for (int x = 0; x < ma.length; x++) {
            ma[x] = mds.get(x);
        }
        this.md = ma;
        return true;
    }
}
