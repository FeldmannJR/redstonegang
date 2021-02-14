package dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.ferreiro;

import org.bukkit.inventory.ItemStack;

public class FerreiroCraft {
    int preco;
    long tempo;
    ItemStack[] precisa;
    ItemStack result;

    public FerreiroCraft(int preco, long tempo, ItemStack result, ItemStack... precisa) {
        this.preco = preco;
        this.tempo = tempo;
        this.precisa = precisa;
        this.result = result;
    }

    public ItemStack getResult() {
        return result;
    }

    public ItemStack[] getPrecisa() {
        return precisa;
    }

    public int getPreco() {
        return preco;
    }

    public long getTempo() {
        return tempo;
    }
}
