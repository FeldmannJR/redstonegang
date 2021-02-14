package dev.feldmann.redstonegang.discord;


import dev.feldmann.redstonegang.app.RedstoneTerminal;
import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.RedstoneGangTerminal;
import dev.feldmann.redstonegang.common.utils.EnvHelper;
import dev.feldmann.redstonegang.common.utils.ObjectLoader;
import dev.feldmann.redstonegang.discord.app.cmds.*;
import dev.feldmann.redstonegang.discord.commands.CommandManager;
import dev.feldmann.redstonegang.discord.config.DiscordConfig;
import dev.feldmann.redstonegang.discord.config.LocalConfig;
import dev.feldmann.redstonegang.discord.config.ProductionConfig;
import dev.feldmann.redstonegang.discord.modules.Module;
import dev.feldmann.redstonegang.discord.music.DiscordMusic;
import dev.feldmann.redstonegang.discord.app.cmds.*;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import java.util.List;
import java.util.Random;

public class Discord extends RedstoneTerminal {

    private Guild guild = null;

    private RedstoneGang redstoneGang;
    private DiscordConfig config;
    public JDA jda;
    public static Random rnd;
    public DiscordMusic music;

    public ChatMessages messageSender;

    public static Discord instance;


    @Override
    public void onDisable() {
    }

    @Override
    public void onEnable() {
        instance = this;
        redstoneGang = new RedstoneGangTerminal();
        loadConfig();
        rnd = new Random();
        music = new DiscordMusic();
        messageSender = new ChatMessages();
        connectBot();
        addCmds();
    }

    public void loadConfig() {
        if (redstoneGang.ENVIRONMENT.equalsIgnoreCase("local")) {
            config = new LocalConfig();
        } else {
            config = new ProductionConfig();
        }
    }

    public ChatMessages messages() {
        return messageSender;
    }

    public void addCmds() {
        addCommand(new CmdMover());
        addCommand(new CmdPapo());
        addCommand(new CmdSendAjuda());
        addCommand(new CmdEntrar());
        addCommand(new CmdSair());
        addCommand(new CmdSync());
        addCommand(new CmdRegras());
    }


    private void connectedBot() {

    }


    private void connectBot() {
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        String token = EnvHelper.get("DISCORD_BOT_TOKEN", null);
        if (token == null) {
            redstoneGang.alert("Token nao configurado!");
            stop();
            return;
        }
        builder.setToken(token);
        builder.addEventListener(new CommandManager());
        builder.addEventListener(new ListenerAdapter() {
            @Override
            public void onReady(ReadyEvent event) {
                if (redstoneGang.ENVIRONMENT.equalsIgnoreCase("local")) {
                    guild = jda.getGuildById(600849510968590338l);
                } else {
                    guild = jda.getGuildById(496489150816714754l);
                }
                if (guild == null) {
                    log("Não achei a guilda");
                    stop();
                }
                for (Guild g : jda.getGuilds()) {
                    if (g.getIdLong() != config.getGuildId()) {
                        log("Guildas inválidas!");
                        stop();
                        break;
                    }
                }
                connectedBot();
            }
        });
        loadModules(builder);
        build(builder);
    }

    public void loadModules(JDABuilder builder) {
        List<Module> load = ObjectLoader.load(Module.class.getPackage().getName(), Module.class, (jar) -> {
            return !jar.getName().endsWith("/Module.class");
        });
        for (Module module : load) {
            log("Loading "+module.getClass().getSimpleName());
            builder.addEventListener(module);
        }
    }

    public void build(JDABuilder builder) {
        try {
            jda = builder.buildAsync();
        } catch (LoginException e) {
            e.printStackTrace();
            stop();
        }
    }

    public static Discord instance() {
        return instance;
    }

    public static JDA getJda() {
        return instance.jda;
    }

    public DiscordConfig getConfig() {
        return config;
    }

    public Guild getGuild() {
        return guild;
    }
}
