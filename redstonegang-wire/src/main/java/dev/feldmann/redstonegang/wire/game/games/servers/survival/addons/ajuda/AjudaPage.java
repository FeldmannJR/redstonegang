package dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.ajuda;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface AjudaPage {

    ItemStack generateIconItemstack();

    int getSlot();

    void openPage(Player player);

    List<AjudaPage> getPages();

    String getCommandSlug();

    void setAddon(SurvivalAjudaAddon adodn);
}
