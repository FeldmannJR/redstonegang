package dev.feldmann.redstonegang.wire.base.cmds.arguments;


import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public class RemainEntityTypeArgument extends Argument<List<EntityType>> {


    public RemainEntityTypeArgument(String name, boolean optional) {
        super(name, optional);
    }

    @Override
    public String getErrorMessage(String input) {
        return "Nome de bixo invalido! Validos: " + getAllMobs();
    }

    @Override
    public boolean onlyLast() {
        return true;
    }

    @Override
    public List<EntityType> process(CommandSender cs, int index, String[] args) {
        if (index >= args.length) return null;
        List<EntityType> its = new ArrayList<>();
        for (int x = index; x < args.length; x++) {
            EntityType e = getEntity(args[x]);
            if (e == null) {
                return null;
            }
            its.add(e);

        }
        if (its.isEmpty()) return null;
        return its;
    }

    public String getAllMobs() {
        String s = "";
        boolean b = true;
        for (EntityType ea : EntityType.values()) {
            if (ea.getEntityClass() == null) continue;
            if (!LivingEntity.class.isAssignableFrom(ea.getEntityClass())) {
                continue;
            }
            if (b) {
                s += "§c ";
            } else {
                s += "§e ";
            }
            s += ea.name();
            b = !b;
        }
        return s;
    }

    private EntityType getEntity(String s) {
        for (EntityType ti : EntityType.values()) {

            if (ti.name().equalsIgnoreCase(s) || s.equalsIgnoreCase(ti.getName())) {
                return ti;
            }
        }
        return null;

    }
}
