package dev.feldmann.redstonegang.wire.game.base.addons.server.quests;

import dev.feldmann.redstonegang.common.utils.Cooldown;
import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.cmds.CmdQuest;
import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.cmds.CmdQuestAdm;
import dev.feldmann.redstonegang.wire.game.base.apis.configurablemap.ConfigurableMapAPI;
import dev.feldmann.redstonegang.wire.game.base.apis.configurablemap.MapConfigurable;
import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import dev.feldmann.redstonegang.wire.game.base.objects.annotations.Dependencies;
import net.citizensnpcs.api.npc.NPC;

/**
 * Esse código ta uma desgraça pqp, e fui eu que fiz
 */
@Dependencies(apis = ConfigurableMapAPI.class)
public class QuestAddon extends Addon {

    public static String nomenpc = "Aventureiro";


    String dbName;
    public QuestDB db;
    public QuestPoints currency;
    public QuestManager manager;

    boolean addQuests;

    @MapConfigurable
    NPC npc = null;


    public QuestAddon(boolean addQuests, String dbName) {
        this.addQuests = addQuests;
        this.dbName = dbName;
    }

    QuestDB getDb() {
        return db;
    }

    @Override
    public void onEnable() {
        db = new QuestDB(this);
        manager = new QuestManager(this);
        manager.onEnable();
        currency = new QuestPoints(dbName);
        registerCommand(new CmdQuestAdm(manager));
        registerCommand(new CmdQuest(this));
        registerListener(new QuestListener(this));
    }

    public QuestPoints getCurrency() {
        return currency;
    }

    public void registerQuest(Quest q) {
        registerListener(q.hook);
    }

    public void unregisterQuest(Quest q) {
        unregisterListener(q.hook);
    }

    public QuestManager getManager() {
        return manager;
    }

    public boolean isAddQuests() {
        return addQuests;
    }
}
