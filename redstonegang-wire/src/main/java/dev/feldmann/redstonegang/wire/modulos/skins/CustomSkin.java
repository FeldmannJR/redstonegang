package dev.feldmann.redstonegang.wire.modulos.skins;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.player.Skin;
import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.StringArgument;
import dev.feldmann.redstonegang.wire.base.modulos.Modulo;
import dev.feldmann.redstonegang.wire.utils.json.RGson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.inventivetalent.packetlistener.PacketListenerAPI;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CustomSkin extends Modulo {

    static ConcurrentHashMap<UUID, PlayerSkin> skins = new ConcurrentHashMap<>();

    @Override
    public void onEnable() {
        PacketListenerAPI.addPacketHandler(new SkinPacketListener());
        register(new RedstoneCmd("setskin", "seta uma skin", new StringArgument("nome", false)) {
            @Override
            public void command(CommandSender sender, Arguments args) {
                StringArgument s = (StringArgument) this.getArgs().get(0);
                String name = args.get(s);
                Skin skin = getSkin(name);
                setSkin((Player) sender, null, skin);
            }
        });
        register(new RedstoneCmd("setnome", "disguise", new StringArgument("nome", false)) {
            @Override
            public void command(CommandSender sender, Arguments args) {
                StringArgument s = (StringArgument) this.getArgs().get(0);
                String name = args.get(s);
                Skin skin = getSkin(name);
                setSkin((Player) sender, name, skin);
                RedstoneGang.instance.user().cache.clearCache(((Player) sender).getUniqueId());
            }
        });
    }

    @Override
    public void onDisable() {

    }

    public static Skin getSkin(String skinName) {
        JsonObject name = RGson.loadFromUrl("https://api.mojang.com/users/profiles/minecraft/" + skinName);
        if (name != null) {
            String s = name.get("id").getAsString();
            JsonObject skin = RGson.loadFromUrl("https://sessionserver.mojang.com/session/minecraft/profile/" + s + "?unsigned=false");
            if (skin != null) {
                JsonArray propsArray = skin.get("properties").getAsJsonArray();
                for (JsonElement el : propsArray) {
                    if (el.isJsonObject()) {
                        JsonObject props = el.getAsJsonObject();
                        if (props.get("name").getAsString().equals("textures")) {
                            String textures = props.get("value").getAsString();
                            String signature = props.get("signature").getAsString();
                            return new Skin(textures, signature);

                        }
                    }
                }
            }
        }
        return null;
    }

    public static void setSkin(Player p, String name, Skin s) {

        PlayerSkin prev = null;
        if (skins.containsKey(p.getUniqueId())) {
            prev = skins.remove(p.getUniqueId());
            prev.remove(s != null);
        }
        if (s != null) {
            PlayerSkin sk = new PlayerSkin(p.getUniqueId(), p.getName(), name, s);
            skins.put(p.getUniqueId(), sk);
            sk.set(prev);
        }
    }

    public static void putWithoutReload(UUID uuid, String name, String disguise, Skin s) {
        skins.put(uuid, new PlayerSkin(uuid, name, disguise, s));
    }


}
