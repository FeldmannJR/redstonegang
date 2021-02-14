package dev.feldmann.redstonegang.wire.utils.world;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.TileEntity;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;

public class BlockSnapshot {
    private NBTTagCompound tag;
    private Material mat;
    private byte data;

    private BlockSnapshot(Material mat, byte data, NBTTagCompound tag) {
        this.tag = tag;
        this.mat = mat;
        this.data = data;
    }

    public void apply(Block b) {
        b.setTypeIdAndData(mat.getId(), data, true);
        applyNbt(b, tag);
        b.getState().update();
    }

    private void applyNbt(Block b, NBTTagCompound nbt) {
        CraftWorld cw = (CraftWorld) b.getWorld();
        TileEntity tile = cw.getTileEntityAt(b.getX(), b.getY(), b.getZ());
        if (tile != null) {
            nbt.setInt("x", b.getX());
            nbt.setInt("y", b.getY());
            nbt.setInt("z", b.getZ());
            tile.a(nbt);
            tile.update();
        }

    }

    public static BlockSnapshot getSnapshot(Block b) {
        CraftWorld cw = (CraftWorld) b.getWorld();
        TileEntity tileEntity = cw.getTileEntityAt(b.getX(), b.getY(), b.getZ());
        NBTTagCompound ntc = new NBTTagCompound();
        if (tileEntity != null) {
            tileEntity.b(ntc);
        }
        return new BlockSnapshot(b.getType(), b.getData(), ntc);
    }
}
