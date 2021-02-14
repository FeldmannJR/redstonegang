package dev.feldmann.redstonegang.wire.base.cmds;

import dev.feldmann.redstonegang.common.player.permissions.PermissionDescription;
import dev.feldmann.redstonegang.common.utils.Cooldown;
import dev.feldmann.redstonegang.common.utils.formaters.StringUtils;
import dev.feldmann.redstonegang.wire.RedstoneGangSpigot;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Argument;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.DefaultArguments;
import dev.feldmann.redstonegang.wire.base.cmds.perm.Permission;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public abstract class RedstoneCmd extends Command {
    //Spigot
    private CommandExecutor exe = null;
    private String name;
    public List<RedstoneCmd> cmds = new ArrayList<>();
    private CmdExecutePerm executePerm = null;
    private RedstoneCmd parent = null;

    private Cooldown cd = null;

    List<Argument> args = new ArrayList();


    public RedstoneCmd(String name, String desc, Argument... args) {
        super(name);
        this.name = name;
        this.description = desc;
        if (args != null) {
            for (Argument a : args) {
                this.args.add(a);
                if (a.onlyLast()) {
                    break;
                }
            }
        }
    }

    public RedstoneCmd(String name) {
        super(name);
        this.name = name;

    }

    public List<Argument> getArgs() {
        return args;
    }

    public void addCmd(RedstoneCmd cmd) {
        this.cmds.add(cmd);
        cmd.setParent(this);
    }

    public RedstoneCmd findSubcmdWithName(String name) {
        for (RedstoneCmd cmd : cmds) {
            if (cmd.getName().equals(name)) {
                return cmd;
            }
        }
        for (RedstoneCmd cmd : cmds) {
            if (cmd.getAliases().contains(name)) {
                return cmd;
            }
        }

        return null;
    }

    protected Function<CommandSender, Boolean> preProcessCommandAsync() {
        return null;
    }

    private Arguments preProcessCommand(CommandSender cs, String[] cmdargs) {
        Arguments rargs = new Arguments();
        for (int x = 0; x < args.size(); x++) {
            Argument a = args.get(x);
            Object value = a.process(cs, x, cmdargs);
            boolean extrapolou = x >= cmdargs.length;
            if (extrapolou) {
                if (!a.isOptional()) {
                    rargs.setError("Faltando argumentos " + a.getName() + "\n" +
                            "Uso correto: " + getFullCommand() + " " + buildUsage());
                } else {
                    if (a.getDefaultValue() != null)
                        rargs.setValue(a, a.getDefaultValue());
                    continue;
                }
                if (a instanceof DefaultArguments) {
                    rargs.setValue(a, value);
                }
                return rargs;
            }
            if (value == null) {
                rargs.setError(a.getErrorMessage(cmdargs[x]));
                return rargs;
            }
            rargs.setValue(a, value);
        }
        return rargs;
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] args) {

        if (!canExecute(commandSender)) {
            return false;
        }
        if (commandSender instanceof Player) {
            if (cd != null && cd.isCooldown(((Player) commandSender).getUniqueId())) {
                C.error(commandSender, "Espere um pouco para executar o comando novamente!");
                return false;
            }
        }
        //Se é o comando não tem pai ele combina os args com aspas "anims "
        if (this.exe != null && combineQuotesArguments())
            args = StringUtils.combineArgs(args);

        boolean foundSubCmd = false;
        Function<CommandSender, Boolean> pre = preProcessCommandAsync();

        if (args.length != 0) {

            RedstoneCmd sub = findSubcmdWithName(args[0]);
            if (sub != null) {
                String[] newargs;
                if (args.length > 1) {
                    newargs = Arrays.copyOfRange(args, 1, args.length);
                } else {
                    newargs = new String[0];
                }
                sub.execute(commandSender, sub.getName(), newargs);
                foundSubCmd = true;
            }


        }

        if (!foundSubCmd) {
            Arguments ar = preProcessCommand(commandSender, args);
            if (pre != null) {
                String[] finalArgs = args;
                RedstoneGangSpigot.instance.runAsync(() -> {
                    if (pre.apply(commandSender)) {
                        RedstoneGangSpigot.instance.runSync(() -> processCommandWithArguments(commandSender, ar, finalArgs));
                    }

                });
            } else {
                processCommandWithArguments(commandSender, ar, args);
            }

        }


        if (this.exe != null) {
            exe.onCommand(commandSender, this, s, args);
        }
        if (commandSender instanceof Player) {
            if (cd != null && !cd.isCooldown(((Player) commandSender).getUniqueId())) {
                cd.addCooldown(((Player) commandSender).getUniqueId());
                return false;
            }
        }

        return false;
    }

    private void processCommandWithArguments(CommandSender commandSender, Arguments ar, String[] args) {
        if (ar.getError() != null) {
            C.error(commandSender, ar.getError());
        } else {
            HelpCmd help = getHelp();
            if (help != null) {
                if (args.length > 0) {
                    C.error(commandSender, "Comando não encontrado!");
                }
                help.showHelp(commandSender, 1);
            } else {
                command(commandSender, ar);

            }
        }
    }

    protected String getFullCommand() {
        String base = getName();
        RedstoneCmd pai = this;
        while (pai != null) {
            pai = pai.getParent();
            if (pai != null) {
                base = pai.getName() + " " + base;
            }
        }
        return base;
    }

    public String buildUsage() {
        String base = "";
        for (Argument arg : args) {
            if (!arg.showInHelp()) {
                continue;
            }
            if (!base.isEmpty()) {
                base += " ";
            }
            if (arg.isOptional()) {
                base += "[" + arg.getName() + "]";
            } else {
                base += "<" + arg.getName() + ">";
            }
        }
        return base;
    }

    public boolean canExecute(CommandSender cs) {
        if (executePerm != null) {
            if (!executePerm.canExecute(cs)) {
                C.error(cs, "Você não tem permissão para executar este comando!");
                return false;
            }
        }

        if (!canConsoleExecute()) {
            if (cs instanceof ConsoleCommandSender) {
                C.error(cs, "Este comando não pode ser executado no console!!");
                return false;
            }
        }
        if (!canPlayerExecute()) {
            if (cs instanceof Player) {
                C.error(cs, "Este comando não pode ser executado por jogadores!");
                return false;
            }
        }
        return true;
    }

    public CmdExecutePerm getExecutePerm() {
        return executePerm;
    }

    @Override
    public String getDescription() {
        return description;
    }

    private HelpCmd getHelp() {
        for (RedstoneCmd cmd : cmds) {
            if (cmd instanceof HelpCmd) {
                return (HelpCmd) cmd;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }


    public boolean canOverride() {
        return false;
    }


    protected RedstoneCmd getParent() {
        return parent;
    }

    private void setParent(RedstoneCmd parent) {
        this.parent = parent;
    }

    public void setExecutePerm(CmdExecutePerm executePerm) {
        this.executePerm = executePerm;
    }

    public void setPermission(PermissionDescription permission) {
        setExecutePerm(new Permission(permission.getKey()));
    }

    @Override
    public void setPermission(String permission) {
        setExecutePerm(new Permission(permission));
    }

    public void setAlias(String... alias) {
        this.setAliases(Arrays.asList(alias));
    }

    public void setCooldown(Cooldown cd) {
        this.cd = cd;
    }

    public boolean canConsoleExecute() {
        return true;
    }

    public boolean canPlayerExecute() {
        return true;
    }


    public boolean combineQuotesArguments() {
        return true;
    }

    public String getLongHelp() {
        return null;
    }

    public void setExecutor(CommandExecutor exe) {
        this.exe = exe;
    }

    /**
     * Esta função é chamada quando nenhum subcomando com o primeiro argumento foi encontrado
     **/
    public abstract void command(CommandSender sender, Arguments args);

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws
            IllegalArgumentException {
        List<String> retorno = new ArrayList();
        if (executePerm != null) {
            if (!executePerm.canExecute(sender)) {
                return retorno;
            }
        }
        if (!canPlayerExecute() && sender instanceof Player) {
            return retorno;
        }
        if (!canConsoleExecute() && sender instanceof ConsoleCommandSender) {
            return retorno;
        }
        if (cmds.isEmpty()) {
            int index = args.length - 1;
            if (index < this.args.size()) {
                Argument a = this.args.get(index);
                return a.autoComplete(sender, args[index]);
            }

        } else {
            //Tem subcomando
            if (args.length == 1) {
                for (RedstoneCmd cmd : cmds) {
                    if (cmd.getName().startsWith(args[0])) {
                        retorno.add(cmd.getName());
                    }
                }
            } else if (args.length > 1) {
                String subcmd = args[0];
                RedstoneCmd sub = findSubcmdWithName(subcmd);
                if (sub != null) {
                    return sub.tabComplete(sender, alias, Arrays.copyOfRange(args, 1, args.length));
                }
            }
        }
        return retorno;
    }


    public int getPlayerId(CommandSender cs) {
        if (cs instanceof Player) {
            return RedstoneGangSpigot.getPlayer(((Player) cs).getUniqueId()).getId();
        }
        return -1;
    }
}
