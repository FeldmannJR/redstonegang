package dev.feldmann.redstonegang.wire.game.base.addons.both.staffcommands;

import dev.feldmann.redstonegang.common.utils.Cooldown;
import org.bukkit.entity.Player;

public enum VanishFlag {
    INTERACT(false, "interagir", 5000),
    DO_DAMAGE(false, "causar dano"),
    CHAT(false, "falar no chat"),
    PICKUP(false, "pegar itens", 5000),
    DROP(false, "dropar itens");

    private boolean defaultValue;
    private String action;
    private Cooldown messageCooldown;

    public String getAction() {
        return action;
    }

    public boolean getDefaultValue() {
        return defaultValue;
    }

    public boolean canSendMessage(Player p) {
        if (messageCooldown.isCooldown(p.getUniqueId())) {
            return false;
        }
        messageCooldown.addCooldown(p.getUniqueId());
        return true;
    }

    VanishFlag(boolean defaultValue, String action) {
        this(defaultValue, action, 100);
    }

    VanishFlag(boolean defaultValue, String action, long messageCooldown) {
        this.defaultValue = defaultValue;
        this.action = action;
        this.messageCooldown = new Cooldown(messageCooldown);
    }
}
