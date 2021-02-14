package dev.feldmann.redstonegang.wire.game.base.addons.server.notificacoes;

import dev.feldmann.redstonegang.common.db.Database;
import dev.feldmann.redstonegang.wire.game.base.addons.server.notificacoes.db.tables.records.NotificacoesRecord;
import dev.feldmann.redstonegang.wire.utils.json.RGson;
import org.jooq.Result;

import java.util.ArrayList;
import java.util.List;

import static dev.feldmann.redstonegang.wire.game.base.addons.server.notificacoes.db.Tables.*;

public class NotificationDB extends Database {
    Notifications manager;

    public NotificationDB(Notifications notifications) {
        super(notifications.databaseName);
        this.manager = notifications;
    }

    @Override
    public void createTables() {
        safeExecute(
                "CREATE TABLE IF NOT EXISTS notificacoes " +
                        "(`type` VARCHAR(100) NOT NULL, " +
                        " `owner` INTEGER," +
                        " `vars` VARCHAR(200)," +
                        " PRIMARY KEY (`owner`,`type`)" +
                        ")");

    }


    public List<Notification> loadNotifications(int pid) {
        List<Notification> notf = new ArrayList();
        Result<NotificacoesRecord> rs = dsl()
                .selectFrom(NOTIFICACOES)
                .where(NOTIFICACOES.OWNER.eq(pid))
                .fetch();
        for (NotificacoesRecord r : rs) {
            NotificationType type = manager.getType(r.getType());
            if (type == null) {
                continue;
            }
            notf.add(new Notification(pid, type, RGson.parse(r.getVars()).getAsJsonObject()));
        }
        return notf;
    }

    public Notification loadNotification(int pid, NotificationType type) {
        NotificacoesRecord r = dsl()
                .selectFrom(NOTIFICACOES)
                .where(NOTIFICACOES.OWNER.eq(pid))
                .and(NOTIFICACOES.TYPE.eq(type.getType()))
                .fetchOne();
        if (r == null) return null;
        return (new Notification(pid, type, RGson.parse(r.getVars()).getAsJsonObject()));
    }

    public void saveNotification(Notification not) {
        dsl().insertInto(NOTIFICACOES)
                .columns(NOTIFICACOES.OWNER, NOTIFICACOES.TYPE, NOTIFICACOES.VARS)
                .values(not.owner, not.type.getType(), not.vars.toString())
                .onDuplicateKeyUpdate().set(NOTIFICACOES.VARS, not.vars.toString())
                .execute();
    }

    public void deleteNotifications(int owner) {
        dsl()
                .deleteFrom(NOTIFICACOES)
                .where(NOTIFICACOES.OWNER.eq(owner))
                .execute();

    }

}
