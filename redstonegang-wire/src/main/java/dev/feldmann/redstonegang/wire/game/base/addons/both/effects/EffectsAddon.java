package dev.feldmann.redstonegang.wire.game.base.addons.both.effects;

import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import dev.feldmann.redstonegang.wire.utils.hitboxes.Hitbox;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class EffectsAddon extends Addon {


    public void effect(World w, Hitbox r, double prec, Effect ef) {
        effect((Object) w, r, prec, ef);
    }

    public void effect(Player player, Hitbox r, double prec, Effect ef) {
        effect((Object) player, r, prec, ef);
    }


    private void effect(Object w, Hitbox r, double prec, Effect ef) {
        double minX = r.getMin().getX();
        double minY = r.getMin().getY();
        double minZ = r.getMin().getZ();

        double maxX = r.getMax().getX();
        double maxY = r.getMax().getY();
        double maxZ = r.getMax().getZ();


        double difx = (maxX - minX);
        double difz = maxZ - minZ;
        double dify = maxY - minY;


        //X
        for (double x = 0; x < difx; x += prec) {

            effect(w, ef, new Vector(minX + x, minY, minZ));
            effect(w, ef, new Vector(minX + x, maxY, minZ));
            effect(w, ef, new Vector(minX + x, minY, maxZ));
            effect(w, ef, new Vector(minX + x, maxY, maxZ));

        }
        // Y
        for (double x = 0; x < dify; x += prec) {
            effect(w, ef, new Vector(minX, minY + x, minZ));
            effect(w, ef, new Vector(maxX, minY + x, minZ));
            effect(w, ef, new Vector(minX, minY + x, maxZ));
            effect(w, ef, new Vector(maxX, minY + x, maxZ));
        }
        // Z
        for (double x = 0; x < difz; x += prec) {
            effect(w, ef, new Vector(minX, minY, minZ + x));
            effect(w, ef, new Vector(maxX, minY, minZ + x));
            effect(w, ef, new Vector(minX, maxY, minZ + x));
            effect(w, ef, new Vector(maxX, maxY, minZ + x));
        }

    }


    public void effect(Object o, Effect ef, Vector v) {
        if (o instanceof World) {
            World w = (World) o;
            w.spigot().playEffect(new Location(w, v.getX(), v.getY(), v.getZ()), ef, 0, 0, 0, 0, 0, 0, 1, 32);
        }
        if (o instanceof Player) {
            Player p = (Player) o;
            p.spigot().playEffect(new Location(p.getWorld(), v.getX(), v.getY(), v.getZ()), ef, 0, 0, 0, 0, 0, 0, 1, 32);
        }
    }

}
