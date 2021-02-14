package dev.feldmann.redstonegang.wire.game.base.addons.both.titulos;

import java.sql.Timestamp;

public class TituloCor {
    private int id;
    private Titulo parent;
    private String cor;
    private String desc = null;
    private Timestamp added;

    public TituloCor(int id, Titulo parent, String cor, Timestamp added) {
        this.id = id;
        this.cor = cor;
        this.added = added;
        this.parent = parent;
    }

    public Titulo getParent() {
        return parent;
    }

    public String getCor() {
        return cor;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public Timestamp getAdded() {
        return added;
    }

    public void setId(int id) {
        if (this.id == -1) {
            this.id = id;
        }
    }

    public int getId() {
        return id;
    }

}
