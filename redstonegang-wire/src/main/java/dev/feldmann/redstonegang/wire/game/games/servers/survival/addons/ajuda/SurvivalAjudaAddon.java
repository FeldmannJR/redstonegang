package dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.ajuda;

import dev.feldmann.redstonegang.wire.game.base.objects.Addon;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.ajuda.pages.basic.*;
import dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.ajuda.pages.basic.*;
import dev.feldmann.redstonegang.wire.modulos.menus.Button;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class SurvivalAjudaAddon extends Addon {

    private Menu mainMenu;
    private List<AjudaPage> pages;
    private AjudaCommand command;


    @Override
    public void onEnable() {
        pages = new ArrayList<>();
        int slot = 0;
        addPage(new TerrenoPage(slot++));
        addPage(new MoneyPage(slot++));
        addPage(new ChatPage(slot++));
        addPage(new TeleportPage(slot++));
        addPage(new ClanPage(slot++));
        addPage(new JobsPage(slot++));
        addPage(new MinerarPage(slot++));
        buildMenu();
        command = new AjudaCommand(this);
        registerCommand(command);
    }

    public void addPage(AjudaPage page) {
        pages.add(page);
        page.setAddon(this);
    }


    public String getCommand(Class<? extends AjudaPage> classe) {
        AjudaPage page = findClass(classe);
        String slug = page.getCommandSlug();
        return "/" + command.getName() + " " + slug;
    }

    public AjudaPage findWithSlug(String slug) {
        return find((ajuda) -> ajuda.getCommandSlug().equals(slug));
    }

    public AjudaPage findClass(Class<? extends AjudaPage> target) {
        return find(ajuda -> ajuda.getClass() == (target));
    }

    public void openMainMenu(Player player) {
        mainMenu.open(player);
    }

    private AjudaPage find(Predicate<AjudaPage> function) {
        return findInList(pages, function);
    }


    public AjudaPage findInList(List<AjudaPage> list, Predicate<AjudaPage> function) {
        for (AjudaPage find : list) {
            if (function.test(find)) {
                return find;
            }
            AjudaPage found = findInList(find.getPages(), function);
            if (found != null) return found;
        }
        return null;
    }


    public void buildMenu() {
        mainMenu = new Menu("Ajuda", 1);
        for (AjudaPage page : pages) {
            mainMenu.add(page.getSlot(), new Button(page.generateIconItemstack()) {
                @Override
                public void click(Player p, Menu m, ClickType click) {
                    page.openPage(p);
                }
            });
        }
    }


}
