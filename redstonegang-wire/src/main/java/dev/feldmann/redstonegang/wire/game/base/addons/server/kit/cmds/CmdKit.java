package dev.feldmann.redstonegang.wire.game.base.addons.server.kit.cmds;

import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.game.base.addons.server.kit.Kit;
import dev.feldmann.redstonegang.wire.game.base.addons.server.kit.KitsAddon;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdKit extends RedstoneCmd {


    KitsAddon manager;
    private KitArgument KIT;

    public CmdKit(KitsAddon k) {
        super("kit", "comando para pegar kits", new KitArgument(k, true));
        KIT = (KitArgument) this.getArgs().get(0);
        setPermission("rg.kit.cmd.kit");
        this.manager = k;
    }


    @Override
    public void command(CommandSender sender, Arguments args) {
        if (args.isPresent(KIT)) {
            Kit k = args.get(KIT);
            manager.giveKit((Player) sender, k);
        }else{
            manager.listar(sender);
        }
    }

    @Override
    public boolean canConsoleExecute() {
        return false;
    }
}
