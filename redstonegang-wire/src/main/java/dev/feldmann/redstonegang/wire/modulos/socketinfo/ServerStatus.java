package dev.feldmann.redstonegang.wire.modulos.socketinfo;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

public enum ServerStatus {
    ABERTO(new MaterialData(Material.EMERALD_BLOCK),ChatColor.GREEN),
    REINICIANDO(new MaterialData(Material.IRON_BLOCK),ChatColor.GRAY),
    EM_JOGO(new MaterialData(Material.REDSTONE_BLOCK),ChatColor.YELLOW),
    RETORNAR(new MaterialData(Material.BEACON),ChatColor.AQUA),
    DESLIGADO(new MaterialData(Material.REDSTONE_BLOCK),ChatColor.RED);


    private MaterialData md;
    private ChatColor cor;

    ServerStatus(MaterialData md, ChatColor cor){
        this.md = md;
        this.cor = cor;
    }

    public ChatColor getCor() {
        return cor;
    }

    public MaterialData getMaterial() {
        return md;
    }
}
