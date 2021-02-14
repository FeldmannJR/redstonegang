package dev.feldmann.redstonegang.common.config;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.utils.EnvHelper;
import dev.feldmann.redstonegang.common.utils.formaters.StringUtils;

public class Config {
    ConfigProvider provider;

    public String API_TOKEN;
    public String API_URL;
    public String APP_BASE_URL;


    public Config() {

        API_TOKEN = EnvHelper.get("SERVER_API_TOKEN", null);
        API_URL = EnvHelper.get("SERVER_API_URL", null);
        RedstoneGang.instance().NAME = EnvHelper.get("SERVER_NAME", null);
        RedstoneGang.instance().DEV = EnvHelper.getBoolean("SERVER_DEV", false);
        RedstoneGang.instance().DEBUG = EnvHelper.getBoolean("SERVER_DEBUG", false);
        RedstoneGang.instance().ENVIRONMENT = EnvHelper.get("SERVER_ENVIRONMENT", "production");

        if (isApiInvalid()) {
            RedstoneGang.instance().alert("API Token or URL not found! ");
            return;
        }
    }

    public boolean isApiInvalid() {
        return StringUtils.isNullOrEmpty(API_TOKEN) || StringUtils.isNullOrEmpty(API_URL);
    }

    public void loadProvider() {
        String provider = EnvHelper.get("SERVER_CONFIG_PROVIDER", "web");

        if (provider.equalsIgnoreCase("web")) {
            if (isApiInvalid()) {
                RedstoneGang.instance().shutdown("API Token or URL not found! ");
                return;
            }
            this.provider = RedstoneGang.instance().webapi().config();
        }
        if (provider.equalsIgnoreCase("env")) {
            this.provider = new EnvConfigProvider();
        }

        if (this.provider == null) {
            RedstoneGang.instance().shutdown("Config provider '" + provider + "' not found!");
        }
    }


    public ServerConfig getConfig() {
        return provider.loadConfig();
    }
}