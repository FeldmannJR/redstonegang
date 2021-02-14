package dev.feldmann.redstonegang.wire.modulos.disguise;

import dev.feldmann.redstonegang.wire.modulos.disguise.types.DisguiseRabbit;
import dev.feldmann.redstonegang.wire.modulos.disguise.types.monsters.DisguiseBlaze;
import dev.feldmann.redstonegang.wire.modulos.disguise.types.monsters.DisguiseCaveSpider;
import dev.feldmann.redstonegang.wire.modulos.disguise.types.monsters.DisguiseCreeper;
import dev.feldmann.redstonegang.wire.modulos.disguise.types.monsters.DisguiseEnderman;
import dev.feldmann.redstonegang.wire.modulos.disguise.types.DisguiseIronGolem;
import dev.feldmann.redstonegang.wire.modulos.disguise.types.monsters.DisguiseSlime;
import dev.feldmann.redstonegang.wire.modulos.disguise.types.monsters.DisguiseSpider;
import dev.feldmann.redstonegang.wire.modulos.disguise.types.DisguiseVillager;
import dev.feldmann.redstonegang.wire.modulos.disguise.types.monsters.DisguiseZombiePigman;
import dev.feldmann.redstonegang.wire.modulos.disguise.types.base.DisguiseData;
import dev.feldmann.redstonegang.wire.modulos.disguise.types.monsters.DisguiseSkeleton;
import dev.feldmann.redstonegang.wire.modulos.disguise.types.monsters.DisguiseZombie;
import org.bukkit.entity.Player;
import java.lang.reflect.InvocationTargetException;

public enum DisguiseTypes {

    ZOMBIE(DisguiseZombie.class),
    CREEPER(DisguiseCreeper.class),
    SKELETON(DisguiseSkeleton.class),
    SPIDER(DisguiseSpider.class),
    CAVESPIDER(DisguiseCaveSpider.class),
    SLIME(DisguiseSlime.class),
    VILLAGER(DisguiseVillager.class),
    PIGZOMBIE(DisguiseZombiePigman.class),
    IRON_GOLEM(DisguiseIronGolem.class),
    ENDERMAN(DisguiseEnderman.class),
    BLAZE(DisguiseBlaze.class),
    RABBIT(DisguiseRabbit.class);

    Class<? extends DisguiseData> classe;

    DisguiseTypes(Class<? extends DisguiseData> classe) {
        this.classe = classe;
    }

    public DisguiseData createData(Player p) {
        try {
            return classe.getConstructor(Player.class).newInstance(p);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
