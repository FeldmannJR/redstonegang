package dev.feldmann.redstonegang.wire.game.base.addons.both.whitelistcmd;

import dev.feldmann.redstonegang.common.db.Database;
import org.jooq.Record1;
import org.jooq.Result;

import static dev.feldmann.redstonegang.wire.game.base.database.addons.Tables.WHITELISTCMD;

import java.util.HashSet;
import java.util.stream.Collectors;

public class WhitelistCmdDB extends Database {
    public WhitelistCmdDB(String database) {
        super(database);
    }

    @Override
    public void createTables() {
        dsl().execute("create table if not exists redstonegang_survival.whitelistcmd" +
                "(" +
                "command varchar(128) not null comment 'Comando sem barra ex ''spawn'''" +
                "primary key," +
                "added timestamp default CURRENT_TIMESTAMP not null" +
                ");" +
                "");
    }

    public HashSet<String> loadAllowed() {
        Result<Record1<String>> commands = dsl().select(WHITELISTCMD.COMMAND).from(WHITELISTCMD).fetch();
        return commands.stream().map((record) -> record.get(WHITELISTCMD.COMMAND)).collect(Collectors.toCollection(HashSet::new));
    }

    public void addAllowedCommand(String command) {
        dsl().insertInto(WHITELISTCMD)
                .columns(WHITELISTCMD.COMMAND)
                .values(command)
                .onDuplicateKeyIgnore().execute();
    }

    public void removeAllowedCommand(String command) {
        dsl().deleteFrom(WHITELISTCMD)
                .where(WHITELISTCMD.COMMAND.eq(command))
                .execute();
    }

}
