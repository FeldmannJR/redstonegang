package dev.feldmann.redstonegang.wire.game.base.addons.server.chestshops;

import dev.feldmann.redstonegang.common.utils.formaters.numbers.NumberUtils;
import dev.feldmann.redstonegang.wire.game.base.addons.both.customBlocks.BlockData;
import dev.feldmann.redstonegang.wire.modulos.language.lang.LanguageHelper;
import dev.feldmann.redstonegang.wire.utils.location.BungeeBlock;
import com.google.gson.JsonObject;
import org.bukkit.Material;
import org.bukkit.block.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


public class ChestShop extends BlockData {

    ItemStack item;
    int ownerId;
    int precoCompra = 0;
    int precoVenda = 0;

    public ChestShop() {

    }

    public ChestShop(BungeeBlock location, int owner, ItemStack item, Integer precoCompra, Integer precoVenda) {
        this.loc = location;
        this.item = item;
        this.precoCompra = precoCompra;
        this.precoVenda = precoVenda;
        this.ownerId = owner;
    }

    public void setItem(ItemStack item) {
        this.item = item;
        Block b = loc.toBlock();
        if (b != null) {
            if (b.getState() != null && b.getState() instanceof Sign) {
                Sign s = (Sign) b.getState();
                s.setLine(1, "§l" + item.getAmount());
                s.setLine(2, LanguageHelper.getShortItemName(item));
                s.update();
            }
        }
    }

    public void setPrecoCompra(int precoCompra) {
        this.precoCompra = precoCompra;

        updateSign();
    }

    public void setPrecoVenda(int precoVenda) {
        this.precoVenda = precoVenda;
        updateSign();
    }

    public void setAmount(int amount) {
        if (this.getItem() != null) {
            this.getItem().setAmount(amount);
            Block b = loc.toBlock();
            if (b != null) {
                if (b.getState() != null && b.getState() instanceof Sign) {
                    Sign s = (Sign) b.getState();
                    s.setLine(1, "§l" + item.getAmount());
                    s.update();
                }
            }
        }

    }

    private void updateSign() {
        String preco = "";
        if (getPrecoVenda() != 0) {
            preco = "C " + NumberUtils.convertToString(getPrecoVenda());
        }
        if (getPrecoCompra() != 0) {
            if (!preco.isEmpty()) {
                preco += " : ";
            }
            preco += NumberUtils.convertToString(getPrecoCompra()) + " V";
        }
        Block b = loc.toBlock();
        if (b != null) {
            if (b.getState() != null && b.getState() instanceof Sign) {
                Sign s = (Sign) b.getState();
                s.setLine(3, preco);
                s.update();
            }
        }

    }

    public boolean isValid() {
        return item != null && (precoCompra > 0 || precoVenda > 0);
    }

    public ItemStack getItem() {
        return item;
    }

    public Integer getPrecoCompra() {
        return precoCompra;
    }

    public BungeeBlock getSign() {
        return loc;
    }

    public Integer getPrecoVenda() {
        return precoVenda;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public Inventory findChest() {
        Block sign = this.loc.toBlock();
        for (BlockFace f : new BlockFace[]{BlockFace.NORTH, BlockFace.SOUTH, BlockFace.UP, BlockFace.DOWN, BlockFace.EAST, BlockFace.WEST}) {
            Block r = sign.getRelative(f);
            if (r.getType() == Material.CHEST || r.getType() == Material.TRAPPED_CHEST) {
                BlockState st = r.getState();
                if (st instanceof DoubleChest) {
                    return ((DoubleChest) st).getInventory();
                } else {
                    if (st instanceof Chest) {
                        return ((Chest) st).getInventory();
                    }
                }
            }
        }
        return null;
    }

    @Override
    public void read(JsonObject obj) {
        this.item = get(obj, "item");
        this.precoVenda = obj.get("precoVenda").getAsInt();
        this.precoCompra = obj.get("precoCompra").getAsInt();
        this.ownerId = obj.get("ownerId").getAsInt();

    }

    @Override
    public void write(JsonObject el) {
        el.addProperty("precoVenda", precoVenda);
        el.addProperty("precoCompra", precoCompra);
        el.addProperty("ownerId", ownerId);
        set(el, "item", item);
    }

    @Override
    public Material getMaterial() {
        return Material.WALL_SIGN;
    }
}
