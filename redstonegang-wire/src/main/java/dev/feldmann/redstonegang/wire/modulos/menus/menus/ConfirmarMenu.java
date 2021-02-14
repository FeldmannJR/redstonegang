package dev.feldmann.redstonegang.wire.modulos.menus.menus;

import dev.feldmann.redstonegang.wire.modulos.menus.Button;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu3x3;
import dev.feldmann.redstonegang.wire.modulos.menus.buttons.DummyButton;
import dev.feldmann.redstonegang.wire.utils.items.CItemBuilder;
import dev.feldmann.redstonegang.wire.utils.items.ItemBuilder;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public abstract class ConfirmarMenu extends Menu3x3 {

    public ConfirmarMenu(ItemStack item, String oq, String desc) {
        super("Confirmar " + oq);
        add(1, new DummyButton(CItemBuilder.item(item).name(C.item(oq)).lore(desc.split("@")).build()));
        add(8, new Button(ItemBuilder.item(Material.EMERALD_BLOCK).name(ChatColor.GREEN + "Confirmar").build()) {
            @Override
            public void click(Player p, Menu m, ClickType click) {
                p.closeInventory();
                confirmar(p);
            }
        });
        add(6, new Button(ItemBuilder.item(Material.REDSTONE_BLOCK).name(ChatColor.RED + "Recusar").build()) {
            @Override
            public void click(Player p, Menu m, ClickType click) {
                p.closeInventory();
                recusar(p);
            }
        });

    }


    public abstract void confirmar(Player p);

    public abstract void recusar(Player p);

}
