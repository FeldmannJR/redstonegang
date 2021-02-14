package dev.feldmann.redstonegang.wire.base.cmds.arguments;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public class EnumArgument<T extends Enum<T>> extends Argument<T> {

    LinkedHashMap<String, T> validArguments = new LinkedHashMap<>();
    Class<T> tipo;

    public EnumArgument(String name, Class<T> tipo, boolean optional) {
        super(name, optional);
        this.tipo = tipo;
        for (T t : values()) {
            validArguments.put(t.name().toLowerCase(), t);
        }

    }

    private T[] values() {
        T[] en = tipo.getEnumConstants();
        return en;

    }

    public String getValidString() {
        String valid = "";
        for (String key : validArguments.keySet()) {
            if (!valid.isEmpty()) {
                valid += " | ";
            }
            valid += key;
        }
        return valid;
    }

    @Override
    public String getErrorMessage(String input) {
        return "Valor `" + input + "` inválido! Valores válidos: " + getValidString();
    }

    @Override
    public T process(CommandSender cs, int index, String[] args) {
        if (index >= args.length) {
            return null;
        }
        if (validArguments.containsKey(args[index].toLowerCase())) {

            return validArguments.get(args[index].toLowerCase());
        }
        return null;
    }

    @Override
    public List<String> autoComplete(CommandSender cs, String start) {
        return new ArrayList<>(validArguments.keySet()).stream().filter(
                (arg) -> arg.startsWith(start.toLowerCase())
        ).collect(Collectors.toList());
    }
}
