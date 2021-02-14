package dev.feldmann.redstonegang.wire.game.base.addons.both.cashshop;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.types.PlayerCmd;
import org.bukkit.entity.Player;

public class CashShopCmd extends PlayerCmd {
    private CashShopAddon addon;

    public CashShopCmd(CashShopAddon addon) {
        super("loja", "Abre a loja de rubis");
        this.addon = addon;
        setAlias("shop");
    }

    @Override
    public void command(Player player, Arguments args) {
        addon.openPlayer(player);
    }
}
