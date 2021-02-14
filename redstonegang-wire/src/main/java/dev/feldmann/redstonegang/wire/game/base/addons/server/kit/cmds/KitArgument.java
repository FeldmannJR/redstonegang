package dev.feldmann.redstonegang.wire.game.base.addons.server.kit.cmds;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.Argument;
import dev.feldmann.redstonegang.wire.game.base.addons.server.kit.Kit;
import dev.feldmann.redstonegang.wire.game.base.addons.server.kit.KitsAddon;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class KitArgument extends Argument<Kit> {

    KitsAddon manager;

    public KitArgument(KitsAddon manager, boolean optional) {
        super("kit", optional);
        this.manager = manager;
    }

    @Override
    public String getErrorMessage(String input) {
        return input + " não é um kit!";
    }

    @Override
    public Kit process(CommandSender cs, int index, String[] args) {
        if (index >= args.length) {
            return null;
        }
        String arg = args[index];
        if (manager.getKits().contains(arg)) {
            return manager.getKit(arg);
        }
        return null;
    }

    @Override
    public List<String> autoComplete(CommandSender cs, String start) {
        List<String> kits = new ArrayList<>();
        for (String k : manager.getKits()) {
            if (manager.hasPermission(cs, manager.getKit(k))) {
                kits.add(k);
            }
        }
        return kits;
    }
}
