package dev.feldmann.redstonegang.wire.game.base.addons.both.clan.listeners;

import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanMember;
import dev.feldmann.redstonegang.wire.game.base.addons.both.titulos.Titulo;
import dev.feldmann.redstonegang.wire.game.base.addons.both.titulos.TituloCor;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.sql.Timestamp;

public class ClanTitle extends Titulo {

    private static final int TITULO_ID = -50;


    ClanAddon clan;

    public ClanTitle(ClanAddon clan, int owner) {
        super(owner, "plg_clan");
        this.clan = clan;
        int x = 0;
        for (ChatColor c : ChatColor.values()) {
            if (c.isColor()) {
                this.cor.put(c.toString(), new TituloCor(-50 - x, this, c.toString(), new Timestamp(System.currentTimeMillis())));
                x++;

            }
        }

    }

    @Override
    public String getProcessedName() {
        ClanMember member = clan.getCache().getMember(getOwner());
        if (member.getClanTag() != null) {
            return "Clan " + member.getClan().getName();
        }
        return "Sem Clan";
    }

    @Override
    public void addCor(TituloCor cor) {

    }

    @Override
    public Material getMaterial() {
        return Material.ANVIL;
    }

    @Override
    public boolean isVirtual() {
        return true;
    }
}
