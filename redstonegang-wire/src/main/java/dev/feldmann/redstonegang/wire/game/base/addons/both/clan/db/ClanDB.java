package dev.feldmann.redstonegang.wire.game.base.addons.both.clan.db;

import dev.feldmann.redstonegang.common.db.Database;
import dev.feldmann.redstonegang.common.db.jooq.redstonegang_common.Tables;
import dev.feldmann.redstonegang.common.player.UserDB;
import dev.feldmann.redstonegang.common.utils.formaters.StringUtils;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.*;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.*;
import dev.feldmann.redstonegang.wire.utils.location.BungeeLocation;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Result;

import java.sql.Timestamp;

import static org.jooq.impl.DSL.*;

public class ClanDB extends Database {

    public ClanDB(String database) {
        super(database);
    }


    @Override
    public void createTables() {
        dsl().createTableIfNotExists(ClanTable.CLANS)
                .columns(ClanTable.TAG, ClanTable.COLORTAG, ClanTable.NAME, ClanTable.FUNDADO, ClanTable.FOUNDER, ClanTable.PROPERTIES, ClanTable.HOME, ClanTable.LAST_ACTIVE_TIME)
                .constraint(constraint().primaryKey(ClanTable.TAG))
                .constraint(constraint().unique(ClanTable.NAME))
                .constraint(constraint().foreignKey(ClanTable.FOUNDER).references(UserDB.TABLE, UserDB.ID))
                .execute();

        dsl().createTableIfNotExists(MemberTable.MEMBERS)
                .columns(MemberTable.ID, MemberTable.CLAN, MemberTable.ROLE)
                .constraint(constraint().primaryKey(MemberTable.ID))
                .constraint(constraint().foreignKey(MemberTable.ID).references(Tables.USERS, Tables.USERS.ID))
                .constraint(constraint().foreignKey(MemberTable.CLAN).references(ClanTable.CLANS, ClanTable.TAG)
                        .onDeleteSetNull()
                        .onUpdateCascade())
                .execute();

        dsl().createTableIfNotExists(ClanRelations.TABLE)
                .columns(ClanRelations.TAG_1, ClanRelations.TAG_2, ClanRelations.TYPE)
                .constraint(constraint().foreignKey(ClanRelations.TAG_1).references(ClanTable.CLANS, ClanTable.TAG).onDeleteCascade())
                .constraint(constraint().foreignKey(ClanRelations.TAG_2).references(ClanTable.CLANS, ClanTable.TAG).onDeleteCascade())
                .constraint(constraint().primaryKey(ClanRelations.TAG_1, ClanRelations.TAG_2))
                .constraint(constraint().unique(ClanRelations.TAG_2, ClanRelations.TAG_1))
                .execute();
    }


    public Clan loadClan(String tag) {
        Record r = dsl().select().from(ClanTable.CLANS).where(ClanTable.TAG.eq(tag)).fetchOne();
        if (r != null) {
            Clan c = new Clan(r.get(ClanTable.TAG), r.get(ClanTable.COLORTAG), r.get(ClanTable.NAME));
            c.setFounder(r.get(ClanTable.FOUNDER));
            c.setFounded(r.get(ClanTable.FUNDADO));
            c.setHome(BungeeLocation.fromString(r.get(ClanTable.HOME)));
            c.setProps(ClanProperties.fromString(r.get(ClanTable.PROPERTIES)));
            loadClanMembers(c);
            //loadRelations(c);
            return c;
        }
        return null;
    }

    public void list(Player sender, int page) {
        runAsync(() -> dsl().select(ClanTable.COLORTAG, ClanTable.TAG, ClanTable.NAME).from(ClanTable.CLANS).orderBy(ClanTable.LAST_ACTIVE_TIME.desc()).limit((page - 1) * 20, 20).fetch(), (fetch) -> {
            if (sender == null || !sender.isOnline()) return;
            boolean found = false;
            for (Record3<String, String, String> r : fetch) {
                if (!found) {
                    sender.sendMessage(" ");
                    sender.sendMessage("§bClans Pagina §f" + page);
                }
                TextComponent t = new TextComponent(TextComponent.fromLegacyText(" " + r.get(ClanTable.COLORTAG) + " §7- §f" + r.get(ClanTable.NAME)));
                t.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/clan ver " + r.get(ClanTable.TAG)));
                sender.spigot().sendMessage(t);
                found = true;
            }
            if (!found) {
                C.error(sender, "Acabou a lista de clans!");
            } else {
                TextComponent nav = new TextComponent("");
                if (page > 1) {
                    TextComponent voltar = new TextComponent("§2<< Voltar");
                    voltar.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/clan listar " + (page - 1)));
                    nav.addExtra(voltar);
                } else {
                    nav.addExtra(StringUtils.generateSpaces("§e<< Voltar".length()) + "§e");
                }
                nav.addExtra(StringUtils.generateSpaces(15));
                nav.addExtra("[" + page + "]");
                nav.addExtra(StringUtils.generateSpaces(15));
                TextComponent avancar = new TextComponent("§aAvançar >>");
                avancar.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/clan listar " + (page + 1)));
                nav.addExtra(avancar);
            }
        });


    }

    public void loadRelations(Clan c) {
        Result<Record3<String, String, Boolean>> rs = dsl().select(ClanRelations.TAG_1, ClanRelations.TAG_2, ClanRelations.TYPE).from(ClanRelations.TABLE).where(ClanRelations.TAG_1.eq(c.getTag()).or(ClanRelations.TAG_2.eq(c.getTag()))).fetch();
        for (Record3<String, String, Boolean> r : rs) {
            String id;
            if (r.get(ClanRelations.TAG_1).equals(c.getTag())) {
                id = r.get(ClanRelations.TAG_2);
            } else {
                id = r.get(ClanRelations.TAG_2);
            }
            //c.setRelation(id, r.get(ClanRelations.TYPE) ? ClanRelationType.ALLY : ClanRelationType.NEUTRAL);
        }

    }

    private void loadClanMembers(Clan c) {
        Result<Record1<Integer>> rs = dsl().select(MemberTable.ID).from(MemberTable.MEMBERS).where(MemberTable.CLAN.eq(c.getTag())).fetch();
        for (Record1<Integer> r : rs) {
            c.addMember(r.get(MemberTable.ID));
        }
    }

    public void setRelation(String clan1, String clan2, ClanRelationType type) {
        if (type == ClanRelationType.NEUTRAL) {
            dsl().deleteFrom(ClanRelations.TABLE)
                    .where(ClanRelations.TAG_1.eq(clan1).and(ClanRelations.TAG_2.eq(clan2)))
                    .or(ClanRelations.TAG_1.eq(clan2).and(ClanRelations.TAG_2.eq(clan1))).execute();
            return;
        }
        boolean b = type == ClanRelationType.ALLY;
        dsl().insertInto(ClanRelations.TABLE).columns(ClanRelations.TAG_1, ClanRelations.TAG_2, ClanRelations.TYPE)
                .values(clan1, clan2, b)
                .onDuplicateKeyUpdate().set(ClanRelations.TYPE, b).execute();

    }

    public void saveClan(Clan c) {
        String home = c.getHome() != null ? c.getHome().toString() : null;
        dsl().insertInto(ClanTable.CLANS)
                .columns(ClanTable.TAG, ClanTable.COLORTAG, ClanTable.NAME, ClanTable.FUNDADO, ClanTable.FOUNDER, ClanTable.HOME, ClanTable.PROPERTIES)
                .values(c.getTag(), c.getColorTag(), c.getName(), c.getFounded(), c.getFounder(), home, c.getProps().toString())
                .onDuplicateKeyUpdate()
                .set(ClanTable.COLORTAG, c.getColorTag())
                .set(ClanTable.NAME, c.getName())
                .set(ClanTable.HOME, home)
                .set(ClanTable.PROPERTIES, c.getProps().toString())
                .execute();
        return;
    }

    public void changeTag(Clan c, String colorless, String tag) {
        dsl().update(ClanTable.CLANS)
                .set(ClanTable.COLORTAG, tag)
                .set(ClanTable.TAG, colorless)
                .where(ClanTable.TAG.eq(c.getTag())).execute();

    }

    public void deleteClan(Clan c) {
        dsl().deleteFrom(ClanTable.CLANS).where(ClanTable.TAG.eq(c.getTag())).execute();
    }

    public void saveMember(ClanMember member) {
        dsl().insertInto(MemberTable.MEMBERS)
                .columns(MemberTable.ID, MemberTable.CLAN, MemberTable.ROLE)
                .values(member.getPlayerId(), member.getClanTag(), member.getRole())
                .onDuplicateKeyUpdate()
                .set(MemberTable.CLAN, member.getClanTag())
                .set(MemberTable.ROLE, member.getRole()).execute();
    }

    public ClanMember loadMember(int player) {
        Record r = dsl().select().from(MemberTable.MEMBERS).where(MemberTable.ID.eq(player)).fetchOne();
        if (r != null) {
            ClanMember m = new ClanMember(player, r.get(MemberTable.CLAN), r.get(MemberTable.ROLE));
            return m;
        }
        return new ClanMember(player, null, ClanRole.MEMBER);
    }


    public boolean existClanWithName(String name) {
        return dsl().select().from(ClanTable.CLANS).where(ClanTable.NAME.equalIgnoreCase(name)).fetchOne() != null;
    }

    public boolean existClanWithTag(String tag) {
        return dsl().select().from(ClanTable.CLANS).where(ClanTable.TAG.equalIgnoreCase(tag)).fetchOne() != null;
    }

    public void updateLastActive(String tag) {
        dsl().update(ClanTable.CLANS).set(ClanTable.LAST_ACTIVE_TIME, new Timestamp(System.currentTimeMillis())).where(ClanTable.TAG.eq(tag)).executeAsync();
    }
}
