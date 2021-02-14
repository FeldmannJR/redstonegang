package dev.feldmann.redstonegang.repeater.commands;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.common.utils.Cooldown;
import dev.feldmann.redstonegang.repeater.RedstoneGangBungee;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class RegisterCommand extends Command {

    Cooldown cooldown = new Cooldown(5000);

    public RegisterCommand() {
        super("register");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer pl = (ProxiedPlayer) sender;
            User user = RedstoneGangBungee.getPlayer(pl);
            if (pl.getPendingConnection().isOnlineMode()) {
                if (cooldown.isCooldown(pl.getUniqueId())) {
                    pl.sendMessage(TextComponent.fromLegacyText("§cEspere um pouco para usar o comando novamente!"));
                    return;
                }
                if (user.getAccountId() != null) {
                    pl.sendMessage(TextComponent.fromLegacyText("§cSua conta já está registrada!"));
                    return;
                }
                String token = RedstoneGang.instance().webapi().auth().generateRegisterToken(user);
                String link = RedstoneGang.instance().config().APP_BASE_URL + "/register/premium/?token=" + token;
                TextComponent component = new TextComponent(TextComponent.fromLegacyText("§eClique §caqui §epara registrar sua conta!"));
                component.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, link));
                pl.sendMessage(component);
                cooldown.addCooldown(pl.getUniqueId());
            } else {
                pl.sendMessage(TextComponent.fromLegacyText("§cVocê já se registrou no site!"));
            }
        }
    }
}
