package dev.feldmann.redstonegang.common.config;

import dev.feldmann.redstonegang.common.utils.EnvHelper;

public class EnvConfigProvider implements ConfigProvider {
    @Override
    public ServerConfig loadConfig() {
        ServerConfig config = new ServerConfig();
        config.database_host = EnvHelper.get("CONFIG_DATABASE_HOST", "localhost");
        config.database_schema = EnvHelper.get("CONFIG_DATABASE_SCHEMA", "redstonegang_common");
        config.database_user = EnvHelper.get("CONFIG_DATABASE_USER", "root");
        config.database_password = EnvHelper.get("CONFIG_DATABASE_PASSWORD", "senha123");

        config.network_host = EnvHelper.get("CONFIG_NETWORK_HOST", "localhost");
        config.network_port = EnvHelper.get("CONFIG_NETWORK_PORT", "3366");
        config.network_token = EnvHelper.get("CONFIG_NETWORK_TOKEN", "123asd1sdaca");
        return config;

    }
}
