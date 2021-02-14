package dev.feldmann.redstonegang.wire.game.base.addons.both.simplecmds.cmds.staff;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.*;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.IntegerArgument;
import dev.feldmann.redstonegang.wire.game.base.addons.both.simplecmds.SimpleCmd;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.ArrayList;
import java.util.List;

public class DelLore extends SimpleCmd
{
    private static final IntegerArgument LINHA = new IntegerArgument("linha", true, 1, Integer.MAX_VALUE);

    public DelLore()
    {
        super("dellore", "Deleta ou Lista frase da lore do item.", "dellore", LINHA);
    }

    @Override
    public void command(Player p, Arguments a)
    {
        if (p.getItemInHand() == null || p.getItemInHand().getType() == Material.AIR)
        {
            C.error(p,"Coloque algum item na sua mão.");
            return;
        }
        ItemStack is = p.getItemInHand().clone();
        if (!is.hasItemMeta())
        {
            C.error(p,"Item não contém lore!");
            return;
        }
        ItemMeta im = is.getItemMeta();
        if (!im.hasLore())
        {
            C.error(p,"Item não contém lore!");
            return;
        }
        List<String> lore = new ArrayList(im.getLore());
        boolean delLore = a.isPresent(LINHA);
        if (!delLore)
        {
            if (lore.isEmpty())
            {
                C.info(p, "Nenhuma linha foi encontrada.");
            }
            else
            {
                int x = 1;
                for (String slinha : lore)
                {
                    C.info(p, "Linha (%%): %%", x, slinha);
                    x++;
                }
            }
            return;
        }
        else
        {
            Integer linha = a.get(LINHA);
            int x = 1;
            String delLine = null;
            for (String slinha : lore)
            {
                if (x == linha)
                {
                    delLine = slinha;
                    break;
                }
                x++;
            }
            if (delLine == null)
            {
                C.error(p,"Linha não encontrada!");
                return;
            }
            lore.remove(delLine);
            im.setLore(lore);
            is.setItemMeta(im);
            p.setItemInHand(is);
            p.updateInventory();
            C.info(p, "Linha deletada do item %%.", is);
        }
    }
}
