package dev.feldmann.redstonegang.wire.game.base.addons.both.clan.db;

import org.jooq.Field;
import org.jooq.Table;
import org.jooq.impl.SQLDataType;

import java.sql.Timestamp;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

public class ClanTable {
    static final Table CLANS = table("clans");
    static final Field<String> TAG = field("tag", SQLDataType.VARCHAR(5).nullable(false));
    static final Field<String> COLORTAG = field("colortag", SQLDataType.VARCHAR(15).nullable(false));
    static final Field<String> NAME = field("name", SQLDataType.VARCHAR(32).nullable(false));
    static final Field<Timestamp> FUNDADO = field("founded", SQLDataType.TIMESTAMP);
    static final Field<Integer> FOUNDER = field("leader", SQLDataType.INTEGER);
    static final Field<String> HOME = field("home", SQLDataType.VARCHAR(200));
    static final Field<String> PROPERTIES = field("properties", SQLDataType.VARCHAR(200));
    static final Field<Timestamp> LAST_ACTIVE_TIME= field("lastactive", SQLDataType.TIMESTAMP);



}
