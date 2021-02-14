package dev.feldmann.redstonegang.discord.permissoes;

import net.dv8tion.jda.core.entities.Member;

public class SyncUser {

    public static void sync(Member discordusr, dev.feldmann.redstonegang.common.player.User usr) {
//        List<Role> toAdd = new ArrayList();
//        for (PermissionServer sv : PermissionServer.values()) {
//            PermissionPlayerServer ser = usr.permissions().getServer(sv);
//            Group g = ser.getParent();
//            if (g != null) {
//                if (g.getDiscordRole() != null) {
//                    if (g.getDiscordRole() == 0) {
//                        return;
//                    }
//                    Role role = Discord.instance().getGuild().getRoleById(g.getDiscordRole());
//                    if (role != null) {
//                        toAdd.add(role);
//                    }
//                }
//            }
//        }
//        Discord.instance().getGuild().getController().removeRolesFromMember(discordusr, discordusr.getRoles()).queue();
//        Discord.instance().getGuild().getController().addRolesToMember(discordusr, toAdd).queue();

    }
}
