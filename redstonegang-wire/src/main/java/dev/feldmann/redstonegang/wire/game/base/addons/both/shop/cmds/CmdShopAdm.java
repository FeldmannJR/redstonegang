package dev.feldmann.redstonegang.wire.game.base.addons.both.shop.cmds;

import dev.feldmann.redstonegang.wire.base.cmds.HelpCmd;
import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.ShopAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.cmds.criar.Criar;
import org.bukkit.command.CommandSender;

public class CmdShopAdm extends RedstoneCmd {

    ShopAddon addon;


    public CmdShopAdm(ShopAddon addon) {
        super(addon.getCommand() + "adm", "comando para editar o /" + addon.getCommand());
        addCmd(new Criar(addon));
        addCmd(new Editar(addon));
        addCmd(new Open(addon));
        addCmd(new HelpCmd());
        if (addon.getAliases() != null && addon.getAliases().length != 0) {
            String[] add = addon.getAliases().clone();
            for (int i = 0; i < add.length; i++) {
                add[i] = add[i] + "adm";
            }
            setAlias(add);
        }
        setPermission("rg.shop.staff.cmd." + addon.getCommand() + "adm");
        this.addon = addon;

    }


    @Override
    public void command(CommandSender sender, Arguments args) {

    }
}
