package dev.feldmann.redstonegang.common.utils.formaters.time;

public enum TimeUnit {
    MILLISECONDS(1, "Milisegundo"),
    SECONDS(1000, "Segundo", 's'),
    MINUTES(1000 * 60L, "Minuto", 'm'),
    HOURS(1000 * 60L * 60L, "Hora", 'h'),
    DAYS(1000 * 60L * 60L * 24L, "Dia", 'd'),
    WEEKS(1000 * 60L * 60L * 24L * 7, "Semana", 'S'),
    MONTHS(1000 * 60L * 60L * 24L * 30L, "Mês", 'M'),
    YEARS(1000 * 60L * 60L * 24L * 30L * 12, "Ano", 'A');

    long mp;
    Character suffix;
    String nome;


    TimeUnit(long mp, String nome, char suffix) {
        this.mp = mp;
        this.suffix = suffix;
        this.nome = nome;
    }

    TimeUnit(long mp, String nome) {
        this.mp = mp;
        this.nome = nome;
    }

    public String getNome(double v) {
        if (v == 1) {
            return nome;
        }
        if (this == MONTHS) {
            return "Meses";
        }
        return nome + "s";
    }

    public long getMp() {
        return mp;
    }

    public Character getSuffix() {
        return suffix;
    }
}


