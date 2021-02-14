package dev.feldmann.redstonegang.wire.game.base.addons.server.multiservertp;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.common.player.config.types.EnumConfig;
import dev.feldmann.redstonegang.common.player.permissions.PermissionDescription;
import dev.feldmann.redstonegang.common.utils.msgs.MsgType;
import dev.feldmann.redstonegang.wire.RedstoneGangSpigot;
import dev.feldmann.redstonegang.wire.game.base.addons.server.multiservertp.cmds.CmdTp;
import dev.feldmann.redstonegang.wire.game.base.addons.server.multiservertp.cmds.CmdTpa;
import dev.feldmann.redstonegang.wire.game.base.addons.server.multiservertp.cmds.CmdTpc;
import dev.feldmann.redstonegang.wire.game.base.addons.server.multiservertp.cmds.CmdTpr;
import dev.feldmann.redstonegang.wire.game.base.database.addons.tables.records.MultiserverTpRequestsRecord;
import dev.feldmann.redstonegang.wire.game.base.database.addons.tables.records.MultiserverTpTeleportsRecord;
import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.invsync.events.PlayerSyncLocationEvent;
import dev.feldmann.redstonegang.wire.plugin.events.NetworkMessageEvent;
import dev.feldmann.redstonegang.wire.plugin.events.update.UpdateEvent;
import dev.feldmann.redstonegang.wire.plugin.events.update.UpdateType;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.util.*;

public class MultiServerTpAddon extends Addon {


    private HashMap<UUID, MultiserverTpRequestsRecord> expirations = new HashMap<>();

    public int teleportRequestExpireSeconds = 60 * 2;

    // Caso o cara caia no meio do teleport pra desvalidar o pedido de teleport
    public int teleportExpireSeconds = 20;

    private MultiServerTpDB db;

    // Meti um enum aqui pq dps eu quero botar pra amigos
    public EnumConfig<TpaAvailability> TPA_AVAILABILITY;

    public PermissionDescription TPA_PERMISSION;
    public PermissionDescription TPA_DIRECT_PERMISSION;

    public MultiServerTpAddon(String database) {
        db = new MultiServerTpDB(database, this);
    }

    @Override
    public void onEnable() {
        TPA_AVAILABILITY = new EnumConfig<TpaAvailability>(generateConfigName("tpa_availability"), TpaAvailability.Ligado);
        addConfig(TPA_AVAILABILITY);
        TPA_PERMISSION = new PermissionDescription("Solicitar Teleporte", generatePermission("request"), "Pode solicitar teleporte para outros jogadores!");
        TPA_DIRECT_PERMISSION = new PermissionDescription("TEleporte Direto", generatePermission("direct"), "Pode teleportar para outros jogadores sem solicitar!");
        addOption(TPA_PERMISSION, TPA_DIRECT_PERMISSION);
        registerCommand(
                new CmdTp(this),
                new CmdTpa(this),
                new CmdTpr(this),
                new CmdTpc(this)
        );
    }

    @EventHandler
    public void updateEvent(UpdateEvent ev) {
        if (ev.getType() == UpdateType.SEC_1) {
            Iterator<Map.Entry<UUID, MultiserverTpRequestsRecord>> iter = this.expirations.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<UUID, MultiserverTpRequestsRecord> next = iter.next();
                MultiserverTpRequestsRecord value = next.getValue();
                if (value.getRequestExpire().before(new Date())) {
                    if (db.existsRequest(value.getId())) {
                        Player online = Bukkit.getPlayer(next.getKey());
                        if (online != null && online.isOnline()) {
                            C.info(online, "Seu pedido de teleporte para %% expirou!", getUser(value.getRequested()));
                        }
                        iter.remove();
                    }
                }
            }
        }
    }

    public boolean hasTeleportPermission(Player player) {
        return getUser(player).hasPermission(this.TPA_DIRECT_PERMISSION);
    }

    public void sendRequest(Player requester, User requested) {
        User user = getUser(requester);
        if (!isAvailable(requested)) {
            C.error(requester, "Jogador não está online!");
            return;
        }
        if (!canSend(user, requested)) {
            C.error(requester, "O jogador %% está com pedidos de tp desligado!", requested);
            return;
        }
        if (db.getRequest(user) != null) {
            C.error(requester, "Você já tem um pedido em andamento!");
            return;
        }
        C.info(requester, "Você solicitou teleporte para %%!", requested);
        db.addRequest(user, requested);
        requested.sendMessage(
                C.msgText(MsgType.INFO, "O jogador %% está querendo teleportar para você use %cmd% para aceitar ou %cmd% para recusar!",
                        user,
                        "/tpa " + requester.getDisplayName(),
                        "/tpr " + requester.getDisplayName()));
    }

    public void acceptTpRequest(Player accepter, User requester) {
        boolean hasRequest = db.hasRequest(requester, getUser(accepter));
        if (hasRequest) {
            if (!isAvailable(requester)) {
                C.error(accepter, "O jogador está indisponível!");
                return;
            }
            C.info(accepter, "Você aceitou o teleporte de %%!", requester);
            network().sendMessageLocal("tpa", getServer().getIdentifier(), "accept", getUser(accepter).getId(), requester.getId());
            db.deleteRequest(requester, getUser(accepter));
        } else {
            C.error(accepter, "O jogador %% não solicitou para teleportar para você!", requester);
        }
    }

    public void declineTeleport(Player pRequested, User requester) {
        boolean hasRequest = db.hasRequest(requester, getUser(pRequested));
        if (!hasRequest) {
            C.error(pRequested, "O jogador %% não solicitou para teleportar para você!", requester);
            return;
        }
        db.deleteRequest(requester, getUser(pRequested));
        if (isAvailable(requester)) {
            requester.sendMessage(C.msgText(MsgType.ERROR, "O jogador %% recusou seu pedido de teleporte!", pRequested));
        }
        C.info(pRequested, "Você recusou o pedido de teleporte de %% !", requester);
    }

    public void cancelRequest(Player requester) {
        MultiserverTpRequestsRecord request = db.getRequest(getUser(requester));
        if (request == null) {
            C.error(requester, "O jogador %% não solicitou para teleportar para você!", requester);
            return;
        }
        request.delete();
        C.info(requester, "Você cancelou o pedido de teleporte !", requester);
    }

    @EventHandler
    public void handleNetwork(NetworkMessageEvent ev) {
        if (ev.is("tpa")) {
            String identifier = ev.get(0);
            if (server().getIdentifier().equals(identifier)) {
                String action = ev.get(1);
                if (action.equalsIgnoreCase("accept")) {
                    int target_id = ev.getInt(2);
                    int user_id = ev.getInt(3);
                    runSync(() -> {
                        Player onlinePlayer = RedstoneGangSpigot.getOnlinePlayer(user_id);
                        if (onlinePlayer != null) {
                            User target = getUser(target_id);
                            C.info(onlinePlayer, "Seu pedido de TP para o jogador %% foi aceito!", target);
                            teleport(onlinePlayer, target, true);
                        }
                    });
                }
            }
        }
    }


    public boolean canSend(User requester, User requested) {
        TpaAvailability config = requested.getConfig(TPA_AVAILABILITY);
        return config == TpaAvailability.Ligado;
    }

    public boolean isAvailable(User requested) {
        return requested.isOnline()
                && getServer().getIdentifier().equals(requested.getServerIdentifier());
    }

    @EventHandler
    public void sync(PlayerSyncLocationEvent ev) {
        MultiserverTpTeleportsRecord teleport = db.getTeleport(getUser(ev.getPlayer()));
        if (teleport != null) {
            if (teleport.getTargetServer() != null && teleport.getTargetServer().equals(RedstoneGang.instance().getNomeServer())) {
                Integer target = teleport.getTarget();
                Player onlinePlayer = RedstoneGangSpigot.getOnlinePlayer(target);
                if (onlinePlayer != null && onlinePlayer.isOnline()) {
                    if (teleport.getIsRequest()) {
                        C.info(onlinePlayer, "O jogador %% chegou em você!", onlinePlayer);
                    } else {
                        if (hasTeleportPermission(onlinePlayer)) {
                            C.info(onlinePlayer, "O jogador %% teleportou para você!", onlinePlayer);
                        }
                    }
                    db.deleteTeleport(getUser(ev.getPlayer()));
                    C.info(ev.getPlayer(), "Você chegou em %%!", onlinePlayer);
                    ev.setSpawnLocation(onlinePlayer.getLocation());
                    ev.setOverrideTeleportLocation(true);
                }
            }
        }
    }

    public void teleport(Player player, User target, boolean request) {
        if (!isAvailable(target)) {
            C.error(player, "Jogador não está online!");
            return;
        }
        Player online = RedstoneGangSpigot.getOnlinePlayer(target.getId());
        if (online != null && online.isOnline()) {
            player.teleport(online);
        } else {
            db.addTeleport(getUser(player), target, target.getServer(), request);
            getServer().teleportToServer(player, target.getServer());
        }
    }


    private enum TpaAvailability {
        Ligado,
        Desligado,
    }

}
