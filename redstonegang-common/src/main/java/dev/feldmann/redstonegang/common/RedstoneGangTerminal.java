package dev.feldmann.redstonegang.common;

import dev.feldmann.redstonegang.common.player.User;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.Timer;
import java.util.TimerTask;

public class RedstoneGangTerminal extends RedstoneGang {

    private Timer t;

    @Override
    public ServerType getServerType() {
        return ServerType.OUTROS;
    }

    @Override
    public void runRepeatingTask(Runnable r, int tempo) {
        if (t == null) {
            t = new Timer();
        }
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                r.run();
            }
        }, 50 * (long) tempo, 50 * (long) tempo);
    }

    @Override
    public void runSync(Runnable r) {
        r.run();
    }

    @Override
    public void runAsync(Runnable r) {
        new Thread(r).start();
    }

    @Override
    public void sendMessage(User player, TextComponent... txt) {

    }

    @Override
    public void shutdown(String cause) {

    }

}
