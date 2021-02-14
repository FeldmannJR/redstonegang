package dev.feldmann.redstonegang.wire.game.base.addons.server.quests;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.hooks.QuestContador;
import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.hooks.bixos.QuestMataBixo;
import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.hooks.bixos.QuestTosaBixo;
import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.hooks.itens.QuestCraftaItem;
import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.hooks.itens.QuestForjaItem;
import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.hooks.itens.QuestPesque;
import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.hooks.itens.QuestQuebraBloco;
import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.hooks.rnd.*;
import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.hooks.rnd.*;
import org.bukkit.entity.Player;

public enum Hooks {
    Quebrar(QuestQuebraBloco.class),
    Matar(QuestMataBixo.class),
    Craftar(QuestCraftaItem.class),
    Cozinhar(QuestForjaItem.class),
    Pocao(QuestFazPocao.class),
    //Captura(QuestCapturaBixo.class, ci),
    Tosa(QuestTosaBixo.class),
    Pesque(QuestPesque.class),
    Projetil(QuestLancaProjetil.class),
    Exp(QuestPegaXp.class),
    Encantar(QuestEncante.class),
    Criar(QuestCriar.class);


    Class<? extends QuestHook> classe;
    Class<? extends QuestInfo> questinfo;

    Hooks(Class<? extends QuestHook> questclass) {
        this(questclass, null);
    }

    Hooks(Class<? extends QuestHook> questclass, Class<? extends QuestInfo> infoclass) {
        this.classe = questclass;
        if (infoclass == null) {
            this.questinfo = QuestContador.QuestContadorInfo.class;
        } else {
            this.questinfo = infoclass;
        }
    }

    public QuestHook create(Player p, Arguments args) {
        try {
            QuestHook qh = classe.newInstance();
            if (qh.createQuestHook(p, args)) {
                return qh;
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;

    }

    public QuestHook createHook() {
        try {
            return classe.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Class<? extends QuestHook> getQuest() {
        return classe;
    }

    public Class<? extends QuestInfo> getQuestinfo() {
        return questinfo;
    }

    public static Hooks getHook(Class<? extends QuestHook> classe) {
        for (Hooks h : Hooks.values()) {
            if (h.getQuest() == classe) {
                return h;
            }
        }

        return null;
    }
}
