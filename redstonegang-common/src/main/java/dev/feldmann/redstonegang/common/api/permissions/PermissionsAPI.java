package dev.feldmann.redstonegang.common.api.permissions;

import dev.feldmann.redstonegang.common.api.RedstoneGangWebAPI;
import dev.feldmann.redstonegang.common.api.Response;
import dev.feldmann.redstonegang.common.api.permissions.responses.SetGroupResponse;
import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.common.player.permissions.Group;

public class PermissionsAPI {

    RedstoneGangWebAPI api;

    public PermissionsAPI(RedstoneGangWebAPI api) {
        this.api = api;
    }

    public boolean setGroup(User user, Group group) {
        Response<SetGroupResponse> response = api
                .builder("permissions", "setgroup")
                .data("user_id", user.getId())
                .data("group_id", group == null ? -1 : group.getIdentifier())
                .post(SetGroupResponse.class);
        return !response.hasFailed();
    }


}
