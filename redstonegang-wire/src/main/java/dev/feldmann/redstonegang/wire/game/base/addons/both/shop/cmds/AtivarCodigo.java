package dev.feldmann.redstonegang.wire.game.base.addons.both.shop.cmds;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.StringArgument;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.command.CommandSender;
import dev.feldmann.redstonegang.common.shop.ShopManager;
import org.bukkit.entity.Player;

public class AtivarCodigo extends RedstoneCmd
{

    public static StringArgument CODIGO = new StringArgument("codigo", false);

    public AtivarCodigo()
    {
        super("ativarcodigo", "Ativa códigos VIPs ou de Rubis", CODIGO);
    }

    @Override
    public void command(CommandSender sender, Arguments args)
    {
        String codigo = args.get(CODIGO);

        if (sender instanceof Player)
        {
            Player p = (Player) sender;
            C.info(p, "O codigo digitado foi: %%.", codigo);
            User user = RedstoneGang.getPlayer(p.getUniqueId());
            boolean ativo = RedstoneGang.instance().shop().tryToActivateCode(user, codigo);
            C.info(p, "Resposta: %%.", ativo);
        }
    }
}
