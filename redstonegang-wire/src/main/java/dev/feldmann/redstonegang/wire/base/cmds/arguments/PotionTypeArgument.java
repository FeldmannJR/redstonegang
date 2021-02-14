package dev.feldmann.redstonegang.wire.base.cmds.arguments;

import dev.feldmann.redstonegang.wire.modulos.customevents.potion.PlayerBrewPotionEvent;
import dev.feldmann.redstonegang.wire.modulos.customevents.potion.PotionData;
import org.bukkit.command.CommandSender;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

public class PotionTypeArgument extends Argument<PotionData> {
    public PotionTypeArgument(String name, boolean optional) {
        super(name, optional);
    }

    @Override
    public String getErrorMessage(String input) {
        input = "Uso incorreto do argumento\n";
        input += "<EfeitoDaPocao:Bonus>\n";
        input += "Onde: \n";
        input += ("§eEfeitoDaPocao §f= O tipo da poção ex: SPEED");
        input += "\n";
        input += ("§eBonus §f= O avancado da poção se tu quer que ele faça splash(SPLASH), ou poção 2(INTENSIDADE), ou extendida(TEMPO) caso só queira a poção normal só coloque o tipo sem :");
        input += "\n";
        String tipo = "";
        String oq = "";
        boolean b = false;
        for (PotionEffectType tipos : PotionEffectType.values()) {
            if (tipos != null) {
                if (PotionType.getByEffect(tipos) == null) {
                    continue;
                }
                if (b) {
                    tipo += "§c";
                } else {
                    tipo += "§e";
                }
                tipo += tipos.getName() + "  ";
                b = !b;
            }
        }
        for (PlayerBrewPotionEvent.UpdatePotionType upd : PlayerBrewPotionEvent.UpdatePotionType.values()) {
            if (upd == PlayerBrewPotionEvent.UpdatePotionType.NONE) continue;
            if (b) {
                oq += "§c";
            } else {
                oq += "§e";
            }
            oq += upd.name() + "   ";
            b = !b;
        }
        input += ("§bEfeitos: " + tipo.toLowerCase());
        input += "\n";
        input += ("§bBonus: " + oq.toLowerCase());
        return input;
    }

    @Override
    public PotionData process(CommandSender cs, int index, String[] args) {
        if (index >= args.length) return null;
        return getPotion(args[index]);
    }

    private PotionData getPotion(String s) {
        String[] split = s.split(":");
        PotionEffectType e = null;
        PlayerBrewPotionEvent.UpdatePotionType u = PlayerBrewPotionEvent.UpdatePotionType.NONE;

        if (split.length >= 1) {
            for (PotionEffectType t : PotionEffectType.values()) {
                if (PotionType.getByEffect(t) == null) {
                    continue;
                }
                if (t != null)
                    if (split[0].equalsIgnoreCase(t.getName())) {
                        e = t;
                    }
            }
        }
        if (split.length >= 2) {
            for (PlayerBrewPotionEvent.UpdatePotionType upd : PlayerBrewPotionEvent.UpdatePotionType.values()) {
                if (upd.name().equalsIgnoreCase(split[1])) {
                    u = upd;
                }
            }

        }

        if (e == null) {
            return null;
        }
        return new PotionData(e, u);
    }
}
