package dev.feldmann.redstonegang.wire.game.base.addons.server.land;


import dev.feldmann.redstonegang.wire.RedstoneGangSpigot;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.properties.PlayerProperties;
import dev.feldmann.redstonegang.wire.utils.msgs.C;

import java.util.HashMap;

public class LandPlayer {

    private int pId;

    public PlayerBuyingInfo buying = null;
    public long buyingStart = -1;

    private HashMap<Integer, PlayerProperties> globalProps = null;

    public Land deletando;
    public long startedDeletando;
    public String codigo;
    public int blocosUsados = 0;
    int terrenos = 0;

    public boolean byPass = false;
    public boolean byPassWild = false;

    public void setDeletando(Land deletando, String codigo) {
        this.deletando = deletando;
        this.codigo = codigo;
        this.startedDeletando = System.currentTimeMillis();
    }

    LandAddon manager;

    public LandPlayer(LandAddon manager, int pId) {
        this.pId = pId;
        this.manager = manager;
    }

    public HashMap<Integer, PlayerProperties> getGlobalProps() {
        initProps();
        return globalProps;
    }

    public PlayerProperties getProperty(int player) {
        initProps();
        if (globalProps.containsKey(player)) {
            return globalProps.get(player);
        }
        return null;
    }

    public PlayerProperties add(int player) {
        initProps();
        PlayerProperties props = new PlayerProperties(manager, player, pId, PlayerProperties.PLAYER);
        globalProps.put(player, props);
        manager.getDB().savePlayerProperty(props);
        return props;
    }

    public void removeProperty(int player) {
        initProps();
        if (globalProps.containsKey(player)) {
            PlayerProperties pl = globalProps.remove(player);
            manager.getDB().deletePlayerProperty(pl);
        }
    }


    private void initProps() {
        if (globalProps == null) {
            globalProps = manager.getDB().loadGlobal(pId);
        }
    }


    public void sendConfirmMessage() {
        C.info(RedstoneGangSpigot.getOnlinePlayer(pId), "Analise se dentro dos blocos vermelhos colocados é o terreno que você quer, use `%cmd%` para comprar o terreno, ou '%cmd%' para não!", "/terreno confirmar", "/terreno cancelar");
    }

    public void sendAlreadyEditing() {
        C.error(RedstoneGangSpigot.getOnlinePlayer(pId), "Você está visualizando um terreno! Caso queira comprar use `%cmd%`, caso não use `%cmd%`", "/terreno confirmar", "/terreno cancelar");
    }
}
