package dev.feldmann.redstonegang.common.api.auth;

import dev.feldmann.redstonegang.common.api.RedstoneGangWebAPI;
import dev.feldmann.redstonegang.common.api.Response;
import dev.feldmann.redstonegang.common.api.auth.responses.LoginResponse;
import dev.feldmann.redstonegang.common.api.auth.responses.PaidTokenResponse;
import dev.feldmann.redstonegang.common.player.User;

public class AuthAPI {

    RedstoneGangWebAPI api;

    public AuthAPI(RedstoneGangWebAPI api) {
        this.api = api;
    }

    public String generateRegisterToken(User user) {
        Response<PaidTokenResponse> response = api.builder("auth", "paid", "token", user.getId() + "").get(PaidTokenResponse.class);
        if (!response.hasFailed()) {
            return response.collect().token;
        }
        return null;
    }

    public boolean login(String username, String password) {
        Response<LoginResponse> response = api.builder("auth", "login")
                .data("username", username)
                .data("password", password)
                .post(LoginResponse.class);
        if (response.hasFailed()) {
            return false;
        }
        if (response.collect().success) {
            return true;
        }
        return false;
    }

}
