package dev.feldmann.redstonegang.wire.game.base.addons.server.correio;

import org.bukkit.Material;

public enum StatusMsg {
    LIDAS("Lidas", "filtrar as lidas", 2, Material.PAPER),
    NAOLIDAS("Não Lidas", "filtrar as não lidas", 4, Material.BOOK),
    TODAS("Todas", "remover filtro", 6, Material.BOOK_AND_QUILL);

    String nome;
    String desc;
    int slot;
    Material material;

    StatusMsg(String nome, String desc, int slot, Material material) {
        this.nome = nome;
        this.desc = desc;
        this.slot = slot;
        this.material = material;
    }

    public int getSlot() {
        return slot;
    }

    public String getDesc() {
        return desc;
    }

    public String getNome() {
        return nome;
    }

    public Material getMaterial() {
        return material;
    }
}
