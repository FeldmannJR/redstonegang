/*
 *  _   __   _   _____   _____       ___       ___  ___   _____
 * | | |  \ | | /  ___/ |_   _|     /   |     /   |/   | /  ___|
 * | | |   \| | | |___    | |      / /| |    / /|   /| | | |
 * | | | |\   | \___  \   | |     / / | |   / / |__/ | | | |
 * | | | | \  |  ___| |   | |    / /  | |  / /       | | | |___
 * |_| |_|  \_| /_____/   |_|   /_/   |_| /_/        |_| \_____|
 *
 * Projeto feito por Carlos Andre Feldmann Junior, Isaias Finger e Gabriel Augusto Souza
 */
package dev.feldmann.redstonegang.wire.modulos.socketinfo;


import dev.feldmann.redstonegang.common.network.NetworkMessage;
import dev.feldmann.redstonegang.common.network.NetworkMessageBuilder;
import dev.feldmann.redstonegang.wire.Wire;
import dev.feldmann.redstonegang.wire.base.modulos.Modulo;
import dev.feldmann.redstonegang.wire.game.base.Games;
import dev.feldmann.redstonegang.wire.plugin.events.NetworkMessageEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;

import java.util.HashMap;


/**
 * Skype: junior.feldmann GitHub: https://github.com/feldmannjr Facebook:
 * https://www.facebook.com/carlosandre.feldmannjunior
 *
 * @author Feldmann
 */
public class ServerTypeStatusAPI extends Modulo {

    private static String prefixo = "todos";


    @EventHandler
    public void socket(NetworkMessageEvent ev) {
        //System.out.println(ev.getMsg());
        ServerInfo info = getInfoFromString(ev.getMsg());
        if (info != null) {
            Bukkit.getPluginManager().callEvent(new ReceivedServerInfoEvent(info));
        }
    }

    public static void send(ServerInfo info) {

        NetworkMessageBuilder builder = new NetworkMessageBuilder();
        builder.add(info.id, info.game.name(), info.name, info.online, info.maxonline, info.owner, info.status.name());
        for (String key : info.vars.keySet()) {
            builder.add(key + "=" + info.vars.get(key));
        }
        Wire.instance.redstonegang().network().sendMessage(builder.build());

    }

    public ServerInfo getInfoFromString(NetworkMessage s) {
        if (s == null) {
            return null;
        }
        if (!s.has(6)) {
            return null;
        }
        int id = s.getInt(0).get();
        String nomeEnum = s.get(1);
        String name = s.get(2);
        int online = s.getInt(3).get();
        int max = s.getInt(4).get();
        int owner = s.getInt(5).get();
        String type = s.get(6);
        HashMap<String, String> vars = new HashMap();

        for (int x = 7; x < s.getLength(); x++) {
            String[] keyvalue = s.get(x).split("=");
            if (keyvalue.length == 2) {
                vars.put(keyvalue[0], keyvalue[1]);
            }
        }
        try {
            ServerInfo info = new ServerInfo();
            info.game = Games.valueOf(nomeEnum);
            info.id = id;
            info.maxonline = max;
            info.online = online;
            info.name = name;
            info.owner = owner;
            info.status = ServerStatus.valueOf(type);
            return info;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }


    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }
}
