package dev.feldmann.redstonegang.wire.base.cmds.arguments;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class Argument<T> {

    private String name;
    private boolean optional;
    private boolean showInHelp = true;
    //Valor se o optional for true
    private T defaultValue;

    public Argument(String name, boolean optional) {
        this(name, optional, null);
    }

    public Argument(String name, boolean optional, T defaultValue) {
        this.defaultValue = defaultValue;
        this.name = name;
        this.optional = optional;
    }

    public Argument(String name, boolean optional, boolean showInHelp) {
        this.name = name;
        this.optional = optional;
        this.showInHelp = showInHelp;
    }

    public boolean isOptional() {
        return optional;
    }

    public boolean showInHelp() {
        return showInHelp;
    }

    public String getName() {
        return name;
    }

    public boolean onlyLast() {
        return false;
    }

    public abstract String getErrorMessage(String input);

    public T getDefaultValue() {
        return defaultValue;
    }

    public abstract T process(CommandSender cs, int index, String[] args);

    public void setShowInHelp(boolean showInHelp) {
        this.showInHelp = showInHelp;
    }

    public List<String> autoComplete(CommandSender sender, String start) {
        return Collections.emptyList();
    }

    protected List<String> autoCompletePlayers(String s) {
        return autoCompletePlayers(s, null);
    }

    protected List<String> autoCompletePlayers(String s, CommandSender exec) {
        List<String> pla = new ArrayList();
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (org.apache.commons.lang.StringUtils.startsWithIgnoreCase(p.getName(), s)) {
                if (exec == null || !(exec instanceof Player) || ((Player) exec).canSee(p))
                    pla.add(p.getName());
            }
        }
        pla.sort(String.CASE_INSENSITIVE_ORDER);
        return pla;
    }
}
