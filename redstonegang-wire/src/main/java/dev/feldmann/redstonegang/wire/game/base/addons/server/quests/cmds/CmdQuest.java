package dev.feldmann.redstonegang.wire.game.base.addons.server.quests.cmds;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.types.PlayerCmd;
import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.QuestAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.QuestInfo;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.entity.Player;

import java.util.List;

public class CmdQuest extends PlayerCmd {
    private QuestAddon addon;

    public CmdQuest(QuestAddon addon) {
        super("missao", "Vê qual é sua missão diaria");
        this.addon = addon;
        setAlias("quest");
    }


    @Override
    public void command(Player player, Arguments args) {
        List<QuestInfo> infos = addon.getManager().getQuestPlayer(player).getInfos();
        for (QuestInfo info : infos) {
            if (info.isTodayDaily()) {

                C.info(player, "Sua missão diaria é %% !", info.getQuest().nome);
                if (!info.isCompleta()) {
                    C.info(player, " Progresso atual: " + info.getStatus());
                } else {
                    C.info(player, " Já completada :D !");
                }
                return;
            }
        }
        C.error(player, "Você não tem uma quest diaria relogue para pegar uma !");
    }
}
