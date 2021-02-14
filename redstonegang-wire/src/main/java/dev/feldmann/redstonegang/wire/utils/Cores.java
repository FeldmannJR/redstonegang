package dev.feldmann.redstonegang.wire.utils;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

/**
 * @author Carlos
 */
public class Cores {

    public static String getNome(ChatColor c) {

        if (c == ChatColor.WHITE) {
            return "Branco";
        }
        if (c == ChatColor.AQUA) {
            return "Azul Claro";
        }
        if (c == ChatColor.BLACK) {
            return "Preto";
        }
        if (c == ChatColor.BLUE) {
            return "Azul";
        }
        if (c == ChatColor.DARK_AQUA) {
            return "Ciano";
        }
        if (c == ChatColor.DARK_BLUE) {
            return "Azul Escuro";
        }
        if (c == ChatColor.DARK_GRAY) {
            return "Cinza Escuro";
        }
        if (c == ChatColor.DARK_GREEN) {
            return "Verde Escuro";
        }
        if (c == ChatColor.DARK_PURPLE) {
            return "Roxo";
        }
        if (c == ChatColor.DARK_RED) {
            return "Vermelho Escuro";
        }
        if (c == ChatColor.GOLD) {
            return "Dourado";
        }
        if (c == ChatColor.GRAY) {
            return "Cinza";
        }
        if (c == ChatColor.GREEN) {
            return "Verde";
        }
        if (c == ChatColor.LIGHT_PURPLE) {
            return "Rosa";
        }
        if (c == ChatColor.RED) {
            return "Vermelho";
        }
        if (c == ChatColor.YELLOW) {
            return "Amarelo";
        }
        if (c == ChatColor.BOLD) {
            return "Negrito";
        }
        if (c == ChatColor.ITALIC) {
            return "Italico";
        }
        if (c == ChatColor.UNDERLINE) {
            return "Sublinhado";
        }
        if (c == ChatColor.STRIKETHROUGH) {
            return "Riscado";
        }
        return "????";
    }

    public static DyeColor getDyeColor(ChatColor color) {
        switch (color) {
            case WHITE:
                return DyeColor.WHITE;
            case BLACK:
                return DyeColor.BLACK;
            case YELLOW:
                return DyeColor.YELLOW;
            case GREEN:
                return DyeColor.LIME;
            case DARK_GREEN:
                return DyeColor.GREEN;
            case GOLD:
                return DyeColor.ORANGE;
            case RED:
                return DyeColor.RED;
            case DARK_RED:
                return DyeColor.RED;
            case LIGHT_PURPLE:
                return DyeColor.MAGENTA;
            case BLUE:
                return DyeColor.BLUE;
            case AQUA:
                return DyeColor.LIGHT_BLUE;
            case DARK_AQUA:
                return DyeColor.CYAN;
            case GRAY:
                return DyeColor.GRAY;
            case DARK_GRAY:
                return DyeColor.GRAY;
            case DARK_BLUE:
                return DyeColor.BLUE;
            case DARK_PURPLE:
                return DyeColor.PURPLE;
            default:
                return DyeColor.WHITE;
        }
    }

    public static MaterialData getData(ChatColor c) {
        Material m = Material.DIRT;
        byte data = 0;
        if (c == ChatColor.WHITE) {
            m = Material.SNOW_BLOCK;
        }
        if (c == ChatColor.AQUA) {
            m = Material.DIAMOND_BLOCK;
        }
        if (c == ChatColor.BLACK) {
            m = Material.COAL_BLOCK;
        }
        if (c == ChatColor.BLUE) {
            m = Material.STAINED_CLAY;
            data = 3;
        }
        if (c == ChatColor.DARK_AQUA) {
            m = Material.WOOL;
            data = 9;
        }
        if (c == ChatColor.DARK_BLUE) {
            m = Material.LAPIS_BLOCK;
        }
        if (c == ChatColor.DARK_GRAY) {
            m = Material.WOOL;
            data = 7;
        }
        if (c == ChatColor.DARK_GREEN) {
            m = Material.STAINED_CLAY;
            data = 13;
        }
        if (c == ChatColor.DARK_PURPLE) {
            m = Material.WOOL;
            data = 10;
        }
        if (c == ChatColor.DARK_RED) {
            m = Material.NETHERRACK;
        }
        if (c == ChatColor.GOLD) {
            m = Material.GOLD_BLOCK;
        }
        if (c == ChatColor.GRAY) {
            m = Material.STONE;
        }
        if (c == ChatColor.GREEN) {
            m = Material.EMERALD_BLOCK;
        }
        if (c == ChatColor.LIGHT_PURPLE) {
            m = Material.WOOL;
            data = 2;
        }
        if (c == ChatColor.RED) {
            m = Material.STAINED_CLAY;
            data = 14;
        }
        if (c == ChatColor.YELLOW) {
            data = 4;
            m = Material.WOOL;
        }
        return new MaterialData(m, data);
    }

    public static DyeColor getDyeColor(net.md_5.bungee.api.ChatColor channelColor) {
        return getDyeColor(ChatColor.getByChar(channelColor.toString().charAt(1)));
    }

    public static String getNome(net.md_5.bungee.api.ChatColor chatColor) {
        return getNome(ChatColor.getByChar(chatColor.toString().charAt(1)));
    }
}
