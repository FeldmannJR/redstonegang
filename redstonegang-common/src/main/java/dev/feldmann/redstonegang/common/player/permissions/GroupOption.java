package dev.feldmann.redstonegang.common.player.permissions;

public class GroupOption {
    String nome;
    String key;
    String desc;
    double defaultValue;

    public GroupOption(String nome, String key, String desc) {
        this(nome, key, desc, 0);
    }

    public GroupOption(String nome, String key, String desc, int defaultValue) {
        this.nome = nome;
        this.key = key;
        this.desc = desc;
        this.defaultValue = defaultValue;
    }

    public String getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }

    public String getNome() {
        return nome;
    }

    public double getDefaultValue() {
        return defaultValue;
    }
}
