package dev.feldmann.redstonegang.wire.game.base.addons.both.clan;

import dev.feldmann.redstonegang.common.utils.formaters.time.TimeUtils;
import dev.feldmann.redstonegang.wire.utils.json.RGson;
import com.google.gson.JsonObject;

public class ClanProperties {

    private boolean ff = false;
    private long lastTrocaTag = 0;
    private long lastTrocaNome = 0;

    ClanProperties() {

    }

    public static ClanProperties fromString(String s) {
        if (s == null) {
            return new ClanProperties();
        }
        JsonObject obj = RGson.parse(s).getAsJsonObject();
        ClanProperties props = new ClanProperties();
        if (obj.has("ff")) {
            props.ff = obj.get("ff").getAsBoolean();
        }

        if (obj.has("lastTrocaTag")) {
            props.lastTrocaTag = obj.get("lastTrocaTag").getAsLong();
        }
        if (obj.has("lastTrocaNome")) {
            props.lastTrocaNome = obj.get("lastTrocaNome").getAsLong();
        }
        return props;
    }

    @Override
    public String toString() {
        JsonObject j = new JsonObject();
        j.addProperty("ff", ff);
        j.addProperty("lastTrocaTag", lastTrocaTag);
        j.addProperty("lastTrocaNome", lastTrocaNome);
        return j.toString();
    }


    public boolean canChangeTag() {
        return lastTrocaTag + ClanAddon.TEMPO_TROCA_TAG < System.currentTimeMillis();
    }

    public void changeTag() {
        lastTrocaTag = System.currentTimeMillis();
    }

    public String getTempoRestanteTag() {
        return TimeUtils.millisToString((lastTrocaTag + ClanAddon.TEMPO_TROCA_TAG) - System.currentTimeMillis());
    }

    public boolean canChangeNome() {
        return lastTrocaNome + ClanAddon.TEMPO_TROCA_NOME < System.currentTimeMillis();
    }

    public void changeNome() {
        lastTrocaNome = System.currentTimeMillis();
    }

    public String getTempoRestanteNome() {
        return TimeUtils.millisToString((lastTrocaNome + ClanAddon.TEMPO_TROCA_NOME) - System.currentTimeMillis());
    }

    public void setFf(boolean ff) {
        this.ff = ff;
    }

    public boolean isFf() {
        return ff;
    }
}
