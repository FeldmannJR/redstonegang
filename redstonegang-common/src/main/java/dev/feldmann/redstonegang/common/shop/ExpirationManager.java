package dev.feldmann.redstonegang.common.shop;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.db.jooq.redstonegang_common.tables.records.ExpirationsRecord;
import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.common.player.permissions.Group;
import dev.feldmann.redstonegang.common.utils.msgs.Msg;
import org.jooq.types.UInteger;

import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

public class ExpirationManager {

    private ShopManager manager;
    private ExpirationDB db;

    public ExpirationManager(ShopManager manager) {
        this.manager = manager;
        this.db = new ExpirationDB();
    }


    public void checkExpiration(User user) {
        ExpirationsRecord active = db.getActive(user);
        if (isExpired(active)) {
            // Se o vip venceu
            currentExpired(user, active);
            return;
        }
    }


    private void currentExpired(User user, ExpirationsRecord expired) {
        // Seta default
        if (!setDefault(user)) {
            // Caso não consiga setar o grupo default no nego que expirou kicka ele pra proxima vez que entrar tentar remover
            user.kick("§cOcorreu um erro no vencimento do seu vip entre em contato com um staff!");
            return;
        }
        // Seta que terminou
        expired.setEnd(new Timestamp(System.currentTimeMillis()));
        expired.store();
        if (db.hasAvailable(user)) {
            msg(user, "Seu VIP acabou! Porém você ainda pode ativar outros vips em %cmd% !", "/vip");
        } else {
            msg(user, "Seu VIP acabou! Considere adquirir novamente para ajudar o servidor :D!");
        }
    }

    /**
     * Dar a opção de quando o vip acabar ele poder escolher um vip que foi "deixado de lado"
     */
    private boolean reactivateVip(User user, ExpirationsRecord vip) {
        ExpirationsRecord active = db.getActive(user);
        if (active != null) {
            Msg.error(user, "Você já tem um VIP ativo!");
            return false;
        }
        if (vip.getUserId() != user.getId()) {
            Msg.error(user, "Este vip não é seu!");
            return false;
        }
        if (isExpired(vip) || vip.getStart() != null) {
            Msg.error(user, "Este vip já foi ativado!");
            return false;
        }
        vip.setStart(new Timestamp(System.currentTimeMillis()));
        if (!setVip(user, vip)) {
            Msg.error(user, "Ocorreu um erro ao reativar o vip!");
            return false;
        }
        vip.store();

        return true;
    }

    public boolean activatePackage(User user, UInteger code_id, String type, int dias) {
        // Se ele já tiver um vip desativa ele e insere denovo com a duração ajustada
        overrideCurrent(user);
        ExpirationsRecord record = db.newExpiration();
        record.setUserId(user.getId());
        record.setType(type);
        record.setDays(dias);
        record.setCodeId(code_id);
        record.setEnd(null);
        record.setStart(new Timestamp(System.currentTimeMillis()));
        record.setParent(null);
        if (!setVip(user, record)) {
            return false;
        }
        record.store();
        return true;
    }


    public void overrideCurrent(User user) {
        ExpirationsRecord active = db.getActive(user);
        if (active != null) {
            int diffDays = 0;
            if (active.getDays() > 0) {
                // Se não for permanente tira a diferança de quando começou até agora
                int usedDays = Math.toIntExact(TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis() - active.getStart().getTime()));
                diffDays = active.getDays() - usedDays;
                if (diffDays <= 0) {
                    // Se não sobrou um dia não salva no banco
                    return;
                }
            }
            // Seta que acabou o vip agora
            active.setEnd(new Timestamp(System.currentTimeMillis()));
            active.store();

            ExpirationsRecord remain = db.newExpiration();
            remain.setParent(active.getId());
            remain.setStart(null);
            remain.setEnd(null);
            remain.setDays(diffDays);
            remain.setCodeId(null);
            remain.setType(active.getType());
            remain.setUserId(user.getId());
            remain.store();

        }
    }


    private boolean setDefault(User user) {
        try {
            return user.permissions().setDefaultGroup();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private boolean setVip(User user, ExpirationsRecord record) {
        try {
            Group group = findGroupByExpiration(record);
            if (group == null) {
                return false;
            }
            return user.permissions().setGroup(group.getIdentifier());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private Group findGroupByExpiration(ExpirationsRecord record) {
        return RedstoneGang.instance().user().getPermissions().getGroupByName(record.getType());
    }

    public boolean isExpired(ExpirationsRecord record) {
        if (record.getDays() == 0) return false;
        if (record.getEnd() != null) return true;
        Timestamp expiration = new Timestamp(record.getStart().getTime() + (record.getDays() * 24L * 60L * 60L * 1000L));
        return expiration.before(new Timestamp(System.currentTimeMillis()));
    }

    private void msg(User user, String msg, Object... obj) {
        Msg.send(user, manager.VIP, msg, obj);
    }


}
