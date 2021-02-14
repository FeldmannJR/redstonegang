package dev.feldmann.redstonegang.wire.game.base.addons.server.kit.menus;

import dev.feldmann.redstonegang.wire.game.base.addons.server.kit.Kit;
import dev.feldmann.redstonegang.wire.game.base.addons.server.kit.KitsAddon;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SetItensMenu extends Menu {

    KitsAddon manager;
    Kit k;

    public SetItensMenu(KitsAddon manager, Kit k) {
        super("Setando itens " + k.getName(), 3);
        this.manager = manager;
        this.k = k;
        addItemStack(k.getItems());
        setMoveItens(true);
        addClose(this::close);
    }

    public void close(Player p) {
        if (!k.isValid) {
            C.error(p, "Ocorreu um erro!");
            return;
        }
        ItemStack[] contents = getContents();
        k.setItems(contents);
        manager.addKit(k);
        C.info(p, "Setado os itens do kit %% com sucesso!", k.getName());
        k.editing = false;
    }

}
