package dev.feldmann.redstonegang.wire.game.base.addons.both.titulos;

import dev.feldmann.redstonegang.wire.Wire;
import dev.feldmann.redstonegang.wire.game.base.addons.both.titulos.events.PlayerGetTitlesEvent;

import java.sql.Timestamp;
import java.util.*;

public class TituloPlayer {

    private int pid;
    private HashMap<String, Titulo> titulos;
    private TituloCor ativo;

    public TituloPlayer(int pid, HashMap<String, Titulo> titulos, TituloCor ativo) {
        this.pid = pid;
        this.titulos = titulos;
        this.ativo = ativo;
    }

    public TituloCor addTitle(String title, String cor) {
        if (!titulos.containsKey(title)) {
            titulos.put(title, new Titulo(pid, title));
        }
        Titulo t = titulos.get(title);
        if (t.hasColor(cor)) {
            return null;
        }
        TituloCor tcor = new TituloCor(-1, t, cor, new Timestamp(System.currentTimeMillis()));
        t.addCor(tcor);
        return tcor;
    }

    public int getPid() {
        return pid;
    }

    void setAtivo(TituloCor ativo) {
        this.ativo = ativo;
    }

    public void removeTitle(TituloCor cor) {
        if (titulos.containsKey(cor.getParent().getTitulo())) {
            Titulo t = titulos.get(cor.getParent().getTitulo());
            if (t.hasColor(cor.getCor()))
                t.removeCor(cor.getCor());
            if (t.getCores() == 0) {
                titulos.remove(t.getTitulo());
            }
        }
    }

    public HashMap<String, Titulo> getTitulos() {
        HashMap<String, Titulo> ts = new HashMap<>(titulos);
        PlayerGetTitlesEvent ev = new PlayerGetTitlesEvent(pid, ts);
        Wire.callEvent(ev);
        return ts;
    }

    public TituloCor getAtivo() {
        return ativo;
    }

    public boolean has(TituloCor cor) {
        if (getTitulos().containsKey(cor.getParent().getTitulo())) {
            return getTitulos().get(cor.getParent().getTitulo()).hasColor(cor.getCor());
        }
        return false;
    }
}
