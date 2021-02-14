package dev.feldmann.redstonegang.wire.base.cmds.arguments;

import org.bukkit.command.CommandSender;

public class RemainStringsArgument extends StringArgument {


    public RemainStringsArgument(String name, boolean optional) {
        super(name, optional);
    }

    @Override
    public String getErrorMessage(String arg) {
        return "Tamanho invalido! Min: " + minSize + " Max:" + maxSize;
    }

    @Override
    public String process(CommandSender cs, int index, String[] args) {
        if (args.length <= index) {
            return null;
        }
        String s = "";
        for (int x = index; x < args.length; x++) {
            if (!s.isEmpty()) {
                s += " ";
            }
            s += args[x];
        }
        if (s.length() < minSize || s.length() > maxSize) {
            return null;
        }
        return s;
    }

    @Override
    public boolean onlyLast() {
        return true;
    }
}
