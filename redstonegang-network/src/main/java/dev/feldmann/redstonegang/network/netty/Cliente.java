package dev.feldmann.redstonegang.network.netty;

import io.netty.channel.Channel;

public class Cliente {

    private ServerType tipo;
    private Channel channel;
    private String nomeServer;

    public Cliente(String nome, ServerType tipo, Channel c)
    {
        this.nomeServer = nome;
        this.channel = c;
        this.tipo = tipo;

    }

    public ServerType getTipo()
    {
        return tipo;
    }

    public Channel getChannel()
    {
        return channel;
    }

    public String getAddress()
    {
        return channel.remoteAddress().toString();
    }

    public String getNome()
    {
        return nomeServer;
    }

    public void sendMessage(String msg)
    {
        channel.writeAndFlush(msg + "\r\n");
    }

    public static enum ServerType {
        BUNGEE,
        SPIGOT,
        OUTROS,
    }
}
