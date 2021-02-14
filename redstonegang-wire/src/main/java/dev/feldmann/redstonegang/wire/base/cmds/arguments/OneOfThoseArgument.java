package dev.feldmann.redstonegang.wire.base.cmds.arguments;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class OneOfThoseArgument extends Argument<String> {


    List<String> validArguments = new ArrayList();

    public OneOfThoseArgument(String name, boolean optional, String... valid)
    {
        super(name, optional);
        for (String v : valid) {
            validArguments.add(v);
        }
    }

    public OneOfThoseArgument(String name, boolean optional, List<String> valid)
    {
        super(name, optional);
        for (String v : valid) {
            validArguments.add(v);
        }

    }

    public String getValidString() {
        String valid = "";
        for (int x = 0; x < validArguments.size(); x++) {
            if (x > 0) {
                valid += " | ";
            }
            valid += validArguments.get(x);
        }
        return valid;
    }

    @Override
    public String getErrorMessage(String input) {
        return "Valor `" + input + "` inválido! Valores válidos: " + getValidString();
    }

    @Override
    public String process(CommandSender cs, int index, String[] args) {
        if (index >= args.length) {
            return null;
        }
        if (validArguments.contains(args[index])) {
            return args[index];
        }
        return null;
    }

    @Override
    public List<String> autoComplete(CommandSender cs,String start) {
        return validArguments;
    }
}
