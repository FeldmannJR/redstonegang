package dev.feldmann.redstonegang.wire.utils.items;

import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Tree;

import javax.xml.crypto.Data;

public class TreeUtils {


    public static TreeInfo getFrom(Block b) {
        if (b.getState().getData() instanceof Tree) {
            Tree tree = (Tree) b.getState().getData();
            return TreeInfo.valueOf(tree.getSpecies().name());
        }
        return null;

    }

    public enum TreeInfo {
        GENERIC(TreeSpecies.GENERIC),
        REDWOOD(TreeSpecies.REDWOOD),
        BIRCH(TreeSpecies.BIRCH),
        JUNGLE(TreeSpecies.JUNGLE),
        DARK_OAK(TreeSpecies.DARK_OAK),
        ACACIA(TreeSpecies.ACACIA);


        TreeSpecies species;


        TreeInfo(TreeSpecies species) {
            this.species = species;
        }


        public ItemStack getLeaves() {
            Material m = Material.LEAVES;
            byte data = species.getData();
            if (data > 3) {
                m = Material.LEAVES_2;
                data = (byte) (4 - data);
            }
            return new ItemStack(m, data);
        }

        public ItemStack getLog() {
            Material m = Material.LOG;
            byte data = species.getData();
            if (data > 3) {
                m = Material.LOG;
                data = (byte) (4 - data);
            }
            return new ItemStack(m, data);
        }

        public ItemStack getWood() {
            Material m = Material.WOOD;
            byte data = species.getData();
            return new ItemStack(m, data);
        }

        public ItemStack getSapling() {
            Material m = Material.SAPLING;
            byte data = species.getData();
            return new ItemStack(m, data);
        }


    }
}
