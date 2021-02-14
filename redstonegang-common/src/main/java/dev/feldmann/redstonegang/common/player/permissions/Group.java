package dev.feldmann.redstonegang.common.player.permissions;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.db.jooq.redstonegang_common.tables.records.GroupsRecord;
import dev.feldmann.redstonegang.common.db.jooq.redstonegang_common.Tables;

import java.util.Collection;
import java.util.HashMap;

public class Group extends PermissionHolder {

    private GroupsRecord data;


    private HashMap<String, Double> options = new HashMap();


    public Group(GroupsRecord record) {
        super(record.getParent());
        this.data = record;
    }

    public String getNome() {
        return data.getName();
    }

    @Override
    public Integer getIdentifier() {
        return data.getId();
    }


    @Override
    public boolean getType() {
        return false;
    }

    public String getDisplayName() {
        if (data.getDisplayName() == null) {
            return this.getNome();
        }
        return data.getDisplayName();
    }

    public void setDisplayName(String name) {
        data.setDisplayName(name);
        data.store(Tables.GROUPS.DISPLAY_NAME);
    }

    public Long getDiscordRole() {
        return data.getDiscordRole();
    }

    public void setOptions(HashMap<String, Double> options) {
        this.options = options;
    }

    public boolean isDefaultGroup() {
        return data.getDefault() != null && data.getDefault();
    }

    public String getPrefix() {
        return data.getPrefix();
    }

    public void setOption(String i, double value) {
        options.put(i, value);
        RedstoneGang.instance.user().getPermissions().getDb().setOption(this, i, value);
    }

    public void removeOption(String op) {
        options.remove(op);
        RedstoneGang.instance.user().getPermissions().getDb().deleteOption(this, op);
    }

    public boolean isOptionPresent(String op) {
        return options.containsKey(op);
    }

    public double getOption(String op) {
        return options.get(op);
    }

    public double getOptionOrElse(String op, double value) {
        if (isOptionPresent(op)) {
            return getOption(op);
        }
        if (getParent() != null) {
            return getParent().getOptionOrElse(op, value);
        }
        return value;
    }

    @Override
    public boolean useDefaultGroup() {
        return false;
    }

    public double getOption(GroupOption option) {
        return getOptionOrElse(option.getKey(), option.defaultValue);
    }

    @Override
    public void setParentId(Integer parentId) {
        super.setParentId(parentId);
        RedstoneGang.instance.user().getPermissions().getDb().setGroupParent(this, parentId);
    }

    public void setSuffix(String suffix) {
        this.data.setSuffix(suffix);
        this.data.store();
    }

    public void setPrefix(String prefix) {
        this.data.setPrefix(prefix);
        this.data.store();
    }

    public String getSuffix() {
        return data.getSuffix();
    }

    public Collection<String> getOptions() {
        return options.keySet();
    }

}
