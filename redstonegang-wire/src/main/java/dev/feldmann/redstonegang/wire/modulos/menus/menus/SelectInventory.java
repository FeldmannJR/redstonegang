package dev.feldmann.redstonegang.wire.modulos.menus.menus;

import dev.feldmann.redstonegang.wire.Wire;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.function.BiConsumer;

public class SelectInventory extends Menu {
    public SelectInventory(String titulo, int linhas, ItemStack[] inicial, BiConsumer<Player, ItemStack[]> closeAction) {
        super(titulo, linhas);
        setMoveItens(true);
        if (inicial != null) {
            for (int x = 0; x < inicial.length; x++) {
                ItemStack it = inicial[x];
                if (it != null) {
                    addItemStack(x, it);
                }
            }
        }
        addClose((player) -> {
            ItemStack[] conts = getContents();
            ItemStack[] retorno = new ItemStack[conts.length];
            for (int x = 0; x < retorno.length; x++) {
                if (getButton(x) == null) {
                    retorno[x] = conts[x];
                }
            }
            Bukkit.getScheduler().scheduleSyncDelayedTask(Wire.instance, () -> {
                closeAction.accept(player, retorno);
            });
        });
    }


}
