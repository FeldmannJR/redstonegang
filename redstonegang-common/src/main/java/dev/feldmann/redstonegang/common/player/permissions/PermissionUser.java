package dev.feldmann.redstonegang.common.player.permissions;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.player.User;

public class PermissionUser extends PermissionHolder {

    private User user;

    public PermissionUser(User user, Integer parent) {
        super(parent);
        this.user = user;
    }


    public User getUser() {
        return user;
    }


    @Override
    public boolean useDefaultGroup() {
        return true;
    }

    public Group getGroup() {
        return getParent();
    }

    public boolean setDefaultGroup() {
        if (useDefaultGroup()) {
            return setGroup((Integer) null);
        }
        return false;
    }

    public boolean setGroup(Integer id) {
        Group group = null;
        if (id != null) {
            group = RedstoneGang.instance.user().getPermissions().getGroupById(id);
            if (group == null) {
                return false;
            }
        }
        if (!RedstoneGang.instance().webapi().permissions().setGroup(user, group)) {
            return false;
        }
        setParentId(id);
        return RedstoneGang.instance.user().getPermissions().getDb().setGroup(user, id);
    }

    public boolean setGroup(String grupo) {
        Integer id = null;
        if (!grupo.equals("default")) {
            Group gr = RedstoneGang.instance.user().getPermissions().getGroupByName(grupo);
            if (gr == null) {
                return false;
            }
            setParentId(gr.getIdentifier());
            id = gr.getIdentifier();
        }
        return setGroup(id);
    }


    @Override
    public Integer getIdentifier() {
        return user.getId();
    }

    @Override
    public boolean getType() {
        return true;
    }
}
