package dev.feldmann.redstonegang.wire.game.base.addons.server.quests.hooks;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.IntegerArgument;
import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.QuestHook;
import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.QuestInfo;


public abstract class QuestContador extends QuestHook {
    protected static IntegerArgument QUANTIDADE = new IntegerArgument("quantidade", false, 1, Integer.MAX_VALUE);

    public int contador;


    public static class QuestContadorInfo extends QuestInfo {

        int quantos;

        @Override
        public void faz(int x) {
            quantos += x;
            QuestContador kill = (QuestContador) getQuest().getHook();
            if (quantos >= kill.contador) {
                quantos = kill.contador;
                finishQuest();
                return;

            }
            sendMsg();
            saveDB();
        }

        @Override
        public String getStatus() {
            QuestContador kill = (QuestContador) getQuest().getHook();
            return "§c" + quantos + "§f/§a" + kill.contador;

        }
    }
}
