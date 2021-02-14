package dev.feldmann.redstonegang.wire.utils.json;

import dev.feldmann.redstonegang.wire.utils.ItemSerializer;
import com.google.gson.*;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Type;

public class ItemStackSerializer implements JsonSerializer<ItemStack>, JsonDeserializer<ItemStack> {
    @Override
    public ItemStack deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        ItemStack[] itemStacks = ItemSerializer.stringToItemStack(jsonElement.getAsString());
        if (itemStacks != null && itemStacks.length == 1) {
            return itemStacks[0];
        }
        return null;
    }

    @Override
    public JsonElement serialize(ItemStack itemStacks, Type type, JsonSerializationContext jsonSerializationContext) {

        return new JsonPrimitive(ItemSerializer.itemStackToString(new ItemStack[]{itemStacks}));
    }
}
