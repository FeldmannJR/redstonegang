package dev.feldmann.redstonegang.common.shop;

import dev.feldmann.redstonegang.common.db.Database;
import dev.feldmann.redstonegang.common.db.jooq.redstonegang_common.tables.records.ExpirationsRecord;
import dev.feldmann.redstonegang.common.player.User;
import org.jooq.Condition;
import org.jooq.Result;

import static dev.feldmann.redstonegang.common.db.jooq.redstonegang_common.Tables.EXPIRATIONS;

import java.util.ArrayList;
import java.util.List;

public class ExpirationDB extends Database {
    @Override
    public void createTables() {
        dsl().execute("create table if not exists expirations\n" +
                "(\n" +
                "    id      int auto_increment,\n" +
                "    user_id int          not null,\n" +
                "    days    int          not null,\n" +
                "    type    varchar(32)  null,\n" +
                "    parent  int unsigned null,\n" +
                "    code_id int unsigned,\n" +
                "    start   timestamp    null,\n" +
                "    end     timestamp    null,\n" +
                "    constraint vip_vencimentos_pk\n" +
                "        primary key (id),\n" +
                "    constraint user_fk\n" +
                "        foreign key (user_id) references users (id)\n" +
                ");\n" +
                "\n");
    }

    public List<ExpirationsRecord> getExpirations(User user) {
        Result<ExpirationsRecord> result = dsl().selectFrom(EXPIRATIONS).where(EXPIRATIONS.USER_ID.eq(user.getId())).fetch();
        return new ArrayList<>(result);
    }

    public ExpirationsRecord getActive(User user) {
        return dsl().selectFrom(EXPIRATIONS)
                .where(EXPIRATIONS.USER_ID.eq(user.getId()))
                .and(isActive())
                .fetchAny();
    }

    public List<ExpirationsRecord> getAvailable(User user) {
        return dsl().selectFrom(EXPIRATIONS)
                .where(EXPIRATIONS.USER_ID.eq(user.getId()))
                .and(isAvailable())
                .orderBy(EXPIRATIONS.ID.asc())
                .fetch();

    }

    public boolean hasAvailable(User user) {
        return dsl().fetchExists(dsl().selectFrom(EXPIRATIONS)
                .where(EXPIRATIONS.USER_ID.eq(user.getId()))
                .and(isAvailable())
        );

    }

    public ExpirationsRecord newExpiration() {
        return dsl().newRecord(EXPIRATIONS);
    }

    private Condition isAvailable() {
        return EXPIRATIONS.START.isNull().and(EXPIRATIONS.END.isNull());
    }

    private Condition isActive() {
        return EXPIRATIONS.START.isNotNull().and(EXPIRATIONS.END.isNull());
    }


}
