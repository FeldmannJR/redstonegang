package dev.feldmann.redstonegang.common.shop;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.player.permissions.Group;

public enum VipType
{
    VIP_ELITE( "elite"),
    VIP_MASTER( "master"),
    VIP_SUPREMO("supremo");

    private String nome;

    private VipType(String nome)
    {
        this.nome = nome;
    }

    public String getNome()
    {
        return this.nome;
    }

    public Group getGroup()
    {
        return RedstoneGang.instance().user().getPermissions().getGroupByName(this.nome);
    }

}
