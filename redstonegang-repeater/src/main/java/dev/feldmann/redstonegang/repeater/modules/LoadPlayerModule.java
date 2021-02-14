package dev.feldmann.redstonegang.repeater.modules;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.api.Response;
import dev.feldmann.redstonegang.common.api.mojang.responses.HasPaidResponse;
import dev.feldmann.redstonegang.common.db.jooq.redstonegang_app.tables.records.AccountsRecord;
import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.common.player.permissions.PermissionServer;
import dev.feldmann.redstonegang.common.player.permissions.PermissionValue;
import dev.feldmann.redstonegang.repeater.RedstoneGangBungee;
import dev.feldmann.redstonegang.repeater.Repeater;
import dev.feldmann.redstonegang.repeater.events.PlayerLoadEvent;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.*;
import net.md_5.bungee.event.EventHandler;

public class LoadPlayerModule extends Module {
    @EventHandler
    public void onLoginRequest(final PreLoginEvent event) {
        ServerInfo teleportServer = Repeater.getInstance().servers().getDefault();
        // Se não tem servidor pro nego conectar já kicka na hora
        if (teleportServer == null) {
            event.setCancelReason(TextComponent.fromLegacyText("Ocorreu um erro ao conectar-se ao servidor!"));
            event.setCancelled(true);
            return;
        }
        register(event);
        Repeater.getInstance().getProxy().getScheduler().runAsync(Repeater.getInstance(), () -> {
            String playerName = event.getConnection().getName();
            Repeater.log(event.getConnection().getName());
            event.getConnection().setOnlineMode(false);
            Response<HasPaidResponse> response = redstonegang().webapi().mojang().hasPaid(playerName);
            if (!response.hasFailed()) {
                boolean original = false;
                switch (response.collect().status) {
                    case 1:
                        original = true;
                    default:
                }

                if (!playerName.equals(response.collect().name))
                {
                    event.setCancelReason(TextComponent.fromLegacyText(ChatColor.RED + "[ATENÇÃO] Seu nick correto é: " + ChatColor.WHITE + ChatColor.BOLD + response.collect().name + ChatColor.RED + ", as letras do seu nick são Case-sensitive (Sensível a Maiúsculas e minúsculas)."));
                    event.setCancelled(true);
                    return;
                }

                event.getConnection().setOnlineMode(original);
                event.completeIntent(Repeater.getInstance());
                return;
            }
            Repeater.log("Check has paid for player " + playerName + " failed!");
            event.setCancelReason(TextComponent.fromLegacyText("Ocorreu um erro nos servidores de Login, tente mais tarde!"));
            event.setCancelled(true);
            event.completeIntent(Repeater.getInstance());
        });
    }

    @EventHandler
    public void login(LoginEvent event) {
        register(event);
        Repeater.runAsync(() -> {
            RedstoneGang.instance().user().cache.clearCache(event.getConnection().getUniqueId());
            boolean onlineMode = event.getConnection().isOnlineMode();
            if (!onlineMode && Repeater.getInstance().servers().getLogin() == null) {
                // Não tem server de login aberto
                event.setCancelReason(TextComponent.fromLegacyText("§cOcorreu um erro nos servidores de Login(2), tente mais tarde!"));
                event.setCancelled(true);
                complete(event);
                return;

            }
            User player = RedstoneGang.getPlayer(event.getConnection().getUniqueId());
            // Se o nego ja ta logado em outro bungee
            if (player != null && player.getBungee() != null && !player.getBungee().equals(RedstoneGang.instance().getNomeServer())) {
                event.setCancelReason(TextComponent.fromLegacyText("§cEsta conta já está logada!"));
                event.setCancelled(true);
                complete(event);
                return;
            }
            // Não ta cadastrado no mine
            if (player == null) {
                if (!event.getConnection().isOnlineMode()) {
                    player = handlePirateFirstLogin(event);
                    if (player == null) {
                        complete(event);
                        return;
                    }
                } else {
                    player = redstonegang().databases().users().insertPlayer(event.getConnection().getUniqueId(), event.getConnection().getName());
                    checkDeveloper(player);
                }
            } else {
                if (event.getConnection().isOnlineMode()) {
                    String nick = event.getConnection().getName();
                    String dbnick = player.getName();
                    if (!nick.equals(dbnick)) {
                        User unick = RedstoneGangBungee.getPlayer(nick);
                        if (unick == null)
                        {
                            redstonegang().databases().users().updateName(player.getId(), nick);
                            if (player.getAccountId() != null)
                            {
                                AccountsRecord user_account = redstonegang().databases().users().getAccount(dbnick);
                                Long forum_id = user_account.getForumId();
                                if (forum_id != null)
                                {
                                    redstonegang().webapi().xenforo().updateUsername(forum_id, nick);
                                    user_account.setUsername(nick);
                                    user_account.store();
                                }
                            }
                        }
                        else
                        {
                            event.setCancelReason(TextComponent.fromLegacyText("§cJá existe um jogador com este nick, por favor troque seu nick por outro!"));
                            event.setCancelled(true);
                        }
                    }
                }
            }

            RedstoneGang.instance().debug("Called Load Event");
            plugin().getProxy().getPluginManager().callEvent(new PlayerLoadEvent(player, event));
            complete(event);
        });
    }

    private User handlePirateFirstLogin(LoginEvent event) {
        AccountsRecord account = RedstoneGang.instance().databases().users().getAccount(event.getConnection().getName());
        if (account == null) {
            event.setCancelReason(TextComponent.fromLegacyText("§cPara jogar no servidor é preciso se registrar! \nEntre em https://bit.ly/rgregistro"));
            event.setCancelled(true);
            RedstoneGang.instance().debug("Mandando pro registro " + event.getConnection().getUniqueId().toString() + " com o nome " + event.getConnection().getName());
            return null;
        }
        if (account.getPremium()) {
            event.setCancelReason(TextComponent.fromLegacyText("§cUma conta premium está registrada com este nick!"));
            event.setCancelled(true);
            return null;
        }
        // Primeiro login inserindo o nego no database
        return RedstoneGang.instance().databases().users().insertPlayer(event.getConnection().getUniqueId(), event.getConnection().getName(), account.getId().intValue());
    }


    public void checkDeveloper(User pl) {
        if (RedstoneGang.instance().ENVIRONMENT.equals("local")) {
            if (pl.getName().equalsIgnoreCase("Feldmann") || pl.getName().equalsIgnoreCase("net32")) {
                pl.permissions()
                        .addPermission(PermissionServer.GERAL, "*", PermissionValue.ALLOW);
            }
        }
    }

    @EventHandler
    public void disconnect(PlayerDisconnectEvent ev) {
        User user = RedstoneGangBungee.getPlayer(ev.getPlayer());
        RedstoneGang.instance().user().updateServer(user, null, null);
        RedstoneGang.instance().user().updateIdentifier(user, null);

    }

    @Override
    public void onDisable() {
        RedstoneGang.instance().databases().users().resetBungeePlayers(RedstoneGang.instance().getNomeServer());
    }

    @Override
    public void onEnable() {
        RedstoneGang.instance().databases().users().resetBungeePlayers(RedstoneGang.instance().getNomeServer());
    }
}
