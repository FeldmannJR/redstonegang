package dev.feldmann.redstonegang.common.player;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.punishment.PunishmentManager;
import dev.feldmann.redstonegang.common.network.NetworkMessage;
import dev.feldmann.redstonegang.common.player.cache.PlayerCache;
import dev.feldmann.redstonegang.common.player.config.ConfigManager;
import dev.feldmann.redstonegang.common.player.permissions.PermissionManager;

import java.util.Optional;

public class UserManager {

    private PermissionManager permissions;
    private UserDB db;
    //Nenhum dos 3 compartilha informação, dependendo da aplicação rodando é preferivel usar um tipo, por exemplo no discord tu não tem o uuid do maluco então tu prefere procurar por discord
    public PlayerCache cache = new PlayerCache();

    private ConfigManager config;

    private PunishmentManager punishment;

    public UserManager() {
        permissions = new PermissionManager();
        db = RedstoneGang.instance().databases().users();
        punishment = new PunishmentManager();
        this.config = new ConfigManager();
        RedstoneGang.instance().network().addHandler(this::handleMessage);
    }


    public User getCachedUser(int pid) {
        if (cache.contains(pid)) {
            return cache.get(pid);
        }
        return null;
    }

    private void handleMessage(NetworkMessage msg) {
        if (msg.get(0).equals("changedserver")) {
            Optional<Integer> pid = msg.getInt(1);
            String sv = msg.get(2);
            String lastServer = msg.get(3);

            if (pid.isPresent()) {
                if (cache.isCached(pid.get())) {
                    if (sv.equals("null")) {
                        sv = null;
                    }
                    if (lastServer.equals("null")) {
                        lastServer = null;
                    }
                    User user = cache.get(pid.get());
                    user.setServer(sv);
                    user.setLastServer(lastServer);
                }
            }
        }

        if (msg.get(0).equals("changedidentifier")) {
            Optional<Integer> pid = msg.getInt(1);
            String identifier = msg.get(2);
            if (pid.isPresent()) {
                if (cache.isCached(pid.get())) {
                    if (identifier.equals("null")) {
                        identifier = null;
                    }
                    User user = cache.get(pid.get());
                    user.setServerIdentifier(identifier);
                }
            }
        }
        if (msg.get(0).equals("changedconfig")) {
            Optional<Integer> pid = msg.getInt(1);
            if (pid.isPresent()) {
                if (getCachedUser(pid.get()) != null) {
                    config.reloadPlayer(pid.get());
                }
            }
        }
    }

    public void updateServer(User pl, String bungee, String sv) {
        String lastServer = pl.getServer();
        pl.setServer(sv);
        pl.setLastServer(lastServer);
        pl.setBungee(bungee);
        RedstoneGang.instance.network().sendMessage("changedserver", pl.getId(), sv != null ? sv : "null", lastServer != null ? lastServer : "null");
        db.setCurrentServer(pl.getId(), bungee, sv);
    }

    public void updateIdentifier(User pl, String identifier) {
        pl.setServerIdentifier(identifier);
        RedstoneGang.instance.network().sendMessage("changedidentifier", pl.getId(), identifier != null ? identifier : "null");
        db.setIdentifier(pl.getId(), identifier);
    }

    public void init() {
        permissions.init();
    }

    public UserDB getDb() {
        return db;
    }

    public PermissionManager getPermissions() {
        return permissions;
    }

    public PunishmentManager getPunishment() {
        return punishment;
    }

    public ConfigManager getConfig() {
        return config;
    }
}
