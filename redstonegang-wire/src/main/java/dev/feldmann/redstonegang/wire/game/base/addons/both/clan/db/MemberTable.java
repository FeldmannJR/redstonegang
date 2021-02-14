package dev.feldmann.redstonegang.wire.game.base.addons.both.clan.db;

import org.jooq.Field;
import org.jooq.Table;
import org.jooq.impl.SQLDataType;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

public class MemberTable {
    static final Table MEMBERS = table("clan_members");
    static final Field<Integer> ID = field("id", SQLDataType.INTEGER.nullable(false));
    static final Field<String> CLAN = field("clan", SQLDataType.VARCHAR(5).nullable(true));
    static final Field<Integer> ROLE = field("role", SQLDataType.INTEGER.defaultValue(0));
}
