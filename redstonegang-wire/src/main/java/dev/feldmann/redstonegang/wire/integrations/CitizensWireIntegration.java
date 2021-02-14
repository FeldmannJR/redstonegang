package dev.feldmann.redstonegang.wire.integrations;

import dev.feldmann.redstonegang.RGSpigotInterface;
import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.api.Response;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.yggdrasil.response.MinecraftProfilePropertiesResponse;
import net.citizensnpcs.CitizensIntegration;
import net.citizensnpcs.npc.profile.ProfileFetchResult;
import net.citizensnpcs.npc.profile.ProfileRequest;
import org.apache.commons.lang.StringUtils;

public class CitizensWireIntegration implements CitizensIntegration, RGSpigotInterface {
    @Override
    public GameProfile fillProfile(GameProfile profile, boolean requireSignature) throws Exception {
        Response request = RedstoneGang.instance().webapi().mojang().profileByUUID(profile.getId(), !requireSignature);
        if (request.hasFailed()) {
            throw new Exception("Request for profile failed!");
        }

        MinecraftProfilePropertiesResponse response = (MinecraftProfilePropertiesResponse) request.collectAnother(MinecraftProfilePropertiesResponse.class);
        GameProfile result = new GameProfile(response.getId(), response.getName());
        result.getProperties().putAll(response.getProperties());
        profile.getProperties().putAll(response.getProperties());
        return result;
    }

    @Override
    public void onProfileNotFound(GameProfile gameProfile, ProfileRequest profileRequest) {
        String name = gameProfile.getName();
        // Custom Skin
        if (name.startsWith("rg-")) {
            Response request = RedstoneGang.instance().webapi().mojang().profileByNameCrakedOnly(name);
            if (!request.hasFailed()) {
                MinecraftProfilePropertiesResponse response = (MinecraftProfilePropertiesResponse) request.collectAnother(MinecraftProfilePropertiesResponse.class);
                GameProfile result = new GameProfile(response.getId(), name);
                result.getProperties().putAll(response.getProperties());
                gameProfile.getProperties().putAll(response.getProperties());
                profileRequest.setResult(result, ProfileFetchResult.SUCCESS);
                return;
            }
        }
        profileRequest.setResult(null, ProfileFetchResult.FAILED);
    }

    @Override
    public GameProfile loadSkullProfile(String s) {
        Response request = RedstoneGang.instance().webapi().mojang().profileByName(s);
        if (!request.hasFailed()) {
            MinecraftProfilePropertiesResponse response = (MinecraftProfilePropertiesResponse) request.collectAnother(MinecraftProfilePropertiesResponse.class);
            if (s.startsWith("rg-")) {
                s = StringUtils.capitalize(s.substring(3));
            }
            GameProfile result = new GameProfile(response.getId(), s);
            result.getProperties().putAll(response.getProperties());
            return result;
        }
        return null;
    }

}
