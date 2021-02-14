package dev.feldmann.redstonegang.wire.game.base.addons.both.customBlocks;

import dev.feldmann.redstonegang.wire.utils.json.RGson;
import dev.feldmann.redstonegang.wire.utils.location.BungeeBlock;
import com.google.gson.JsonObject;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public abstract class BlockData {

    protected BungeeBlock loc;
    protected boolean change = false;
    protected boolean ramOnly = false;
    public boolean deleted = false;

    public BlockData() {
        change = true;
    }

    public BlockData from(BungeeBlock loc) {
        this.loc = loc;
        change = true;
        return this;
    }

    public BlockData from(Block loc) {
        return from(BungeeBlock.fromBlock(loc));
    }

    public void setTo(Block b) {
        from(b);
        b.setType(getMaterial());

    }

    public void setRamOnly(boolean ramOnly) {
        this.ramOnly = ramOnly;
    }

    public boolean isRamOnly() {
        return ramOnly;
    }

    public BungeeBlock getLoc() {
        return loc;
    }

    public abstract void read(JsonObject jobj);

    public abstract void write(JsonObject obj);

    public abstract Material getMaterial();

    public ItemStack get(JsonObject obj, String key) {
        return RGson.gson().fromJson(obj.get(key), ItemStack.class);
    }

    public void set(JsonObject obj, String key, ItemStack item) {
        obj.add(key, RGson.gson().toJsonTree(item));
    }

    public void breakBlock(CustomBlocksAddon addon, BlockBreakEvent ev) {
    }

    public void interactBlock(CustomBlocksAddon addon, PlayerInteractEvent ev) {

    }
}
