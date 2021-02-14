package dev.feldmann.redstonegang.wire.utils.json;

import com.google.gson.*;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;


import java.lang.reflect.Type;

public class VisualItemSerializer implements JsonSerializer<ItemStack> {
    @Override
    public JsonElement serialize(ItemStack itemStacks, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject el = new JsonObject();
        el.addProperty("material", itemStacks.getType().name());
        el.addProperty("id", itemStacks.getType().getId());
        el.addProperty("data", itemStacks.getDurability());
        el.addProperty("amount", itemStacks.getAmount());

        net.minecraft.server.v1_8_R3.ItemStack nms = CraftItemStack.asNMSCopy(itemStacks);
        NBTTagCompound tag = nms.getTag();
        if (tag != null) {
            JsonElement nbt = jsonSerializationContext.serialize(tag);
            el.add("nbt", nbt);
        }
        return el;
    }
}
