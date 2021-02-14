package dev.feldmann.redstonegang.wire.game.base.addons.server.chestshops;

import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.common.player.config.types.BooleanConfig;
import dev.feldmann.redstonegang.common.player.permissions.PermissionDescription;
import dev.feldmann.redstonegang.wire.RedstoneGangSpigot;
import dev.feldmann.redstonegang.wire.game.base.addons.both.customBlocks.BlockData;
import dev.feldmann.redstonegang.wire.game.base.addons.both.customBlocks.CustomBlocksAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.economy.EconomyAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.notificacoes.Notification;
import dev.feldmann.redstonegang.wire.game.base.addons.server.notificacoes.NotificationType;
import dev.feldmann.redstonegang.wire.game.base.addons.server.notificacoes.Notifications;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.Land;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.LandAddon;
import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.chestshops.menus.EditShop;
import dev.feldmann.redstonegang.wire.game.base.addons.server.chestshops.menus.ViewShop;

import dev.feldmann.redstonegang.wire.game.base.objects.annotations.Dependencies;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import dev.feldmann.redstonegang.wire.utils.json.RGson;
import dev.feldmann.redstonegang.wire.utils.items.InventoryHelper;
import dev.feldmann.redstonegang.wire.utils.location.BungeeBlock;
import dev.feldmann.redstonegang.common.utils.msgs.MsgType;
import com.google.gson.JsonObject;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@Dependencies(hard = {CustomBlocksAddon.class, EconomyAddon.class, Notifications.class}, soft = LandAddon.class)
public class ChestShopAddon extends Addon implements NotificationType {

    public PermissionDescription CREATE_SHOPS = null;
    private static final String shopLine = "§9[LOJA]";
    private static final String SHOP_DATA = "PLAYER_SHOP";

    public BooleanConfig SHOW_MESSAGES;

    private ChestShopDB db;

    public ChestShopAddon(String dbName) {
        db = new ChestShopDB(dbName);
    }

    @Override
    public void onEnable() {
        SHOW_MESSAGES = new BooleanConfig("ad_chestshop_" + getServer().getIdentifier() + "_messages", true);
        addConfig(SHOW_MESSAGES);
        a(Notifications.class).register(this);
        a(CustomBlocksAddon.class).registerType(SHOP_DATA, ChestShop.class);
        CREATE_SHOPS = new PermissionDescription("criar loja", "rg.chestshops.create", "permissão para criar chest shops");
        addOption();
    }

    @Override
    public void onStart() {
    }

    public CustomBlocksAddon custom() {
        return a(CustomBlocksAddon.class);
    }

    public boolean canCreate(Player player) {
        return this.getUser(player).hasPermission(this.CREATE_SHOPS);
    }


    public void save(ChestShop shop) {
        custom().save(shop);
    }

    @EventHandler
    public void placeSign(SignChangeEvent ev) {
        String[] lines = ev.getLines();
        if (lines[0].trim().equalsIgnoreCase(ChatColor.stripColor(shopLine))) {
            if (!canCreate(ev.getPlayer())) {
                C.error(ev.getPlayer(), "Somente VIPs podem criar lojas!");
                ev.setCancelled(true);
            }
            LandAddon terrenosAddon = getServer().getAddon(LandAddon.class);

            if (terrenosAddon == null || terrenosAddon.getTerreno(ev.getBlock()) == null || terrenosAddon.getTerreno(ev.getBlock()).getOwnerId() != getPlayerId(ev.getPlayer())) {
                ev.setLine(0, "Inválido");
                ev.setCancelled(true);
                C.error(ev.getPlayer(), "Você só pode colocar lojas no seu terreno!");
            } else {
                ev.setLine(0, shopLine);
                ev.setLine(1, "???");
                save(new ChestShop(BungeeBlock.fromBlock(ev.getBlock()), getPlayerId(ev.getPlayer()), null, 0, 0));
            }
        }
    }


    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void interact(PlayerInteractEvent ev) {
        if (ev.getAction() == Action.RIGHT_CLICK_BLOCK || ev.getAction() == Action.LEFT_CLICK_BLOCK) {
            Material m = ev.getClickedBlock().getType();
            if (m == Material.SIGN || m == Material.SIGN_POST || m == Material.WALL_SIGN) {
                Sign s = (Sign) ev.getClickedBlock().getState();
                if (s.getLine(0).equals(shopLine)) {
                    LandAddon terrenosAddon = getServer().getAddon(LandAddon.class);
                    ChestShop shop = getShop(BungeeBlock.fromBlock(ev.getClickedBlock()));
                    if (terrenosAddon == null || terrenosAddon.getTerreno(ev.getClickedBlock()) == null) {
                        s.setLine(0, "Inválido!");
                        return;
                    }
                    Land t = getServer().getAddon(LandAddon.class).getTerreno(ev.getClickedBlock());
                    if (t == null || t.getOwnerId() != shop.getOwnerId()
                            || RedstoneGangSpigot.getPlayer(shop.getOwnerId()) == null
                            || !RedstoneGangSpigot.getPlayer(shop.getOwnerId()).hasPermission(CREATE_SHOPS)) {
                        deleteShop(shop);
                        C.error(ev.getPlayer(), "Shop inválido!");
                        return;
                    }
                    if ((shop.getOwnerId() == getPlayerId(ev.getPlayer()) || ev.getPlayer().getGameMode() == GameMode.CREATIVE) && ev.getAction() == Action.LEFT_CLICK_BLOCK) {
                        return;
                    }
                    ev.setCancelled(true);
                    clickShop(ev.getPlayer(), shop, ev.getAction() == Action.RIGHT_CLICK_BLOCK);
                }
            }
        }
    }

    public void deleteShop(ChestShop shop) {
        custom().remove(shop.getSign());
    }

    public void clickShop(Player p, ChestShop shop, boolean rightClick) {
        if (shop.getOwnerId() == getPlayerId(p)) {
            clickOwnShop(p, shop, rightClick);
        } else {
            clickAnotherShop(p, shop, rightClick);
        }

    }

    public void clickAnotherShop(Player p, ChestShop shop, boolean rightClick) {
        if (!shop.isValid()) {
            C.error(p, "Shop não configurado corretamente!");
            return;
        }
        if (p.isSneaking()) {
            new ViewShop(shop).open(p);
            return;
        }
        if (rightClick) {
            clickBuy(p, shop);
        } else {
            clickSell(p, shop);
        }
    }

    public void clickOwnShop(Player p, ChestShop shop, boolean rightClick) {
        if (rightClick)
            new EditShop(this, shop).open(p);
    }

    public void clickBuy(Player p, ChestShop shop) {
        if (shop.getItem() == null || shop.getPrecoVenda() <= 0) {
            C.error(p, "Shop não configurado!");
            return;
        }
        if (!economy().hasWithMessage(p, shop.precoVenda)) {
            return;
        }
        Inventory bau = shop.findChest();
        if (bau != null) {
            if (!InventoryHelper.inventoryContains(bau, shop.getItem(), shop.getItem().getAmount())) {
                C.error(p, "Loja sem estoque!");
                return;
            }
            if (InventoryHelper.getInventoryFreeSpace(p, shop.getItem()) < shop.getItem().getAmount()) {
                C.error(p, "Você não tem espaço no inventário!");

                return;
            }
            InventoryHelper.removeInventoryItems(bau, shop.getItem(), shop.getItem().getAmount());
            economy().remove(p, shop.getPrecoVenda());
            economy().add(shop.getOwnerId(), shop.getPrecoVenda());
            User pl = RedstoneGangSpigot.getPlayer(shop.getOwnerId());
            ItemStack give = shop.getItem().clone();
            p.getInventory().addItem(give);
            p.updateInventory();

            C.send(p, MsgType.INFO, "Você comprou %% de %% por ## !", shop.getItem(), pl, shop.getPrecoVenda());

            Player owner = Bukkit.getPlayer(pl.getUuid());
            if (pl.isOnline()) {
                if (pl.getConfig(SHOW_MESSAGES)) {
                    pl.sendMessage(C.msg(MsgType.INFO, "%% comprou %% na sua loja por ## !", p, shop.getItem(), shop.getPrecoVenda()));
                }
            } else {
                a(Notifications.class).addNotification(new Notification(shop.getOwnerId(), this, new ChestShopNotfication(0, 0, shop.getPrecoVenda(), 1)));
            }
            db.addEntry(shop.getOwnerId(), getPlayerId(p), shop.getItem(), shop.getPrecoCompra(), true);
        }
    }

    public void clickSell(Player p, ChestShop shop) {
        if (shop.getItem() == null || shop.getPrecoCompra() <= 0) {
            C.error(p, "Shop não configurado!");
            return;
        }
        Inventory bau = shop.findChest();
        if (bau != null) {
            int tem = InventoryHelper.getItemAmountInInventory(p.getInventory(), shop.getItem());
            if (tem > shop.getItem().getAmount()) {
                tem = shop.getItem().getAmount();
            }

            if (tem <= 0) {
                C.error(p, "Você não tem %% para vender!", shop.getItem());
                return;
            }
            if (InventoryHelper.getInventoryFreeSpace(bau, shop.getItem()) < tem) {
                C.error(p, "A loja não tem espaço para comprar seu item!");
                return;
            }
            double preco = (double) shop.getPrecoCompra() / (double) shop.getItem().getAmount();
            User pl = RedstoneGangSpigot.getPlayer(shop.getOwnerId());

            preco = preco * tem;
            if (!economy().has(shop.getOwnerId(), preco)) {
                C.error(p, "O jogador %% está quebrado e não tem dinheiro para te pagar!", pl);
                return;
            }
            InventoryHelper.removeInventoryItems(p.getInventory(), shop.getItem(), tem);

            economy().remove(shop.getOwnerId(), preco);
            economy().add(p, preco);

            C.send(p, MsgType.INFO, "Você vendeu %% para %% por ## !", shop.getItem(), pl, preco);
            ItemStack clone = shop.getItem().clone();
            clone.setAmount(tem);
            bau.addItem(clone);

            if (pl.isOnline()) {
                if (pl.getConfig(SHOW_MESSAGES)) {

                    pl.sendMessage(C.msg(MsgType.INFO
                            , "%% vendeu %% na sua loja por ## !", p, shop.getItem(), preco));
                }
            } else {
                a(Notifications.class).addNotification(new Notification(shop.getOwnerId(), this, new ChestShopNotfication(1, preco, 0, 0)));
            }

            db.addEntry(shop.getOwnerId(), getPlayerId(p), clone, preco, false);
        }
    }


    public ChestShop getShop(BungeeBlock block) {
        BlockData data = custom().get(block);
        if (data != null && data instanceof ChestShop) {
            return (ChestShop) data;

        }
        return null;
    }

    public EconomyAddon economy() {
        return getServer().a(EconomyAddon.class);
    }


    @Override
    public TextComponent[] process(JsonObject obj) {
        ChestShopNotfication old = RGson.gson().fromJson(obj, ChestShopNotfication.class);
        return new TextComponent[]{
                C.msgText(MsgType.INFO, "Você vendeu %% itens ganhando ## e comprou %% pagando ##!", old.vendas, old.mVendas, old.compras, old.mVendas),
        };

    }

    @Override
    public String getType() {
        return "CHESTSHOP";
    }

    @Override
    public JsonObject processAdd(JsonObject obj, JsonObject newValues) {
        ChestShopNotfication newV = RGson.gson().fromJson(newValues, ChestShopNotfication.class);
        if (obj != null) {
            ChestShopNotfication old = RGson.gson().fromJson(obj, ChestShopNotfication.class);
            newV.compras += old.compras;
            newV.mCompras += old.mCompras;
            newV.vendas += old.vendas;
            newV.mVendas += old.mVendas;
        }
        return RGson.gson().toJsonTree(newV).getAsJsonObject();


    }
}
