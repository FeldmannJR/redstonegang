package dev.feldmann.redstonegang.wire.game.base.addons.server.safetime.commands;

import dev.feldmann.redstonegang.common.utils.Cooldown;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.perm.Permission;
import dev.feldmann.redstonegang.wire.base.cmds.types.PlayerCmd;
import dev.feldmann.redstonegang.wire.game.base.addons.server.safetime.SafeTimeAddon;
import dev.feldmann.redstonegang.wire.modulos.menus.menus.ConfirmarMenu;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CmdSeguro extends PlayerCmd {
    SafeTimeAddon addon;

    public CmdSeguro(SafeTimeAddon addon) {
        super("seguro", "remove o modo seguro da conta");
        setExecutePerm(new Permission("rg.safetime.cmd.seguro"));
        this.addon = addon;
        setCooldown(new Cooldown(1000));
    }

    @Override
    public void command(Player player, Arguments args) {
        if (!addon.isInSafetime(player)) {
            C.error(player, "Você não tem o modo %% ativado!", "SEGURO");
            return;
        }
        new ConfirmarMenu(new ItemStack(Material.BED),
                "Desativar Modo Seguro",
                "Você não poderá@reativar o modo seguro!@Pense bem!") {
            @Override
            public void confirmar(Player p) {
                if (addon.isInSafetime(p)) {
                    addon.removeSafeTime(p);
                    C.info(p, "Você removeu o modo seguro! Fica esperto!");
                }
            }

            @Override
            public void recusar(Player p) {

            }
        }.open(player);
    }

}
