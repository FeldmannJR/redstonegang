package dev.feldmann.redstonegang.network.netty;


import dev.feldmann.redstonegang.network.RedstoneNetwork;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.util.SelfSignedCertificate;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;


public final class Server {

    public String networkPort;
    public String networkToken;
    public String delimiter = "ᚌ";

    EventLoopGroup bossGroup;
    EventLoopGroup workerGroup;
    public ConcurrentHashMap<String, Cliente> clientes = new ConcurrentHashMap();

    public Server(String networkPort, String networkToken) {
        this.networkPort = networkPort;
        this.networkToken = networkToken;
    }

    public void initServer() throws Exception {
        SelfSignedCertificate ssc = new SelfSignedCertificate();
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup();
        try {
            RedstoneNetwork.addlog("Abrindo servidor netty na porta " + networkPort, true);
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).handler(new LoggingHandler(LogLevel.INFO)).childHandler(new ServerInitializer(this));
            b.bind(Integer.parseInt(networkPort)).sync().channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public void stop() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();

    }

    public Collection<Cliente> getClientes() {
        return clientes.values();
    }

    public void bootServer() {

        new Thread() {
            @Override
            public void run() {
                try {
                    initServer();
                } catch (Exception ex) {
                    RedstoneNetwork.addlog("Erro ao Iniciar o Servidor Socket!", true);
                    ex.printStackTrace();
                }
            }
        }.start();
    }

    public boolean containsChannel(Channel ch) {
        for (Cliente c : clientes.values()) {
            if (c.getChannel() == ch) {
                return true;
            }
        }
        return false;
    }
}
