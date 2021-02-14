package dev.feldmann.redstonegang.common.api;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.api.auth.AuthAPI;
import dev.feldmann.redstonegang.common.api.config.ConfigAPI;
import dev.feldmann.redstonegang.common.api.maps.MapsAPI;
import dev.feldmann.redstonegang.common.api.mojang.MojangAPI;
import dev.feldmann.redstonegang.common.api.permissions.PermissionsAPI;
import dev.feldmann.redstonegang.common.api.skins.SkinsAPI;
import dev.feldmann.redstonegang.common.api.xenforo.XenforoAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RedstoneGangWebAPI {

    private String baseUrl = "http://app.rg/api/";
    private String token = "";
    private String apiVersion = "v1";

    private AuthAPI auth;
    private ConfigAPI config;
    private MojangAPI mojang;
    private MapsAPI maps;
    private PermissionsAPI permissions;
    private SkinsAPI skins;
    private XenforoAPI xenforo;
    private Gson gson;


    public RedstoneGangWebAPI(String baseUrl, String token, String apiVersion) {
        this.baseUrl = baseUrl;
        this.token = token;
        this.apiVersion = apiVersion;
        this.buildGson();

        config = new ConfigAPI(this);
        auth = new AuthAPI(this);
        mojang = new MojangAPI(this);
        maps = new MapsAPI(this);
        permissions = new PermissionsAPI(this);
        skins = new SkinsAPI(this);
        xenforo = new XenforoAPI(this);
    }

    public void showApiInfo() {
        Response<InfoResponse> r = builder().get(InfoResponse.class);
        if (r.hasFailed()) {
            RedstoneGang.instance().alert("Api token or url doesn't working! Url:" + baseUrl + " Token:" + token);
        } else {
            InfoResponse json = r.collect();
            RedstoneGang.instance().getLogger().info("Webapi funcionando corretamente:");
            RedstoneGang.instance().getLogger().info(" Versão: " + json.version);
            RedstoneGang.instance().getLogger().info(" Ambiente: " + json.environment);

        }
    }

    private void buildGson() {
        GsonBuilder builder = new GsonBuilder();
        RedstoneGang.instance().registerGsonTypes(builder);
        gson = builder.create();
    }

    public MojangAPI mojang() {
        return mojang;
    }

    public AuthAPI auth() {
        return auth;
    }

    public ConfigAPI config() {
        return config;
    }

    public MapsAPI maps() {
        return maps;
    }

    public SkinsAPI skins() {
        return skins;
    }

    public XenforoAPI xenforo() {
        return xenforo;
    }

    public PermissionsAPI permissions() {
        return permissions;
    }

    public RequestBuilder builder(String endpoint) {
        return new RequestBuilder(baseUrl, apiVersion, token, endpoint);
    }

    public RequestBuilder builder(String... endpoint) {
        return builder(convertArrayToString(endpoint));
    }

    private static String convertArrayToString(String[] array) {
        if (array == null || array.length == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (String param : array) {
            builder.append(param);
            builder.append('/');
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

    public String getToken() {
        return token;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public Gson getGson() {
        return gson;
    }
}
