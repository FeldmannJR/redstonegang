package dev.feldmann.redstonegang.wire.game.base.addons.server.floatshop.cmds.subs;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.game.base.addons.server.floatshop.FloatShop;
import dev.feldmann.redstonegang.wire.modulos.menus.menus.ConfirmarMenu;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Deletar extends ShopSubCmd {


    public Deletar() {
        super("deletar", "remove a loja do npc", true);
    }

    @Override
    public void command(Player p, Arguments args) {
        NPC selected = getSelected(p);
        if (!getManager().hasShop(selected)) {
            C.error(p, "Este npc não tem uma float shop nele!");
            return;
        }
        FloatShop shop = getManager().getShop(selected);
        new ConfirmarMenu(new ItemStack(Material.BARRIER), "Remover Shop", "Tem certeza?") {
            @Override
            public void confirmar(Player p) {
                if (getManager().getShop(shop.getId()) != null) {
                    getManager().deleteShop(shop);
                    C.info(p,"Shop Removido!");
                }
            }

            @Override
            public void recusar(Player p) {
                p.closeInventory();
            }
        }.open(p);
    }
}
