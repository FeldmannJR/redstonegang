package dev.feldmann.redstonegang.wire.game.base.addons.both.titulos;

import dev.feldmann.redstonegang.common.db.Database;
import dev.feldmann.redstonegang.common.player.UserDB;
import org.jooq.*;
import org.jooq.impl.SQLDataType;

import java.sql.Timestamp;
import java.util.HashMap;

import static org.jooq.impl.DSL.*;

public class TituloDB extends Database {

    private static final Table TABLE = table("titulos");
    private static final Field<Integer> ID = field("id", SQLDataType.INTEGER.nullable(false).identity(true));
    private static final Field<Integer> PLAYER = field("player", SQLDataType.INTEGER.nullable(false));
    private static final Field<String> TITULO = field("titulo", SQLDataType.VARCHAR(64).nullable(false));
    private static final Field<String> COR = field("cor", SQLDataType.VARCHAR(20).nullable(false));
    private static final Field<Timestamp> QUANDO = field("quando", SQLDataType.TIMESTAMP.defaultValue(now()));
    private static final Field<String> DESC = field("description", SQLDataType.VARCHAR(255).nullable(true));


    private static final Table ATIVO_TABLE = table("titulos_ativo");
    private static final Field<Integer> ATIVO_PLAYER = field("player", SQLDataType.INTEGER.nullable(false));
    private static final Field<Integer> ATIVO_ID = field("ativo", SQLDataType.INTEGER.nullable(true));

    TituloAddon addon;

    public TituloDB(String database) {
        super(database);
    }

    @Override
    public void createTables() {
        dsl().createTableIfNotExists(TABLE)
                .columns(ID, PLAYER, TITULO, COR, QUANDO, DESC)
                .constraints(
                        constraint().primaryKey(ID),
                        constraint().unique(PLAYER, TITULO, COR),
                        constraint().foreignKey(PLAYER).references(UserDB.TABLE, UserDB.ID).onDeleteCascade())
                .execute();

        dsl().createTableIfNotExists(ATIVO_TABLE)
                .columns(ATIVO_PLAYER, ATIVO_ID)
                .constraints(
                        constraint().primaryKey(ATIVO_PLAYER),
                        constraint().foreignKey(ATIVO_PLAYER).references(UserDB.TABLE, UserDB.ID).onDeleteCascade()

                ).execute();

    }

    public TituloPlayer loadTitulos(int pid) {
        HashMap<String, Titulo> titulos = new HashMap<>();
        TituloCor ativo = null;
        Result<Record5<Integer, String, String, Timestamp, String>> rs = dsl().select(ID, TITULO, COR, QUANDO, DESC).from(TABLE).fetch();
        for (Record5<Integer, String, String, Timestamp, String> r : rs) {
            String name = r.get(TITULO);
            Titulo t;
            if (titulos.containsKey(name)) {
                t = titulos.get(name);
            } else {
                t = new Titulo(pid, r.get(TITULO));
                titulos.put(t.getTitulo(), t);
            }
            TituloCor cor = new TituloCor(r.get(ID), t, r.get(COR), r.get(QUANDO));
            if (r.get(DESC) != null) {
                cor.setDesc(r.get(DESC));
            }
            t.addCor(cor);

        }
        TituloPlayer pl = new TituloPlayer(pid, titulos, ativo);
        pl.setAtivo(getAtivo(pl));
        return pl;
    }

    public boolean addTitulo(TituloCor t) {
        Record execute = dsl().insertInto(TABLE)
                .columns(PLAYER, TITULO, COR, DESC)
                .values(t.getParent().getOwner(), t.getParent().getTitulo(), t.getCor(), t.getDesc())
                .returning(ID)
                .fetchOne();

            t.setId(execute.get(ID));
            return true;
    }

    public boolean setActive(int pid, TituloCor cor) {
        dsl().insertInto(ATIVO_TABLE)
                .columns(ATIVO_PLAYER, ATIVO_ID)
                .values(pid, cor.getId())
                .onDuplicateKeyUpdate()
                .set(ATIVO_ID, cor.getId())
                .execute();
        return true;
    }

    public TituloCor getAtivo(TituloPlayer pl) {
        Record r = dsl().select().from(ATIVO_TABLE).where(ATIVO_PLAYER.eq(pl.getPid())).fetchOne();
        if (r != null) {
            HashMap<String, Titulo> ts = pl.getTitulos();
            for (Titulo t : ts.values()) {
                for (TituloCor cor : t.getCor().values()) {
                    if (cor.getId() == r.get(ATIVO_ID)) {
                        return cor;
                    }
                }
            }
        }
        return null;
    }

    public boolean removeTitulo(TituloCor t) {
        return dsl().deleteFrom(TABLE).where(ID.eq(t.getId())).execute() >= 1;
    }
}
