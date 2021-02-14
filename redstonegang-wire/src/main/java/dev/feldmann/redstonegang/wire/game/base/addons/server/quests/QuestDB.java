package dev.feldmann.redstonegang.wire.game.base.addons.server.quests;

import dev.feldmann.redstonegang.common.db.Database;
import dev.feldmann.redstonegang.wire.game.base.objects.BaseListener;
import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.db.tables.records.QuestInfosRecord;
import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.db.tables.records.QuestsRecord;
import dev.feldmann.redstonegang.wire.utils.json.RGson;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import static dev.feldmann.redstonegang.wire.game.base.addons.server.quests.db.Tables.*;

public class
QuestDB extends Database {

    QuestAddon addon;
    Gson gson;

    private static String questtable = "CREATE TABLE IF NOT EXISTS quests ( `id` INT NOT NULL AUTO_INCREMENT , `vars` TEXT NOT NULL , `nome` VARCHAR(200) NOT NULL , `hook` VARCHAR(30) NOT NULL , `desc` VARCHAR(200) , PRIMARY KEY (`id`)) ENGINE = InnoDB;";
    private static String questinfotabble = "" +
            "CREATE TABLE IF NOT EXISTS quest_infos " +
            "( `id` INT NOT NULL AUTO_INCREMENT ," +
            " `vars` TEXT NOT NULL ," +
            " `comecou` TIMESTAMP NOT NULL," +
            " `newstreak` BOOLEAN," +
            " `daily` BOOLEAN," +
            " `playerId` INTEGER NOT NULL ," +
            " `qid` INT NOT NULL ," +
            " `completa` BOOLEAN NOT NULL ," +
            " `terminar` TIMESTAMP NULL," +
            " `terminou` TIMESTAMP NULL," +
            "  PRIMARY KEY (`id`)) ENGINE = InnoDB;    ";

    public QuestDB(QuestAddon manager) {
        super(manager.dbName);
        this.addon = manager;
        GsonBuilder builder = RGson.createBuilder();
        ExclusionStrategy ex = new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                if (fieldAttributes.getDeclaringClass() == QuestHook.class || fieldAttributes.getDeclaringClass() == QuestInfo.class || fieldAttributes.getDeclaringClass() == BaseListener.class) {
                    return true;
                }
                return fieldAttributes.hasModifier(Modifier.STATIC);
            }

            @Override
            public boolean shouldSkipClass(Class<?> aClass) {

                return false;
            }
        };
        builder.addSerializationExclusionStrategy(ex);
        builder.addDeserializationExclusionStrategy(ex);
        gson = builder.create();
    }


    @Override
    public void createTables() {
        dsl().execute(questinfotabble);
        dsl().execute(questtable);
    }


    public List<Quest> loadQuests() {
        List<Quest> q = new ArrayList<>();
        Result<QuestsRecord> rs = dsl().selectFrom(QUESTS).fetch();
        for (QuestsRecord r : rs) {
            String nome = r.getNome();
            String desc = r.getDesc();
            String vars = r.getVars();
            String hookname = r.getHook();
            Quest quest = new Quest();
            quest.id = r.getId();
            quest.nome = nome;
            quest.desc = desc.split(";");
            Hooks hook = Hooks.valueOf(hookname);
            QuestHook h = gson.fromJson(vars, hook.getQuest());
            h.setManager(addon.getManager());
            quest.hook = h;
            h.questid = r.getId();
            quest.manager = addon.getManager();
            q.add(quest);
        }
        return q;
    }

    public int countDone(int playerId) {
        Record1<Integer> record = dsl()
                .select(DSL.count(QUEST_INFOS.COMPLETA).as("c"))
                .from(QUEST_INFOS)
                .where(QUEST_INFOS.PLAYERID.eq(playerId))
                .and(QUEST_INFOS.COMPLETA.eq(true)).fetchOne();
        if (record != null) {
            return record.value1();
        }
        return 0;

    }

    public boolean deleteQuest(Quest q) {
        dsl().update(QUEST_INFOS).set(QUEST_INFOS.QID, 0).where(QUEST_INFOS.QID.eq(q.id)).and(QUEST_INFOS.COMPLETA.eq(true)).execute();
        dsl().deleteFrom(QUEST_INFOS).where(QUEST_INFOS.QID.eq(q.id)).and(QUEST_INFOS.COMPLETA.eq(false)).execute();
        dsl().deleteFrom(QUESTS).where(QUESTS.ID.eq(q.id)).execute();
        return true;
    }

    public boolean saveQuest(Quest quest) {

        String desc = "";
        if (quest.desc != null) {
            for (int x = 0; x < quest.desc.length; x++) {
                if (x > 0) {
                    desc += ";";
                }
                desc += quest.desc[x];
            }
        }
        if (quest.getId() == -1) {
            QuestsRecord re = dsl().insertInto(QUESTS)
                    .columns(QUESTS.VARS, QUESTS.NOME, QUESTS.HOOK, QUESTS.DESC)
                    .values(gson.toJson(quest.hook), quest.nome, Hooks.getHook(quest.hook.getClass()).name(), desc)
                    .returning(QUESTS.ID).fetchOne();
            if (re != null) {
                quest.setId(re.getId());
            }
        } else {
            dsl().update(QUESTS)
                    .set(QUESTS.VARS, gson.toJson(quest.hook))
                    .set(QUESTS.NOME, quest.nome)
                    .set(QUESTS.DESC, desc)
                    .where(QUESTS.ID.eq(quest.id)).execute();
        }

        return true;

    }


    public boolean saveQuestInfo(QuestInfo qinfo) {

        if (qinfo.getId() == -1) {

            QuestInfosRecord r = dsl().insertInto(QUEST_INFOS)
                    .columns(QUEST_INFOS.PLAYERID, QUEST_INFOS.VARS, QUEST_INFOS.QID, QUEST_INFOS.TERMINAR, QUEST_INFOS.COMECOU, QUEST_INFOS.TERMINOU, QUEST_INFOS.COMPLETA, QUEST_INFOS.NEWSTREAK, QUEST_INFOS.DAILY)
                    .values(qinfo.getPlayerId(), gson.toJson(qinfo), qinfo.quest, qinfo.terminar, qinfo.comecou, qinfo.terminou, qinfo.completa, qinfo.isNewstreak(), qinfo.daily)
                    .returning(QUEST_INFOS.ID)
                    .fetchOne();
            if (r != null) {
                qinfo.setId(r.getId());
            }
        } else {
            dsl().update(QUEST_INFOS)
                    .set(QUEST_INFOS.VARS, gson.toJson(qinfo))
                    .set(QUEST_INFOS.TERMINAR, qinfo.terminar)
                    .set(QUEST_INFOS.COMECOU, qinfo.comecou)
                    .set(QUEST_INFOS.TERMINOU, qinfo.terminou)
                    .set(QUEST_INFOS.COMPLETA, qinfo.completa)
                    .set(QUEST_INFOS.NEWSTREAK, qinfo.isNewstreak())
                    .set(QUEST_INFOS.DAILY, qinfo.daily)
                    .where(QUEST_INFOS.ID.eq(qinfo.id)).executeAsync();
        }

        return true;
    }


    public QuestPlayer loadTodas(int playerId) {
        return load(playerId, null, null, null);
    }


    public QuestPlayer loadAtivas(int playerId) {
        return load(playerId, QUEST_INFOS.TERMINAR.greaterOrEqual(DSL.now()).and(QUEST_INFOS.QID.greaterThan(0)), null, null);
        //"and terminar >= NOW() and completa = 0 and qid>0"
    }

    public QuestPlayer load(int playerId, Condition cond) {
        return load(playerId, cond, null, null);
    }

    public List<Integer> getLast(int player, int max) {
        List<Integer> last = new ArrayList<>();
        //and terminar < NOW() and qid > 0 and daily = 1 ORDER BY comecou LIMIT " + quantas
        dsl().select(QUEST_INFOS.QID).from(QUEST_INFOS)
                .where(QUEST_INFOS.TERMINAR.lessThan(DSL.now()))
                .and(QUEST_INFOS.QID.greaterThan(0))
                .and(QUEST_INFOS.DAILY.eq(true))
                .and(QUEST_INFOS.PLAYERID.eq(player))
                .orderBy(QUEST_INFOS.COMECOU)
                .limit(max).fetch();
        return last;
    }

    public QuestPlayer load(int playerId, Condition cond, SortField order[], Integer limit) {
        QuestPlayer pla = new QuestPlayer();
        SelectConditionStep<QuestInfosRecord> sele = dsl().selectFrom(QUEST_INFOS).where(QUEST_INFOS.PLAYERID.eq(playerId));
        if (cond != null) {
            sele = sele.and(cond);
        }
        Result<QuestInfosRecord> rs;
        if (order != null) {
            SelectSeekStepN<QuestInfosRecord> step = sele.orderBy(order);
            if (limit != null) {
                rs = step.limit(limit).fetch();
            } else {
                rs = step.fetch();
            }
        } else {
            rs = sele.fetch();
        }

        for (QuestInfosRecord r : rs) {
            int qid = r.getQid();
            Quest q = addon.getManager().getQuestById(qid);
            QuestInfo qif;
            if (qid > 0 && q != null) {
                Hooks hook = Hooks.getHook(q.hook.getClass());
                Class<? extends QuestInfo> questinfo = hook.getQuestinfo();
                String vars = r.getVars();
                qif = gson.fromJson(vars, questinfo);
            } else {
                qif = new QuestInfo() {
                    @Override
                    public void faz(int x) {

                    }

                    @Override
                    public String getStatus() {
                        return "";
                    }
                };
                qif.fail = true;

            }
            qif.quest = qid;
            qif.daily = r.getDaily();
            qif.completa = r.getCompleta();
            qif.terminar = r.getTerminar();
            qif.terminou = r.getTerminou();
            qif.setPlayerId(r.getPlayerid());
            qif.setComecou(r.getComecou());
            qif.setNewstreak(r.getNewstreak());
            qif.id = r.getId();
            pla.infos.add(qif);
            qif.setManager(addon.getManager());
        }
        return pla;
    }
}
