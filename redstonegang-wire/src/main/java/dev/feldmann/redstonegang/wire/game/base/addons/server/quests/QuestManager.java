package dev.feldmann.redstonegang.wire.game.base.addons.server.quests;

import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.hooks.itens.QuestQuebraBloco;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

import java.sql.Timestamp;
import java.util.*;

public class QuestManager {
    private QuestAddon addon;
    private DailyQuests daily;

    private HashMap<Integer, QuestPlayer> cache = new HashMap<>();
    private HashMap<Integer, Quest> loadedQuests = null;
    private HashMap<Integer, Integer> cachefeitas = new HashMap<>();


    public QuestManager(QuestAddon addon) {
        this.addon = addon;
    }

    public void clearCache(User user) {
        cache.remove(user.getId());
        cachefeitas.remove(user.getId());
    }

    public void onEnable() {
        daily = new DailyQuests(this);
        loadQuests();
    }


    public QuestInfo createInfo(Quest q, int playerId) {
        Hooks h = Hooks.getHook(q.getHook().getClass());
        try {
            QuestInfo questInfo = h.getQuestinfo().newInstance();
            questInfo.setPlayerId(playerId);
            questInfo.setQuest(q.id);
            questInfo.setComecou(new Timestamp(System.currentTimeMillis()));
            questInfo.setCompleta(false);
            questInfo.setManager(this);
            return questInfo;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getQuestsCompleatedBy(int playerId) {
        if (!cachefeitas.containsKey(playerId)) {
            int f = getDB().countDone(playerId);
            cachefeitas.put(playerId, f);
        }
        return cachefeitas.get(playerId);

    }

    public void loadQuests() {
        loadedQuests = new HashMap<>();
        for (Quest q : getDB().loadQuests()) {
            loadedQuests.put(q.id, q);
            addon.registerQuest(q);
        }
        if (loadedQuests.isEmpty()) {
            createDefaults();
        }
    }

    public Collection<Quest> getQuests() {
        return loadedQuests.values();
    }

    public void addQuest(Quest q) {
        getDB().saveQuest(q);
        q.hook.questid = q.id;
        loadedQuests.put(q.id, q);
        addon.registerQuest(q);

    }

    public void createDefaults() {
        QuestQuebraBloco dirt = new QuestQuebraBloco();
        dirt.md = new MaterialData[]{new MaterialData(Material.DIRT), new MaterialData(Material.GRASS)};
        dirt.contador = 30;
        Quest q = new Quest();
        q.nome = "Quebrar 30 terras";
        q.desc = new String[]{"Quebre 30 terras"};
        q.hook = dirt;
        q.hook.setManager(this);
        addQuest(q);
    }


    public QuestPlayer getQuestPlayer(Player p) {
        return getQuestPlayer(addon.getPlayerId(p));
    }

    public QuestPlayer getQuestPlayer(int pid) {
        if (!cache.containsKey(pid)) {
            cache.put(pid, getDB().loadAtivas(pid));
        }
        return cache.get(pid);
    }

    public void addQuestToPlayer(User user, QuestInfo info) {

        Calendar agora = Calendar.getInstance();
        agora.setTime(new Date(System.currentTimeMillis()));
        Calendar termina = Calendar.getInstance();
        termina.set(agora.get(Calendar.YEAR), agora.get(Calendar.MONTH), agora.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        info.terminar = new Timestamp(termina.getTime().getTime());
        getQuestPlayer(user.getId()).infos.add(info);
        save(info);

    }

    public Quest getQuestById(int id) {
        for (Quest q : getQuests()) {
            if (q.id == id) {
                return q;
            }
        }
        return null;
    }


    public int getRecompensa(int dia) {
        if (dia < 1 || dia > 7) {
            return 1;
        }
        if (dia == 7) {
            return 15;
        }
        return dia;
    }

    public int giveReward(int playerId) {
        int dia = daily.getDia(playerId);
        int coins = getRecompensa(dia);
        addon.getCurrency().add(playerId, coins);
        return coins;
    }

    public void deleteQuest(Quest q) {
        getDB().deleteQuest(q);
        loadedQuests.remove(q.id);
        addon.unregisterQuest(q);
        for (QuestPlayer p : cache.values()) {
            for (QuestInfo info : new ArrayList<>(p.getInfos())) {
                if (info.quest == q.id) {
                    if (info.isCompleta()) {
                        info.fail = true;
                        info.quest = 0;
                    } else {
                        p.infos.remove(info);
                    }
                }
            }
        }

    }

    public void save(QuestInfo questInfo) {
        getDB().saveQuestInfo(questInfo);
    }

    public QuestDB getDB() {
        return addon.getDb();
    }

    public int getPlayerId(Player player) {
        return addon.getPlayerId(player);
    }

    public QuestAddon getAddon() {
        return addon;
    }

    public DailyQuests getDaily() {
        return daily;
    }


}
