package dev.feldmann.redstonegang.wire.base.cmds.arguments;


import dev.feldmann.redstonegang.common.utils.formaters.time.TimeUnit;
import dev.feldmann.redstonegang.common.utils.formaters.time.TimeUtils;
import org.bukkit.command.CommandSender;

public class TimeArgument extends Argument<Long> {

    TimeUnit unit;
    long min = 1;
    long max = Long.MAX_VALUE;

    public TimeArgument(String name, TimeUnit base, boolean optional, Long defaul, long min, long max) {
        super(name, optional, defaul);
        this.min = min;
        this.max = max;
        this.unit = base;
    }

    public TimeArgument(String name, TimeUnit base, boolean optional, long min, long max) {
        this(name, base, optional, null, min, max);
    }

    public TimeArgument(String name, TimeUnit base) {
        this(name, base, false, 1, Long.MAX_VALUE);
    }

    @Override
    public String getErrorMessage(String input) {
        try {

            Long x = TimeUtils.convertFromString(input, unit);
            if (x == null) {
                String suf = "";
                for (TimeUnit unit : TimeUnit.values()) {
                    if (unit.getMp() > this.unit.getMp() && unit.getSuffix() != null) {
                        suf += unit.getSuffix() + "(" + unit.getNome(1) + ") ";
                    }
                }
                return input + " não é uma entrada válida! Você pode usar os sufixos: " + suf.trim();
            }
            if (x > max || x < min) {
                return "Comando só aceita valores entre " + min + " " + unit.getNome(min) + " e " + max + " " + unit.getNome(max);
            }
            return "";
        } catch (NumberFormatException ex) {
            return input + " não é um !";
        }

    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setMin(int min) {
        this.min = min;
    }

    @Override
    public Long process(CommandSender cs, int index, String[] args) {
        if (index >= args.length) {
            return null;
        }
        return TimeUtils.convertFromString(args[index], unit);

    }
}
