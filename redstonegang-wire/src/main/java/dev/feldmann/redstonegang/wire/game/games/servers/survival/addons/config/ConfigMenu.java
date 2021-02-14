package dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.config;

import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.wire.RedstoneGangSpigot;
import dev.feldmann.redstonegang.wire.game.base.addons.both.chat.ChatChannel;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanMember;
import dev.feldmann.redstonegang.wire.game.base.addons.both.tell.TellAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.chat.ServerChatAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.titulos.TituloAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.multiservertp.MultiServerTpAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.LandAddon;
import dev.feldmann.redstonegang.wire.game.games.servers.survival.Survival;
import dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.respawn.SurvivalRespawnAddon;
import dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.deathmessages.SurvivalDeathMessagesAddon;
import dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.scoreboard.SurvivalScoreboard;
import dev.feldmann.redstonegang.wire.modulos.menus.Button;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.modulos.menus.buttons.BackButton;
import dev.feldmann.redstonegang.wire.modulos.menus.buttons.ConfigButton;
import dev.feldmann.redstonegang.wire.modulos.menus.buttons.DummyButton;
import dev.feldmann.redstonegang.wire.utils.Cores;
import dev.feldmann.redstonegang.wire.utils.items.BannerBuilder;
import dev.feldmann.redstonegang.wire.utils.items.CItemBuilder;
import dev.feldmann.redstonegang.wire.utils.items.ItemBuilder;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import dev.feldmann.redstonegang.common.utils.msgs.MsgType;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class ConfigMenu extends Menu {

    Survival surv;
    User user;

    public ConfigMenu(Survival surv, User pl) {
        super("Config", 3);
        this.surv = surv;
        this.user = pl;
        addChat(pl);
        addVip(pl, 4);
        int start = 10;
        addTitulo(start++);
        addTpa(start++);
        addClan(pl, start++);
        addDeathMessage(start++);
        addRespawnLocation(pl, start++);
        addScoreboard(start++);
        addTerrenoTitle(start++);

        if (pl.isStaff()) {
            add(0, new Button(ItemBuilder.item(Material.COMMAND).name(C.item("Configs Staff")).build()) {
                @Override
                public void click(Player p, Menu m, ClickType click) {
                    new StaffConfigMenu(surv, pl).open(p);
                }
            });
        }


    }

    public void addDeathMessage(int slot) {
        add(slot, new ConfigButton(user, CItemBuilder.item(Material.SKULL_ITEM).name("Mortes no Chat").build(), surv.a(SurvivalDeathMessagesAddon.class).DEATH_MESSAGES));
    }

    public void addRespawnLocation(User pl, int slot) {
        add(slot, new ConfigButton(pl, CItemBuilder.item(Material.BED).name("Lugar de Respawn").build(), surv.a(SurvivalRespawnAddon.class).RESPAWN_AT_HOME));
    }


    public void addVip(User pl, int slot) {
        if (pl.isVip()) {
            add(slot, new Button(ItemBuilder.item(Material.DIAMOND).name(C.item("Configurações de VIPs")).build()) {
                @Override
                public void click(Player p, Menu m, ClickType click) {
                    new VipConfigMenu(surv, pl).open(p);
                }
            });
        } else {
            add(slot, new DummyButton(CItemBuilder.item(Material.BARRIER).name("Configurações de VIPs").build()));
        }
    }

    private void addClan(User pl, int slot) {
        ClanMember member = surv.a(ClanAddon.class).getCache().getMember(pl.getId());
        if (member.getClanTag() == null) {

            add(slot, new DummyButton(ItemBuilder
                    .item(Material.BARRIER)
                    .name(C.msg(MsgType.ITEM_CANT, "Mostrar tag do Clan"))
                    .lore(C.msg(MsgType.ITEM_CANT_DESC, "Você não tem um clan!"))
                    .build()));
            return;
        }
        if (!member.getClan().canUseTag()) {

            add(slot, new DummyButton(ItemBuilder
                    .item(Material.BARRIER)
                    .name(C.msg(MsgType.ITEM_CANT, "Mostrar tag do Clan"))
                    .lore(C.msg(MsgType.ITEM_CANT_DESC, "O líder do seu clan não tem esta permissão!"))
                    .build()));
            return;
        }


        add(slot, new ConfigButton(pl, ItemBuilder.item(Material.BEACON).name(C.item("Mostrar tag do clan")).build(), surv.a(ClanAddon.class).SUFFIX_CONFIG));
    }


    public void addChat(User pl) {
        ServerChatAddon chat = surv.a(ServerChatAddon.class);

        int x = 20;
        for (ChatChannel ch : new ChatChannel[]{chat.getGlobal(), chat.getLocal(), chat.getAjuda(), surv.a(ClanAddon.class).channel}) {
            add(x, new ConfigButton(pl,
                    ItemBuilder.item(
                            BannerBuilder.getCharBanner(ch.getChannelDisplayName().toLowerCase().charAt(0), Cores.getDyeColor(ch.getChannelColor()), DyeColor.WHITE)
                    ).name(ch.getChannelColor() + "Chat " + ch.getChannelName()).build(), ch.getEnableConfig()));
            x++;
        }
        add(x, new ConfigButton(pl,
                CItemBuilder
                        .item(BannerBuilder.getCharBanner('P', DyeColor.PINK, DyeColor.WHITE))
                        .name("§dChat Privado")
                        .build()
                , surv.a(TellAddon.class).TELL_AVAILABILITY));
    }

    public void addTpa(int slot) {
        if (this.surv.hasAddon(MultiServerTpAddon.class)) {
            MultiServerTpAddon addon = surv.a(MultiServerTpAddon.class);
            add(slot, new ConfigButton(
                    this.user,
                    CItemBuilder.item(Material.ENDER_PEARL)
                            .name("Pedidos de Teleporte")
                            .build(),
                    addon.TPA_AVAILABILITY)
            );
        }
    }

    public void addScoreboard(int slot) {
        if (this.surv.hasAddon(SurvivalScoreboard.class)) {
            add(slot, new ConfigButton(this.user,
                    CItemBuilder.item(Material.LADDER)
                            .name("Barra Lateral")
                            .build(),
                    surv.a(SurvivalScoreboard.class).USE_SIDEBAR));

        }
    }

    public void addTerrenoTitle(int slot) {
        if (this.surv.hasAddon(LandAddon.class)) {
            add(slot, new ConfigButton(this.user,
                    CItemBuilder.item(Material.FENCE)
                            .name("Ver dono do terreno ao entrar")
                            .build(),
                    surv.a(LandAddon.class).SHOW_ENTER_TITLE));

        }
    }

    public void addTitulo(int slot) {
        add(slot, new Button(ItemBuilder.item(Material.NAME_TAG).name(C.item("Titulos")).lore(C.itemDesc("Clique aqui para trocar"), C.itemDesc("seu titulo!")).build()) {
            @Override
            public void click(Player p, Menu m, ClickType click) {
                surv.a(TituloAddon.class).openEditor(p, new BackButton() {
                    @Override
                    public void click(Player p, Menu m, ClickType click) {
                        new ConfigMenu(surv, RedstoneGangSpigot.getPlayer(p)).open(p);
                    }
                });
            }
        });
    }
}
