package dev.feldmann.redstonegang.wire.game.base.addons.server.npcshops.cmds;

import dev.feldmann.redstonegang.wire.base.cmds.HelpCmd;
import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.game.base.addons.server.npcshops.NPCShopAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.npcshops.cmds.subs.*;
import dev.feldmann.redstonegang.wire.game.base.addons.server.npcshops.cmds.subs.*;
import org.bukkit.command.CommandSender;

public class LojaAdm extends RedstoneCmd {

    private NPCShopAddon manager;

    public LojaAdm(NPCShopAddon manager) {
        super("npcshop", "comando para criar lojas em npcs");
        this.manager = manager;
        setPermission("rg.npcshop.staff.cmd.npcshop");
        addCmd(new Create());
        addCmd(new Edit());
        addCmd(new Vende());
        addCmd(new Compra());
        addCmd(new Listar());
        addCmd(new Delete());
        addCmd(new HelpCmd());
    }

    @Override
    public void command(CommandSender sender, Arguments args) {

    }

    public NPCShopAddon getManager() {
        return manager;
    }

}
