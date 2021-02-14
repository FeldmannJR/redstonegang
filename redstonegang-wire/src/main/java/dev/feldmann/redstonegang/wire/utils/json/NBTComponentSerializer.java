package dev.feldmann.redstonegang.wire.utils.json;

import com.google.gson.*;
import net.minecraft.server.v1_8_R3.*;

import java.lang.reflect.Type;
import java.util.Set;

public class NBTComponentSerializer implements JsonSerializer<NBTTagCompound> {


    @Override
    public JsonElement serialize(NBTTagCompound nbt, Type type, JsonSerializationContext jsonSerializationContext) {
        if (nbt == null)
            return null;

        JsonObject obj = new JsonObject();
        Set<String> keys = nbt.c();
        for (String key : keys) {
            JsonElement el = serialize(nbt.get(key));
            if (el != null) {
                obj.add(key, el);
            }
        }
        return obj;
    }

    public JsonElement serialize(NBTBase value) {
        if (value instanceof NBTTagInt) {
            return new JsonPrimitive(((NBTTagInt) value).d());
        }
        if (value instanceof NBTTagDouble) {

            return new JsonPrimitive(((NBTTagDouble) value).g());
        }
        if (value instanceof NBTTagByte) {

            return new JsonPrimitive(((NBTTagByte) value).f());
        }
        if (value instanceof NBTTagShort) {

            return new JsonPrimitive(((NBTTagShort) value).e());
        }
        if (value instanceof NBTTagFloat) {

            return new JsonPrimitive(((NBTTagFloat) value).h());
        }
        if (value instanceof NBTTagLong) {

            return new JsonPrimitive(((NBTTagLong) value).c());
        }
        if (value instanceof NBTTagString) {
            return new JsonPrimitive(((NBTTagString) value).a_());
        }
        if (value instanceof NBTTagByteArray) {
            byte[] data = ((NBTTagByteArray) value).c();
            JsonArray ar = new JsonArray();
            for (int x = 0; x < data.length; x++) {
                ar.add(new JsonPrimitive(data[x]));
            }
            return ar;
        }
        if (value instanceof NBTTagIntArray) {
            int[] data = ((NBTTagIntArray) value).c();
            JsonArray ar = new JsonArray();
            for (int x = 0; x < data.length; x++) {
                ar.add(new JsonPrimitive(data[x]));
            }
            return ar;
        }

        if (value instanceof NBTTagList) {
            NBTTagList list = (NBTTagList) value;
            JsonArray array = new JsonArray();
            for (int x = 0; x < list.size(); x++) {
                NBTBase b = list.g(x);
                array.add(serialize(b));
            }
            return array;
        }

        if (value instanceof NBTTagCompound) {
            return serialize((NBTTagCompound) value, null, null);
        }

        return null;
    }
}
