package dev.feldmann.redstonegang.wire.game.base.addons.server.quests;

public class Quest {
    int id = -1;
    QuestHook hook;
    public String nome;
    public String[] desc;
    transient QuestManager manager;

    public void setManager(QuestManager manager) {
        this.manager = manager;
    }

    public QuestManager getManager() {
        return manager;
    }

    public int getId() {
        return id;
    }

    public QuestHook getHook() {
        return hook;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setHook(QuestHook hook) {
        this.hook = hook;
    }
}
