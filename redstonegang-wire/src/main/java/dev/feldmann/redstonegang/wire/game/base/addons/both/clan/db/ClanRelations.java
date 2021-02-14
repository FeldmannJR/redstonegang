package dev.feldmann.redstonegang.wire.game.base.addons.both.clan.db;

import org.jooq.Field;
import org.jooq.Table;
import org.jooq.impl.SQLDataType;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

public class ClanRelations {
    static final Table TABLE = table("clan_relations");
    static final Field<String> TAG_1 = field("tag_1", SQLDataType.VARCHAR(5).nullable(false));
    static final Field<String> TAG_2 = field("tag_2", SQLDataType.VARCHAR(5).nullable(false));
    static final Field<Boolean> TYPE = field("type", SQLDataType.BOOLEAN.nullable(false));


}
