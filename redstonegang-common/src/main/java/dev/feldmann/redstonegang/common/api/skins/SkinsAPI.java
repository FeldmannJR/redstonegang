package dev.feldmann.redstonegang.common.api.skins;

import dev.feldmann.redstonegang.common.api.RedstoneGangWebAPI;
import dev.feldmann.redstonegang.common.api.Response;

public class SkinsAPI {

    private RedstoneGangWebAPI api;

    public SkinsAPI(RedstoneGangWebAPI api) {
        this.api = api;
    }

    public Response<String[]> custom() {
        return api.builder("skins", "custom").get(String[].class);
    }

}
