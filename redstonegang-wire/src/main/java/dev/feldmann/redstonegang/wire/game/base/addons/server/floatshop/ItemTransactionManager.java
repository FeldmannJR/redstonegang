package dev.feldmann.redstonegang.wire.game.base.addons.server.floatshop;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ItemTransactionManager {
    private FloatItem item;
    private FloatShopDB db;

    List<ItemTransaction> transactions = new ArrayList();
    private ItemTransaction current;

    public ItemTransactionManager(FloatItem item, FloatShopDB db) {
        this.item = item;
        this.db = db;
    }


    public ItemTransaction getCurrent() {
        if (current != null) {
            if (isCurrent(current)) {
                return current;
            } else {
                // Quando troca salva a antiga
                saveCurrent();
            }
        } else {
            for (ItemTransaction transaction : transactions) {
                if (isCurrent(transaction)) {
                    current = transaction;
                    return current;
                }
            }
        }
        // Não entende isso né? ta pensando q tava fudido quando fez isso?
        // ele só separa em intervalos de 5 minutos pra n entupir o banco
        // fica ex 11:40 - 11:45, 11:45 - 11:50
        int minutesValid = 5;
        Date d = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        int minutes = (c.get(Calendar.MINUTE) / minutesValid) * minutesValid;
        c.set(Calendar.MINUTE, minutes);
        c.set(Calendar.SECOND, 0);
        Timestamp min = new Timestamp(c.getTime().getTime());
        c.add(Calendar.MINUTE, minutesValid);
        Timestamp max = new Timestamp(c.getTime().getTime());
        current = new ItemTransaction(item.id, 0, min, max);
        transactions.add(current);
        return current;

    }

    private boolean isCurrent(ItemTransaction transaction) {
        if (transaction == null) return false;
        return transaction.max.after(new Date());
    }


    public void addSell(int quantity) {
        getCurrent().quantity -= quantity;
    }

    public void addBuy(int quantity) {
        getCurrent().quantity += quantity;
    }

    public double calculateDiff() {
        long diff = 0;
        if (transactions != null) {
            for (ItemTransaction t : transactions) {
                if (t.isValid()) {
                    diff += t.quantity;
                }

            }
        }
        return diff;
    }

    public void clean() {
        transactions.removeIf(itemTransaction -> !itemTransaction.isValid());
    }

    public void saveCurrent() {
        if (current != null) {
            if (current.id == -1) {
                this.db.addTransaction(current);
            } else {
                this.db.saveTransaction(current);
            }
        }
    }
}
