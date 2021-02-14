package dev.feldmann.redstonegang.wire.game.base.addons.server.floatshop;

import dev.feldmann.redstonegang.common.player.permissions.PermissionDescription;
import dev.feldmann.redstonegang.wire.game.base.addons.server.floatshop.cmds.CmdFloatShop;
import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import dev.feldmann.redstonegang.wire.plugin.events.update.UpdateEvent;
import dev.feldmann.redstonegang.wire.plugin.events.update.UpdateType;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class FloatShopAddon extends Addon {


    public static final int horasDemanda = 3;

    public static final double defaultLowPercentage = 0.8;
    public static final double defaultHighPercentage = 1.2;
    public static final int defaultMaxAvailable = 0;

    public static final PermissionDescription SHOPADM_PERMISSION = new PermissionDescription("floats shop adm", "rg.floatshops.staff.edit", "Pode editar os shops de npcs", true);

    FloatShopDB db;
    List<FloatShop> shops;


    public FloatShopAddon(String database) {
        db = new FloatShopDB(this, database);
        shops = db.loadShops();
    }

    @Override
    public void onEnable() {
        addOption(SHOPADM_PERMISSION);
        registerCommand(new CmdFloatShop(this));
        registerListener(new FloatShopListener(this));
    }

    @Override
    public void onDisable() {
        for (FloatShop s : shops) {
            for (FloatItem item : s.itens) {
                item.transactions.saveCurrent();
            }
        }
    }

    @EventHandler
    public void updatePrices(UpdateEvent ev) {
        if (ev.getType() == UpdateType.MIN_1) {
            for (FloatShop s : shops) {
                for (FloatItem item : s.itens) {
                    item.doRegenTick();
                }

            }
        }
        if (ev.getType() == UpdateType.SEC_16) {
            for (FloatShop s : shops) {
                for (FloatItem item : s.itens) {
                    item.removeUnvalidTransactions();
                    item.refreshMod();
                }
            }
        }
    }


    public boolean hasShop(NPC npc) {
        for (FloatShop shop : shops) {
            if (npc.getUniqueId().equals(shop.getNpcUUID())) {
                return true;
            }
        }
        return false;
    }

    public void openShop(Player p, NPC n) {
        FloatShop shop = getShop(n);
        if (shop != null) {
            shop.getMenu(this).open(p);
        }
    }

    public FloatShop getShop(NPC npc) {
        for (FloatShop shop : shops) {
            if (npc.getUniqueId().equals(shop.getNpcUUID())) {
                return shop;
            }
        }
        return null;
    }

    public FloatShop createShop(NPC npc, int linhas, String permission) {
        FloatShop shop = new FloatShop(-1, npc.getUniqueId(), permission, linhas);
        shop.setItens(new ArrayList<>());
        db.saveShop(shop);
        shops.add(shop);
        return shop;
    }

    public FloatItem createItem(ItemStack item, FloatShop shop, double buyPrice, double sellPrice) {
        FloatItem it = new FloatItem(this, -1, item, sellPrice, buyPrice, defaultMaxAvailable / 3, defaultMaxAvailable);
        it.shopId = shop.getId();
        shop.addItem(it);
        db.saveItem(it);
        return it;
    }

    public FloatShop getShop(int shopId) {
        for (FloatShop shop : shops) {
            if (shop.getId() == shopId) {
                return shop;
            }
        }
        return null;
    }

    public void deleteItem(FloatItem item) {
        FloatShop shop = getShop(item.getShopId());
        shop.getItens().remove(item);
        db.deleteItem(item);
    }

    public void deleteShop(FloatShop shop) {
        db.deleteShop(shop);
        shops.remove(shop);
    }

    public void saveShop(FloatShop shop) {
        if (shop.isSaved())
            db.saveShop(shop);
    }
}
