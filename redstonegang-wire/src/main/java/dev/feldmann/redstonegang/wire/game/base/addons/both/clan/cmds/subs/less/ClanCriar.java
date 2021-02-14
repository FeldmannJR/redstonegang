package dev.feldmann.redstonegang.wire.game.base.addons.both.clan.cmds.subs.less;

import dev.feldmann.redstonegang.wire.Wire;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.RemainStringsArgument;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.StringArgument;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.Clan;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanMember;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanRole;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.cmds.ClanLessCmd;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.event.ClanCreateEvent;
import dev.feldmann.redstonegang.wire.game.base.addons.server.economy.EconomyAddon;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.modulos.menus.menus.SellMenu;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import dev.feldmann.redstonegang.common.utils.msgs.MsgType;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.sql.Timestamp;

public class ClanCriar extends ClanLessCmd {
    private static final StringArgument TAG = new StringArgument("tag", false);
    private static final StringArgument NAME = new RemainStringsArgument("nome", false);

    public ClanCriar(ClanAddon addon) {
        super(addon, "criar", "cria um clan", TAG, NAME);
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        Player p = (Player) sender;


        String tag = args.get(TAG);
        String nome = args.get(NAME);
        String color = ChatColor.translateAlternateColorCodes('&', tag);

        if (!canBuyClan(p, tag, nome)) {
            return;
        }
        ClanCreateEvent ev = new ClanCreateEvent(p, color, nome, getAddon());
        if (Wire.callEvent(ev)) {
            return;
        }
        new SellMenu("Clan", getAddon().a(EconomyAddon.class).getCurrency(), getAddon().PRECO_CRIAR, new ItemStack(Material.BEACON)) {
            @Override
            public boolean compra(Player p, Menu m) {
                if (!canBuyClan(p, tag, nome)) {
                    return false;
                }
                Clan c = new Clan(ChatColor.stripColor(color).toLowerCase(), color, nome);
                c.setFounded(new Timestamp(System.currentTimeMillis()));
                c.setFounder(getAddon().getPlayerId(p));
                c.addMember(getAddon().getPlayerId(p));
                c.setManager(getAddon());
                ClanMember member = getAddon().getCache().getMember(p);
                member.setClan(c.getTag());
                member.setRole(ClanRole.LEADER);
                getAddon().getCache().saveClan(c);
                getAddon().getCache().saveMember(member);
                getAddon().broadcast(C.msgText(MsgType.INFO, "Clan %% criado por %%!", c, p));
                return true;
            }
        }.open(p);
    }

    private boolean canBuyClan(Player p, String tag, String name) {
        String tagValid = getAddon().isTagValid(p, tag);
        if (tagValid != null) {
            C.error(p, tagValid);
            return false;
        }
        String nameValid = getAddon().isNameValid(name);
        if (nameValid != null) {
            C.error(p, nameValid);
            return false;
        }
        if (getAddon().getCache().existsTag(tag)) {
            C.error(p, "Já existe um clan com esta tag!");
            return false;
        }

        if (getAddon().getCache().existsName(name)) {
            C.error(p, "Já existe um clan com este nome!");
            return false;
        }
        return true;
    }
}
