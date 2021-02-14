package dev.feldmann.redstonegang.wire.base.cmds.defaults.cash;

import dev.feldmann.redstonegang.wire.base.cmds.HelpCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.defaults.cash.subs.Add;
import dev.feldmann.redstonegang.wire.base.cmds.defaults.cash.subs.Get;
import dev.feldmann.redstonegang.wire.base.cmds.defaults.cash.subs.Remove;
import dev.feldmann.redstonegang.wire.base.cmds.defaults.cash.subs.Set;
import dev.feldmann.redstonegang.wire.base.cmds.types.ConsoleCmd;
import org.bukkit.command.ConsoleCommandSender;

public class CashAdm extends ConsoleCmd {
    public CashAdm() {
        super("cashadm", "comandos para setar cash");
        addCmd(new Get());
        addCmd(new Set());
        addCmd(new Remove());
        addCmd(new Add());
        addCmd(new HelpCmd());
    }

    @Override
    public void command(ConsoleCommandSender player, Arguments args) {

    }
}
