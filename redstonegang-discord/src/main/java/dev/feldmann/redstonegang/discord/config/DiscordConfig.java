package dev.feldmann.redstonegang.discord.config;

public abstract class DiscordConfig {

    public abstract long getGuildId();

    public abstract long getWelcomeId();

    public abstract long getRulesId();

    public abstract long getBatePapo();

    public abstract long getAjudaChannel();

    public String getCommandPrefix() {
        return "+";
    }

}
