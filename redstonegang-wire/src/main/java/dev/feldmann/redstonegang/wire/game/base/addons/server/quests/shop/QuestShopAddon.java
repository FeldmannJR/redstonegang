package dev.feldmann.redstonegang.wire.game.base.addons.server.quests.shop;

import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.QuestAddon;
import dev.feldmann.redstonegang.wire.game.base.objects.annotations.Dependencies;
import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.ShopAddon;

@Dependencies(hard = QuestAddon.class)
public class QuestShopAddon extends ShopAddon {

    public QuestShopAddon(String dbName) {
        super(dbName, "quest_shop", null);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.currency = a(QuestAddon.class).currency;
    }

    @Override
    public String getIdentifierName() {
        return "questshop";
    }

    @Override
    public String getCommand() {
        return "qshop";
    }

    @Override
    public String getNome() {
        return "Quest Shop";
    }
}
