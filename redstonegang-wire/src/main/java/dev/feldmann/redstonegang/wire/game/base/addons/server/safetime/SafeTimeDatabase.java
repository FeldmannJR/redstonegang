package dev.feldmann.redstonegang.wire.game.base.addons.server.safetime;

import dev.feldmann.redstonegang.common.db.Database;
import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.wire.game.base.database.addons.tables.records.SafetimeRecord;
import org.jooq.impl.DSL;

import static dev.feldmann.redstonegang.wire.game.base.database.addons.Tables.SAFETIME;

public class SafeTimeDatabase extends Database {


    public SafeTimeDatabase(String database) {
        super(database);
    }

    @Override
    public void createTables() {
        safeExecute("create table if not exists `safetime`\n" +
                "(\n" +
                "    id int auto_increment,\n" +
                "    user_id int null unique ,\n" +
                "    start timestamp default now() not null,\n" +
                "    end timestamp not null,\n" +
                "    constraint safetime_pk\n" +
                "        primary key (id),\n" +
                "    constraint user_fk\n" +
                "        foreign key (user_id) references redstonegang_common.users (id)\n" +
                "            on update cascade on delete cascade\n" +
                ");\n" +
                "\n");
        // Limpa a tabela
        dsl().deleteFrom(SAFETIME).where(SAFETIME.END.lessThan(DSL.now())).execute();
    }

    public SafetimeRecord getSafeTime(User p) {
        SafetimeRecord safeTime = dsl().selectFrom(SAFETIME)
                .where(SAFETIME.USER_ID.eq(p.getId()))
                .and(SAFETIME.START.lessOrEqual(DSL.now()))
                .and(SAFETIME.END.greaterOrEqual(DSL.now())).fetchAny();
        return safeTime;
    }

    public void removeSafeTime(int user_id) {
        dsl().deleteFrom(SAFETIME).where(SAFETIME.USER_ID.eq(user_id)).execute();
    }

    public SafetimeRecord createSafeTime(int userId) {
        SafetimeRecord safe = dsl().newRecord(SAFETIME);
        safe.setUserId(userId);
        return safe;
    }


}
