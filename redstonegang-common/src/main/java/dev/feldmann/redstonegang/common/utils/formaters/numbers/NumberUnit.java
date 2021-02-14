package dev.feldmann.redstonegang.common.utils.formaters.numbers;

public enum NumberUnit {
    ONE(1, null, ""),
    THOUSAND(1000, 'k', "Mil"),
    MILLION(1000000, 'M', "Milhão");

    int mp;
    Character suffix;
    String nome;

    NumberUnit(int mp, Character suffix, String nome) {
        this.mp = mp;
        this.suffix = suffix;
        this.nome = nome;
    }
}
