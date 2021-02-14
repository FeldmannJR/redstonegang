package dev.feldmann.redstonegang.wire.game.base.addons.server.economy.cmds;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.*;
import dev.feldmann.redstonegang.wire.game.base.addons.server.economy.EconomyAddon;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.IntegerArgument;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.OnlinePlayerArgument;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Give extends RedstoneCmd {

    private final static OnlinePlayerArgument PLAYER = new OnlinePlayerArgument("name", false);
    private final static IntegerArgument QUANTIDADE = new IntegerArgument("quantidade", 5, 100000);

    public Give() {
        super("dar", "transferi money para um jogador", PLAYER, QUANTIDADE);
        setAlias("pay", "give");
        setPermission("rg.economy.cmd.money.give");
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        Player p = (Player) sender;
        User rp = RedstoneGang.getPlayer(p.getUniqueId());
        EconomyAddon manager = ((EconomyCommand) getParent()).getAddon();
        if (!manager.hasWithMessage(p, args.get(QUANTIDADE))) {
            return;
        }
        manager.remove(rp.getId(), args.get(QUANTIDADE));
        manager.add(args.get(PLAYER).getId(), args.get(QUANTIDADE));
        manager.getDb().addTransfer(rp.getId(), args.get(PLAYER).getId(), args.get(QUANTIDADE));
        C.info(p, " Você enviou ## para %% !", args.get(QUANTIDADE), args.get(PLAYER));
    }

    @Override
    public boolean canConsoleExecute() {
        return false;
    }
}
