package dev.feldmann.redstonegang.common.utils.invites;

public class Invite<K, V> {
    K inviter;
    V invited;
    long expires;

    public Invite(K inviter, V invited) {
        this.inviter = inviter;
        this.invited = invited;
    }

    public V getInvited() {
        return invited;
    }

    public K getInviter() {
        return inviter;
    }

    public long getExpires() {
        return expires;
    }

    public boolean isValid() {
        return expires > System.currentTimeMillis();
    }
}
