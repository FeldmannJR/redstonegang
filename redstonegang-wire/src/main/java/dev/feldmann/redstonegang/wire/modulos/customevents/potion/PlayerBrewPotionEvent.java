package dev.feldmann.redstonegang.wire.modulos.customevents.potion;

import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PlayerBrewPotionEvent extends PlayerEvent {

    private static final HandlerList handlers = new HandlerList();

    private Block brewer;
    ItemStack added;
    int qtd;
    PotionData[] potions;

    public PlayerBrewPotionEvent(Block brewer, Player who, ItemStack addedtopotion, PotionData... data) {
        super(who);
        this.added = addedtopotion;
        this.potions = data;
        this.brewer = brewer;
    }

    public ItemStack getAdded() {
        return added;
    }

    public Block getBrewer() {
        return brewer;
    }

    public PotionData[] getPotions() {
        return potions;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public static class PotionData {
        ItemStack before;
        ItemStack after;


        private Potion getPotionMeta(ItemStack it) {
            if (it != null) {
                return Potion.fromItemStack(it);
            }
            return null;
        }


        public static void debug(CommandSender cs, ItemStack i) {
            if (i == null) {
                cs.sendMessage("i == null");
                return;
            }
            Potion p = Potion.fromItemStack(i);
            if (p == null) {
                cs.sendMessage("Potion == null");
                return;
            }
            if (p.getEffects() == null) {
                cs.sendMessage("PotionEffects == null");
                return;
            }

            if (p.getEffects().isEmpty()) {
                cs.sendMessage("PotionEffects.isEmpty");
                return;
            }
            for (PotionEffect e : p.getEffects()) {
                cs.sendMessage(e.getType().getName() + " " + e.getAmplifier() + " " + e.getDuration());
            }


        }

        public boolean upgratedSplash() {
            if (getBefore() == null || getAfter() == null) {
                return false;
            }
            return !getBefore().isSplash() && getAfter().isSplash();
        }

        public List<PotionEffectU> getNew() {
            List<PotionEffectU> novas = new ArrayList<>();

            if (getBefore() == null || getAfter() == null) return novas;
            Collection<PotionEffect> before = getBefore().getEffects();
            Collection<PotionEffect> after = getAfter().getEffects();


            for (PotionEffect af : after) {
                boolean tem = false;
                ANTES:
                for (PotionEffect bf : before) {
                    if (bf.getType() == af.getType()) {
                        UpdatePotionType type = UpdatePotionType.NONE;
                        if ((af.getAmplifier() > bf.getAmplifier())) {
                            type = UpdatePotionType.INTENSIDADE;
                        } else if ((af.getDuration() > bf.getDuration())) {
                            type = UpdatePotionType.TEMPO;
                        }

                        if (type != UpdatePotionType.NONE) {
                            novas.add(new PotionEffectU(af, type));
                        }

                        tem = true;
                        break ANTES;
                    }
                }
                if (!tem) {
                    novas.add(new PotionEffectU(af, UpdatePotionType.NONE));
                } else {
                    if (upgratedSplash()) {
                        novas.add(new PotionEffectU(af, UpdatePotionType.SPLASH));
                    }
                }
            }
            return novas;
        }


        public ItemStack getAfterItemStack() {
            return after;
        }

        public ItemStack getBeforeItemStack() {
            return before;
        }

        public Potion getBefore() {
            return getPotionMeta(before);
        }

        public Potion getAfter() {
            return getPotionMeta(after);
        }


    }

    public static class PotionEffectU {
        public PotionEffect e;
        public UpdatePotionType updatetype;

        public PotionEffectU(PotionEffect e, UpdatePotionType t) {
            this.e = e;
            this.updatetype = t;
        }

    }

    public enum UpdatePotionType {
        TEMPO,
        INTENSIDADE,
        NONE,
        SPLASH

    }


}
