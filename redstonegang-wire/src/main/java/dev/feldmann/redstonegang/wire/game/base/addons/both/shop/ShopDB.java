package dev.feldmann.redstonegang.wire.game.base.addons.both.shop;


import dev.feldmann.redstonegang.common.db.Database;
import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.db.tables.LojaItens;
import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.db.tables.records.LojaItensRecord;
import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.menus.Excluir;
import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.tipos.ShopItemStack;
import dev.feldmann.redstonegang.wire.utils.json.RGson;

import com.google.gson.*;
import org.jooq.Record;
import org.jooq.Result;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;


public class ShopDB extends Database {

    Gson gson;
    ShopAddon addon;
    LojaItens LOJA_ITENS = new LojaItens();

    public ShopDB(String database, String tablename, ShopAddon addon) {
        super(database);
        this.addon = addon;
        buildGson();
        LOJA_ITENS = LOJA_ITENS.rename(tablename);
        dsl().execute("CREATE TABLE IF NOT EXISTS " + tablename + " (`id` INTEGER AUTO_INCREMENT PRIMARY KEY, `slot` INTEGER, `tipo` VARCHAR(60) ,`pageid` INTEGER, `vars` TEXT)");

    }

    public ShopDB() {
        buildGson();
    }

    public void buildGson() {
        GsonBuilder b = RGson.createBuilder();
        ExclusionStrategy s = new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                for (Annotation an : fieldAttributes.getAnnotations()) {
                    if (an.getClass() == Excluir.class) {
                        return true;
                    }
                }
                return false;
            }

            @Override
            public boolean shouldSkipClass(Class<?> aClass) {
                return false;
            }
        };
        b.setExclusionStrategies(s);
        gson = b.create();

    }

    @Override
    public void createTables() {
    }

    public boolean saveItem(ShopClick item) {
        JsonElement json = gson.toJsonTree(item);
        if (item instanceof ShopItemStack) {
            json.getAsJsonObject().add("itensVisual", RGson.visual().toJsonTree(((ShopItemStack) item).getItens()));
        }
        if (item.id == -1) {
            Record r = dsl().insertInto(LOJA_ITENS)
                    .columns(LOJA_ITENS.SLOT, LOJA_ITENS.PAGEID, LOJA_ITENS.VARS, LOJA_ITENS.TIPO)
                    .values(item.slot, item.pageid, json.toString(), ShopTipo.getByObject(item).name())
                    .returning(LOJA_ITENS.ID)
                    .fetchOne();
            if (r != null) {
                item.addon = addon;
                item.id = r.get(LOJA_ITENS.ID);
            }
        } else {
            dsl().update(LOJA_ITENS)
                    .set(LOJA_ITENS.SLOT, item.slot)
                    .set(LOJA_ITENS.PAGEID, item.pageid)
                    .set(LOJA_ITENS.VARS, json.toString())
                    .where(LOJA_ITENS.ID.eq(item.id))
                    .execute();
        }
        return true;
    }

    public List<ShopClick> getItens() {
        List<ShopClick> click = new ArrayList<>();
        Result<LojaItensRecord> rs = dsl().selectFrom(LOJA_ITENS).fetch();
        for (LojaItensRecord r : rs) {
            Class<? extends ShopClick> tipo = ShopTipo.getClassByName(r.getTipo());
            if (tipo != null) {
                ShopClick item = gson.fromJson(r.getVars(), tipo);
                item.id = r.getId();
                item.pageid = r.getPageid();
                item.slot = r.getSlot();
                item.invalid = false;
                item.addon = addon;
                click.add(item);
            }

        }

        return click;
    }

    public void remove(int id) {
        dsl().deleteFrom(LOJA_ITENS).where(LOJA_ITENS.ID.eq(id)).execute();
    }
}
