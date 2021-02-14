package dev.feldmann.redstonegang.wire.utils.items;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

import static org.bukkit.block.banner.PatternType.*;

public class BannerBuilder {

    private ItemStack item;
    private BannerMeta meta;

    private BannerBuilder(DyeColor baseColor) {
        this.item = new ItemStack(Material.BANNER);
        meta = (BannerMeta) item.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        meta.setBaseColor(baseColor);
    }

    public static BannerBuilder baseColor(DyeColor color) {
        return new BannerBuilder(color);
    }

    public BannerBuilder pattern(PatternType type, DyeColor color) {
        meta.addPattern(new Pattern(color, type));
        return this;
    }

    public ItemStack create() {
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack getCharBanner(char c, DyeColor baseColor, DyeColor charColor) {
        BannerBuilder builder = baseColor(baseColor);
        switch (c) {
            case 'a':
                builder
                        .pattern(BASE,charColor)
                        .pattern(HALF_HORIZONTAL,baseColor)
                        .pattern(STRIPE_BOTTOM,baseColor)
                        .pattern(STRIPE_RIGHT,charColor)
                        .pattern(STRIPE_LEFT,charColor)
                        .pattern(STRIPE_TOP,charColor)
                        .pattern(BORDER,baseColor);
                break;
            case 'b':
                builder
                        .pattern(BASE,charColor)
                        .pattern(STRIPE_CENTER,baseColor)
                        .pattern(STRIPE_BOTTOM,charColor)
                        .pattern(STRIPE_TOP,charColor)
                        .pattern(STRIPE_MIDDLE,charColor)
                        .pattern(BORDER,baseColor);
                break;
            case 'c':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(STRIPE_LEFT,charColor)
                        .pattern(STRIPE_TOP,charColor)
                        .pattern(STRIPE_BOTTOM,charColor)
                        .pattern(BORDER,baseColor);
                break;
            case 'd':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(STRIPE_LEFT,charColor)
                        .pattern(STRIPE_BOTTOM,charColor)
                        .pattern(DIAGONAL_RIGHT_MIRROR,baseColor)
                        .pattern(STRIPE_DOWNRIGHT,charColor)
                        .pattern(BORDER,baseColor);
                break;
            case 'e':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(STRIPE_MIDDLE,charColor)
                        .pattern(STRIPE_RIGHT,baseColor)
                        .pattern(STRIPE_LEFT,charColor)
                        .pattern(STRIPE_TOP,charColor)
                        .pattern(STRIPE_BOTTOM,charColor)
                        .pattern(BORDER,baseColor);
                break;
            case 'f':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(STRIPE_MIDDLE,charColor)
                        .pattern(STRIPE_RIGHT,baseColor)
                        .pattern(STRIPE_LEFT,charColor)
                        .pattern(STRIPE_TOP,charColor)
                        .pattern(BORDER,baseColor);
                break;
            case 'g':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(STRIPE_RIGHT,charColor)
                        .pattern(HALF_HORIZONTAL,baseColor)
                        .pattern(STRIPE_BOTTOM,charColor)
                        .pattern(STRIPE_LEFT,charColor)
                        .pattern(STRIPE_TOP,charColor)
                        .pattern(BORDER,baseColor);
                break;
            case 'h':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(STRIPE_MIDDLE,charColor)
                        .pattern(STRIPE_RIGHT,charColor)
                        .pattern(STRIPE_LEFT,charColor)
                        .pattern(BORDER,baseColor);
                break;
            case 'i':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(STRIPE_TOP,charColor)
                        .pattern(STRIPE_BOTTOM,charColor)
                        .pattern(STRIPE_CENTER,charColor)
                        .pattern(BORDER,baseColor);
                break;
            case 'j':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(STRIPE_LEFT,charColor)
                        .pattern(HALF_HORIZONTAL,baseColor)
                        .pattern(STRIPE_BOTTOM,charColor)
                        .pattern(STRIPE_RIGHT,charColor)
                        .pattern(BORDER,baseColor);
                break;
            case 'k':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(STRIPE_LEFT,charColor)
                        .pattern(STRIPE_LEFT,charColor)
                        .pattern(STRIPE_MIDDLE,charColor)
                        .pattern(HALF_VERTICAL_MIRROR,baseColor)
                        .pattern(CROSS,charColor)
                        .pattern(BORDER,baseColor);
                break;
            case 'l':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(STRIPE_LEFT,charColor)
                        .pattern(STRIPE_BOTTOM,charColor)
                        .pattern(BORDER,baseColor);
                break;
            case 'm':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(TRIANGLE_TOP,charColor)
                        .pattern(TRIANGLES_TOP,baseColor)
                        .pattern(STRIPE_LEFT,charColor)
                        .pattern(STRIPE_RIGHT,charColor)
                        .pattern(BORDER,baseColor);
                break;
            case 'n':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(STRIPE_LEFT,charColor)
                        .pattern(DIAGONAL_RIGHT_MIRROR,baseColor)
                        .pattern(STRIPE_DOWNRIGHT,charColor)
                        .pattern(STRIPE_RIGHT,charColor)
                        .pattern(BORDER,baseColor);
                break;
            case 'o':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(STRIPE_LEFT,charColor)
                        .pattern(STRIPE_TOP,charColor)
                        .pattern(STRIPE_RIGHT,charColor)
                        .pattern(STRIPE_BOTTOM,charColor)
                        .pattern(BORDER,baseColor);
                break;
            case 'p':
                builder
                        .pattern(BASE,charColor)
                        .pattern(HALF_HORIZONTAL,baseColor)
                        .pattern(STRIPE_RIGHT,charColor)
                        .pattern(STRIPE_BOTTOM,baseColor)
                        .pattern(STRIPE_LEFT,charColor)
                        .pattern(STRIPE_TOP,charColor)
                        .pattern(BORDER,baseColor);
                break;
            case 'q':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(STRIPE_LEFT,charColor)
                        .pattern(STRIPE_TOP,charColor)
                        .pattern(STRIPE_RIGHT,charColor)
                        .pattern(STRIPE_BOTTOM,charColor)
                        .pattern(BORDER,baseColor)
                        .pattern(SQUARE_BOTTOM_RIGHT,charColor);
                break;
            case 'r':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(CROSS,charColor)
                        .pattern(HALF_VERTICAL,baseColor)
                        .pattern(HALF_HORIZONTAL,charColor)
                        .pattern(STRIPE_MIDDLE,charColor)
                        .pattern(STRIPE_LEFT,charColor)
                        .pattern(BORDER,baseColor);
                break;
            case 's':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(STRIPE_BOTTOM,charColor)
                        .pattern(STRIPE_TOP,charColor)
                        .pattern(STRIPE_DOWNRIGHT,charColor)
                        .pattern(BORDER,baseColor);
                break;
            case 't':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(STRIPE_TOP,charColor)
                        .pattern(STRIPE_CENTER,charColor)
                        .pattern(BORDER,baseColor);
                break;
            case 'u':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(STRIPE_LEFT,charColor)
                        .pattern(STRIPE_BOTTOM,charColor)
                        .pattern(STRIPE_RIGHT,charColor)
                        .pattern(BORDER,baseColor);
                break;
            case 'v':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(STRIPE_LEFT,charColor)
                        .pattern(TRIANGLE_BOTTOM,baseColor)
                        .pattern(STRIPE_DOWNLEFT,charColor)
                        .pattern(BORDER,baseColor);
                break;
            case 'w':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(TRIANGLE_BOTTOM,charColor)
                        .pattern(TRIANGLES_BOTTOM,baseColor)
                        .pattern(STRIPE_LEFT,charColor)
                        .pattern(STRIPE_RIGHT,charColor)
                        .pattern(BORDER,baseColor);
                break;
            case 'x':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(STRIPE_TOP,charColor)
                        .pattern(STRIPE_BOTTOM,charColor)
                        .pattern(STRIPE_CENTER,baseColor)
                        .pattern(CROSS,charColor)
                        .pattern(BORDER,baseColor);
                break;
            case 'y':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(CROSS,charColor)
                        .pattern(HALF_VERTICAL_MIRROR,baseColor)
                        .pattern(STRIPE_DOWNLEFT,charColor)
                        .pattern(BORDER,baseColor);
                break;
            case 'z':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(STRIPE_TOP,charColor)
                        .pattern(STRIPE_BOTTOM,charColor)
                        .pattern(STRIPE_DOWNLEFT,charColor)
                        .pattern(BORDER,baseColor);
                break;
            case '0':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(STRIPE_TOP,charColor)
                        .pattern(STRIPE_RIGHT,charColor)
                        .pattern(STRIPE_BOTTOM,charColor)
                        .pattern(STRIPE_LEFT,charColor)
                        .pattern(STRIPE_DOWNLEFT,charColor)
                        .pattern(BORDER,baseColor);
                break;
            case '1':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(SQUARE_TOP_LEFT,charColor)
                        .pattern(STRIPE_CENTER,charColor)
                        .pattern(STRIPE_BOTTOM,charColor)
                        .pattern(BORDER,baseColor);
                break;
            case '2':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(STRIPE_TOP,charColor)
                        .pattern(RHOMBUS_MIDDLE,baseColor)
                        .pattern(STRIPE_DOWNLEFT,charColor)
                        .pattern(STRIPE_BOTTOM,charColor)
                        .pattern(BORDER,baseColor);
                break;
            case '3':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(STRIPE_MIDDLE,charColor)
                        .pattern(STRIPE_LEFT,baseColor)
                        .pattern(STRIPE_BOTTOM,charColor)
                        .pattern(STRIPE_RIGHT,charColor)
                        .pattern(STRIPE_TOP,charColor)
                        .pattern(BORDER,baseColor);
                break;
            case '4':
                builder
                        .pattern(BASE,charColor)
                        .pattern(HALF_HORIZONTAL,baseColor)
                        .pattern(STRIPE_LEFT,charColor)
                        .pattern(STRIPE_BOTTOM,baseColor)
                        .pattern(STRIPE_RIGHT,charColor)
                        .pattern(STRIPE_MIDDLE,charColor)
                        .pattern(BORDER,baseColor);
                break;
            case '5':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(STRIPE_BOTTOM,charColor)
                        .pattern(STRIPE_DOWNRIGHT,charColor)
                        .pattern(CURLY_BORDER,baseColor)
                        .pattern(SQUARE_BOTTOM_LEFT,charColor)
                        .pattern(STRIPE_TOP,charColor)
                        .pattern(BORDER,baseColor);
                break;
            case '6':
                builder
                        .pattern(BASE,charColor)
                        .pattern(HALF_HORIZONTAL_MIRROR,baseColor)
                        .pattern(STRIPE_RIGHT,charColor)
                        .pattern(STRIPE_TOP,baseColor)
                        .pattern(STRIPE_BOTTOM,charColor)
                        .pattern(STRIPE_LEFT,charColor)
                        .pattern(BORDER,baseColor);
                break;
            case '7':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(STRIPE_TOP,charColor)
                        .pattern(DIAGONAL_RIGHT,baseColor)
                        .pattern(STRIPE_DOWNLEFT,charColor)
                        .pattern(SQUARE_BOTTOM_LEFT,charColor)
                        .pattern(BORDER,baseColor);
                break;
            case '8':
                builder
                        .pattern(BASE,charColor)
                        .pattern(STRIPE_CENTER,baseColor)
                        .pattern(STRIPE_BOTTOM,charColor)
                        .pattern(STRIPE_TOP,charColor)
                        .pattern(STRIPE_MIDDLE,charColor)
                        .pattern(BORDER,baseColor);
                break;
            case '9':
                builder
                        .pattern(BASE,charColor)
                        .pattern(HALF_HORIZONTAL,baseColor)
                        .pattern(STRIPE_LEFT,charColor)
                        .pattern(STRIPE_BOTTOM,baseColor)
                        .pattern(STRIPE_TOP,charColor)
                        .pattern(STRIPE_RIGHT,charColor)
                        .pattern(BORDER,baseColor);
                break;
            case ' ':
                builder
                        .pattern(BASE,baseColor);
                break;
            case ',':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(SQUARE_BOTTOM_LEFT,charColor)
                        .pattern(CURLY_BORDER,baseColor)
                        .pattern(BORDER,baseColor);
                break;
            case '\'':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(SQUARE_TOP_RIGHT,charColor)
                        .pattern(DIAGONAL_RIGHT,baseColor)
                        .pattern(BORDER,baseColor);
                break;
            case '"':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(SQUARE_TOP_RIGHT,charColor)
                        .pattern(SQUARE_TOP_LEFT,charColor)
                        .pattern(STRIPE_CENTER,baseColor)
                        .pattern(BORDER,baseColor);
                break;
            case ':':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(SQUARE_BOTTOM_LEFT,charColor)
                        .pattern(SQUARE_TOP_LEFT,charColor)
                        .pattern(BORDER,baseColor);
                break;
            case ';':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(SQUARE_BOTTOM_LEFT,charColor)
                        .pattern(CURLY_BORDER,baseColor)
                        .pattern(SQUARE_TOP_LEFT,charColor)
                        .pattern(BORDER,baseColor);
                break;
            case '!':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(HALF_HORIZONTAL,charColor)
                        .pattern(STRIPE_MIDDLE,charColor)
                        .pattern(SQUARE_BOTTOM_LEFT,charColor)
                        .pattern(HALF_VERTICAL_MIRROR,baseColor)
                        .pattern(BORDER,baseColor);
                break;
            case '?':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(STRIPE_RIGHT,charColor)
                        .pattern(HALF_HORIZONTAL_MIRROR,baseColor)
                        .pattern(STRIPE_TOP,charColor)
                        .pattern(STRIPE_MIDDLE,charColor)
                        .pattern(SQUARE_BOTTOM_LEFT,charColor)
                        .pattern(BORDER,baseColor);
                break;
            case '%':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(STRIPE_DOWNLEFT,charColor)
                        .pattern(SQUARE_BOTTOM_RIGHT,charColor)
                        .pattern(SQUARE_TOP_LEFT,charColor)
                        .pattern(BORDER,baseColor);
                break;
            case '^':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(STRIPE_TOP,charColor)
                        .pattern(RHOMBUS_MIDDLE,baseColor)
                        .pattern(CURLY_BORDER,baseColor);
                break;
            case '*':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(FLOWER,charColor)
                        .pattern(RHOMBUS_MIDDLE,charColor)
                        .pattern(BORDER,baseColor);
                break;
            case '+':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(STRIPE_CENTER,charColor)
                        .pattern(STRIPE_TOP,baseColor)
                        .pattern(STRIPE_BOTTOM,baseColor)
                        .pattern(STRIPE_MIDDLE,charColor)
                        .pattern(BORDER,baseColor);
                break;
            case '-':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(STRIPE_MIDDLE,charColor)
                        .pattern(BORDER,baseColor);
                break;
            case '=':
                builder
                        .pattern(BASE,charColor)
                        .pattern(STRIPE_TOP,baseColor)
                        .pattern(STRIPE_MIDDLE,baseColor)
                        .pattern(STRIPE_BOTTOM,baseColor)
                        .pattern(BORDER,baseColor);
                break;
            case '/':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(STRIPE_DOWNLEFT,charColor)
                        .pattern(BORDER,baseColor);
                break;
            case '\\':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(STRIPE_DOWNRIGHT,charColor)
                        .pattern(BORDER,baseColor);
                break;
            case '|':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(STRIPE_CENTER,charColor)
                        .pattern(BORDER,baseColor);
                break;
            case '[':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(STRIPE_TOP,charColor)
                        .pattern(STRIPE_BOTTOM,charColor)
                        .pattern(STRIPE_LEFT,charColor)
                        .pattern(STRIPE_RIGHT,baseColor)
                        .pattern(BORDER,baseColor);
                break;
            case ']':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(STRIPE_TOP,charColor)
                        .pattern(STRIPE_BOTTOM,charColor)
                        .pattern(STRIPE_RIGHT,charColor)
                        .pattern(STRIPE_LEFT,baseColor)
                        .pattern(BORDER,baseColor);
                break;
            case '(':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(STRIPE_DOWNRIGHT,charColor)
                        .pattern(STRIPE_DOWNLEFT,charColor)
                        .pattern(HALF_VERTICAL,baseColor)
                        .pattern(BORDER,baseColor);
                break;
            case ')':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(STRIPE_DOWNRIGHT,charColor)
                        .pattern(STRIPE_DOWNLEFT,charColor)
                        .pattern(HALF_VERTICAL_MIRROR,baseColor)
                        .pattern(BORDER,baseColor);
                break;
            case '{':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(CURLY_BORDER,charColor)
                        .pattern(STRIPE_CENTER,charColor)
                        .pattern(HALF_VERTICAL_MIRROR,baseColor)
                        .pattern(TRIANGLES_BOTTOM,charColor)
                        .pattern(TRIANGLES_TOP,charColor)
                        .pattern(SQUARE_TOP_LEFT,charColor)
                        .pattern(SQUARE_BOTTOM_LEFT,charColor)
                        .pattern(BORDER,baseColor);
                break;
            case '}':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(CURLY_BORDER,charColor)
                        .pattern(STRIPE_CENTER,charColor)
                        .pattern(HALF_VERTICAL,baseColor)
                        .pattern(TRIANGLES_BOTTOM,charColor)
                        .pattern(TRIANGLES_TOP,charColor)
                        .pattern(SQUARE_TOP_RIGHT,charColor)
                        .pattern(SQUARE_BOTTOM_RIGHT,charColor)
                        .pattern(BORDER,baseColor);
                break;
            case 'A':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(STRIPE_TOP,charColor)
                        .pattern(STRIPE_LEFT,charColor)
                        .pattern(STRIPE_RIGHT,charColor)
                        .pattern(STRIPE_MIDDLE,charColor);
                break;
            case 'B':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(STRIPE_TOP,charColor)
                        .pattern(STRIPE_LEFT,charColor)
                        .pattern(STRIPE_RIGHT,charColor)
                        .pattern(STRIPE_MIDDLE,charColor)
                        .pattern(STRIPE_BOTTOM,charColor);
                break;
            case 'C':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(STRIPE_TOP,charColor)
                        .pattern(STRIPE_BOTTOM,charColor);
                break;
            case 'D':
                builder
                        .pattern(BASE,charColor)
                        .pattern(RHOMBUS_MIDDLE,baseColor)
                        .pattern(STRIPE_TOP,charColor)
                        .pattern(STRIPE_BOTTOM,charColor)
                        .pattern(STRIPE_LEFT,charColor);
                break;
            case 'E':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(STRIPE_MIDDLE,charColor)
                        .pattern(STRIPE_RIGHT,baseColor)
                        .pattern(STRIPE_BOTTOM,charColor)
                        .pattern(STRIPE_TOP,charColor)
                        .pattern(STRIPE_LEFT,charColor);
                break;
            case 'F':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(STRIPE_MIDDLE,charColor)
                        .pattern(STRIPE_RIGHT,baseColor)
                        .pattern(STRIPE_LEFT,charColor)
                        .pattern(STRIPE_TOP,charColor);
                break;
            case 'G':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(STRIPE_RIGHT,charColor)
                        .pattern(HALF_HORIZONTAL,baseColor)
                        .pattern(STRIPE_BOTTOM,charColor)
                        .pattern(STRIPE_LEFT,charColor)
                        .pattern(STRIPE_TOP,charColor);
                break;
            case 'H':
                builder
                        .pattern(BASE,charColor)
                        .pattern(STRIPE_TOP,baseColor)
                        .pattern(STRIPE_BOTTOM,baseColor)
                        .pattern(STRIPE_LEFT,charColor)
                        .pattern(STRIPE_RIGHT,charColor);
                break;
            case 'I':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(STRIPE_BOTTOM,charColor)
                        .pattern(STRIPE_TOP,charColor)
                        .pattern(STRIPE_CENTER,charColor);
                break;
            case 'J':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(STRIPE_LEFT,charColor)
                        .pattern(HALF_HORIZONTAL,baseColor)
                        .pattern(STRIPE_BOTTOM,charColor)
                        .pattern(STRIPE_RIGHT,charColor);
                break;
            case 'K':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(STRIPE_MIDDLE,charColor)
                        .pattern(STRIPE_RIGHT,baseColor)
                        .pattern(STRIPE_DOWNLEFT,charColor)
                        .pattern(STRIPE_DOWNRIGHT,charColor)
                        .pattern(STRIPE_LEFT,charColor);
                break;
            case 'L':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(STRIPE_LEFT,charColor)
                        .pattern(STRIPE_BOTTOM,charColor);
                break;
            case 'M':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(TRIANGLE_TOP,charColor)
                        .pattern(TRIANGLES_TOP,baseColor)
                        .pattern(STRIPE_LEFT,charColor)
                        .pattern(STRIPE_RIGHT,charColor);
                break;
            case 'N':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(STRIPE_LEFT,charColor)
                        .pattern(TRIANGLE_TOP,baseColor)
                        .pattern(STRIPE_RIGHT,charColor)
                        .pattern(STRIPE_DOWNRIGHT,charColor);
                break;
            case 'O':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(STRIPE_TOP,charColor)
                        .pattern(STRIPE_RIGHT,charColor)
                        .pattern(STRIPE_BOTTOM,charColor)
                        .pattern(STRIPE_LEFT,charColor);
                break;
            case 'P':
                builder
                        .pattern(BASE,charColor)
                        .pattern(HALF_HORIZONTAL,baseColor)
                        .pattern(STRIPE_RIGHT,charColor)
                        .pattern(STRIPE_BOTTOM,baseColor)
                        .pattern(STRIPE_TOP,charColor)
                        .pattern(STRIPE_LEFT,charColor);
                break;
            case 'Q':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(STRIPE_DOWNRIGHT,charColor)
                        .pattern(HALF_HORIZONTAL,baseColor)
                        .pattern(STRIPE_LEFT,charColor)
                        .pattern(STRIPE_BOTTOM,charColor)
                        .pattern(STRIPE_RIGHT,charColor)
                        .pattern(STRIPE_TOP,charColor);
                break;
            case 'R':
                builder
                        .pattern(BASE,charColor)
                        .pattern(HALF_HORIZONTAL_MIRROR,baseColor)
                        .pattern(STRIPE_DOWNRIGHT,charColor)
                        .pattern(HALF_VERTICAL,baseColor)
                        .pattern(STRIPE_LEFT,charColor)
                        .pattern(STRIPE_TOP,charColor)
                        .pattern(STRIPE_MIDDLE,charColor);
                break;
            case 'S':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(TRIANGLE_TOP,charColor)
                        .pattern(TRIANGLE_BOTTOM,charColor)
                        .pattern(SQUARE_TOP_RIGHT,charColor)
                        .pattern(SQUARE_BOTTOM_LEFT,charColor)
                        .pattern(RHOMBUS_MIDDLE,baseColor)
                        .pattern(STRIPE_DOWNRIGHT,charColor);
                break;
            case 'T':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(STRIPE_CENTER,charColor)
                        .pattern(STRIPE_TOP,charColor);
                break;
            case 'U':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(STRIPE_BOTTOM,charColor)
                        .pattern(STRIPE_RIGHT,charColor)
                        .pattern(STRIPE_LEFT,charColor);
                break;
            case 'V':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(STRIPE_LEFT,charColor)
                        .pattern(TRIANGLE_BOTTOM,baseColor)
                        .pattern(STRIPE_DOWNLEFT,charColor);
                break;
            case 'W':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(TRIANGLE_BOTTOM,charColor)
                        .pattern(TRIANGLES_BOTTOM,baseColor)
                        .pattern(STRIPE_RIGHT,charColor)
                        .pattern(STRIPE_LEFT,charColor);
                break;
            case 'X':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(STRIPE_DOWNLEFT,charColor)
                        .pattern(STRIPE_DOWNRIGHT,charColor);
                break;
            case 'Y':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(STRIPE_DOWNRIGHT,charColor)
                        .pattern(HALF_HORIZONTAL_MIRROR,baseColor)
                        .pattern(STRIPE_DOWNLEFT,charColor);
                break;
            case 'Z':
                builder
                        .pattern(BASE,baseColor)
                        .pattern(TRIANGLE_TOP,charColor)
                        .pattern(TRIANGLE_BOTTOM,charColor)
                        .pattern(SQUARE_TOP_LEFT,charColor)
                        .pattern(SQUARE_BOTTOM_RIGHT,charColor)
                        .pattern(RHOMBUS_MIDDLE,baseColor)
                        .pattern(STRIPE_DOWNLEFT,charColor);
                break;

        }
        return builder.create();

    }
}
