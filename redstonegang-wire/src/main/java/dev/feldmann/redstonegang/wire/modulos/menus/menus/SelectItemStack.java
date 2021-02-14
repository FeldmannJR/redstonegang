package dev.feldmann.redstonegang.wire.modulos.menus.menus;

import dev.feldmann.redstonegang.wire.Wire;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.modulos.menus.buttons.DummyButton;
import dev.feldmann.redstonegang.wire.utils.items.ItemBuilder;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public abstract class SelectItemStack extends Menu {

    public SelectItemStack() {
        this(null);
    }

    public SelectItemStack(ItemStack it) {
        super("Selecionar Item", 1);

        setMoveItens(true);
        for (int x = 0; x < 9; x++) {
            if (x != 4) {
                add(x, new DummyButton(ItemBuilder.item(Material.STAINED_GLASS_PANE).color(DyeColor.RED).name(" ").build()));
            }
        }
        if (it != null) {
            addItemStack(it.clone());
        }
        addClose((player) -> {
            ItemStack sel = null;
            List<ItemStack> non = getNonButtonItems();
            if (!non.isEmpty()) {
                sel = non.get(0);
            }
            final ItemStack self = sel;
            Bukkit.getScheduler().scheduleSyncDelayedTask(Wire.instance, () -> {
                seleciona(self);
            });
        });
    }

    public abstract void seleciona(ItemStack it);


}
