package dev.feldmann.redstonegang.wire.game.base.addons.server.kit.menus;

import dev.feldmann.redstonegang.wire.game.base.addons.server.kit.Kit;
import dev.feldmann.redstonegang.wire.game.base.addons.server.kit.KitsAddon;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CriarKitMenu extends Menu {

    KitsAddon manager;
    String nome;
    long cd;

    public CriarKitMenu(KitsAddon manager, String nome, long cd) {
        super("Criando Kit " + nome, 3);
        this.manager = manager;
        this.nome = nome;
        this.cd = cd;
        setMoveItens(true);
        addClose(this::close);
    }

    public void close(Player p) {
        if (manager.getKits().contains(nome)) {
            C.error(p, "Ocorreu um erro!");
            return;
        }
        ItemStack[] contents = getContents();
        Kit k = new Kit(nome, (int) cd, contents);
        manager.addKit(k);
        C.info(p, "Kit %% criado com sucesso!", nome);
    }

}
