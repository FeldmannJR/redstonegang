package dev.feldmann.redstonegang.wire.game.base.objects.maps;

public enum SaveOption {
    OPTIONAL("§9Opcional"),
    REQUIRED("§cObrigatório"),
    SEQUENTIAL("§6Em sequencia"),;
    String nome;

    SaveOption(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
