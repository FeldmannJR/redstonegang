package dev.feldmann.redstonegang.wire.modulos.menus;

import dev.feldmann.redstonegang.wire.modulos.menus.buttons.SelectStringButton;
import dev.feldmann.redstonegang.wire.utils.items.CItemBuilder;
import dev.feldmann.redstonegang.wire.utils.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public abstract class MultiplePageMenu<T> extends Menu {

    public int perPage = 45;
    protected int currentPage = 0;
    private String currentFilter = null;

    public MultiplePageMenu(String titulo, int perPage) {
        super(titulo, 6);
        this.perPage = perPage;
    }

    public MultiplePageMenu(String titulo) {
        super(titulo, 6);
    }

    public abstract Button getButton(T ob, int page);

    public abstract List<T> getAll();

    public Collection<T> getPage(int page) {
        List<T> all = getFiltered();
        List<T> pages = new ArrayList();
        int start = perPage * page;
        int termina = start + perPage;
        if (all.size() < termina) {
            termina = all.size();
        }
        for (int x = start; x < termina; x++) {
            pages.add(all.get(x));
        }
        return pages;
    }

    public int getPages() {
        List<T> a = getFiltered();
        int x = a.size() / perPage;
        if (a.size() % perPage != 0) {
            x++;
        }
        return x;
    }

    private List<T> getFiltered() {
        List<T> all = getAll();
        if (this.currentFilter != null) {
            return all.stream().filter((el) -> {
                if (this instanceof FilterableMultipageMenu) {
                    return ((FilterableMultipageMenu) this).filter(el, this.currentFilter);
                }
                return true;
            }).collect(Collectors.toList());
        } else {
            return all;
        }
    }

    protected void addNextButton(Button btn) {
        addNext(btn);
    }

    public void removeToNextPage() {
        Iterator<Button> iterator = getButtons().iterator();
        while (iterator.hasNext()) {
            Button next = iterator.next();
            if (next.getSlot() > 45 && next.getSlot() < 53) {
                continue;
            }
            removeButton(next);
        }
    }

    public void loadPage(int page) {
        removeToNextPage();
        for (T t : getPage(page)) {
            addNextButton(getButton(t, page));
        }
        if (page > 0) {
            add(45, new Button(ItemBuilder.item(Material.DIODE).name("§eVoltar").lore("§fClique aqui para", "voltar para a pagina " + page + ".").build()) {
                @Override
                public void click(Player p, Menu m, ClickType click) {
                    loadPage(page - 1);
                }
            });
        } else if (page == 0) {
            if (getPreviousButton() != null) {
                add(45, getPreviousButton());
            }
        }
        if (page < (getPages() - 1)) {
            add(53, new Button(ItemBuilder.item(Material.DIODE).name("§eProximo").lore("§fClique aqui para", "ir para a pagina " + (page + 2) + ".").build()) {
                @Override
                public void click(Player p, Menu m, ClickType click) {
                    loadPage(page + 1);
                }
            });
        }
        if (this instanceof FilterableMultipageMenu) {
            if (this.currentFilter != null) {
                removeFilterButton(49);
            } else {
                addFilterButton(49);
            }
        }
    }

    public Button getPreviousButton() {
        return null;
    }

    private void addFilterButton(int slot) {
        add(slot, new SelectStringButton(CItemBuilder.item(Material.NAME_TAG).name("Filtrar").descBreak("Clique aqui para escolher filtrar!").build()) {
            @Override
            public void accept(String string, Player p) {
                if (string.isEmpty()) {
                    MultiplePageMenu.super.open(p);
                    return;
                }
                MultiplePageMenu.this.currentFilter = string;
                MultiplePageMenu.this.open(p);
            }
        });
    }

    private void removeFilterButton(int slot) {
        add(slot, new Button(CItemBuilder.item(Material.BARRIER).name("Remover Filtro").descBreak("Clique aqui para remover o filtro!").build()) {
            @Override
            public void click(Player p, Menu m, ClickType click) {
                MultiplePageMenu.this.currentFilter = null;
                MultiplePageMenu.this.open(p);
            }
        });
    }


    public void open(Player p, int page) {
        loadPage(page);
        super.open(p);
        currentPage = page;
    }

    @Override
    public void open(Player p) {
        this.open(p, 0);
    }

}
