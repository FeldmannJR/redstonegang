package dev.feldmann.redstonegang.wire.game.base.addons.server.quests;

import java.util.ArrayList;
import java.util.List;

public class QuestPlayer {
    List<QuestInfo> infos = new ArrayList();


    public List<QuestInfo> getInfos() {
        return infos;
    }

    public List<QuestInfo> getActive(Hooks hook) {
        List<QuestInfo> infos = new ArrayList<>();

        for (QuestInfo info : getInfos()) {
            Quest q = info.getQuest();
            if (q != null) {
                if (q.hook.getClass() == hook.getQuest()) {
                    if (info.isAtiva() && !info.isCompleta()) {
                        infos.add(info);
                    }
                }
            }
        }
        return infos;
    }
}
