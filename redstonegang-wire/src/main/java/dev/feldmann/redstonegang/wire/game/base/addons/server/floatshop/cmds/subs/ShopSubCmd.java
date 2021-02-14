package dev.feldmann.redstonegang.wire.game.base.addons.server.floatshop.cmds.subs;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.Argument;
import dev.feldmann.redstonegang.wire.base.cmds.types.PlayerCmd;
import dev.feldmann.redstonegang.wire.game.base.addons.server.npcshops.NPCShopAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.floatshop.FloatShopAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.floatshop.cmds.CmdFloatShop;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.command.CommandSender;

abstract class ShopSubCmd extends PlayerCmd {


    boolean haveSelected;

    public ShopSubCmd(String name, String desc, boolean haveNpcSelected, Argument... args) {
        super(name, desc, args);
        this.haveSelected = haveNpcSelected;
    }

    @Override
    public boolean canExecute(CommandSender cs) {
        boolean b = super.canExecute(cs);
        if (!b) return b;
        if (haveSelected) {
            if (NPCShopAddon.getSelected(cs) == null) {
                C.error(cs, "Você precisa ter um npc selecionado para executar este comando! Use o comando %%", "/npc");
                return false;
            }
        }
        return b;
    }


    public FloatShopAddon getManager() {
        return ((CmdFloatShop) getParent()).getManager();
    }

    public NPC getSelected(CommandSender cs) {
        return NPCShopAddon.getSelected(cs);
    }
}
