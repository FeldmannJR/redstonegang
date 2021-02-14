package dev.feldmann.redstonegang.wire.utils;

import dev.feldmann.redstonegang.common.utils.encode.Base64;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

public class ItemSerializer {
    

    public static String itemStackToString(ItemStack[] itens) {
        if (itens == null) {
            return null;
        }
        if (itens.length == 0) {
            return null;
        }
        byte[] bytes = serializeItemStacks(itens);
        return Base64.encode(bytes);
    }

    public static ItemStack[] stringToItemStack(String s) {
        if (s == null) {
            return new ItemStack[0];
        }
        byte[] b = Base64.decode(s);
        if (b == null) {
            return new ItemStack[0];
        }
        return deserializeItemStacks(b);
    }


    public static ItemStack[] blobToItemStack(Blob b) {
        return deserializeItemStacks(BlobToBytes(b));
    }

    public static Blob itemStackToBlob(ItemStack[] i) {
        return BytesToBlob(serializeItemStacks(i));
    }


    private static byte[] BlobToBytes(Blob blob) {
        int blobLength;
        byte[] blobAsBytes = null;
        try {
            blobLength = (int) blob.length();
            blobAsBytes = blob.getBytes(1, blobLength);
            blob.free();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return blobAsBytes;
    }

    private static Blob BytesToBlob(byte[] bit) {
        try {
            return new javax.sql.rowset.serial.SerialBlob(bit);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static ItemStack[] deserializeItemStacks(byte[] b) {
        if (b == null) {
            return null;
        }
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(b);
            BukkitObjectInputStream bois = new BukkitObjectInputStream(bais);
            return (ItemStack[]) bois.readObject();
        } catch (Exception ex) {
            return null;
        }
    }

    public static ItemStack deserializeItemStack(byte[] b) {
        if (b == null) {
            return null;
        }
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(b);
            BukkitObjectInputStream bois = new BukkitObjectInputStream(bais);
            return (ItemStack) bois.readObject();
        } catch (Exception ex) {
            return null;
        }
    }

    public static byte[] serializeItemStack(ItemStack item) {
        if (item == null) return null;
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            BukkitObjectOutputStream bos = null;
            try {
                bos = new BukkitObjectOutputStream(os);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            bos.writeObject(item);
            return os.toByteArray();
        } catch (IOException ex) {
            return null;
        }
    }


    public static byte[] serializeItemStacks(ItemStack[] inv) {
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            BukkitObjectOutputStream bos = null;
            try {
                bos = new BukkitObjectOutputStream(os);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            bos.writeObject(inv);
            return os.toByteArray();
        } catch (IOException ex) {
            return null;
        }
    }
}
