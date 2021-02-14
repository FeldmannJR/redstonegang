package dev.feldmann.redstonegang.wire.game.base.addons.server.quests;

import dev.feldmann.redstonegang.common.utils.RandomUtils;
import dev.feldmann.redstonegang.wire.utils.player.Title;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.jooq.SortField;
import org.jooq.impl.DSL;
import org.jooq.types.DayToSecond;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static dev.feldmann.redstonegang.wire.game.base.addons.server.quests.db.Tables.*;

public class DailyQuests {


    private QuestManager manager;
    private Random rnd;

    public DailyQuests(QuestManager manager) {
        this.manager = manager;
        rnd = new Random();
    }


    public void addDailyQuest(Player p) {
        final int playerId = manager.getPlayerId(p);
        manager.getAddon().runAsync(() -> {
            if (!hasDailyQuest(playerId)) {
                final int randomQuestId = randomQuestForPlayer(playerId);
                final boolean streak = shouldBeNewStreak(p);
                manager.getAddon().runSync(() -> {
                    QuestInfo info = manager.createInfo(manager.getQuestById(randomQuestId), playerId);
                    info.setNewstreak(streak);
                    info.daily = true;
                    manager.addQuestToPlayer(manager.getAddon().getUser(playerId), info);
                    sendNewQuestMessage(p, randomQuestId, info);
                });
            }
        });

    }


    public void sendNewQuestMessage(Player player, int questId, QuestInfo status) {
        int playerId = manager.getAddon().getPlayerId(player);
        TextComponent tx = new TextComponent();
        Quest q = manager.getQuestById(questId);
        tx.setText("§9[Missão] §aVocê recebeu a missão diária do dia §f" + getDia(playerId) + "º §a!");
        tx.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[]{new TextComponent("§b" + q.nome + "\n" + status.getStatus())}));
        Title.sendTitle(player, "§aNova Missão", "§7Fale com o " + QuestAddon.nomenpc + " no spawn", 20, 60, 20);
        player.spigot().sendMessage(tx);
        player.playSound(player.getLocation(), Sound.CHEST_OPEN, 1, 1);
    }

    public boolean hasDailyQuest(int playerId) {
        // " and DATE(`terminar`) = CURDATE() and daily =1"
        QuestPlayer load = manager.getDB().load(playerId, DSL.date(QUEST_INFOS.COMECOU).eq(DSL.currentDate()));
        boolean e = load.infos.isEmpty();
        return !e;
    }

    /*
     * @return 0  se não tem streek
     * @return 6 se ta no ultimo
     * */
    public QuestInfo[] getLast7(int playerId) {
        QuestInfo[] difs = new QuestInfo[7];
        //and DATE(terminar)< DATE(NOW()) and DATE(NOW() - INTERVAL 7 DAY) <= DATE(terminar) and completa = 1 and daily = 1 ORDER BY terminar DESC LIMIT 7
        QuestPlayer p = manager.getDB().load(playerId,
                DSL.date(QUEST_INFOS.TERMINAR).lessThan(DSL.currentDate())
                        .and(DSL.currentDate().sub(new DayToSecond(7)).lessOrEqual(DSL.date(QUEST_INFOS.TERMINAR)))
                        .and(QUEST_INFOS.COMPLETA.eq(true))
                        .and(QUEST_INFOS.DAILY.eq(true)), new SortField[]{QUEST_INFOS.TERMINAR.desc()}, 7
        );
        int combo = 0;
        long lastday = 0L;

        Calendar agora = Calendar.getInstance();
        agora.setTime(new Date(System.currentTimeMillis()));
        Calendar termina = Calendar.getInstance();
        termina.set(agora.get(Calendar.YEAR), agora.get(Calendar.MONTH), agora.get(Calendar.DATE), 23, 59, 59);
        long hoje = termina.getTime().getTime();

        for (QuestInfo info : p.getInfos()) {
            long terminadia = info.getTerminar().getTime();
            long dif = hoje - terminadia + 1L;

            int dias = (int) TimeUnit.MILLISECONDS.toDays(dif);
            dias = 7
                    - dias;
            difs[dias] = info;
        }

        return difs;
    }


    public int getDia(int playerId) {
        int dia = 1;
        for (QuestInfo info : getCurrentStreak(playerId)) {
            if (info != null && info.isTodayDaily()) {
                return dia;
            }
            dia++;
        }
        return 1;
    }


    public List<QuestInfo> getCurrentStreak(int playerId) {
        QuestInfo q = getTodayQuest(playerId);
        List<QuestInfo> info = new ArrayList();

        QuestInfo[] last = getLast7(playerId);

        int laststreak = -1;
        for (int x = 6; x >= 0; x--) {
            if ((last[x] != null) &&
                    (last[x].isNewstreak())) {
                laststreak = x;
                break;
            }
        }

        if (laststreak == -1) {
            info.add(q);
            return info;
        }

        int feitas = 0;
        for (int x = laststreak; x < 7; x++) {
            if ((last[x] != null) &&
                    (last[x] != q) &&
                    (last[x].isCompleta())) {
                feitas++;
            }
        }

        if (feitas >= 7) {
            info.add(q);

            return info;
        }
        for (int x = laststreak; x < 7; x++) {
            if (last[x] == null) {
                info.add(q);
                return info;
            }
        }

        boolean daily = false;

        for (int x = laststreak; x < 7; x++) {
            info.add(last[x]);
            if (last[x].equals(q)) {
                daily = true;
            }
        }
        if (!daily) {
            info.add(q);
        }

        return info;
    }

    public QuestInfo getTodayQuest(int playerId) {
        //"and DATE(terminou) = DATE(now()) and daily = 1"
        List<QuestInfo> i = manager.getDB()
                .load(playerId, DSL.date(QUEST_INFOS.TERMINAR).eq(DSL.currentDate()).and(QUEST_INFOS.DAILY.eq(true))).getInfos();
        if (!i.isEmpty()) return i.get(0);
        return null;
    }

    public boolean shouldBeNewStreak(Player p) {
        UUID uid = p.getUniqueId();

        QuestInfo[] last = getLast7(manager.getPlayerId(p));

        int laststreak = -1;
        for (int x = 6; x >= 0; x--) {
            if ((last[x] != null) &&
                    (last[x].isNewstreak())) {
                laststreak = x;
                break;
            }
        }

        if (laststreak == -1) {
            return true;
        }
        for (int x = laststreak; x < 7; x++) {
            if (last[x] == null) {
                return true;
            }
        }

        return laststreak == 0;
    }


    public int randomQuestForPlayer(int playerId) {
        int repete = 20;
        List<Integer> pode = new ArrayList<>();
        List<Integer> foi = manager.getDB().getLast(playerId, repete);
        for (Quest q : manager.getQuests()) {
            if (!foi.contains(q.id)) {
                pode.add(q.id);
            }
        }

        if (pode.isEmpty()) {
            for (Quest q : manager.getQuests()) {
                pode.add(q.id);
            }
        }
        return RandomUtils.getRandom(pode);
    }


}
