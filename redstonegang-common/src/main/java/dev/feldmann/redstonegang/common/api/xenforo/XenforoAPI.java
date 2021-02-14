package dev.feldmann.redstonegang.common.api.xenforo;

import dev.feldmann.redstonegang.common.api.RedstoneGangWebAPI;
import dev.feldmann.redstonegang.common.api.Response;

public class XenforoAPI
{
    RedstoneGangWebAPI api;

    public XenforoAPI(RedstoneGangWebAPI api)
    {
        this.api = api;
    }

    public Response updateUsername(Long id, String username)
    {
        return api.builder("xenforo", "updateuser", id.toString()).json("{\"user\":{\"username\":\"" + username + "\"}}").post();
    }
}
