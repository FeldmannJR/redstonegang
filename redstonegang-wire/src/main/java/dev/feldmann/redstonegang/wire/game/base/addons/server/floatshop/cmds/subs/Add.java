package dev.feldmann.redstonegang.wire.game.base.addons.server.floatshop.cmds.subs;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.DoubleArgument;
import dev.feldmann.redstonegang.wire.game.base.addons.server.floatshop.FloatItem;
import dev.feldmann.redstonegang.wire.game.base.addons.server.floatshop.FloatShop;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Add extends ShopSubCmd {

    private static DoubleArgument PRECO_COMPRA = new DoubleArgument("preco compra", 1, Double.MAX_VALUE);
    private static DoubleArgument PRECO_VENDA = new DoubleArgument("preco venda", 1, Double.MAX_VALUE);

    public Add() {
        super("additem", "adiciona um item ao shop", true, PRECO_COMPRA, PRECO_VENDA);
    }

    @Override
    public void command(Player p, Arguments args) {
        NPC selected = getSelected(p);
        if (!getManager().hasShop(selected)) {
            C.error(p, "Este npc não tem uma float shop nele!");
            return;
        }
        ItemStack item = p.getItemInHand();
        if (item == null || item.getType()== Material.AIR) {
            C.error(p, "Você precisa estar com o item que vai adicionar na mão!");
            return;
        }
        FloatShop shop = getManager().getShop(selected);
        if (!shop.hasEmptySlot()) {
            C.error(p, "Esta loja não tem espaço para colocar o item!");
            return;
        }
        FloatItem fitem = getManager().createItem(item.clone(), shop, args.get(PRECO_COMPRA), args.get(PRECO_VENDA));
        C.info(p, "Item adicionado a loja!");
    }
}
