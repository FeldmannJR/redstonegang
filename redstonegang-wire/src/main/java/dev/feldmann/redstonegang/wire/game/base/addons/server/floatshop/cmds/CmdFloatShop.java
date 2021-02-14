package dev.feldmann.redstonegang.wire.game.base.addons.server.floatshop.cmds;

import dev.feldmann.redstonegang.wire.base.cmds.HelpCmd;
import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.game.base.addons.server.floatshop.FloatShopAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.floatshop.cmds.subs.*;
import dev.feldmann.redstonegang.wire.game.base.addons.server.floatshop.cmds.subs.*;
import org.bukkit.command.CommandSender;

public class CmdFloatShop extends RedstoneCmd {

    FloatShopAddon addon;

    public CmdFloatShop(FloatShopAddon addon) {
        super("floatshop", "gerencia os shops do spawn");
        setPermission(FloatShopAddon.SHOPADM_PERMISSION);
        this.addon = addon;
        addCmd(new Create());
        addCmd(new Add());
        addCmd(new Organizar());
        addCmd(new Editar());
        addCmd(new Deletar());
        addCmd(new SetLinhas());
        addCmd(new HelpCmd());
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
    }

    public FloatShopAddon getManager() {
        return addon;
    }

    @Override
    public boolean canConsoleExecute() {
        return false;
    }
}
