package dev.feldmann.redstonegang.wire.game.games.other.login;


import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.ServerType;
import dev.feldmann.redstonegang.common.api.maps.responses.MapResponse;
import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.wire.RedstoneGangSpigot;
import dev.feldmann.redstonegang.wire.game.base.Server;
import dev.feldmann.redstonegang.wire.game.base.addons.world.WorldControlAddon;
import dev.feldmann.redstonegang.wire.game.games.other.login.cmds.CmdLogin;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import dev.feldmann.redstonegang.common.utils.msgs.MsgType;
import dev.feldmann.redstonegang.wire.utils.player.Title;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Login extends Server {

    private static final int MAX_ATTEMPTS = 5;
    HashMap<Integer, LoginData> data = new HashMap<>();

    @Override
    public void enable() {
        super.enable();
        addAddon(
                new WorldControlAddon()
        );
        registerListener(new Listeners(this));
        registerCommand(new CmdLogin(this));
    }


    @Override
    public void lateEnable() {
        super.lateEnable();
        MapResponse res = mapas().anyFromGame();
        if (res != null) {
            loadMapa(res);
        } else {
            setInvalid(true);
        }
    }

    public LoginData getData(Player p) {
        int id = RedstoneGang.getPlayer(p.getUniqueId()).getId();
        if (!data.containsKey(id)) {
            data.put(id, new LoginData(id));
        }
        return data.get(id);
    }

    public boolean clearData(Player p) {
        int id = RedstoneGang.getPlayer(p.getUniqueId()).getId();
        return data.remove(id) != null;
    }

    public void sendMessageLogin(Player p) {
        C.info(p, "Use o comando %% para logar no servidor!", "/login <senha>");
    }

    public void sendTitle(Player p) {
        LoginData data = getData(p);
        if (data.loggedIn) {
            Title.sendTitle(p, "§a§lLogado", "§fLogado com sucesso!", 0, 40, 0);
        } else {
            Title.sendTitle(p, "§cLogin", "§fUse /login <senha>", 0, 120, 1);
        }
    }

    public void login(Player player, String senha) {
        LoginData data = getData(player);
        if (data.loggedIn) {
            C.info(player, "Vocẽ já está logado!");
            return;
        }
        data.logging = true;
        String nome = player.getName();
        User user = RedstoneGangSpigot.getPlayer(player);
        C.info(player,"Logando...");
        scheduler().runAsync(() -> {
            boolean success = RedstoneGang.instance().webapi().auth().login(nome, senha);
            scheduler().runSync(() -> {
                if (success) {
                    data.loggedIn = true;
                    RedstoneGang.instance().network().sendMessageToType(ServerType.BUNGEE, "login", user.getId());
                    C.info(player, "Logado com sucesso! Aguarde...");
                    sendTitle(player);
                } else {
                    data.attempts++;
                    if (data.attempts >= MAX_ATTEMPTS) {
                        player.kickPlayer(C.msg(MsgType.ERROR, "Você errou a senha %% vezes seguidas!", MAX_ATTEMPTS));
                    }
                    C.error(player, "Senha incorreta (%%/%%)!", data.attempts, MAX_ATTEMPTS);
                }
                data.logging = false;
            });

        });

    }
}
