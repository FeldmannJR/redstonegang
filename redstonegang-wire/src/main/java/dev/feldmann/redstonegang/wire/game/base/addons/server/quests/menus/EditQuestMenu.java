package dev.feldmann.redstonegang.wire.game.base.addons.server.quests.menus;

import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.wire.RedstoneGangSpigot;
import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.Quest;
import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.QuestInfo;
import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.hooks.QuestContador;
import dev.feldmann.redstonegang.wire.modulos.menus.Button;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.modulos.menus.buttons.BackButton;
import dev.feldmann.redstonegang.wire.modulos.menus.buttons.ConfirmarButton;
import dev.feldmann.redstonegang.wire.modulos.menus.buttons.SelectIntegerButton;
import dev.feldmann.redstonegang.wire.modulos.menus.buttons.SelectStringButton;
import dev.feldmann.redstonegang.wire.utils.items.CItemBuilder;
import dev.feldmann.redstonegang.wire.utils.items.ItemBuilder;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class EditQuestMenu extends Menu {

    Quest q;


    public EditQuestMenu(Quest q) {
        super("Editando Quest " + q.getId(), 1);
        this.q = q;
        add(1, new SelectStringButton(ItemBuilder.item(Material.BOOK_AND_QUILL).name(C.item("Setar Nome")).lore(C.itemDesc("Atual: %%", q.nome)).build()) {
            @Override
            public void accept(String string, Player p) {
                q.nome = string;
                q.getManager().getDB().saveQuest(q);
                C.info(p, "Nome alterado para %%!", q.nome);
            }
        });
        add(4, new ConfirmarButton(ItemBuilder.item(Material.BARRIER).name("§cDeletar").build(), "Deletar Quest", "§f" + q.nome) {
            @Override
            public void confirmar(Player p) {
                q.getManager().deleteQuest(q);
                new ListQuestAdmMenu(q.getManager()).open(p);
            }

            @Override
            public void recusar(Player p) {
                new ListQuestAdmMenu(q.getManager()).open(p);
            }
        });
        addEditQuantity(5);
        add(7, new Button(ItemBuilder.item(Material.CHEST).name(C.item("Começar")).build()) {
            @Override
            public void click(Player player, Menu menu, ClickType clickType) {
                User user = RedstoneGangSpigot.getPlayer(player);
                QuestInfo info = q.getManager().createInfo(q, user.getId());
                q.getManager().addQuestToPlayer(user, info);
                player.closeInventory();
                player.sendMessage("§aQuest começou!");
            }
        });
        add(0, new BackButton() {
            @Override
            public void click(Player player, Menu menu, ClickType clickType) {
                new ListQuestAdmMenu(q.getManager()).open(player);
            }
        });


    }

    public void addEditQuantity(int slot) {
        if (q.getHook() instanceof QuestContador) {
            QuestContador contador = (QuestContador) q.getHook();
            add(slot, new SelectIntegerButton(
                    CItemBuilder
                            .item(Material.EGG)
                            .name("Selecionar Quantidade")
                            .desc("Atual: %%", contador.contador)
                            .build(),
                    contador.contador + ""
                    , 1
            ) {
                @Override
                public void accept(Integer integer, Player p) {
                    contador.contador = integer;
                    q.getManager().getDB().saveQuest(q);
                    C.info(p, "Quantidade alterada para %%!", integer);
                }
            });
        }
    }

}
