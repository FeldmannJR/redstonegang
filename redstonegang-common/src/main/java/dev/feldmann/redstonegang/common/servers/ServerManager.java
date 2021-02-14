package dev.feldmann.redstonegang.common.servers;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.ServerType;
import dev.feldmann.redstonegang.common.db.jooq.redstonegang_common.tables.records.ServersRecord;

import java.sql.Timestamp;

public class ServerManager {


    boolean on = false;

    public ServerManager() {
        RedstoneGang.instance().runRepeatingTask(this::updateAlive, 20 * 30);
    }

    public void updateAlive() {
        if (!on) return;
        ServersRecord record = database().fetchOrCreate(RedstoneGang.instance().NAME);
        record.setAlive(new Timestamp(System.currentTimeMillis()));
        record.store();
    }

    public void serverTurnOn(boolean joinable, String game) {
        String name = RedstoneGang.instance().NAME;
        boolean dev = RedstoneGang.instance().DEV;
        Timestamp now = new Timestamp(System.currentTimeMillis());
        ServersRecord record = database().fetchOrCreate(name);
        record.setAlive(now);
        record.setStarted(now);
        record.setGame(game);
        record.setOnline((byte) 1);
        record.setDev((byte) (dev ? 1 : 0));
        record.setJoinable((byte) (joinable ? 1 : 0));
        record.setHost(name);
        record.store();
        on = true;
        RedstoneGang.instance().network().sendMessageToType(ServerType.BUNGEE, "server", "on", name);
    }

    public void changeGame(String game) {
        if (!on) return;
        String name = RedstoneGang.instance().NAME;
        ServersRecord record = database().fetchOrCreate(name);
        record.setGame(game);
        record.store();
    }

    public void serverTurnOff() {
        String name = RedstoneGang.instance().NAME;
        ServersRecord record = database().fetchOrCreate(name);
        record.setOnline((byte) 0);
        record.setStopped(new Timestamp(System.currentTimeMillis()));
        record.store();
        on = false;
        RedstoneGang.instance().network().sendMessageToType(ServerType.BUNGEE, "server", "off", name);
    }

    public ServerDB database() {
        return RedstoneGang.instance().databases().servers();
    }
}
