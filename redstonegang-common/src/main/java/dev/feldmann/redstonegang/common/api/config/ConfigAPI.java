package dev.feldmann.redstonegang.common.api.config;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.api.RedstoneGangWebAPI;
import dev.feldmann.redstonegang.common.api.Response;
import dev.feldmann.redstonegang.common.config.ConfigProvider;
import dev.feldmann.redstonegang.common.config.ServerConfig;

public class ConfigAPI implements ConfigProvider {
    private ServerConfig server;

    private RedstoneGangWebAPI api;

    public ConfigAPI(RedstoneGangWebAPI api) {
        this.api = api;
    }


    @Override
    public ServerConfig loadConfig() {
        if (server == null) {
            Response<ServerConfig> response = api.builder("config", "server", "" + RedstoneGang.instance().DEV).get(ServerConfig.class);
            if (!response.hasFailed()) {
                server = response.collect();
            }
        }
        return server;
    }
}
