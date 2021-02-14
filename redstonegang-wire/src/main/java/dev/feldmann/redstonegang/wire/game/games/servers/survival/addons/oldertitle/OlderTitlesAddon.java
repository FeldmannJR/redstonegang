package dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.oldertitle;

import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.common.utils.formaters.DateUtils;
import dev.feldmann.redstonegang.wire.RedstoneGangSpigot;
import dev.feldmann.redstonegang.wire.game.base.addons.both.titulos.events.PlayerGetTitlesEvent;
import dev.feldmann.redstonegang.wire.game.base.addons.both.titulos.Titulo;
import dev.feldmann.redstonegang.wire.game.base.addons.both.titulos.TituloAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.titulos.TituloCor;
import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import dev.feldmann.redstonegang.wire.game.base.objects.annotations.Dependencies;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;

@Dependencies(soft = TituloAddon.class)
public class OlderTitlesAddon extends Addon {

    public int baseId = -250;

    @EventHandler
    public void checkTitulos(PlayerGetTitlesEvent ev) {
        User pl = RedstoneGangSpigot.getPlayer(ev.getOwner());
        if (pl != null) {
            if (DateUtils.isBefore(pl.getRegistred(), 1, 1, 2019)) {
                Titulo t = new Titulo(ev.getOwner(), "Vetereno") {
                    @Override
                    public Material getMaterial() {
                        return Material.WEB;
                    }
                };
                for (ChatColor c : ChatColor.values()) {
                    if (c.isColor()) {
                        TituloCor cor = new TituloCor(baseId++, t, c.toString(), pl.getRegistred());
                        cor.setDesc("Ganhado por se cadastrar antes de 2019");
                        t.addCor(cor);
                    }
                }
                ev.addTitle(t);
            }
        }
    }
}
