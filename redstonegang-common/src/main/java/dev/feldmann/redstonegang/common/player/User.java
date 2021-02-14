package dev.feldmann.redstonegang.common.player;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.ServerType;
import dev.feldmann.redstonegang.common.currencies.Currencies;
import dev.feldmann.redstonegang.common.db.jooq.redstonegang_common.tables.records.UsersRecord;
import dev.feldmann.redstonegang.common.db.money.CurrencyChangeType;
import dev.feldmann.redstonegang.common.player.config.ConfigType;
import dev.feldmann.redstonegang.common.player.permissions.*;
import dev.feldmann.redstonegang.common.player.permissions.Group;
import dev.feldmann.redstonegang.common.player.permissions.GroupOption;
import dev.feldmann.redstonegang.common.player.permissions.PermissionDescription;
import dev.feldmann.redstonegang.common.player.permissions.PermissionUser;
import dev.feldmann.redstonegang.common.utils.formaters.DateUtils;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;

import static dev.feldmann.redstonegang.common.db.jooq.redstonegang_common.Tables.USERS;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.UUID;

public class User {

    public static String STAFF_PERMISSION = "redstonegang.staff";

    private UsersRecord data;
    //Identificadores
    private long loadedTime;


    //Permissões
    private PermissionUser permissions;

    public User(UsersRecord data) {
        this.data = data;
        this.loadedTime = System.currentTimeMillis();
    }

    public long getLoadedTime() {
        return this.loadedTime;
    }

    public void setPermissions(PermissionUser permissions) {
        this.permissions = permissions;
    }

    public PermissionUser permissions() {
        return this.permissions;
    }

    public int getId() {
        return this.data.getId();
    }

    public UUID getUuid() {
        return UUID.fromString(this.data.getUuid());
    }

    public String getName() {
        return this.data.getName();
    }

    public String getDisplayName() {
        if (data.getDisguiseName() != null) return data.getDisguiseName();
        return data.getName();
    }

    public String getPrefix() {
        if (data.getDisguiseName() == null) {
            Group gr = permissions.getGroup();
            if (gr != null && gr.getPrefix() != null) {
                return gr.getPrefix();
            }
            return "";
        }
        return RedstoneGang.instance.user().getPermissions().getDefault().getPrefix();
    }

    public String getNameWithPrefix() {
        return getPrefix() + getDisplayName();
    }

    public void claimDailyCash() {
        this.data.setLastCashClaim(new Timestamp(System.currentTimeMillis()));
        this.data.store(USERS.LAST_CASH_CLAIM);
    }

    public Timestamp getLastClaim() {
        return this.data.getLastCashClaim();
    }

    public boolean canClaimWithoutCache() {
        this.data.refresh(USERS.LAST_CASH_CLAIM);
        return canClaimCash();
    }

    public boolean canClaimCash() {
        if (this.isVip()) {
            if (this.getLastClaim() == null) {
                return true;
            }
            Calendar c = DateUtils.getCalendarWithoutTime();
            return this.data.getLastCashClaim().before(c.getTime());
        }
        return false;
    }


    public String getSuffix() {
        if (data.getDisguiseName() == null) {
            Group gr = permissions.getGroup();
            if (gr != null && gr.getSuffix() != null) {
                return gr.getSuffix();
            }
            return "";
        }
        return RedstoneGang.instance.user().getPermissions().getDefault().getSuffix();
    }

    public boolean hasVip1() {
        return permissions.hasPermission("rg.vip.1");
    }

    public boolean hasVip2() {
        return permissions.hasPermission("rg.vip.2");
    }

    public boolean isVip() {
        return hasVip1() || hasVip2() || hasVip3();
    }

    public boolean hasVip3() {
        return permissions.hasPermission("rg.vip.3");
    }

    public void updateName(String newName) {
        boolean containsCache = false;
        if (RedstoneGang.instance().user().cache.contains(getId())) {
            containsCache = true;
            RedstoneGang.instance().user().cache.clearCache(getId());
        }
        this.data.setName(newName);
        this.data.store(USERS.NAME);
        if (containsCache) {
            RedstoneGang.instance().user().cache.cache((User) this);
        }
    }

    public Timestamp getRegistred() {
        return data.getRegistred();
    }

    public boolean isStaff() {
        return permissions.hasPermission(STAFF_PERMISSION);
    }

    public boolean hasPermission(PermissionDescription desc) {
        return permissions.hasPermission(desc.getKey());
    }

    public Integer getAccountId() {
        return data.getAccountId();
    }


    public void setServer(String server) {
        this.data.setServer(server);
    }

    public void setLastServer(String lastServer) {
        this.data.setLastServer(lastServer);
    }

    public String getIp() {
        this.data.refresh(USERS.IP);
        return this.data.getIp();
    }

    public void setIp(String ip) {
        this.data.setIp(ip);
        // Fazer assim pra executar async
        RedstoneGang.instance().databases().users().dsl().update(USERS).set(USERS.IP, ip).where(USERS.ID.eq(this.getId())).executeAsync();
    }

    public void setBungee(String bungee) {
        this.data.setBungee(bungee);
    }

    public String getBungee() {
        return this.data.getBungee();
    }

    public String getLastServer() {
        return this.data.getLastServer();
    }

    public int getCash() {
        return RedstoneGang.instance.user().getDb().getCash(getId());
    }

    public boolean addCash(int qtd) {
        boolean success = RedstoneGang.instance.user().getDb().addCash(getId(), qtd);
        RedstoneGang.instance().currencies().notifyHooks(getId(), Currencies.CASH, CurrencyChangeType.ADD);
        return success;
    }

    public boolean addCashWithLog(int qtd, String reason) {
        boolean success = addCash(qtd);
        RedstoneGang.instance().logs().insertMoeda(getId(), reason, qtd, Currencies.CASH);
        return success;
    }

    public boolean setCash(int qtd) {
        boolean success = RedstoneGang.instance().user().getDb().setCash(getId(), qtd);
        RedstoneGang.instance().currencies().notifyHooks(getId(), Currencies.CASH, CurrencyChangeType.SET);
        return success;
    }

    public boolean removeCash(int qtd) {
        boolean success = RedstoneGang.instance.user().getDb().removeCash(getId(), qtd);
        RedstoneGang.instance().currencies().notifyHooks(getId(), Currencies.CASH, CurrencyChangeType.REMOVE);
        return success;
    }


    public void setLastLogin(Timestamp lastLogin) {
        this.data.setLastLogin(lastLogin);
    }

    public Timestamp getLastLogin() {
        return this.data.getLastLogin();
    }

    public String getDisguiseName() {
        return this.data.getDisguiseName();
    }

    public void setDisguiseName(String disguiseName) {
        this.data.setDisguiseName(disguiseName);
    }


    public <T> T getConfig(ConfigType<T, ?> type) {
        return RedstoneGang.instance.user().getConfig().get(getId()).get(type);
    }

    public <T> void setConfig(ConfigType<T, ?> type, T value) {
        RedstoneGang.instance.user().getConfig().get(getId()).set(type, value);
        RedstoneGang.instance().network().sendMessage("changedconfig", getId() + "");
    }

    public double getOption(GroupOption option) {
        Group group = permissions().getGroup();
        if (group != null) {
            return group.getOption(option);
        }
        return option.getDefaultValue();
    }

    public String getServer() {
        return this.data.getServer();
    }

    public boolean isOnline() {
        return getServer() != null;
    }

    public String getServerIdentifier() {
        return this.data.getServerIdentifier();
    }

    public void setServerIdentifier(String serverIdentifier) {
        this.data.setServerIdentifier(serverIdentifier);
    }

    public void sendMessage(String msg) {
        RedstoneGang.instance.sendMessage(this, new TextComponent(TextComponent.fromLegacyText(msg)));
    }

    public void sendMessage(String... msg) {
        TextComponent[] component = new TextComponent[msg.length];
        for (int i = 0; i < msg.length; i++) {
            component[i] = new TextComponent(TextComponent.fromLegacyText(msg[i]));
        }
        RedstoneGang.instance.sendMessage(this, component);
    }


    public void sendMessage(TextComponent... component) {
        RedstoneGang.instance.sendMessage(this, component);
    }

    public void kick(TextComponent reason) {
        RedstoneGang.instance().network().sendMessageToType(ServerType.BUNGEE, "kick", getId(), ComponentSerializer.toString(reason));
    }

    public void kick(String reason) {
        this.kick(new TextComponent(TextComponent.fromLegacyText(reason)));
    }


    public boolean isMuted() {
        return RedstoneGang.instance().user().getPunishment().isMuted(this);
    }

}
