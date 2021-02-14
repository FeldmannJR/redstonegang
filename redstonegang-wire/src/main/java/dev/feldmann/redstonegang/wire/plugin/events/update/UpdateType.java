package dev.feldmann.redstonegang.wire.plugin.events.update;

public enum UpdateType {

    MIN_5(60000 * 5),
    MIN_1(60000),
    SEC_32(32000),
    SEC_16(16000),
    SEC_4(4000),
    SEC_1(1000),
    MS_500(500),
    MS_250(250),
    MS_125(125),
    TICK(49);

    long tempo;
    long last;

    UpdateType(long tempo) {
        this.tempo = tempo;
        last = System.currentTimeMillis();
    }

    public boolean isElapsed() {
        if ((last + tempo) < System.currentTimeMillis()) {
            this.last = System.currentTimeMillis();
            return true;
        }
        return false;

    }

}
