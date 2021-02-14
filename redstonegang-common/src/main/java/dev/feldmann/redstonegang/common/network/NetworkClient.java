package dev.feldmann.redstonegang.common.network;


import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.ServerType;
import dev.feldmann.redstonegang.common.utils.ArrayUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


public final class NetworkClient {

    public static String delimiter = "ᚌ";

    private Long TryReconnect = System.currentTimeMillis() - 1000;
    // lista de funções registradas pelo programa para chamar ao receber uma msg
    private List<Consumer<NetworkMessage>> networkHandlers = new ArrayList<>();

    //Netty stuff
    private Channel ch = null;
    private ChannelFuture lastWriteFuture = null;
    private EventLoopGroup group = null;

    private String networkHost;
    private String networkPort;
    private String networkToken;

    public NetworkClient(String networkHost, String networkPort, String networkToken) {
        this.networkHost = networkHost;
        this.networkPort = networkPort;
        this.networkToken = networkToken;
        RedstoneGang.instance.runRepeatingTask(new Runnable() {
            @Override
            public void run() {
                checkConnection();
            }
        }, 40);
        this.checkConnection();
    }


    private boolean initClient() {
        if (System.currentTimeMillis() < TryReconnect) {
            return false;
        }
        TryReconnect = System.currentTimeMillis() + 5000;
        try {
            group = new NioEventLoopGroup();
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class).handler(new ClientInitializer());
            ch = b.connect(networkHost, Integer.parseInt(networkPort)).sync().channel();
            sendMessage("connect", RedstoneGang.instance.getNomeServer(), RedstoneGang.instance.getServerType().name(), networkToken);
            System.out.println("Conectei socket!");
        } catch (Exception e) {
            System.out.println("Erro ao conectar no socket!");
            return false;
        }
        return true;
    }


    public void sendMessageToType(ServerType type, Object... msg) {
        msg = ArrayUtils.insertFirst(msg, "selecttype", type.name());
        sendMessage(msg);
    }

    public void sendMessageToPrefix(String sv, Object... msg) {
        msg = ArrayUtils.insertFirst(msg, "select", sv);
        sendMessage(msg);
    }

    public void sendMessageToPrefixLocal(String sv, Object... msg) {
        msg = ArrayUtils.insertFirst(msg, "select", sv);
        sendMessageLocal(msg);
    }

    public void sendMessageLocal(Object... msg) {
        sendMessageAlert(true, msg);
    }

    public void sendMessage(Object... msg) {
        sendMessageAlert(false, msg);
    }

    private void sendMessageAlert(boolean alertLocal, Object... msg) {
        if (msg != null) {
            String tmp = "";
            String f = "";
            boolean special = msg[0].equals("select") || msg[0].equals("selectype");
            for (int x = 0; x < msg.length; x++) {
                if (x > 0) {
                    f += delimiter;
                }
                f += msg[x].toString();
                if (!special || x >= 2) {
                    if (!tmp.isEmpty()) {
                        tmp += delimiter;
                    }
                    tmp += msg[x].toString();
                }
            }
            if (alertLocal) {
                NetworkMessage nmsg = new NetworkMessage((tmp.split(NetworkClient.delimiter)));
                for (Consumer<NetworkMessage> alert : getHandlers()) {
                    alert.accept(nmsg);
                }
            }
            sendinfo(f);
        }
    }

    public void addHandler(Consumer<NetworkMessage> handler) {
        networkHandlers.add(handler);
    }

    public List<Consumer<NetworkMessage>> getHandlers() {
        return networkHandlers;
    }


    private void sendinfo(String msg) {

        if (!checkConnection()) {
            return;
        }

        lastWriteFuture = ch.writeAndFlush(msg + "\r\n");

    }

    public boolean checkConnection() {
        if (ch != null) {

            if (!ch.isOpen() || !ch.isActive()) {
                ch.deregister();
                ch.disconnect();
                ch = null;
                return initClient();
            }
        } else {
            return initClient();
        }
        return true;
    }


    private void closeClient() {
        try {
            if (lastWriteFuture != null) {
                lastWriteFuture.sync();
            }
            ch.closeFuture().sync();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        group.shutdownGracefully();
    }
}
