package dev.feldmann.redstonegang.common.network;

import dev.feldmann.redstonegang.common.RedstoneGang;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.function.Consumer;


/**
 * Handles a client-side channel.
 */

/**
 * Handles a client-side channel.
 */
public class ClienteHandler extends SimpleChannelInboundHandler<String> {
    RedstoneGang api;


    public ClienteHandler(RedstoneGang api) {
        this.api = api;
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) {
        NetworkMessage nmsg = new NetworkMessage((msg.split(NetworkClient.delimiter)));
        for (Consumer<NetworkMessage> handler : api.network().getHandlers()) {
            try {
                handler.accept(nmsg);
            } catch (Exception c) {
                c.printStackTrace();
            }
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
