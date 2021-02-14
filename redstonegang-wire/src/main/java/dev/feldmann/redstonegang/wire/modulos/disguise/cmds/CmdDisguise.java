package dev.feldmann.redstonegang.wire.modulos.disguise.cmds;

import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.DefaultArguments;
import dev.feldmann.redstonegang.wire.base.cmds.perm.Fodao;
import dev.feldmann.redstonegang.wire.modulos.disguise.DisguiseAPI;
import dev.feldmann.redstonegang.wire.modulos.disguise.DisguiseTypes;
import dev.feldmann.redstonegang.wire.modulos.disguise.annotations.ActionAnnotation;
import dev.feldmann.redstonegang.wire.modulos.disguise.annotations.SetAnnotation;
import dev.feldmann.redstonegang.wire.modulos.disguise.types.base.DisguiseData;
import dev.feldmann.redstonegang.wire.modulos.disguise.types.player.DisguisePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CmdDisguise extends RedstoneCmd {

    public CmdDisguise() {
        super("disguise", "Transforma em um animal");
        setExecutePerm(Fodao.INSTANCE);
    }


    public void sendInfo(Player p, DisguiseData data) {
        for (Method m : getAllMethodsInHierarchy(data.getClass())) {
            SetAnnotation an = m.getAnnotation(SetAnnotation.class);
            if (an != null) {
                String nome = an.nome();
                if (nome.isEmpty()) {
                    nome = m.getName();
                }

                p.sendMessage(nome + "(" + m.getParameterTypes()[0].getSimpleName() + "), ");
            }
            ActionAnnotation ana = m.getAnnotation(ActionAnnotation.class);
            if (ana != null) {
                String nome = ana.nome();
                if (nome.isEmpty()) {
                    nome = m.getName();
                }
                p.sendMessage(nome + ", ");
            }

        }

    }

    public boolean doSet(Player p, DisguiseData data, String key, String value) {
        for (Method m : getAllMethodsInHierarchy(data.getClass())) {
            SetAnnotation an = m.getAnnotation(SetAnnotation.class);
            if (an != null) {
                String nome = an.nome();
                if (nome.isEmpty()) {
                    nome = m.getName();
                }

                if (nome.equalsIgnoreCase(key)) {
                    if (m.getParameterCount() == 1) {

                        Class<?> parameterType = m.getParameterTypes()[0];
                        if (parameterType.isEnum()) {
                            Class<? extends Enum> enumClass = (Class<? extends Enum>) parameterType;
                            String values = "";
                            for (Enum e : enumClass.getEnumConstants()) {
                                values += e.name().toLowerCase() + " ";
                                if (e.name().equalsIgnoreCase(value)) {
                                    setSilently(m, data, e);
                                    return true;
                                }
                            }
                            p.sendMessage("Valor inválido: " + values);
                        }
                        if (parameterType == boolean.class || parameterType == Boolean.class) {
                            boolean b;
                            if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("1")) {
                                b = true;
                            } else if (value.equalsIgnoreCase("false") || value.equalsIgnoreCase("0")) {
                                b = false;
                            } else {
                                p.sendMessage("Valor inválido Use true(1) false(0)");
                                return true;
                            }
                            setSilently(m, data, b);
                            return true;
                        }
                        if (parameterType == int.class || parameterType == Integer.class) {
                            int i;
                            try {
                                i = Integer.valueOf(value);
                            } catch (NumberFormatException ex) {
                                p.sendMessage("Numero inválido!");
                                return true;
                            }
                            setSilently(m, data, i);
                            return true;
                        }
                        if (parameterType == byte.class || parameterType == Byte.class) {
                            byte i;
                            try {
                                i = Byte.valueOf(value);
                            } catch (NumberFormatException ex) {
                                p.sendMessage("Numero inválido!");
                                return false;
                            }
                            setSilently(m, data, i);
                            return true;
                        }
                    }
                    break;
                }
            }
        }
        return false;
    }

    public boolean doAction(Player p, DisguiseData data, String action) {
        for (Method m : getAllMethodsInHierarchy(data.getClass())) {
            ActionAnnotation an = m.getAnnotation(ActionAnnotation.class);
            if (an != null) {
                String nome = an.nome();
                if (nome.isEmpty()) {
                    nome = m.getName();
                }
                if (nome.equalsIgnoreCase(action)) {
                    try {
                        m.invoke(data);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    return true;
                }
            }

        }
        return false;
    }

    public static List<Method> getAllMethodsInHierarchy(Class<?> objectClass) {
        List<Method> allMethods = new ArrayList<>();
        Method[] declaredMethods = objectClass.getDeclaredMethods();
        Method[] methods = objectClass.getMethods();
        if (objectClass.getSuperclass() != null) {
            Class<?> superClass = objectClass.getSuperclass();
            List<Method> superClassMethods = getAllMethodsInHierarchy(superClass);
            allMethods.addAll(superClassMethods);
        }
        // allMethods.addAll(Arrays.asList(declaredMethods));
        allMethods.addAll(Arrays.asList(methods));
        return allMethods;
    }

    public void setSilently(Method m, Object objeto, Object valor) {
        m.setAccessible(true);
        try {
            m.invoke(objeto, valor);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static final DefaultArguments array = new DefaultArguments("arguments");

    @Override
    public void command(CommandSender cs, Arguments args) {
        if (cs instanceof Player) {
            Player p = (Player) cs;
            String[] strings = args.getValue(array);
            if (strings.length == 0) {
                if (DisguiseAPI.getDisguise(p.getUniqueId()) != null) {
                    DisguiseAPI.setDisguise(p, null);
                    p.sendMessage("Voltou ao normal!");
                    return;
                }
            }
            if (strings.length == 1) {
                String a = strings[0];

                DisguiseData data = DisguiseAPI.getDisguise(p.getUniqueId());
                if (a.equalsIgnoreCase("info")) {
                    if (data == null) {
                        p.sendMessage("Você não está com um disguise!");
                        return;
                    } else {
                        sendInfo(p, data);
                        return;
                    }
                }
                if (data != null && doAction(p, data, a)) {
                    p.sendMessage("§aFeito ação " + a);
                    return;
                }
                String tipos = "";
                for (DisguiseTypes type : DisguiseTypes.values()) {
                    if (type.name().equalsIgnoreCase(strings[0])) {
                        DisguiseAPI.setDisguise(p, type.createData(p));
                        p.sendMessage("Transformado em " + type.name());
                        return;
                    }
                    tipos += type.name().toLowerCase() + " ";
                }
                cs.sendMessage("Tipo não encontrado! Tipos:" + tipos);
            }
            if (strings.length == 2) {

                if (strings[0].equalsIgnoreCase("player")) {
                    String nome = strings[1];
                    DisguiseAPI.setDisguise(p, new DisguisePlayer(p, nome));
                    return;
                }
                DisguiseData data = DisguiseAPI.getDisguise(p.getUniqueId());

                if (data != null && doSet(p, data, strings[0], strings[1])) {
                    p.sendMessage("§aSetado " + strings[0] + " = " + strings[1]);
                    return;
                }
                p.sendMessage("§cNão encontrado data para setar");

            }
        }
        return;
    }
}
