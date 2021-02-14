package dev.feldmann.redstonegang.repeater.modules;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.db.jooq.redstonegang_common.tables.records.ServersRecord;
import dev.feldmann.redstonegang.common.servers.ServerDB;
import dev.feldmann.redstonegang.repeater.Repeater;

import dev.feldmann.redstonegang.repeater.events.NetworkMessageEvent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.event.EventHandler;

import java.net.InetSocketAddress;

public class ServerManager extends Module {

    public ServerDB database;

    public ServerManager() {
        this.database = RedstoneGang.instance().databases().servers();
    }

    @Override
    public void onEnable() {
        loadFromDB();
    }

    public void loadFromDB() {
        for (ServersRecord server : database.all()) {
            if (server.getOnline() == 1) {
                turnServerOn(server);
            }
        }
    }


    public void turnServerOff(ServersRecord server) {
        String name = server.getName();
        removeFromBungee(server.getName());
    }

    public void turnServerOn(ServersRecord server) {
        String name = server.getName();
        String host = server.getHost();
        ServerInfo current = Repeater.getInstance().getProxy().getServers().get(name);
        if (current != null) {
            // Adiciona dnv azar
            removeFromBungee(name);
        }
        ServerInfo info = Repeater.getInstance().getProxy().constructServerInfo(name, new InetSocketAddress(host, 25565), "Motd " + host, false);
        Repeater.getInstance().getProxy().getServers().put(name, info);
    }

    public void removeFromBungee(String nome) {
        Repeater.getInstance().sendToDefault(nome);
        Repeater.getInstance().getProxy().getServers().remove(nome);
    }

    public ServerInfo getDefault() {
        return Repeater.getInstance().getProxy().getServerInfo("Survival-Spawn");
    }

    public ServerInfo getLogin() {
        return Repeater.getInstance().getProxy().getServerInfo("Login");
    }

    @EventHandler
    public void networkMessage(NetworkMessageEvent ev) {
        if (ev.is("server")) {
            if (ev.get(0).equals("on") || ev.get(0).equals("off")) {
                String name = ev.get(1);
                ServersRecord record = RedstoneGang.instance().servers().database().get(name);
                if (record == null) {
                    return;
                }
                if (ev.get(0).equals("on")) {
                    turnServerOn(record);
                } else {
                    turnServerOff(record);
                }
            }
        }
    }


}
