package dev.feldmann.redstonegang.wire.base.cmds.arguments;

import org.bukkit.command.CommandSender;

public class StringArgument extends Argument<String> {


    int minSize = 1;
    int maxSize = Integer.MAX_VALUE;

    public StringArgument(String name, boolean optional, int minSize, int maxSize) {
        super(name, optional);
        this.maxSize = maxSize;
        this.minSize = minSize;
    }

    public StringArgument(String nome) {
        super(nome, false);
    }


    public StringArgument(String nome, boolean optional, boolean showInHelp) {
        super(nome, optional, showInHelp);
    }

    public StringArgument(String nome, boolean optional) {
        super(nome, optional);
    }

    public StringArgument(String nome, boolean optional, String defau) {
        super(nome, optional, defau);
    }

    public StringArgument(String nome, int minSize, int maxSize) {
        this(nome, false, minSize, maxSize);
    }

    @Override
    public String getErrorMessage(String arg) {
        if (arg.length() < minSize || arg.length() > maxSize) {
            return "O tamanho do argumento passado precisa ser entre " + minSize + " e " + maxSize;
        }
        return "WTF CHAMA O ADMIN";
    }

    @Override
    public String process(CommandSender cs, int index, String[] args) {
        if (args.length <= index) {
            return null;
        }
        String arg = args[index];
        if (arg.length() < minSize || arg.length() > maxSize) {
            return null;
        }
        return arg;
    }


}
