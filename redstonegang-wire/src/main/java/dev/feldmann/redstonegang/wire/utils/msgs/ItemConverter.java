package dev.feldmann.redstonegang.wire.utils.msgs;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class ItemConverter {

    public static String convertItemStackToJson(ItemStack itemStack) {
        net.minecraft.server.v1_8_R3.ItemStack nms = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound nbt = new NBTTagCompound();
        nms.save(nbt);
        return nbt.toString();
    }

    public static BaseComponent[] itemStackToBaseComponent(ItemStack it) {
        return new BaseComponent[]{new TextComponent(convertItemStackToJson(it))};
    }

    public static HoverEvent hoverItem(ItemStack item) {
        return new HoverEvent(HoverEvent.Action.SHOW_ITEM, itemStackToBaseComponent(item));
    }
}
