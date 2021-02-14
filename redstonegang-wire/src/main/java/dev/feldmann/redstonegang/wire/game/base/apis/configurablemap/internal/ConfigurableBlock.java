package dev.feldmann.redstonegang.wire.game.base.apis.configurablemap.internal;

import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;

public class ConfigurableBlock extends ConfigurableEntry<Block> {


    public ConfigurableBlock(String keyName) {
        super(keyName);
    }

    @Override
    public void deserialize(JsonObject s) {
        if (s != null) {
            if (s.has("world")) {
                String world = s.get("world").getAsString();
                int x = s.get("x").getAsInt();
                int y = s.get("y").getAsInt();
                int z = s.get("z").getAsInt();
                World w = Bukkit.getWorld(world);
                if (w != null) {
                    this.value = w.getBlockAt(x, y, z);
                }
            }
        }
    }

    @Override
    public JsonObject serialize() {
        if (getValue() == null) {
            return null;
        }
        JsonObject obj = new JsonObject();
        if (getValue() != null) {
            obj.addProperty("world", getValue().getWorld().getName());
            obj.addProperty("x", getValue().getX());
            obj.addProperty("y", getValue().getY());
            obj.addProperty("z", getValue().getZ());

        }
        return obj;
    }
}
