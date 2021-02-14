package dev.feldmann.redstonegang.wire.game.base.addons.both.simplecmds.cmds.staff;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.RemainStringsArgument;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.StringArgument;
import dev.feldmann.redstonegang.wire.game.base.addons.both.simplecmds.SimpleCmd;
import dev.feldmann.redstonegang.wire.utils.items.ItemUtils;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AddLore extends SimpleCmd
{
    private static final StringArgument FRASE = new RemainStringsArgument("frase", false);

    public AddLore()
    {
        super("addlore", "Adiciona uma nova frase na lore do item.", "addlore", FRASE);
    }

    @Override
    public void command(Player p, Arguments a)
    {
        if (p.getItemInHand() == null || p.getItemInHand().getType() == Material.AIR)
        {
            C.error(p,"Coloque algum item na sua mão.");
            return;
        }
        String frase = a.get(FRASE);
        frase = ChatColor.translateAlternateColorCodes('&', frase);
        ItemStack is = p.getItemInHand().clone();
        is = ItemUtils.addLore(is, frase);
        p.setItemInHand(is);
        p.updateInventory();
        C.info(p,"Item %% adicionado frase %%.", is, frase);
    }
}
