package dev.feldmann.redstonegang.common.utils.msgs;


import net.md_5.bungee.api.ChatColor;

public class MsgType {

    public static MsgType INFO = new MsgType(
            ChatColor.YELLOW,//Fundo
            ChatColor.RED, //Jogador 1
            ChatColor.BLUE, //Jogador 2
            ChatColor.WHITE, //Palavras destacadas
            ChatColor.GREEN, //Itens
            ChatColor.GOLD);// Dinheiro
    public static MsgType SELL = new MsgType(ChatColor.GREEN,//Fundo
            ChatColor.WHITE, //Jogador 1
            ChatColor.BLUE, //Jogador 2
            ChatColor.YELLOW, //Palavras destacadas
            ChatColor.WHITE, //Itens
            ChatColor.GOLD);
    // Dinheiro)
    public static MsgType BUY = new MsgType(ChatColor.BLUE,//Fundo
            ChatColor.WHITE, //Jogador 1
            ChatColor.YELLOW, //Jogador 2
            ChatColor.YELLOW, //Palavras destacadas
            ChatColor.WHITE, //Itens
            ChatColor.GOLD);
    // Dinheiro),

    public static MsgType ERROR = new MsgType(ChatColor.RED, ChatColor.WHITE, ChatColor.GRAY, ChatColor.YELLOW, ChatColor.GREEN, ChatColor.DARK_GREEN);

    public static MsgType ITEM = new MsgType(ChatColor.YELLOW, ChatColor.GRAY, ChatColor.GRAY, ChatColor.WHITE, ChatColor.WHITE, ChatColor.GOLD);

    public static MsgType ON = new MsgType(ChatColor.GREEN, ChatColor.WHITE, ChatColor.GRAY, ChatColor.YELLOW, ChatColor.GREEN, ChatColor.DARK_GREEN);

    public static MsgType OFF = new MsgType(ChatColor.RED, ChatColor.WHITE, ChatColor.GRAY, ChatColor.YELLOW, ChatColor.GREEN, ChatColor.DARK_GREEN);

    public static MsgType ITEM_DESC = new MsgType(ChatColor.GRAY, ChatColor.GRAY, ChatColor.GRAY, ChatColor.WHITE, ChatColor.WHITE, ChatColor.GOLD);

    public static MsgType ITEM_CAN = new MsgType(ChatColor.GREEN, ChatColor.WHITE, ChatColor.GRAY, ChatColor.YELLOW, ChatColor.GREEN, ChatColor.DARK_GREEN);

    public static MsgType ITEM_CANT = new MsgType(ChatColor.GREEN, ChatColor.WHITE, ChatColor.GRAY, ChatColor.YELLOW, ChatColor.GREEN, ChatColor.DARK_GREEN);

    public static MsgType ITEM_CAN_DESC = new MsgType(ChatColor.GREEN, ChatColor.WHITE, ChatColor.GRAY, ChatColor.YELLOW, ChatColor.GREEN, ChatColor.DARK_GREEN);

    public static MsgType ITEM_CANT_DESC = new MsgType(ChatColor.GREEN, ChatColor.WHITE, ChatColor.GRAY, ChatColor.YELLOW, ChatColor.GREEN, ChatColor.DARK_GREEN);

    //SUCCESS("§a", "§a", "§2", "§6", "§b"),
    //DESCRPTION("§7", "§a", "§e", "§6", "§b");
    ;

    public String prefix;
    public ChatColor baseColor;
    public ChatColor playerColor;
    public ChatColor otherPlayerColor;
    public ChatColor numberColor;
    public ChatColor itemColor;
    public ChatColor moneyColor;

    public MsgType(ChatColor baseColor, ChatColor playerColor, ChatColor otherPlayerColor, ChatColor numberColor, ChatColor itemColor, ChatColor moneyColor) {
        this(null, baseColor, playerColor, otherPlayerColor, numberColor, itemColor, moneyColor);
    }

    public MsgType(String prefix, ChatColor baseColor, ChatColor playerColor, ChatColor otherPlayerColor, ChatColor numberColor, ChatColor itemColor, ChatColor moneyColor) {
        this.baseColor = baseColor;
        this.playerColor = playerColor;
        this.otherPlayerColor = otherPlayerColor;
        this.numberColor = numberColor;
        this.itemColor = itemColor;
        this.moneyColor = moneyColor;
        this.prefix = prefix;
    }
}
