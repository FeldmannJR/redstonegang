package dev.feldmann.redstonegang.wire.game.base.addons.both.simplecmds.cmds.staff;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.StringArgument;
import dev.feldmann.redstonegang.wire.game.base.addons.both.simplecmds.SimpleCmd;
import dev.feldmann.redstonegang.wire.utils.items.ItemUtils;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Head extends SimpleCmd
{
    private static final StringArgument NAME = new StringArgument("nome", false);

    public Head()
    {
        super("head", "Cria o item cabeça do jogador solicitado.", "head", NAME);
    }


    @Override
    public void command(Player p, Arguments a)
    {
        if (p.getItemInHand().getType() != Material.AIR)
        {
            C.error(p,"Sua mão deve estar vazia!");
            return;
        }
        String name = a.get(NAME);
        ItemStack head = ItemUtils.getHead(name);
        p.setItemInHand(head);
        p.updateInventory();
        C.info(p,"Item %% foi colocado na sua mão.", p.getItemInHand().clone());
    }

    @Override
    public boolean canOverride()
    {
        return true;
    }

}

