package dev.feldmann.redstonegang.common.utils.invites;

import dev.feldmann.redstonegang.common.RedstoneGang;

import java.util.HashSet;
import java.util.Iterator;

public class InviteManager<K, V, T extends Invite<K, V>> {
    HashSet<T> invites = new HashSet<>();

    long expires;

    public InviteManager(long expires) {
        this.expires = expires;
        RedstoneGang.instance.runRepeatingTask(() -> {
            Iterator<T> it = invites.iterator();
            while (it.hasNext()) {
                T n = it.next();
                if (!n.isValid()) {
                    it.remove();
                    inviteExpired(n);
                }
            }
        }, 20);
    }

    protected void inviteExpired(T inv) {

    }

    public boolean addInvite(T inv) {
        Invite previous = getInvite(inv.getInviter(), inv.getInvited());
        if (previous != null) {
            return false;
        }
        inv.expires = System.currentTimeMillis() + expires;
        invites.add(inv);
        return true;
    }

    public boolean isInviting(K inviter, V invited) {
        return getInvite(inviter, invited) != null;
    }

    public void deleteInvite(K inviter, V invited) {
        invites.remove(getInvite(inviter, invited));
    }

    public T getInvite(K inviter, V invited) {
        for (T inv : invites) {
            if (inv.getInvited().equals(invited) && inv.getInviter().equals(inviter) && inv.isValid()) {
                return inv;
            }
        }
        return null;
    }

}
