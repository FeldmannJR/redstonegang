package dev.feldmann.redstonegang.wire.base.cmds.arguments;

import dev.feldmann.redstonegang.common.utils.formaters.numbers.NumberUtils;
import org.bukkit.command.CommandSender;

public class IntegerArgument extends Argument<Integer> {

    int min = Integer.MIN_VALUE;
    int max = Integer.MAX_VALUE;

    public IntegerArgument(String name, boolean optional, Integer defaul, int min, int max) {
        super(name, optional, defaul);
        this.min = min;
        this.max = max;
    }

    public IntegerArgument(String name, boolean optional, int min, int max) {
        this(name, optional, null, min, max);
    }

    public IntegerArgument(String name, int min, int max) {
        this(name, false, min, max);
    }

    @Override
    public String getErrorMessage(String input) {
        Integer x = NumberUtils.convertFromString(input);
        if (x == null) {
            return input + " não é um numero inteiro!";
        }
        if (x > max || x < min) {
            return "Comando só aceita valores entre " + min + " e " + max + "";
        }
        return "";
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setMin(int min) {
        this.min = min;
    }

    @Override
    public Integer process(CommandSender cs, int index, String[] args) {
        if (index >= args.length) {
            return null;
        }
        Integer x = NumberUtils.convertFromString(args[index]);
        if (x == null) return null;
        if (x > max || x < min) {
            return null;
        }
        return x;

    }
}
