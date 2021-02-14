package dev.feldmann.redstonegang.wire.utils.items.book;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class BookStackUtil
{
    public static boolean hasBookMeta(ItemStack item)
    {
        if (item == null) {
            return false;
        }
        Material type = item.getType();
        if (type == Material.WRITTEN_BOOK) {
            return true;
        }
        return type == Material.BOOK_AND_QUILL;
    }

    public static boolean isBookMetaEmpty(ItemStack item)
    {
        if (item == null) {
            return true;
        }
        BookMeta meta = getBookMeta(item);
        return isBookMetaEmpty(meta);
    }

    public static boolean isBookMetaEmpty(BookMeta meta)
    {
        if (meta == null) {
            return true;
        }
        if (meta.hasTitle()) {
            return false;
        }
        if (meta.hasAuthor()) {
            return false;
        }
        return !meta.hasPages();
    }

    public static BookMeta getBookMeta(ItemStack item)
    {
        if (item == null) {
            return null;
        }
        ItemMeta meta = item.getItemMeta();
        if (!(meta instanceof BookMeta)) {
            return null;
        }
        return (BookMeta) meta;
    }

    public static boolean unLock(ItemStack item)
    {
        if (item == null) {
            return false;
        }
        if (item.getType() == Material.BOOK_AND_QUILL) {
            return true;
        }
        item.setType(Material.BOOK_AND_QUILL);
        return true;
    }

    public static boolean lock(ItemStack item)
    {
        if (item == null) {
            return false;
        }
        if (item.getType() == Material.WRITTEN_BOOK) {
            return true;
        }
        item.setType(Material.WRITTEN_BOOK);
        return true;
    }

    public static boolean isLocked(ItemStack item)
    {
        if (item == null) {
            return false;
        }
        return item.getType() == Material.WRITTEN_BOOK;
    }

    public static boolean isUnlocked(ItemStack item)
    {
        if (item == null) {
            return false;
        }
        return item.getType() == Material.BOOK_AND_QUILL;
    }

    public static String getTitle(ItemStack item)
    {
        BookMeta meta = getBookMeta(item);
        if (meta == null) {
            return null;
        }
        if (!meta.hasTitle()) {
            return null;
        }
        return meta.getTitle();
    }

    public static boolean setTitle(ItemStack item, String title)
    {
        BookMeta meta = getBookMeta(item);
        if (meta == null) {
            return false;
        }
        meta.setTitle(title);
        return item.setItemMeta(meta);
    }

    public static boolean isTitleEquals(ItemStack item, String title)
    {
        String actualTitle = getTitle(item);
        if (actualTitle == null) {
            return title == null;
        }
        return actualTitle.equals(title);
    }

    public static String getAuthor(ItemStack item)
    {
        BookMeta meta = getBookMeta(item);
        if (meta == null) {
            return null;
        }
        if (!meta.hasAuthor()) {
            return null;
        }
        return meta.getAuthor();
    }

    public static boolean setAuthor(ItemStack item, String author)
    {
        BookMeta meta = getBookMeta(item);
        if (meta == null) {
            return false;
        }
        meta.setAuthor(author);
        return item.setItemMeta(meta);
    }

    public static boolean isAuthorEqualsId(ItemStack item, String author)
    {
        String actualAuthor = getAuthor(item);
        if (actualAuthor == null) {
            return author == null;
        }
        return actualAuthor.equalsIgnoreCase(author);
    }

    public static List<String> getPages(ItemStack item)
    {
        BookMeta meta = getBookMeta(item);
        if (meta == null) {
            return null;
        }
        if (!meta.hasPages()) {
            return null;
        }
        return meta.getPages();
    }

    public static boolean setPages(ItemStack item, List<String> pages)
    {
        BookMeta meta = getBookMeta(item);
        if (meta == null) {
            return false;
        }
        meta.setPages(pages);
        return item.setItemMeta(meta);
    }

    public static boolean isPagesEquals(ItemStack item, List<String> pages)
    {
        List actualPages = getPages(item);
        if (actualPages == null) {
            return pages == null;
        }
        return actualPages.equals(pages);
    }

    public static boolean setDisplayName(ItemStack item, String newDisplayName)
    {
        if (item == null) {
            return false;
        }
        ItemMeta meta = item.getItemMeta();
        String currentDisplayName = meta.getDisplayName();
        meta.setDisplayName(newDisplayName);
        return item.setItemMeta(meta);
    }

    public static boolean clear(ItemStack item)
    {
        item.setDurability((short) 0);
        item.setType(Material.BOOK_AND_QUILL);
        item.setItemMeta(null);
        return true;
    }

    public static boolean isCleared(ItemStack item)
    {
        return (item.getDurability() == 0) && (item.getType() == Material.BOOK_AND_QUILL) && (!item.hasItemMeta());
    }

    public static boolean containsFlag(ItemStack item, String flag)
    {
        if (flag == null) {
            return false;
        }
        if (!item.hasItemMeta()) {
            return false;
        }
        ItemMeta meta = item.getItemMeta();
        if (!meta.hasLore()) {
            return false;
        }
        List lore = meta.getLore();
        return lore.contains(flag);
    }

    public static boolean addFlag(ItemStack item, String flag)
    {
        if (flag == null) {
            return false;
        }
        if (containsFlag(item, flag)) {
            return false;
        }
        ItemMeta meta = item.getItemMeta();
        if (meta.hasLore()) {
            List lore = meta.getLore();
            lore.add(flag);
            meta.setLore(lore);
        }
        return item.setItemMeta(meta);
    }

    public static boolean removeFlag(ItemStack item, String flag)
    {
        if (flag == null) {
            return false;
        }
        if (!containsFlag(item, flag)) {
            return false;
        }
        ItemMeta meta = item.getItemMeta();
        if (!meta.hasLore()) {
            return false;
        }
        List lore = meta.getLore();
        lore.remove(flag);
        if (lore.size() == 0) {
            meta.setLore(null);
        } else {
            meta.setLore(lore);
        }
        return item.setItemMeta(meta);
    }

}
