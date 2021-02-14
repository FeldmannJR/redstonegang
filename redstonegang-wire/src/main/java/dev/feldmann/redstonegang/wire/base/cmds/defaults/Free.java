package dev.feldmann.redstonegang.wire.base.cmds.defaults;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.types.ConsoleCmd;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.command.ConsoleCommandSender;

public class Free extends ConsoleCmd {
    public Free() {
        super("free", "Ve quanto de ram está usando");
    }

    @Override
    public void command(ConsoleCommandSender sender, Arguments args) {
        long usage = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long total = Runtime.getRuntime().totalMemory();
        usage /= 1024 * 1024;
        total /= 1024 * 1024;
        C.info(sender, "%%/%%", usage, total);
    }
}
