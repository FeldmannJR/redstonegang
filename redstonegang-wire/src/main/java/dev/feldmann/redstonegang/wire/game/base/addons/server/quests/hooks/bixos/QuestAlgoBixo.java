package dev.feldmann.redstonegang.wire.game.base.addons.server.quests.hooks.bixos;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.Argument;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.RemainEntityTypeArgument;
import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.hooks.QuestContador;
import dev.feldmann.redstonegang.wire.modulos.language.lang.LanguageHelper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class QuestAlgoBixo extends QuestContador {
    protected static RemainEntityTypeArgument BIXOS = new RemainEntityTypeArgument("nomebixos", false);


    public EntityType[] ents;


    @Override
    public Argument[] getArgs() {
        return new Argument[]{QUANTIDADE, BIXOS};
    }


    @Override
    public boolean createQuestHook(Player p, Arguments args) {

        List<EntityType> ts = args.get(BIXOS);

        EntityType[] ents = new EntityType[ts.size()];
        for (int x = 0; x < ents.length; x++) {
            ents[x] = ts.get(x);
        }
        this.ents = ents;
        this.contador = args.get(QUANTIDADE);
        return true;
    }


    public abstract String getNome();

    @Override
    public String toString() {
        String s = getNome() + " " + contador + " ";
        for (EntityType t : ents) {
            s += t.name() + " ";
        }
        return s;
    }

    @Override
    public String suggestName() {
        if (ents.length == 1) {
            return getNome() + " " + contador + " " + LanguageHelper.getEntityName(ents[0]);
        }
        String quebrar = getNome() + " " + contador + " ";

        for (int x = 0; x < ents.length; x++) {
            if (x > 0) {
                quebrar += " ou ";
            }
            quebrar += LanguageHelper.getEntityName(ents[x]);

        }
        return quebrar;
    }


}
