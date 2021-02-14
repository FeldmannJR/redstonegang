package dev.feldmann.redstonegang.wire.game.base.addons.both.shopintegration;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.wire.game.base.addons.both.shopintegration.cmds.AtivarCodigo;
import dev.feldmann.redstonegang.wire.game.base.events.player.PlayerJoinServerEvent;
import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.event.EventHandler;

public class ShopIntegrationAddon extends Addon {

    public boolean giveCashOnLogin = true;
    private int quantidade = 50;

    @Override
    public void onEnable() {
        registerCommand(new AtivarCodigo());
    }

    @EventHandler
    public void join(PlayerJoinServerEvent ev) {
        if (!giveCashOnLogin) {
            return;
        }
        boolean canClaim = ev.getUser().canClaimCash();
        // Pega o que ta cacheado
        if (canClaim) {
            // Refresha o cache se pode pegar
            canClaim = ev.getUser().canClaimWithoutCache();
            if (canClaim) {
                ev.getUser().claimDailyCash();
                ev.getUser().addCashWithLog(quantidade, "Daily Cash Claim");
                C.send(ev.getPlayer(), RedstoneGang.instance().shop().RUBI, "Você ganhou %% Rubis diarios!", quantidade);
            }
        }
    }

}
