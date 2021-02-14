package dev.feldmann.redstonegang.wire.game.base.addons.both.customItems.cmds;

import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.game.base.addons.both.customItems.CustomItem;
import dev.feldmann.redstonegang.wire.game.base.addons.both.customItems.CustomItemsAddon;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdCustomItem extends RedstoneCmd {

    CustomItemsAddon manager;

    public CmdCustomItem(CustomItemsAddon manager) {
        super("customitems", "ve todos os itens customs do servidor");
        setPermission("rg.customitems.cmd");
        this.manager = manager;
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        Menu m = new Menu("Custom Items", 6);
        m.setMoveItens(true);
        for (CustomItem item : manager.getItens()) {
            m.addItemStack(item.generateItem(1));
        }
        m.open((Player) sender);
    }

    @Override
    public boolean canConsoleExecute() {
        return false;
    }
}
