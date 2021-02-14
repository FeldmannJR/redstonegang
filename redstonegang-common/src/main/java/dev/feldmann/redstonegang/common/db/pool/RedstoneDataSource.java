package dev.feldmann.redstonegang.common.db.pool;

import dev.feldmann.redstonegang.common.db.DatabaseManager;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.util.HashMap;

public class RedstoneDataSource {


    private HashMap<String, HikariDataSource> pools = new HashMap();


    private DatabaseManager database;

    public RedstoneDataSource(DatabaseManager database) {
        this.database = database;
    }

    private HikariDataSource getDataSource(String schema) {
        HikariConfig config = new HikariConfig();
        try {
            Class.forName("com.updated.mysql.cj.jdbc.Driver");
            config.setDriverClassName("com.updated.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
        }
        config.setJdbcUrl("jdbc:mysql://" + database.getDatabaseHost() + "/" + database.getSchema() + "?useSSL=false");
        config.setUsername(database.getDatabaseUser());
        config.setPassword(database.getDatabasePassword());
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("useServerPrepStmts", "true");
        config.setMaximumPoolSize(15);
        config.setIdleTimeout(30000);
        config.setCatalog(schema);
        config.addDataSourceProperty("characterEncoding","utf8");
        config.addDataSourceProperty("useUnicode","true");
        config.setLeakDetectionThreshold(15000);
        return new HikariDataSource(config);
    }


    public HikariDataSource getSource(String database) {
        if (!pools.containsKey(database)) {
            pools.put(database, getDataSource(database));
        }
        return pools.get(database);
    }
}
