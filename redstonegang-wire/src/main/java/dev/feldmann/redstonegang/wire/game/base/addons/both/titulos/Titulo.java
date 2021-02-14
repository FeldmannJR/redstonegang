package dev.feldmann.redstonegang.wire.game.base.addons.both.titulos;

import org.bukkit.Material;

import java.util.*;

public class Titulo {
    private int owner;
    private String titulo;
    protected HashMap<String, TituloCor> cor;

    public Titulo(int owner, String titulo) {
        this.owner = owner;
        this.titulo = titulo;
        this.cor = new HashMap<>();
    }

    public int getOwner() {
        return owner;
    }

    public void addCor(TituloCor cor) {
        this.cor.put(cor.getCor(), cor);
    }

    public void removeCor(String cor) {
        this.cor.remove(cor);
    }

    public String getTitulo() {
        return titulo;
    }

    public String getProcessedName() {
        return titulo;
    }

    public Material getMaterial() {
        return Material.BOOK_AND_QUILL;
    }

    public int getCores() {
        return cor.size();
    }

    @Override
    public int hashCode() {
        return Objects.hash(owner, titulo, cor);
    }

    public boolean hasColor(String color) {
        return cor.containsKey(color);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj instanceof Titulo) {
            Titulo t2 = (Titulo) obj;
            return t2.getTitulo().equals(getTitulo()) &&
                    t2.getOwner() == getOwner();
        }
        return false;

    }

    public HashMap<String, TituloCor> getCor() {
        return cor;
    }

    public boolean useColors() {
        return true;
    }

    public boolean isVirtual() {
        return false;
    }
}
