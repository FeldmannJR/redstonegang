package dev.feldmann.redstonegang.wire.base.cmds.arguments;

import org.bukkit.command.CommandSender;

public class DoubleArgument extends Argument<Double> {

    double min;
    double max;

    public DoubleArgument(String name, boolean optional, Double defaul, double min, double max) {
        super(name, optional, defaul);
        this.min = min;
        this.max = max;
    }

    public DoubleArgument(String name, boolean optional, double min, double max) {
        this(name, optional, null, min, max);
    }

    public DoubleArgument(String name, boolean optional) {
        this(name, optional, null, -Double.MAX_VALUE,Double.MAX_VALUE);
    }

    public DoubleArgument(String name, double min, double max) {
        this(name, false, min, max);
    }

    @Override
    public String getErrorMessage(String input) {
        try {
            Double x = Double.valueOf(input.replace(",", "."));
            if (x > max || x < min) {
                return "Comando só aceita valores entre " + min + " e " + max + "";
            }
            return "";
        } catch (NumberFormatException ex) {
            return input + " não é um numero com virgula!";
        }

    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setMin(int min) {
        this.min = min;
    }

    @Override
    public Double process(CommandSender cs, int index, String[] args) {
        if (index >= args.length) {
            return null;
        }
        try {
            Double x = Double.valueOf(args[index].replace(",", "."));
            if (x > max || x < min) {
                return null;
            }
            return x;
        } catch (NumberFormatException ex) {
            return null;
        }
    }
}
