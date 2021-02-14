package dev.feldmann.redstonegang.wire.modulos.disguise.types.player;

import com.mojang.authlib.GameProfile;
import dev.feldmann.redstonegang.wire.modulos.disguise.DisguiseModule;
import dev.feldmann.redstonegang.wire.modulos.disguise.types.base.DisguiseData;
import dev.feldmann.redstonegang.wire.modulos.disguise.types.base.EquipmentData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.util.UUID;

public class DisguisePlayer extends DisguiseData {

    public PlayerDisguiseData data;
    public boolean showScoreboard = true;

    public DisguisePlayer(Player p, String nome) {
        super(p);
        data = new PlayerDisguiseData(p, nome);

    }

    @Override
    public void restore(DisguiseData next) {
        removeFromTab(true);
        if (next == null || !(next instanceof DisguisePlayer)) {
            getPlayer().setDisplayName(getPlayer().getName());
            nms().addToTabList(getPlayer());
        }
        respawn(true, next == null, (next == null || !(next instanceof DisguisePlayer)), data == null || !(next instanceof EquipmentData || next instanceof DisguisePlayer));
    }

    @Override
    public void disguise(DisguiseData last) {
        if (last == null || !(last instanceof DisguisePlayer)) {
            removeFromTab(false);
        }
        nms().addToTabList(getPlayer());
        getPlayer().setDisplayName(data.nome);
        respawn(last == null, true, true, true);
    }

    public void removeFromTab(boolean disguised) {
        Object other = nms().removeFromTabList(data.getUUID(), data.nome);
        Object self = nms().removeFromTabList(getPlayer().getUniqueId(), data.nome);
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p != getPlayer() && disguised) {
                nms().sendPacket(p, other);
            } else {
                nms().sendPacket(p, self);
            }
        }
    }

    public void respawn(boolean destroyb, boolean spawnb, boolean reload, boolean equipment) {
        Object destroy = nms().buildDestroy(getPlayer().getEntityId());
        Object spawn = nms().buildSpawnPlayer(getPlayer());
        for (Player p : getSeeing()) {
            if (destroyb)
                nms().sendPacket(p, destroy);
            if (spawnb)
                nms().sendPacket(p, spawn);
            if (equipment)
                showPlayerEquipment(p);
        }
        if (reload)
            nms().reloadPlayer(getPlayer());
    }

    public void showPlayerEquipment(Player p) {
        sendEquipmentPacket(p, EquipmentSlot.HAND, getPlayer().getItemInHand());
        sendEquipmentPacket(p, EquipmentSlot.HEAD, getPlayer().getInventory().getHelmet());
        sendEquipmentPacket(p, EquipmentSlot.CHEST, getPlayer().getInventory().getChestplate());
        sendEquipmentPacket(p, EquipmentSlot.LEGS, getPlayer().getInventory().getLeggings());
        sendEquipmentPacket(p, EquipmentSlot.FEET, getPlayer().getInventory().getBoots());
    }

    private void sendEquipmentPacket(Player p, EquipmentSlot slot, ItemStack it) {
        int id = -getPlayer().getEntityId();
        DisguiseModule.nms.sendPacket(p, DisguiseModule.nms.buildEquipment(id, slot.ordinal(), it));
    }

    GameProfile cache;

    public GameProfile create(boolean others) {

        if (others && cache != null) {
            return cache;
        }
        GameProfile profile;
        if (others) {
            profile = new GameProfile(data.uid, data.nome);
        } else {
            profile = new GameProfile(getPlayer().getUniqueId(), data.nome);
        }

        try {
            Field fProperties = profile.getClass().getDeclaredField("properties");
            fProperties.setAccessible(true);
            Field name = profile.getClass().getDeclaredField("name");
            name.setAccessible(true);
            try {
                name.set(profile, data.nome);
                fProperties.set(profile, SkinDownloader.getTexture(data.nome));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        if (cache == null && others) {
            cache = profile;
        }

        return profile;
    }

    public class PlayerDisguiseData {
        String nome;
        Player p;
        UUID uid;

        public PlayerDisguiseData(Player p, String nome) {
            this.p = p;
            this.nome = nome;

            uid = UUID.randomUUID();
        }

        public String getNome() {
            return nome;
        }

        public UUID getUUID() {
            return uid;
        }
    }

}
