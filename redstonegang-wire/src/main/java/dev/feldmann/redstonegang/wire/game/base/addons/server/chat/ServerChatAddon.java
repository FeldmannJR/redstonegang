package dev.feldmann.redstonegang.wire.game.base.addons.server.chat;

import dev.feldmann.redstonegang.wire.game.base.addons.both.chat.ChatAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.chat.ChatChannel;
import dev.feldmann.redstonegang.wire.game.base.addons.both.chat.channels.StaffChannel;
import dev.feldmann.redstonegang.wire.game.base.addons.server.chat.channels.AjudaChannel;
import dev.feldmann.redstonegang.wire.game.base.addons.server.chat.channels.GlobalChannel;
import dev.feldmann.redstonegang.wire.game.base.addons.server.chat.channels.LocalChannel;
import dev.feldmann.redstonegang.wire.game.base.addons.server.economy.EconomyAddon;
import dev.feldmann.redstonegang.wire.game.base.objects.annotations.AConfig;
import dev.feldmann.redstonegang.wire.game.base.objects.annotations.Dependencies;

@Dependencies(hard = EconomyAddon.class)
public class ServerChatAddon extends ChatAddon {

    @AConfig
    int globalChatPrice = 10;

    public ServerChatAddon(String db) {
        super(db);
    }

    private LocalChannel local;
    private GlobalChannel global;
    private AjudaChannel ajuda;

    @Override
    public void onEnable() {
        super.onEnable();
        local = new LocalChannel(getServer());
        ajuda = new AjudaChannel();
        global = new GlobalChannel(globalChatPrice, getServer());
        registerChannel(local);
        registerChannel(global);
        registerChannel(ajuda);
        registerChannel(new StaffChannel());
    }

    public LocalChannel getLocal() {
        return local;
    }

    public AjudaChannel getAjuda() {
        return ajuda;
    }

    public GlobalChannel getGlobal() {
        return global;
    }

    @Override
    public ChatChannel getDefautlChannel() {
        return local;
    }
}
