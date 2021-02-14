package dev.feldmann.redstonegang.wire.game.base.addons.server.multiservertp;

import dev.feldmann.redstonegang.common.db.Database;
import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.wire.game.base.database.addons.tables.records.MultiserverTpRequestsRecord;
import dev.feldmann.redstonegang.wire.game.base.database.addons.tables.records.MultiserverTpTeleportsRecord;
import org.jooq.DatePart;
import org.jooq.impl.DSL;

import java.sql.Timestamp;

import static dev.feldmann.redstonegang.wire.game.base.database.addons.Tables.MULTISERVER_TP_REQUESTS;
import static dev.feldmann.redstonegang.wire.game.base.database.addons.Tables.MULTISERVER_TP_TELEPORTS;

public class MultiServerTpDB extends Database {

    MultiServerTpAddon addon;


    public MultiServerTpDB(String database, MultiServerTpAddon addon) {
        super(database);
        this.addon = addon;
    }

    @Override
    public void createTables() {
        dsl().execute("CREATE TABLE IF NOT EXISTS redstonegang_survival.multiserver_tp_requests\n" +
                "(\n" +
                "\tid bigint auto_increment\n" +
                "\t\tprimary key,\n" +
                "\trequester int not null,\n" +
                "\trequested int not null,\n" +
                "\trequest_time timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,\n" +
                "\trequest_expire timestamp default '0000-00-00 00:00:00' not null,\n" +
                "\tconstraint request_unique_fk\n" +
                "\t\tunique (requester, requested),\n" +
                "\tconstraint requested_fk\n" +
                "\t\tforeign key (requested) references redstonegang_common.users (id)\n" +
                "\t\t\ton update cascade on delete cascade,\n" +
                "\tconstraint requester_fk\n" +
                "\t\tforeign key (requester) references redstonegang_common.users (id)\n" +
                "\t\t\ton update cascade on delete cascade\n" +
                ");\n" +
                "\n");
        dsl().execute("CREATE TABLE IF NOT EXISTS redstonegang_survival.multiserver_tp_teleports\n" +
                "(\n" +
                "\tteleporter int not null\n" +
                "\t\tprimary key,\n" +
                "\ttarget int not null,\n" +
                "\ttarget_server varchar(32) not null,\n" +
                "\tteleport_time timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,\n" +
                "\tis_request tinyint(1) not null,\n" +
                "\tconstraint target_fk\n" +
                "\t\tforeign key (target) references redstonegang_common.users (id)\n" +
                "\t\t\ton update cascade on delete cascade,\n" +
                "\tconstraint teleporter_fk\n" +
                "\t\tforeign key (teleporter) references redstonegang_common.users (id)\n" +
                "\t\t\ton update cascade on delete cascade\n" +
                ");\n" +
                "\n");
    }

    public boolean hasRequest(User requester, User requested) {
        return dsl().fetchExists(
                dsl().selectFrom(MULTISERVER_TP_REQUESTS)
                        .where(MULTISERVER_TP_REQUESTS.REQUESTER.eq(requester.getId()))
                        .and(MULTISERVER_TP_REQUESTS.REQUESTED.eq(requested.getId()))
                        .and(MULTISERVER_TP_REQUESTS.REQUEST_EXPIRE.gt(DSL.now()))
        );
    }

    public boolean existsRequest(long id) {
        return dsl().fetchExists(
                dsl().selectFrom(MULTISERVER_TP_REQUESTS)
                        .where(MULTISERVER_TP_REQUESTS.ID.eq(id))
        );
    }

    public MultiserverTpRequestsRecord getRequest(User requester) {
        return dsl().selectFrom(MULTISERVER_TP_REQUESTS)
                .where(MULTISERVER_TP_REQUESTS.REQUESTER.eq(requester.getId()))
                .and(MULTISERVER_TP_REQUESTS.REQUEST_EXPIRE.gt(DSL.now())).fetchAny();
    }

    public void removeRequest(User requester, User requested) {
        dsl().deleteFrom(MULTISERVER_TP_REQUESTS)
                .where(MULTISERVER_TP_REQUESTS.REQUESTER.eq(requester.getId()))
                .and(MULTISERVER_TP_REQUESTS.REQUESTED.eq(requested.getId()))
                .execute();
    }

    public void deleteRequest(User requester, User requested) {
        dsl().deleteFrom(MULTISERVER_TP_REQUESTS)
                .where(MULTISERVER_TP_REQUESTS.REQUESTER.eq(requester.getId()))
                .and(MULTISERVER_TP_REQUESTS.REQUESTED.eq(requested.getId()))
                .execute();
    }

    public void deleteRequest(User requester) {
        dsl().deleteFrom(MULTISERVER_TP_REQUESTS)
                .where(MULTISERVER_TP_REQUESTS.REQUESTER.eq(requester.getId()))
                .execute();
    }

    public void deleteTeleport(User teleporter) {
        dsl().deleteFrom(MULTISERVER_TP_TELEPORTS)
                .where(MULTISERVER_TP_TELEPORTS.TELEPORTER.eq(teleporter.getId()))
                .execute();
    }

    public void addRequest(User requester, User requested) {
        MultiserverTpRequestsRecord request = dsl().selectFrom(MULTISERVER_TP_REQUESTS)
                .where(MULTISERVER_TP_REQUESTS.REQUESTER.eq(requester.getId()))
                .and(MULTISERVER_TP_REQUESTS.REQUESTED.eq(requested.getId()))
                .fetchAny();
        if (request == null) {
            request = dsl().newRecord(MULTISERVER_TP_REQUESTS);
            request.setRequested(requested.getId());
            request.setRequester(requester.getId());
        }
        request.setRequestTime(new Timestamp(System.currentTimeMillis()));
        request.setRequestExpire(new Timestamp(System.currentTimeMillis() + (1000L * addon.teleportRequestExpireSeconds)));
        request.store();
    }

    public void addTeleport(User requester, User target, String server, boolean isRequest) {
        dsl().insertInto(MULTISERVER_TP_TELEPORTS)
                .columns(MULTISERVER_TP_TELEPORTS.TELEPORTER,
                        MULTISERVER_TP_TELEPORTS.TARGET,
                        MULTISERVER_TP_TELEPORTS.TARGET_SERVER,
                        MULTISERVER_TP_TELEPORTS.TELEPORT_TIME,
                        MULTISERVER_TP_TELEPORTS.IS_REQUEST
                ).values(requester.getId(), target.getId(), server, new Timestamp(System.currentTimeMillis()), isRequest)
                .onDuplicateKeyUpdate()
                .set(MULTISERVER_TP_TELEPORTS.TARGET, target.getId())
                .set(MULTISERVER_TP_TELEPORTS.TELEPORT_TIME, new Timestamp(System.currentTimeMillis()))
                .set(MULTISERVER_TP_TELEPORTS.TARGET_SERVER, server)
                .set(MULTISERVER_TP_TELEPORTS.IS_REQUEST, isRequest)
                .execute();
    }

    public MultiserverTpTeleportsRecord getTeleport(User user) {
        return dsl().selectFrom(MULTISERVER_TP_TELEPORTS)
                .where(MULTISERVER_TP_TELEPORTS.TELEPORTER.eq(user.getId()))
                .and(
                        MULTISERVER_TP_TELEPORTS.TELEPORT_TIME.ge(
                                DSL.timestampSub(DSL.now(), addon.teleportExpireSeconds, DatePart.SECOND))
                )
                .fetchAny();
    }


}
