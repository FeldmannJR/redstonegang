package dev.feldmann.redstonegang.wire.game.base.objects;

import dev.feldmann.redstonegang.wire.Wire;
import dev.feldmann.redstonegang.wire.game.base.Server;


public abstract class API extends BaseListener {

    private Server sv;

    public abstract void onEnable();

    public abstract void onDisable();


    public Wire plugin() {
        return Wire.instance;
    }

    public void log(String log) {
        Wire.log(log);
    }

    public void setSv(Server sv) {
        this.sv = sv;
    }

    public Server getServer() {
        return sv;
    }
}
