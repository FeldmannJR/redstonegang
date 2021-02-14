package dev.feldmann.redstonegang.wire.modulos.menus.buttons;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.wire.modulos.anvilgui.AnvilGUI;
import dev.feldmann.redstonegang.wire.modulos.menus.Button;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public abstract class SelectUserButton extends Button {

    public SelectUserButton(ItemStack i) {
        super(i);
    }


    @Override
    public void click(Player pl, Menu m, ClickType click) {
        new AnvilGUI(pl, "", (p, string) -> {
            if (string.isEmpty() || string.length() < 3 || RedstoneGang.getPlayer(string) == null) {
                C.error(pl, "Jogador não encontrado!");
                reject(pl);
                return null;
            }
            accept(pl, RedstoneGang.getPlayer(string));
            return null;
        }, 30);
    }

    public abstract void reject(Player p);

    public abstract void accept(Player p, User target);
}
