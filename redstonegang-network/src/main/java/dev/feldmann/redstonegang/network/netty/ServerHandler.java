package dev.feldmann.redstonegang.network.netty;


import dev.feldmann.redstonegang.network.RedstoneNetwork;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


public class ServerHandler extends SimpleChannelInboundHandler<String> {

    private Server server;

    public ServerHandler(Server s) {
        this.server = s;
    }

    public void remove(String FromSv) {
        if (server.clientes.containsKey(FromSv)) {
            server.clientes.get(FromSv).getChannel().disconnect();
            server.clientes.get(FromSv).getChannel().deregister();
            server.clientes.remove(FromSv);
        }
    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx) {

    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        for (String a : server.clientes.keySet()) {
            if (server.clientes.get(a).getAddress().equals(ctx.channel().remoteAddress().toString())) {
                server.clientes.remove(a);
                RedstoneNetwork.addlog("Conexão Fechada <-> " + a + " <-> " + ctx.channel().remoteAddress().toString(), false);
            }
        }
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String mensagem) throws Exception {
        String[] args = mensagem.split(server.delimiter);
        String comando = args[0].toLowerCase();
        String FromSv;
        boolean isConnected = server.containsChannel(ctx.channel());
        if (RedstoneNetwork.rg.DEBUG) {
            RedstoneNetwork.addlog(mensagem, true);
        }
        if (comando != null) {
            switch (comando) {
                case "connect":
                    FromSv = args[1];
                    if (args.length != 4 || !args[3].equals(server.networkToken)) {
                        remove(FromSv);
                        RedstoneNetwork.addlog("Recebi chave errada do arrombado do " + FromSv, false);
                        ctx.close();
                        return;
                    }
                    String tipo = args[2];
                    Cliente.ServerType ctipo = Cliente.ServerType.OUTROS;
                    for (Cliente.ServerType f : Cliente.ServerType.values()) {
                        if (f.name().equalsIgnoreCase(tipo)) {
                            ctipo = f;
                        }
                    }
                    remove(FromSv);
                    server.clientes.put(FromSv, new Cliente(FromSv, ctipo, ctx.channel()));
                    return;
                case "disconnect":
                    FromSv = args[1];
                    remove(FromSv);
                    ctx.close();
                    return;
                default:
                    if (isConnected) {
                        String to = null;
                        if (comando != null && (comando.equals("select") || comando.equals("selecttype"))) {
                            to = args[1];
                            mensagem = "";
                            for (int x = 2; x < args.length; x++) {
                                if (!mensagem.isEmpty()) {
                                    mensagem += server.delimiter;
                                }
                                mensagem += args[x];
                            }
                        }
                        for (Cliente c : server.clientes.values()) {
                            if (c.getChannel() != ctx.channel()) {
                                if (to != null) {
                                    if (comando.equals("select")) {
                                        if (!c.getNome().startsWith(to)) {
                                            continue;
                                        }
                                    }
                                    if (comando.equals("selecttype")) {
                                        if (!c.getTipo().name().equals(to)) {
                                            continue;
                                        }
                                    }
                                }
                                c.sendMessage(mensagem);
                            }
                        }
                    }
                    break;
            }
        } else {
            RedstoneNetwork.addlog("Exception: " + mensagem, false);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
