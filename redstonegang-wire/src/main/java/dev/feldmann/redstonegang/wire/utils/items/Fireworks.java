package dev.feldmann.redstonegang.wire.utils.items;

import dev.feldmann.redstonegang.common.utils.RandomUtils;
import dev.feldmann.redstonegang.wire.Wire;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.ArrayList;
import java.util.List;

public class Fireworks {


    private static List<Color> FIREWORK_COLORS = new ArrayList<>();

    static {
        FIREWORK_COLORS.add(Color.RED);
        FIREWORK_COLORS.add(Color.GREEN);
        FIREWORK_COLORS.add(Color.BLUE);
        FIREWORK_COLORS.add(Color.YELLOW);
        FIREWORK_COLORS.add(Color.WHITE);
        FIREWORK_COLORS.add(Color.ORANGE);
        FIREWORK_COLORS.add(Color.GRAY);
        FIREWORK_COLORS.add(Color.OLIVE);
        FIREWORK_COLORS.add(Color.PURPLE);
        FIREWORK_COLORS.add(Color.SILVER);

    }

    public static void randomFirework(Player p, int qtd) {
        final Location loc = p.getLocation();
        for (int x = 0; x < qtd; x++) {
            Wire.instance.game().getServer().scheduler().runAfter(new Runnable() {
                @Override
                public void run() {
                    randomFirework(loc);
                }
            }, x * 10);
        }
    }

    public static void randomFirework(Location loc) {
        Firework firewo = loc.getWorld().spawn(loc, Firework.class);
        FireworkMeta meta = firewo.getFireworkMeta();
        meta.addEffect(getRandomEffect());
        meta.setPower(RandomUtils.randomInt(1, 2));
        firewo.setFireworkMeta(meta);
    }


    public static FireworkEffect getRandomEffect() {
        FireworkEffect.Builder b = FireworkEffect.builder()
                .withColor(RandomUtils.getRandom(FIREWORK_COLORS));
        if (RandomUtils.randomBoolean()) {
            b.with(FireworkEffect.Type.BALL_LARGE);
        } else {
            b.with(FireworkEffect.Type.BALL);
        }
        if (RandomUtils.randomBoolean()) {
            b.withTrail();
        }
        if (RandomUtils.randomBoolean()) {
            b.withFlicker();
        }

        return b.build();

    }
}
