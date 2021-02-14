package dev.feldmann.redstonegang.wire.game.base.addons.server.economy.cmds.adm;

import dev.feldmann.redstonegang.wire.base.cmds.HelpCmd;
import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.game.base.addons.server.economy.EconomyAddon;
import org.bukkit.command.CommandSender;

public class EconomyAdmCommand extends RedstoneCmd {
    private EconomyAddon manager;

    public EconomyAdmCommand(EconomyAddon manager) {
        super("moneyadm", "comandos de economia para admin");
        setPermission("rg.economy.staff.cmd.moneyadm");
        this.manager = manager;
        addCmd(new Set());
        addCmd(new HelpCmd());


    }

    protected EconomyAddon getManager() {
        return manager;
    }

    @Override
    public void command(CommandSender sender, Arguments args) {

    }
}
