package dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.ajuda.pages;

import dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.ajuda.AjudaPage;
import dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.ajuda.SurvivalAjudaAddon;
import dev.feldmann.redstonegang.wire.utils.items.book.BookUtil;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public abstract class AjudaBookPage implements AjudaPage {

    private int slot;
    private SurvivalAjudaAddon addon;

    public AjudaBookPage(int slot) {
        this.slot = slot;
    }

    public abstract List<String> generate();

    @Override
    public int getSlot() {
        return slot;
    }

    public SurvivalAjudaAddon getAddon() {
        return addon;
    }

    @Override
    public void openPage(Player player) {
        List<String> pages = generate();
        BookUtil.openBook(pages, player);
    }

    @Override
    public List<AjudaPage> getPages() {
        return Collections.emptyList();
    }

    @Override
    public void setAddon(SurvivalAjudaAddon addon) {
        this.addon = addon;
    }

    public net.md_5.bungee.api.ChatColor getBackgroundColor() {
        return ChatColor.BLACK;
    }
    public net.md_5.bungee.api.ChatColor getCommandColor(){
        return ChatColor.DARK_RED;
    }
}
