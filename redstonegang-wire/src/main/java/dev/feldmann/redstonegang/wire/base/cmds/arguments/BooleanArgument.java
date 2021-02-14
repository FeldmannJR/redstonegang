package dev.feldmann.redstonegang.wire.base.cmds.arguments;

import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public class BooleanArgument extends Argument<Boolean> {

    public String trueString = "sim";
    public String falseString = "não";

    public BooleanArgument(String name, boolean optional, Boolean defaultValue) {
        super(name, optional, defaultValue);
    }

    public BooleanArgument(String name, boolean optional, String falseString, String trueString) {
        super(name, optional);
        this.trueString = trueString;
        this.falseString = falseString;
    }


    @Override
    public String getErrorMessage(String input) {
        return input + " não é uma entrada valida! Use " + trueString + " ou " + falseString;
    }

    @Override
    public List<String> autoComplete(CommandSender cs, String start) {
        return Arrays.asList(trueString, falseString);
    }

    @Override
    public Boolean process(CommandSender cs, int index, String[] args) {
        if (index >= args.length) {
            return null;
        }
        try {
            String a = args[index];
            if (a.equalsIgnoreCase(trueString)) {
                return true;
            } else if (a.replace('ã', 'a').equalsIgnoreCase(falseString.replace('ã', 'a'))) {
                return false;
            } else {
                return null;
            }
        } catch (NumberFormatException ex) {
            return null;
        }
    }
}
