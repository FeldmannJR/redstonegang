package dev.feldmann.redstonegang.common.api.mojang;

import dev.feldmann.redstonegang.common.api.RedstoneGangWebAPI;
import dev.feldmann.redstonegang.common.api.Response;
import dev.feldmann.redstonegang.common.api.mojang.responses.HasPaidResponse;

import java.util.UUID;

public class MojangAPI {

    RedstoneGangWebAPI api;

    public MojangAPI(RedstoneGangWebAPI api) {
        this.api = api;
    }

    public Response<HasPaidResponse> hasPaid(String username) {
        return api.builder("mojang", "haspaid", username).get(HasPaidResponse.class);
    }

    public Response profileByUUID(UUID uuid, boolean unsigned) {
        return api.builder("mojang", "profile", "uuid", uuid.toString().replace("-", ""), "?unsigned=" + unsigned).get();
    }

    public Response profileByName(String name) {
        return api.builder("mojang", "profile", "name", name).get();
    }

    public Response profileByNameCrakedOnly(String name) {
        return api.builder("mojang", "profile", "cracked", name).get();

    }

    public Response skin(String username) {
        return api.builder("mojang", "skin", username.toLowerCase()).get();
    }


}
