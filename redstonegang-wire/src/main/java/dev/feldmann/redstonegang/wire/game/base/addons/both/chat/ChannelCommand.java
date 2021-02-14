package dev.feldmann.redstonegang.wire.game.base.addons.both.chat;


import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.RemainStringsArgument;
import dev.feldmann.redstonegang.wire.base.cmds.types.PlayerCmd;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.entity.Player;


public class ChannelCommand extends PlayerCmd {

    private static final RemainStringsArgument MENSAGEM = new RemainStringsArgument("mensagem", true);

    private ChatChannel channel;
    private ChatAddon addon;

    public ChannelCommand(ChatChannel channel, ChatAddon addon) {
        super(channel.getChannelPrefix(), "comando para usar o chat " + channel.getChannelName(), MENSAGEM);
        this.channel = channel;
        this.addon = addon;
    }

    @Override
    public void command(Player player, Arguments args) {
        if (!args.isPresent(MENSAGEM)) {
            // Entra no canal automatico
            if (channel.canUse(player) && channel.isJoinable()) {
                addon.changeToChannel(player, channel);
            } else {
                C.error(player, "Você precisa informar uma mensagem!");
            }
        } else {
            String msg = args.get(MENSAGEM).trim();
            addon.handleChannel(channel, player, msg);
        }

    }
}
