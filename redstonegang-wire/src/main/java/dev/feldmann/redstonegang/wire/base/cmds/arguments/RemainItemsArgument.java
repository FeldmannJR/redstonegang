package dev.feldmann.redstonegang.wire.base.cmds.arguments;


import dev.feldmann.redstonegang.common.utils.formaters.numbers.NumberUtils;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.List;

public class RemainItemsArgument extends Argument<List<MaterialData>> {


    public RemainItemsArgument(String name, boolean optional) {
        super(name, optional);
    }

    @Override
    public String getErrorMessage(String input) {
        return "Item inválido! Use ID:DATA ID2:DATA2 ...";
    }

    @Override
    public List<MaterialData> process(CommandSender cs, int index, String[] args) {
        if (index >= args.length) return null;
        List<MaterialData> its = new ArrayList<>();
        for (int x = index; x < args.length; x++) {
            String s = args[x];
            String material;
            byte data = 0;
            if (s.contains(":")) {
                material = s.split(":")[0];
                try {
                    data = Byte.valueOf(s.split(":")[1]);
                } catch (NumberFormatException ex) {
                    return null;
                }
            } else {
                material = s;
            }
            Material m;
            Integer id = NumberUtils.integerFromString(material);
            if (id == null) {
                m = Material.getMaterial(material);
            } else {
                m = Material.getMaterial(id);
            }
            if (m == null) {
                return null;
            }
            its.add(new MaterialData(m, data));
        }
        if (its.isEmpty()) return null;
        return its;
    }
}
