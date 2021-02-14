package dev.feldmann.redstonegang.wire.game.games.servers.build.cmds;

import dev.feldmann.redstonegang.common.utils.ZipUtils;
import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.StringArgument;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.*;

public class CreateVoid extends RedstoneCmd {
    private static final StringArgument NOME = new StringArgument("nome");

    public CreateVoid() {
        super("criarvoid", "cria um mundo void", NOME);
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        String n = args.get(NOME);
        File folder = new File(Bukkit.getWorldContainer().getAbsolutePath() + File.separator + n + File.separator);
        if (folder.exists()) {
            C.error(sender, "Já existe este mundo");
            return;
        }
        try {
            loadNewWorld(n);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mv import " + n + " normal");
            if (sender instanceof Player)
                ((Player) sender).teleport(Bukkit.getWorld(n).getSpawnLocation());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadNewWorld(String nome) throws IOException {
        InputStream fis = getClass().getResourceAsStream("/resources/voidWorld.zip");
        File dest = new File(Bukkit.getWorldContainer().getAbsolutePath() + File.separator + nome);

        ZipUtils.unzip(fis, dest);
    }



}

