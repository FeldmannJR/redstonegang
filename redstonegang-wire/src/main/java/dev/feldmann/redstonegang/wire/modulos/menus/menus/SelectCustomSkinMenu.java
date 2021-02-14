package dev.feldmann.redstonegang.wire.modulos.menus.menus;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.wire.modulos.menus.Button;
import dev.feldmann.redstonegang.wire.modulos.menus.FilterableMultipageMenu;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.modulos.menus.MultiplePageMenu;
import dev.feldmann.redstonegang.wire.utils.items.CItemBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.Arrays;
import java.util.List;

public abstract class SelectCustomSkinMenu extends MultiplePageMenu<String> implements FilterableMultipageMenu<String> {
    private List<String> skins;

    public SelectCustomSkinMenu(String[] skins) {
        super("Selecionar Skin");
        this.skins = Arrays.asList(skins);
    }

    @Override
    public Button getButton(String ob, int page) {
        return new Button(CItemBuilder.head("rg-"+ob).name("" + ob).descBreak("Clique aqui para selecionar esta skin!").build()) {
            @Override
            public void click(Player p, Menu m, ClickType click) {
                selectSkin(p, ob);
            }
        };

    }

    @Override
    public List<String> getAll() {
        return skins;
    }

    @Override
    public boolean filter(String s, String key) {
        return s.contains(key);
    }

    public abstract void selectSkin(Player p, String skin);
}
