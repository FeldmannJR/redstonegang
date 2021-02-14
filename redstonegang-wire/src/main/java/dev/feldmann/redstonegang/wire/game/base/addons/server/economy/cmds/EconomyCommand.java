package dev.feldmann.redstonegang.wire.game.base.addons.server.economy.cmds;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.PlayerNameArgument;
import dev.feldmann.redstonegang.wire.game.base.addons.server.economy.EconomyAddon;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EconomyCommand extends RedstoneCmd {

    private static final PlayerNameArgument PLAYER = new PlayerNameArgument("jogador", true);

    EconomyAddon addon;

    public EconomyCommand(EconomyAddon addon) {
        super("dinheiro", "comando de economia", PLAYER);
        this.addon = addon;
        setAlias("moedas", "money");
        setPermission("rg.economy.cmd.money");
        addCmd(new Give());
    }

    protected EconomyAddon getAddon() {
        return addon;
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        User pl = null;
        if (args.isPresent(PLAYER)) {
            pl = args.get(PLAYER);
        } else {
            if (sender instanceof Player) {
                pl = RedstoneGang.getPlayer(((Player) sender).getUniqueId());
            } else {
                C.error(sender, "Jogador não encontrado!");
                return;
            }
        }
        final int pId = pl.getId();
        final User fpl = pl;
        RedstoneGang.instance.runAsync(() -> {
            final double money = addon.getDb().get(pId);
            RedstoneGang.instance.runSync(() -> {
                C.info(sender, "%% tem ## !", fpl, money);

            });
        });

    }
}
