package dev.feldmann.redstonegang.wire.game.base.addons.server.quests.hooks.rnd;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.Argument;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.PotionTypeArgument;
import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.QuestInfo;
import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.hooks.QuestContador;
import dev.feldmann.redstonegang.wire.modulos.language.lang.LanguageHelper;
import dev.feldmann.redstonegang.wire.modulos.customevents.potion.PlayerBrewPotionEvent;

import dev.feldmann.redstonegang.wire.modulos.customevents.potion.PotionData;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

public class QuestFazPocao extends QuestContador {

    public static PotionTypeArgument POTION = new PotionTypeArgument("potion", false);

    @Override
    public Argument[] getArgs() {
        return new Argument[]{QUANTIDADE, POTION};
    }


    public PlayerBrewPotionEvent.UpdatePotionType update;
    public String nomefeito;

    public PotionEffectType getEfeito() {
        for (PotionEffectType ef : PotionEffectType.values()) {
            if (ef != null)
                if (ef.getName().equalsIgnoreCase(nomefeito)) {
                    return ef;
                }

        }
        return null;
    }

    @EventHandler
    public void brew(PlayerBrewPotionEvent ev) {
        QuestInfo i = getFazendo(ev.getPlayer());
        if (i == null) return;
        int x = 0;
        for (PlayerBrewPotionEvent.PotionData d : ev.getPotions()) {
            for (PlayerBrewPotionEvent.PotionEffectU dif : d.getNew()) {
                if (dif.e.getType().getName().equalsIgnoreCase(nomefeito)) {
                    if (update == dif.updatetype) {
                        x++;
                    }
                }
            }
        }
        if (x > 0) {
            i.faz(x);
        }
    }

    public ItemStack toItemStack() {
        ItemStack it;
        if (getEfeito() != null) {

            PotionEffectType tipo = getEfeito();
            PotionType a = PotionType.getByEffect(tipo);
            if (a != null) {
                Potion p = new Potion(a, 1, update == PlayerBrewPotionEvent.UpdatePotionType.SPLASH);
                it = p.toItemStack(1);
            } else {
                it = new ItemStack(Material.POTION);
            }
        } else {
            it = new ItemStack(Material.POTION);
        }
        return it;

    }


    @Override
    public boolean createQuestHook(Player p, Arguments args) {
        this.contador = args.get(QUANTIDADE);
        PotionData data = args.get(POTION);
        this.nomefeito = data.getType().getName();
        this.update = data.getUpdate();
        return true;
    }

    @Override
    public String toString() {
        String s = "Poção " + contador + " ";
        s += nomefeito.toLowerCase() + ":" + update.name();
        if (PotionType.getByEffect(getEfeito()) == null) {
            s += "  §c§lBUGADA";
        }
        return s;
    }

    @Override
    public String suggestName() {
        PotionType type = PotionType.getByEffect(getEfeito());
        boolean splash = update == PlayerBrewPotionEvent.UpdatePotionType.SPLASH;
        boolean II = update == PlayerBrewPotionEvent.UpdatePotionType.INTENSIDADE;
        boolean tempo = update == PlayerBrewPotionEvent.UpdatePotionType.INTENSIDADE;
        Potion p = new Potion(type, II ? 2 : 1);
        p.setSplash(splash);
        p.setHasExtendedDuration(tempo);

        String pot = "Faça " + contador + "x " + LanguageHelper.getItemName(p.toItemStack(1));
        /*
        switch (updateFloatItems) {

            case SPLASH:
                pot += " Arremesavel";
                break;
            case TEMPO:
                pot += " Extendida";
                break;
            case INTENSIDADE:
                pot += " II";
                break;
        }*/
        return pot;
    }

}
