package dev.feldmann.redstonegang.wire.game.base.addons.both.shop;


import dev.feldmann.redstonegang.common.currencies.Currency;
import dev.feldmann.redstonegang.common.player.config.types.BooleanConfig;
import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.cmds.CmdShopAdm;
import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.menus.MenuShop;
import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.menus.ShopMenuBase;
import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.tipos.ShopPage;
import dev.feldmann.redstonegang.wire.game.base.apis.ChatInputAPI;
import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import dev.feldmann.redstonegang.wire.game.base.objects.annotations.Dependencies;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.modulos.menus.MenuManager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;


@Dependencies(apis = ChatInputAPI.class)
public abstract class ShopAddon extends Addon {

    public BooleanConfig SHOW_BUY_MESSAGES;
    public Currency currency;

    public String dbName;
    public String tableName;

    private ShopDB db;


    private List<ShopClick> clicks = new ArrayList();

    public ShopAddon(String dbName, String tableName, Currency cur) {
        this.dbName = dbName;
        this.currency = cur;
        this.tableName = tableName;
    }


    @Override
    public void onEnable() {
        db = new ShopDB(dbName, tableName, this);
        reloadClicks();
        SHOW_BUY_MESSAGES = new BooleanConfig("ad_shop_" + getIdentifier() + "_buymessage", true);
        addConfig(SHOW_BUY_MESSAGES);
        registerCommand(new CmdShopAdm(this));
//        registerCommand(new AtivarCodigo());
    }


    public void reloadClicks() {
        closeAll();
        for (ShopClick c : clicks) {
            c.invalid = true;

        }
        clicks = db.getItens();
    }

    public List<ShopClick> getClicks() {
        return clicks;
    }

    public List<ShopClick> getPage(int page) {
        List<ShopClick> clicks = new ArrayList<>();
        for (ShopClick click : getClicks()) {
            if (click.pageid == page) {
                if (click.slot != -1) {
                    clicks.add(click);
                }
            }
        }
        return clicks;
    }

    public ShopClick getClickById(int id) {
        for (ShopClick c : getClicks()) {
            if (c.id == id) {
                return c;
            }
        }
        return null;
    }

    public static void closeAll() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            Menu m = MenuManager.getOpenMenu(p);
            if (m instanceof ShopMenuBase) {
                p.closeInventory();
            }
        }
    }

    public int getLines(int pageid) {
        if (pageid == -1) {
            return 5;
        }
        ShopPage page = getShopPage(pageid);
        if (page == null) {
            return 6;
        }
        return page.lines;
    }

    public ShopPage getShopPage(int id) {
        for (ShopClick c : getClicks()) {
            if (c.id == id) {
                if (c instanceof ShopPage) {
                    return (ShopPage) c;
                }
            }
        }
        return null;
    }

    public void delete(ShopClick item) {
        if (item instanceof ShopPage) {

            for (ShopClick c : getClicks()) {
                if (c.id == item.id) continue;
                if (c.pageid == item.id) {
                    c.pageid = -1;
                    c.slot = -1;
                    save(c);
                }
            }
        }
        clicks.remove(item);
        item.invalid = true;
        db.remove(item.id);
    }

    public void openPlayer(Player p) {
        openPlayer(p, -1);
    }

    public void openPlayer(Player p, int pageid) {
        String nome = getNome();
        if (pageid != -1) {
            ShopPage page = getShopPage(pageid);
            if (page != null) {

                nome = page.nome;
            }
        }
        new MenuShop(nome, pageid, this).open(p);
    }

    public void save(ShopClick click) {
        db.saveItem(click);
    }

    public void add(ShopClick itemstack) {
        clicks.add(itemstack);
        save(itemstack);
    }

    public String getIdentifier() {
        return server().getIdentifier() + "_" + getIdentifierName();
    }

    public String[] getAliases() {
        return new String[0];
    }

    public abstract String getIdentifierName();

    public abstract String getCommand();

    public abstract String getNome();

}
