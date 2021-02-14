package dev.feldmann.redstonegang.common.db;

import dev.feldmann.redstonegang.common.RedstoneGang;
import com.zaxxer.hikari.HikariDataSource;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.SQLDialect;
import org.jooq.Table;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class Database {

    private HikariDataSource dataSource;


    public Database() {
        this(RedstoneGang.instance.db().getSchema());
    }


    public Database(String database) {
        this(database, false);
    }

    public Database(String database, boolean createIfNotExists) {
        if (createIfNotExists) {
            RedstoneGang.instance().db().createDatabaseIfNotExists(database);
        }

        dataSource = RedstoneGang.instance.db().getDataSource().getSource(database);
        createTables();
    }


    public Connection getConn() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public abstract void createTables();

    // Caso queira puxar a conexão fora do database, o correto é criar uma classe só pro db mas na correria foda-se
    public static DSLContext database() {
        return DSL.using(RedstoneGang.instance().db().getDataSource().getSource(RedstoneGang.instance.db().getSchema()), SQLDialect.MARIADB);
    }

    public DSLContext dsl() {
        return dslpool();
    }

    public DSLContext dslpool() {

        return DSL.using(dataSource, SQLDialect.MARIADB);
    }

    public HikariDataSource getDataSource() {
        return dataSource;
    }

    public void safeExecute(String data) {
        try {
            Connection con = getConn();
            con.createStatement().execute(data);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void createColumnIfNotExists(Table table, Field f) {
        String tableName = table.getName();
        String column = f.getName();

        boolean exists = false;
        try (Connection con = getConn()) {
            ResultSet rs = con.createStatement().executeQuery("SHOW COLUMNS FROM `" + tableName + "` LIKE '" + column + "'");
            exists = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (!exists) {
            dsl().alterTable(table).addColumn(f).execute();
        }
    }

    public void setDatabase(String database) {
        dataSource = RedstoneGang.instance.db().getDataSource().getSource(database);
    }

    public <U> void runAsync(Supplier<U> func, Consumer<U> sync) {
        RedstoneGang.instance.runAsync(() -> {
            U get = func.get();
            RedstoneGang.instance.runSync(() -> {
                sync.accept(get);
            });
        });
    }
}
