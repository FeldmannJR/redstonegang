package dev.feldmann.redstonegang.common.db;


import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.db.pool.RedstoneDataSource;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariPool;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseManager {

    public static String defaultCollation = "utf8_unicode_ci";
    public static String defaultCharset = "utf8";

    String databaseHost;
    String databaseSchema;
    String databaseUser;
    String databasePassword;

    RedstoneDataSource dataSource;

    private DatabaseHelper helper = new DatabaseHelper(this);


    public DatabaseManager(String databaseHost, String databaseSchema, String databaseUser, String databasePassword) {
        this.databaseHost = databaseHost;
        this.databaseSchema = databaseSchema;
        this.databaseUser = databaseUser;
        this.databasePassword = databasePassword;
        this.dataSource = new RedstoneDataSource(this);
        testDatabase();
    }

    public void
    testDatabase() {
        try (Connection con = getDefault().getConnection()) {
            con.createStatement().executeQuery("SELECT 1");
        } catch (SQLException e) {
            e.printStackTrace();
            RedstoneGang.instance().alert("Não consegui conectar no db 1!");
        } catch (HikariPool.PoolInitializationException ex) {
            ex.printStackTrace();
            RedstoneGang.instance().alert("Não consegui conectar no db!");
            RedstoneGang.instance().shutdown("Não conectou no db!");
        }
    }

    public String getSchema() {
        return databaseSchema;
    }

    public String getDatabaseHost() {
        return databaseHost;
    }

    public String getDatabaseUser() {
        return databaseUser;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public HikariDataSource getDefault() {
        return getDataSource().getSource(getSchema());
    }

    public RedstoneDataSource getDataSource() {
        return dataSource;
    }

    public boolean isConnected() {
        HikariDataSource source = getDefault();
        return true;
    }

    public DatabaseHelper helper() {
        return helper;
    }

    public void createDatabaseIfNotExists(String database) {
        try (Connection con = RedstoneGang.instance().db().getDefault().getConnection()) {

            con.createStatement().execute("CREATE DATABASE IF NOT EXISTS " + database + " CHARACTER SET = '" + defaultCharset + "' COLLATE = '" + defaultCollation + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
