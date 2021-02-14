package dev.feldmann.redstonegang.wire.modulos.citizens.trait;

import dev.feldmann.redstonegang.wire.utils.items.book.BookUtil;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.exception.NPCLoadException;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.trait.TraitName;
import net.citizensnpcs.api.util.DataKey;
import net.citizensnpcs.api.util.ItemStorage;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

@TraitName("book")
public class BookTrait extends Trait {

    private ItemStack book;

    public BookTrait() {
        super("book");
    }

    public void setBook(ItemStack book) {
        this.book = book;
    }

    @EventHandler
    public void click(NPCRightClickEvent ev) {
        if (ev.getNPC() == this.getNPC()) {
            if (this.book != null && this.book.getType() == Material.WRITTEN_BOOK) {
                BookUtil.openBook(book, ev.getClicker());
            }
        }
    }

    @Override
    public void load(DataKey key) throws NPCLoadException {
        if (key.keyExists("book")) {
            this.book = ItemStorage.loadItemStack(key.getRelative("book"));
        }
    }

    @Override
    public void save(DataKey key) {
        saveOrRemove(key.getRelative("book"), this.book);
    }

    private void saveOrRemove(DataKey key, ItemStack item) {
        if (item != null) {
            ItemStorage.saveItem(key, item);
        } else if (key.keyExists("")) {
            key.removeKey("");
        }

    }
}
