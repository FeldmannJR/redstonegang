package dev.feldmann.redstonegang.wire.game.base.addons.both.changeserver;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.db.jooq.redstonegang_common.tables.records.ServersRecord;
import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.common.player.permissions.PermissionDescription;
import dev.feldmann.redstonegang.common.servers.ServerManager;
import dev.feldmann.redstonegang.wire.game.base.addons.both.changeserver.cmds.ServidorCmd;
import dev.feldmann.redstonegang.wire.game.base.objects.Addon;

import java.util.List;
import java.util.stream.Collectors;

public class ChangeServerAddon extends Addon {


    List<String> servers = null;
    long lastUpdate = -1;

    @Override
    public void onEnable() {
        registerCommand(new ServidorCmd(this));
        for (String server : getValidServers()) {
            PermissionDescription desc = new PermissionDescription("Tp Server " + server, generatePermission("server." + server), "Pode teleportar para o server " + server);
            addOption(desc);
        }
    }


    public List<String> getValidServers() {
        if (lastUpdate == -1 || lastUpdate + 60000 < System.currentTimeMillis()) {
            updateServers();
        }
        return servers;
    }

    public void updateServers() {
        List<ServersRecord> servers = RedstoneGang.instance().servers().database().all();
        this.servers = servers.stream().map((r) -> r.getName().toLowerCase()).collect(Collectors.toList());
        lastUpdate = System.currentTimeMillis();

    }

    public boolean isServerOnline(String server) {
        return getValidServers().contains(server.toLowerCase());
    }

    public boolean hasPermission(User user, String server) {
        return user.permissions().hasPermission(generatePermission("server." + server));
    }

}

