package dev.feldmann.redstonegang.wire.game.base.addons.both.simplecmds.cmds.staff;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.RemainStringsArgument;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.StringArgument;
import dev.feldmann.redstonegang.wire.game.base.addons.both.simplecmds.SimpleCmd;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class NameItem extends SimpleCmd {

    private static final StringArgument NAME = new RemainStringsArgument("nome", false);

    public NameItem() {
        super("nomeitem", "Renomeia o item que o jogador tem na mão.", "nomeitem", NAME);
    }

    @Override
    public void command(Player p, Arguments a) {
        if (p.getItemInHand() == null || p.getItemInHand().getType() == Material.AIR)
        {
            C.info(p,"Coloque algum item na sua mão.");
            return;
        }
        String name = a.get(NAME);
        name = ChatColor.translateAlternateColorCodes('&', name);
        ItemStack oldIs = p.getItemInHand().clone();
        ItemStack is = p.getItemInHand().clone();
        ItemMeta im = p.getItemInHand().getItemMeta();
        im.setDisplayName(name);
        is.setItemMeta(im);
        p.setItemInHand(is);
        p.updateInventory();
        C.info(p, "Item %% alterado para %%.", oldIs, is);
    }

    @Override
    public boolean canOverride() {
        return true;
    }

}