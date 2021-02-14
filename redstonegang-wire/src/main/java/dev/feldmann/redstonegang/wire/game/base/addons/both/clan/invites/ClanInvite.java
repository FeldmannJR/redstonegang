package dev.feldmann.redstonegang.wire.game.base.addons.both.clan.invites;

import dev.feldmann.redstonegang.common.utils.invites.Invite;

public class ClanInvite extends Invite<String, Integer> {

    public ClanInvite(String inviter, Integer invited) {
        super(inviter, invited);
    }

}
