package dev.feldmann.redstonegang.repeater.modules.skins;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.db.Database;
import dev.feldmann.redstonegang.common.db.jooq.redstonegang_app.tables.records.SkinsRecord;
import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.repeater.events.PlayerLoadEvent;
import dev.feldmann.redstonegang.repeater.modules.Module;
import net.md_5.bungee.connection.InitialHandler;
import net.md_5.bungee.connection.LoginResult;
import net.md_5.bungee.event.EventHandler;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static dev.feldmann.redstonegang.common.db.jooq.redstonegang_app.Tables.SKINS;

public class SkinLoaderModule extends Module {


    List<SkinsRecord> skins = new ArrayList<>();

    public SkinLoaderModule() {
        loadSkins();
    }

    private void loadSkins() {
        this.skins = Database.database().selectFrom(SKINS).where(SKINS.USE.eq(true)).fetch();
    }

    @EventHandler
    public void postLogin(PlayerLoadEvent ev) {
        RedstoneGang.instance().debug("PostLogin");
        InitialHandler handler = (InitialHandler) ev.getBaseEvent().getConnection();
        // Skin só pra pirata
        if (!handler.isOnlineMode()) {
            User user = ev.getPlayer();
            applySkin(user, handler);
        }
    }

    private void applySkin(User player, InitialHandler handler) {
        RedstoneGang.instance().debug("Applying skin in " + player.getName());
        SkinsRecord skin = getSkin(player.getId());
        if (skin == null) return;
        LoginResult.Property[] properties = new LoginResult.Property[1];
        properties[0] = new LoginResult.Property("textures", skin.getTextures(), skin.getSignature());
        LoginResult profile = handler.getLoginProfile();
        if (profile == null) {
            profile = new LoginResult(null, null, properties);
        }
        profile.setProperties(properties);
        try {
            getProfileField().set(handler, profile);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    private SkinsRecord getSkin(int playerId) {
        // Pega baseado no id a skin
        if (skins.isEmpty()) return null;
        return skins.get(playerId % skins.size());
    }

    private static Field profileField = null;

    public static Field getProfileField() throws NoSuchFieldException, SecurityException {
        if (profileField == null) {
            profileField = InitialHandler.class.getDeclaredField("loginProfile");
            profileField.setAccessible(true);
        }
        return profileField;
    }
}
