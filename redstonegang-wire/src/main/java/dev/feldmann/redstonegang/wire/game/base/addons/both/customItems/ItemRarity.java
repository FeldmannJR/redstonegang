package dev.feldmann.redstonegang.wire.game.base.addons.both.customItems;

public enum ItemRarity {
    PAID("§c", "§f"),
    COMMON("§f", "§7"),
    UNCOMMON("§e", "§7"),
    RARE("§9", "§f"),
    EPIC("§4§l", "§c");

    String color;
    String descColor;

    ItemRarity(String color, String descColor) {
        this.color = color;
        this.descColor = descColor;
    }
}
