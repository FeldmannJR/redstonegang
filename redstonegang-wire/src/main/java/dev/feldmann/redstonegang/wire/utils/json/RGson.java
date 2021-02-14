package dev.feldmann.redstonegang.wire.utils.json;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.apache.commons.io.IOUtils;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class RGson {


    private static GsonBuilder getDefault() {
        GsonBuilder b = new GsonBuilder();
        b.registerTypeHierarchyAdapter(ItemStack.class, new ItemStackSerializer());
        return b;
    }

    public static GsonBuilder getVisual() {
        GsonBuilder b = new GsonBuilder();
        b.registerTypeHierarchyAdapter(ItemStack.class, new VisualItemSerializer());
        b.registerTypeHierarchyAdapter(NBTTagCompound.class, new NBTComponentSerializer());
        return b;
    }

    public static GsonBuilder createBuilder() {
        return getDefault();
    }

    private static Gson gson = null;
    private static Gson visual = null;

    private static JsonParser parser;

    public static JsonParser parser() {
        if (parser == null) {
            parser = new JsonParser();
        }
        return parser;
    }

    public static JsonElement parse(String str) {
        return parser().parse(str);
    }

    public static JsonElement parse(File f) {
        try {
            return parser().parse(new JsonReader(new FileReader(f)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static Gson gson() {
        if (gson == null) {
            gson = getDefault().create();
        }
        return gson;
    }

    public static Gson visual() {
        if (visual == null) {
            visual = getVisual().create();
        }
        return visual;
    }


    public static JsonObject loadFromUrl(String url) {
        try {
            String content = IOUtils.toString(new URL(url), StandardCharsets.UTF_8);
            if (content == null) {
                return null;
            }
            JsonElement json = parse(content);
            if (json != null) {
                return json.getAsJsonObject();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }


}
