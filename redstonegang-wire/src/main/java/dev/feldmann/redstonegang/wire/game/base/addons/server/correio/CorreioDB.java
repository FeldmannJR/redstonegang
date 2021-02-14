/*
 *  _   __   _   _____   _____       ___       ___  ___   _____
 * | | |  \ | | /  ___/ |_   _|     /   |     /   |/   | /  ___|
 * | | |   \| | | |___    | |      / /| |    / /|   /| | | |
 * | | | |\   | \___  \   | |     / / | |   / / |__/ | | | |
 * | | | | \  |  ___| |   | |    / /  | |  / /       | | | |___
 * |_| |_|  \_| /_____/   |_|   /_/   |_| /_/        |_| \_____|
 *
 * Projeto feito por Carlos Andre Feldmann Junior, Isaias Finger e Gabriel Augusto Souza
 */
package dev.feldmann.redstonegang.wire.game.base.addons.server.correio;

import dev.feldmann.redstonegang.common.db.Database;

import dev.feldmann.redstonegang.wire.game.base.addons.server.correio.db.tables.records.CorreioRecord;
import dev.feldmann.redstonegang.wire.utils.ItemSerializer;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import static dev.feldmann.redstonegang.wire.game.base.addons.server.correio.db.Tables.*;


/**
 * Skype: isaias.finger GitHub: https://github.com/net32
 *
 * @author NeT32
 */
public class CorreioDB extends Database {

    CorreioAddon addon;

    public CorreioDB(CorreioAddon addon, String database) {
        super(database);
        this.addon = addon;
    }

    private static final int POR_PAGINA = 28;

    @Override
    public void createTables() {
        dsl().execute("CREATE TABLE IF NOT EXISTS `correio` (\n" +
                "  `id` BIGINT NOT NULL AUTO_INCREMENT,\n" +
                "  `remetente` INTEGER NULL,\n" +
                "  `destinatario` INTEGER NOT NULL,\n" +
                "  `mensagem` varchar(300) NOT NULL,\n" +
                "  `coins` double unsigned NOT NULL DEFAULT '0',\n" +
                "  `itens` blob NOT NULL,\n" +
                "  `itenstrans` tinyint(1) NOT NULL DEFAULT '0',\n" +
                "  `aberta` tinyint(1) NOT NULL DEFAULT '0',\n" +
                "  `data` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
                "  PRIMARY KEY (`id`)\n" +
                ") ENGINE=InnoDB");
    }


    public int getTotalCaixa(CorreioAddon.Caixa cx, int playerId, StatusMsg smsg) {
        Record1<Integer> fetch = dsl().select(DSL.count()).from(CORREIO).where(caixaToSQL(cx).eq(playerId)).and(filtroToSQL(smsg)).orderBy(CORREIO.ID.desc()).fetchOne();
        if (fetch != null) {
            return fetch.value1();
        }
        return 0;
    }

    public List<CorreioMsg> getCaixa(CorreioAddon.Caixa cx, int playerId, StatusMsg smsg, int pagina) {
        List<CorreioMsg> caixa = new ArrayList<>();
        Result<CorreioRecord> rs = dsl().selectFrom(CORREIO).where(caixaToSQL(cx).eq(playerId)).and(filtroToSQL(smsg)).orderBy(CORREIO.ID.desc()).limit(pagina * POR_PAGINA, POR_PAGINA).fetch();
        for (CorreioRecord r : rs) {
            caixa.add(new CorreioMsg(
                    addon,
                    r.getId(),
                    r.getRemetente(),
                    r.getDestinatario(),
                    r.getMensagem(),
                    r.getCoins(),
                    ItemSerializer.deserializeItemStacks(r.getItens()),
                    r.getItenstrans(),
                    r.getAberta(),
                    r.getData().getTime()
            ));
        }
        return caixa;
    }

    public boolean saveMsg(CorreioMsg msg) {

        try {
            if (msg.isSaved()) {
                return dsl().update(CORREIO)
                        .set(CORREIO.ABERTA, msg.isRead())
                        .set(CORREIO.ITENSTRANS, msg.isItensTranferidos())
                        .where(CORREIO.ID.eq(msg.getId())).execute() > 0;
            } else {
                CorreioRecord c = dsl().insertInto(CORREIO)
                        .columns(CORREIO.REMETENTE, CORREIO.DESTINATARIO, CORREIO.MENSAGEM, CORREIO.COINS, CORREIO.ITENS, CORREIO.ITENSTRANS, CORREIO.ABERTA, CORREIO.DATA)
                        .values(msg.getRemetente(), msg.getDestinatario(), msg.getMensagem(), msg.getCoins(), ItemSerializer.serializeItemStacks(msg.getItens()), msg.isItensTranferidos(), msg.isRead(), new Timestamp(System.currentTimeMillis()))
                        .returning(CORREIO.ID)
                        .fetchOne();

                if (c != null) {
                    msg.setSaveId(c.getId());
                    return true;
                }
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteMsg(CorreioMsg msg) {
        if (msg.isSaved()) {
            return dsl().deleteFrom(CORREIO).where(CORREIO.ID.eq(msg.getId())).execute() > 0;
        }

        return false;
    }


    public Condition filtroToSQL(StatusMsg smsg) {
        Condition c = DSL.trueCondition();
        if (smsg.equals(StatusMsg.LIDAS)) {
            c = CORREIO.ABERTA.eq(true);
        } else if (smsg.equals(StatusMsg.NAOLIDAS)) {
            c = CORREIO.ABERTA.eq(false);
        }
        return c;
    }

    public Field<Integer> caixaToSQL(CorreioAddon.Caixa cx) {
        Field<Integer> quem = CORREIO.DESTINATARIO;
        if (cx.equals(CorreioAddon.Caixa.SAIDA)) {
            quem = CORREIO.REMETENTE;
        }
        return quem;
    }


}
