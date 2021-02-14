package dev.feldmann.redstonegang.wire.utils.hitboxes;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.function.Function;

public class RayCast {


    //http://www.3dkingdoms.com/weekly/weekly.php?a=3
    //Esse código é bem mais gostozinho, não utiliza repetição e pra testar cada ponto
    public static Vector intersect(Vector l1, Vector l2, Hitbox hitbox) {
        return CheckLineBox(hitbox.min, hitbox.max, l1, l2);
    }

    public static Vector CheckLineBox(Vector B1, Vector B2, Vector L1, Vector L2) {
        Vector Hit = new Vector();
        if (L2.getX() < B1.getX() && L1.getX() < B1.getX()) return null;
        if (L2.getX() > B2.getX() && L1.getX() > B2.getX()) return null;
        if (L2.getY() < B1.getY() && L1.getY() < B1.getY()) return null;
        if (L2.getY() > B2.getY() && L1.getY() > B2.getY()) return null;
        if (L2.getZ() < B1.getZ() && L1.getZ() < B1.getZ()) return null;
        if (L2.getZ() > B2.getZ() && L1.getZ() > B2.getZ()) return null;
        if (L1.getX() > B1.getX() && L1.getX() < B2.getX() &&
                L1.getY() > B1.getY() && L1.getY() < B2.getY() &&
                L1.getZ() > B1.getZ() && L1.getZ() < B2.getZ()) {
            Hit = L1;
            return Hit;
        }
        if ((GetIntersection(L1.getX() - B1.getX(), L2.getX() - B1.getX(), L1, L2, Hit) && InBox(Hit, B1, B2, 1))
                || (GetIntersection(L1.getY() - B1.getY(), L2.getY() - B1.getY(), L1, L2, Hit) && InBox(Hit, B1, B2, 2))
                || (GetIntersection(L1.getZ() - B1.getZ(), L2.getZ() - B1.getZ(), L1, L2, Hit) && InBox(Hit, B1, B2, 3))
                || (GetIntersection(L1.getX() - B2.getX(), L2.getX() - B2.getX(), L1, L2, Hit) && InBox(Hit, B1, B2, 1))
                || (GetIntersection(L1.getY() - B2.getY(), L2.getY() - B2.getY(), L1, L2, Hit) && InBox(Hit, B1, B2, 2))
                || (GetIntersection(L1.getZ() - B2.getZ(), L2.getZ() - B2.getZ(), L1, L2, Hit) && InBox(Hit, B1, B2, 3)))
            return Hit;

        return null;
    }

    private static boolean GetIntersection(double fDst1, double fDst2, Vector P1, Vector P2, Vector hit) {
        if ((fDst1 * fDst2) >= 0.0f) return false;
        if (fDst1 == fDst2) return false;


        Vector tmp = P1.clone();

        Vector sub = P2.clone().subtract(P1);
        sub = sub.multiply(-fDst1 / (fDst2 - fDst1));
        tmp = tmp.add(sub);

        hit.setX(tmp.getX());
        hit.setY(tmp.getY());
        hit.setZ(tmp.getZ());

        return true;
    }

    public static double distance(Hitbox box, Vector point) {
        double dx = Math.max(0, Math.max(box.min.getX() - point.getX(), point.getX() - box.max.getX()));
        double dy = Math.max(0, Math.max(box.min.getY() - point.getY(), point.getY() - box.max.getY()));
        double dz = Math.max(0, Math.max(box.min.getZ() - point.getZ(), point.getZ() - box.max.getZ()));
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    private static boolean InBox(Vector Hit, Vector B1, Vector B2, int Axis) {
        if (Axis == 1 && Hit.getZ() > B1.getZ() && Hit.getZ() < B2.getZ() && Hit.getY() > B1.getY() && Hit.getY() < B2.getY())
            return true;
        if (Axis == 2 && Hit.getZ() > B1.getZ() && Hit.getZ() < B2.getZ() && Hit.getX() > B1.getX() && Hit.getX() < B2.getX())
            return true;
        return Axis == 3 && Hit.getX() > B1.getX() && Hit.getX() < B2.getX() && Hit.getY() > B1.getY() && Hit.getY() < B2.getY();
    }

    public static Vector getHitPoint(Player p, double distance, Hitbox box) {
        Vector inicial = p.getEyeLocation().toVector();
        Vector fim = p.getEyeLocation().toVector().add(p.getLocation().getDirection().multiply(distance));

        return intersect(inicial, fim, box);

    }

    public static Entity getTarget(final LivingEntity ent, int maxRange) {
        return getTarget(ent, maxRange, null);
    }

    public static Entity getTarget(final LivingEntity ent, int maxRange, Function<Entity, Boolean> valid) {
        assert maxRange > 10;
        Entity target = null;
        double targetDistanceSquared = 0;
        final double radiusSquared = 1;
        final Vector l = ent.getEyeLocation().toVector(), n = ent.getLocation().getDirection().normalize();
        final double cos45 = Math.cos(Math.PI / 4);
        for (final Entity other : ent.getNearbyEntities(maxRange, maxRange, maxRange)) {
            if (other == ent) {
                continue;
            }
            if (valid != null && !valid.apply(ent)) {
                continue;
            }
            if (target == null || targetDistanceSquared > other.getLocation().distanceSquared(ent.getLocation())) {
                final Vector t = other.getLocation().add(0, 1, 0).toVector().subtract(l);
                if (n.clone().crossProduct(t).lengthSquared() < radiusSquared && t.normalize().dot(n) >= cos45) {
                    target = other;
                    targetDistanceSquared = target.getLocation().distanceSquared(ent.getLocation());
                }
            }
        }
        return target;
    }


}
