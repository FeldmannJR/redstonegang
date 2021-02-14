package dev.feldmann.redstonegang.wire.game.base.addons.server.home;

import dev.feldmann.redstonegang.common.player.config.types.BooleanConfig;
import dev.feldmann.redstonegang.common.player.permissions.GroupOption;
import dev.feldmann.redstonegang.common.player.permissions.PermissionDescription;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.home.cmds.*;
import dev.feldmann.redstonegang.wire.game.base.addons.server.home.cmds.*;
import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import org.bukkit.entity.Player;

public class HomeAddon extends Addon {

    public  PermissionDescription HOME_ADMIN;
    public static BooleanConfig SHOW_TELEPORT;


    private String databaseName;
    private HomeDB db;

    public HomeAddon(String databaseName) {
        this.databaseName = databaseName;
    }

    public GroupOption MAX_HOMES;


    @Override
    public void onEnable() {
        db = new HomeDB(databaseName);
        SHOW_TELEPORT = new BooleanConfig("ad_home_" + getServer().getIdentifier() + "_view_teleports", true);
        registerCommand(new CmdHome(this));
        registerCommand(new CmdSetHome(this));
        registerCommand(new CmdDeleteHome(this));
        registerCommand(new CmdHomes(this));
        registerCommand(new CmdInviteHome(this));
        HOME_ADMIN = new PermissionDescription("home admin", "rg.home.staff", "pode ir/deletar/listar a home dos outros", true);

        addOption(HOME_ADMIN);
        MAX_HOMES = new GroupOption("maximo de homes", "homes_maxHomes", "maximo de homes que um jogador pode setar", 0);
        addOption(MAX_HOMES);
        addConfig(SHOW_TELEPORT);
    }

    public HomeEntry getDefault(Player p) {
        return db.loadHome(getPlayerId(p), "casa");
    }

    public boolean canUse(Player p, HomeEntry entr) {
        if (entr.getOwner() == getPlayerId(p)) {
            return true;
        }
        if (getUser(p).hasPermission(HOME_ADMIN)) {
            return true;
        }
        if (entr.getType() == HomeType.OPEN) {
            return true;
        }
        if (entr.getType() == HomeType.CLAN) {
            ClanAddon c = a(ClanAddon.class);
            if (c != null) {
                if (c.getCache().hasClan(entr.getOwner())) {
                    if (c.getCache().hasClan(p)) {
                        return c.getCache().getMember(entr.getOwner()).getClanTag().equals(c.getCache().getMember(p).getClanTag());
                    }
                }
            }
        }

        return false;
    }

    public HomeDB getDb() {
        return db;
    }
}
