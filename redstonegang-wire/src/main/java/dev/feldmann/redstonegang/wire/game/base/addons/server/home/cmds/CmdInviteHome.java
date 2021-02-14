package dev.feldmann.redstonegang.wire.game.base.addons.server.home.cmds;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.common.utils.Cooldown;
import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.OneOfThoseArgument;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.StringArgument;
import dev.feldmann.redstonegang.wire.game.base.addons.server.home.HomeEntry;
import dev.feldmann.redstonegang.wire.game.base.addons.server.home.HomeAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.home.HomeType;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdInviteHome extends RedstoneCmd {


    private static final StringArgument HOME = new StringArgument("home", false);
    private static final OneOfThoseArgument QUEM = new OneOfThoseArgument("quem", false, "ninguem", "todos", "clan");

    private HomeAddon manager;

    public CmdInviteHome(HomeAddon manager) {
        super("invitehome", "altera quem pode usar uma home", HOME, QUEM);
        this.manager = manager;
        setPermission("rg.home.cmd.invitehome");
        setCooldown(new Cooldown(3000));
    }

    @Override
    public boolean canConsoleExecute() {
        return false;
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        String home = args.get(HOME);
        User pl = RedstoneGang.getPlayer(((Player) sender).getUniqueId());
        new Thread(() -> {
            synchronized (sender) {
                HomeEntry entry = manager.getDb().loadHome(pl.getId(), home);
                if (entry == null) {
                    C.error(sender, "Home não encontrada! Use %% para ver suas homes!", "/homes");
                    return;
                }
                HomeType type;
                String msg = "";
                switch (args.get(QUEM)) {
                    case "ninguem":
                        type = HomeType.CLOSED;
                        msg = "Agora só você pode usar a home %%!";
                        break;
                    case "todos":
                        type = HomeType.OPEN;
                        msg = "Agora todos do servidor podem usar a home %%!";
                        break;
                    case "clan":
                        type = HomeType.CLAN;
                        msg = "Agora somente membros do seu clan podem usar a home %%!";
                        break;
                    default:
                        type = HomeType.CLOSED;
                }
                entry.setType(type);
                manager.getDb().saveHome(entry);
                C.info(sender, msg, entry.getName());

            }
        }
        ).start();
    }


}
