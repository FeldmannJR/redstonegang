package dev.feldmann.redstonegang.wire.modulos.menus.menus;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.currencies.Currency;
import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.wire.RedstoneGangSpigot;
import dev.feldmann.redstonegang.wire.modulos.menus.Button;
import dev.feldmann.redstonegang.wire.modulos.menus.FurnaceMenu;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.modulos.menus.buttons.DummyButton;
import dev.feldmann.redstonegang.wire.utils.items.BannerBuilder;
import dev.feldmann.redstonegang.wire.utils.items.ItemBuilder;
import dev.feldmann.redstonegang.wire.utils.items.ItemUtils;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public abstract class SellMenu extends FurnaceMenu {

    Currency moeda;
    int valor;
    String oq;
    boolean msg;
    ItemStack icone;
    Consumer<Player> backAction;

    public SellMenu(String oq, Currency moeda, int valor, ItemStack icone) {
        this(oq, moeda, valor, icone, null, false);
    }

    public SellMenu(String oq, Currency moeda, int valor, ItemStack icone, Consumer<Player> back) {
        this(oq, moeda, valor, icone, back, false);
    }

    public SellMenu(String oq, Currency moeda, int valor, ItemStack icone, Consumer<Player> back, boolean msg) {
        super(oq);
        this.valor = valor;
        this.oq = oq;
        this.moeda = moeda;
        this.icone = icone;
        this.msg = msg;
        this.backAction = back;
        setArrowAnim(true);
        setFireAnim(true);
        addButtons();
    }


    public void addButtons() {
    /*    setFuel(new BackButton() {
            @Override
            public void click(Player p, Menu m, ClickType click) {
                if (backAction == null) {
                    p.closeInventory();
                } else {
                    backAction.accept(p);
                }

            }
        });*/
        ItemStack display = new ItemStack(moeda.getMaterial().getId(), 1, moeda.getMaterial().getData());
        ItemUtils.setItemName(display, moeda.getValue(valor));
        ItemUtils.addLore(display, "§7Será gasto " + moeda.getValue(valor));
        if (valor <= 64) {
            display.setAmount(valor);
        }
        setFuel(new DummyButton(display));
        ItemUtils.setItemName(icone, "§c§l" + oq);
        setIngredient(new DummyButton(icone));
        setResult(new Button(
                ItemBuilder.item(BannerBuilder.baseColor(DyeColor.LIME).create())
                        .name(ChatColor.GREEN + "Confirmar")
                        .lore("§fClique para confirmar", "§fa compra de", "§7" + oq + "", "§fpor " + moeda.getValue(valor)
                        ).build()) {
            @Override
            public void click(Player p, Menu m, ClickType click) {
                User pl = RedstoneGangSpigot.getPlayer(p);
                if (pl == null) {
                    p.closeInventory();
                    return;
                }
                int id = pl.getId();
                Object tem = moeda.get(id);
                if (tem == null) {
                    p.closeInventory();
                    return;
                }
                if (!moeda.have(id, valor)) {
                    p.spigot().sendMessage(moeda.getErrorMessage());
                    quit(p, QuitType.FAILED);
                    return;
                }
                if (compra(p, m)) {
                    moeda.remove(id, valor);
                    RedstoneGang.instance.logs().insertMoeda(id, "Comprou " + oq, -valor, moeda);
                    if (msg)
                        C.info(p, "Você comprou %% por %% !", oq, moeda.getValue(valor));
                    quit(p, QuitType.SUCCESS);
                }
            }
        });

    }

    public void quit(Player p, QuitType type) {
        p.closeInventory();
    }

    public abstract boolean compra(Player p, Menu m);

    public enum QuitType {
        SUCCESS,
        BACK,
        FAILED,
    }

}
