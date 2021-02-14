package dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.ajuda.pages;

import dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.ajuda.AjudaPage;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import org.bukkit.entity.Player;

public abstract class AjudaMenuPage extends Menu implements AjudaPage {

    private int slot;

    public AjudaMenuPage(String titulo, int linhas, int slot) {
        super(titulo, linhas);
        this.slot = slot;
    }

    @Override
    public int getSlot() {
        return slot;
    }

    @Override
    public void openPage(Player player) {
        open(player);
    }
}
