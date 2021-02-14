package dev.feldmann.redstonegang.wire.modulos.menus.menus;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.player.permissions.Group;
import dev.feldmann.redstonegang.common.player.permissions.PermissionServer;
import dev.feldmann.redstonegang.wire.modulos.menus.Button;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.utils.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.Collection;

public abstract class SelectGroupMenu extends Menu {

    public SelectGroupMenu() {
        this(false);
    }

    public SelectGroupMenu(boolean includeDefault) {
        super("Escolha o grupo", 6);
        Collection<Group> gr = RedstoneGang.instance.user().getPermissions().getGroups();
        for (Group g : gr) {
            if (!includeDefault || !g.isDefaultGroup()) {
                addNext(new Button(ItemBuilder.item(Material.DIAMOND).name("Grupo " + g.getNome()).lore("Prefix: " + g.getPrefix()).build()) {
                    @Override
                    public void click(Player p, Menu m, ClickType click) {
                        p.closeInventory();
                        input(p, g);
                    }
                });
            }
        }
    }


    public abstract void input(Player p, Group gr);
}
