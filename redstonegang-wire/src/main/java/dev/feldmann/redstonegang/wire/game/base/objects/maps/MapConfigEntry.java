package dev.feldmann.redstonegang.wire.game.base.objects.maps;

public class MapConfigEntry {
    String nome;
    SaveType tipo;
    SaveOption option;
    String desc;

    public MapConfigEntry(String nome, SaveType tipo, SaveOption option) {
        this(nome, tipo, option, "");
    }

    public MapConfigEntry(String nome, SaveType tipo, SaveOption option, String desc) {
        this.nome = nome;
        this.tipo = tipo;
        this.option = option;
        this.desc = desc;
    }

    public String getNome() {
        return nome;
    }

    public SaveOption getOption() {
        return option;
    }

    public SaveType getTipo() {
        return tipo;
    }

    public String getDesc() {
        return desc;
    }
}
