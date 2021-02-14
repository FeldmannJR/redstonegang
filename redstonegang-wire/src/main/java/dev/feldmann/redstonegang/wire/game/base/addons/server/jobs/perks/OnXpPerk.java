package dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.perks;

import dev.feldmann.redstonegang.common.utils.Cooldown;
import org.bukkit.entity.Player;

public abstract class OnXpPerk extends JobPerk {
    public OnXpPerk(int level, Cooldown cooldown, String nome, int chance) {
        super(level, cooldown, nome, chance);
    }

    public OnXpPerk(int level, String nome, int chance) {
        super(level, nome, chance);
    }

    public boolean gainXp(Player p) {
        return true;
    }

}