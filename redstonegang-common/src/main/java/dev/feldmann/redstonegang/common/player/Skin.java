package dev.feldmann.redstonegang.common.player;

public class Skin {
    private String texture;
    private String signature;

    public Skin(String texture, String signature) {
        this.texture = texture;
        this.signature = signature;
    }

    public String getSignature() {
        return signature;
    }

    public String getTexture() {
        return texture;
    }
}
