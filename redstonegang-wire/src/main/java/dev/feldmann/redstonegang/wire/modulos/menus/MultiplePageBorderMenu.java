package dev.feldmann.redstonegang.wire.modulos.menus;

import dev.feldmann.redstonegang.wire.modulos.menus.buttons.DummyButton;

import java.util.List;

public abstract class MultiplePageBorderMenu<T> extends MultiplePageMenu<T> {


    public MultiplePageBorderMenu(String titulo) {
        super(titulo, 28);
        this.addBorder();
    }

    @Override
    public void removeToNextPage() {
        List<Integer> slots = MenuHelper.buildHollowSquare(10, 43);
        slots.add(45);
        slots.add(53);
        for (Integer m : slots) {
            if (getButton(m) != null) {
                removeButton(m);
            }
        }
        return;
    }

    @Override
    public void loadPage(int page) {
        super.loadPage(page);
        if (getButton(45) == null) {
            add(45, new DummyButton(getBorderItemStack()));
        }
        if (getButton(53) == null) {
            add(53, new DummyButton(getBorderItemStack()));
        }

    }
}
