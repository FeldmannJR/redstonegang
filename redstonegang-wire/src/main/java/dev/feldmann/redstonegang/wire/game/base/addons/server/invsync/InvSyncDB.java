package dev.feldmann.redstonegang.wire.game.base.addons.server.invsync;

import dev.feldmann.redstonegang.common.db.Database;
import dev.feldmann.redstonegang.wire.game.base.database.addons.tables.records.PlayerInventoriesRecord;
import dev.feldmann.redstonegang.wire.utils.ItemSerializer;
import dev.feldmann.redstonegang.wire.utils.location.BungeeLocation;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jooq.Field;

import static dev.feldmann.redstonegang.wire.game.base.database.addons.Tables.PLAYER_INVENTORIES;

import java.util.*;

public class InvSyncDB extends Database {
    @Override
    public void createTables() {
        safeExecute("CREATE TABLE IF NOT EXISTS `player_inventories` (" +
                " `id` INTEGER PRIMARY KEY," +

                " `invContents` blob NOT NULL," +
                " `armorContents` blob NOT NULL," +
                " `enderContents` blob NOT NULL," +
                " `selectedItem` INTEGER, " +

                " `potions` varchar(200)," +
                " `health` double NOT NULL DEFAULT '20'," +
                " `foodLevel` integer NOT NULL DEFAULT 20," +
                " `foodExhaustion` float NOT NULL DEFAULT 0 ," +
                " `foodSaturation` float NOT NULL DEFAULT 5," +

                " `exp` float NOT NULL DEFAULT '0'," +
                " `level` int(5) DEFAULT '0'," +

                "`location` VARCHAR(200) DEFAULT NULL" +
                ") ENGINE=InnoDB DEFAULT CHARSET=latin1;");
        dsl().execute("ALTER TABLE `player_inventories` ADD COLUMN IF NOT EXISTS `teleport_location` VARCHAR(200) DEFAULT NULL");
        dsl().execute("ALTER TABLE `player_inventories` ADD COLUMN IF NOT EXISTS `gamemode` INTEGER DEFAULT 0");
    }

    public InvSyncDB(String database) {
        super(database);
    }

    public PlayerInventoriesRecord loadInv(int pid) {
        return dsl().selectFrom(PLAYER_INVENTORIES).where(PLAYER_INVENTORIES.ID.eq(pid)).fetchOne();
    }


    public PlayerInventoriesRecord savePlayer(Player p, int pid, BungeeLocation loc, BungeeLocation teleport, boolean update) {


        HashMap<Field, Object> vals = new HashMap<>();
        vals.put(PLAYER_INVENTORIES.ID, pid);
        vals.put(PLAYER_INVENTORIES.INVCONTENTS, ItemSerializer.serializeItemStacks(p.getInventory().getContents()));
        vals.put(PLAYER_INVENTORIES.ARMORCONTENTS, ItemSerializer.serializeItemStacks(p.getInventory().getArmorContents()));
        vals.put(PLAYER_INVENTORIES.ENDERCONTENTS, ItemSerializer.serializeItemStacks(p.getEnderChest().getContents()));
        vals.put(PLAYER_INVENTORIES.SELECTEDITEM, p.getInventory().getHeldItemSlot());
        vals.put(PLAYER_INVENTORIES.POTIONS, playerEffectToString(p.getActivePotionEffects()));
        vals.put(PLAYER_INVENTORIES.HEALTH, p.getHealth());
        vals.put(PLAYER_INVENTORIES.FOODLEVEL, p.getFoodLevel());
        vals.put(PLAYER_INVENTORIES.FOODEXHAUSTION, Double.valueOf(p.getExhaustion()));
        vals.put(PLAYER_INVENTORIES.FOODSATURATION, Double.valueOf(p.getSaturation()));
        vals.put(PLAYER_INVENTORIES.EXP, Double.valueOf(p.getExp()));
        vals.put(PLAYER_INVENTORIES.LEVEL, p.getLevel());
        /*
         * Quando um jogador não tem uuid.dat criado o Bukkit retorna GameMode null.
         */
        if (p.getGameMode() != null)
        {
            vals.put(PLAYER_INVENTORIES.GAMEMODE, p.getGameMode().ordinal());
        }
        else
        {
            vals.put(PLAYER_INVENTORIES.GAMEMODE, GameMode.SURVIVAL.ordinal());
        }
        vals.put(PLAYER_INVENTORIES.TELEPORT_LOCATION, teleport == null ? null : teleport.toString());
        if (loc != null) {
            vals.put(PLAYER_INVENTORIES.LOCATION, loc.toString());
        }

        PlayerInventoriesRecord record = dsl().newRecord(PLAYER_INVENTORIES);
        HashMap<Field, Object> clone = new HashMap<>(vals);

        clone.remove(PLAYER_INVENTORIES.ID);
        dsl().insertInto(PLAYER_INVENTORIES)
                .set(vals)
                .onDuplicateKeyUpdate()
                .set(clone)
                .execute();

        for (Field field : vals.keySet()) {
            record.set(field, vals.get(field));
        }
        return record;
    }


    public static String playerEffectToString(Collection<PotionEffect> listPotions) {
        String Effects = "";
        for (PotionEffect ef : listPotions) {
            Effects = Effects + ef.getType().getName() + "," + ef.getDuration() + "," + ef.getAmplifier() + ";";
        }
        if (Effects.length() > 0 && !Effects.equalsIgnoreCase("")) {
            Effects = Effects.substring(0, Effects.length() - 1);
        }
        return Effects;
    }

    public static List<PotionEffect> effectsStringToPlayer(String Effects) {
        if (Effects == null || Effects.equalsIgnoreCase("")) {
            return Arrays.asList();
        }
        List<PotionEffect> list = new ArrayList();
        for (String Effect : Effects.split(";")) {
            if (Effect != null && Effect.contains(",")) {
                list.add(new PotionEffect(PotionEffectType.getByName(Effect.split(",")[0]), Integer.parseInt(Effect.split(",")[1]), Integer.parseInt(Effect.split(",")[2])));
            }
        }
        return list;
    }
}
