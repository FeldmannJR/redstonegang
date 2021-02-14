package dev.feldmann.redstonegang.wire.game.base.addons.both.stats;

import dev.feldmann.redstonegang.wire.utils.items.ItemUtils;
import dev.feldmann.redstonegang.wire.utils.items.SpawnEggs;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public class Stats {

    //Geral
    public static PlayerStat MINUTES_ONLINE = s("all_minutes_online", Material.WATCH);


    //Survival
    public static PlayerStat SURV_MINUTES_ONLINE = s("surv_minutes_online", Material.WATCH);
    public static PlayerStat SURV_MINUTES_ONLINE_MINERAR = s("surv_minutes_online_minerar", Material.WATCH);
    public static PlayerStat SURV_MINUTES_ONLINE_TERRENOS = s("surv_minutes_online_terrenos", Material.WATCH);
    public static PlayerStat SURV_BLOCKS_WALKED = s("surv_blocks_walked", Material.GRASS);

    //Kills
    public static PlayerStat SURV_DEATH_PLAYER = s("surv_death_player", ItemUtils.getHead("Steve"));
    public static PlayerStat SURV_DEATH_MOBS = s("surv_death_mobs", new ItemStack(Material.SKULL_ITEM, 1, (short) 4));
    public static PlayerStat SURV_DEATH_OTHER = s("surv_death_other", Material.LAVA);

    public static PlayerStat SURV_KILL_PLAYER = s("surv_kill_player", ItemUtils.getHead("Steve"));

    public static PlayerStatKill SURV_KILL_SKELETON = new PlayerStatKill("surv_kill_skeleton", SpawnEggs.getItemStack(EntityType.SKELETON), EntityType.SKELETON);
    public static PlayerStatKill SURV_KILL_WITHER_SKELETON = new PlayerStatKill("surv_kill_wither_skeleton", SpawnEggs.getItemStack(EntityType.SKELETON), EntityType.SKELETON);
    public static PlayerStatKill SURV_KILL_SLIME = new PlayerStatKill("surv_kill_slime", SpawnEggs.getItemStack(EntityType.SLIME), EntityType.SLIME);
    public static PlayerStatKill SURV_KILL_CAVE_SPIDER = new PlayerStatKill("surv_kill_cave_spider", SpawnEggs.getItemStack(EntityType.CAVE_SPIDER), EntityType.CAVE_SPIDER);
    public static PlayerStatKill SURV_KILL_CHARGED_CREEPER = new PlayerStatKill("surv_kill_charged_creeper", SpawnEggs.getItemStack(EntityType.CREEPER));
    public static PlayerStatKill SURV_KILL_CREEPER = new PlayerStatKill("surv_kill_creeper", SpawnEggs.getItemStack(EntityType.CREEPER), EntityType.CREEPER);
    public static PlayerStatKill SURV_KILL_WITCH = new PlayerStatKill("surv_kill_witch", SpawnEggs.getItemStack(EntityType.WITCH), EntityType.WITCH);
    public static PlayerStatKill SURV_KILL_SPIDER = new PlayerStatKill("surv_kill_spider", SpawnEggs.getItemStack(EntityType.SPIDER), EntityType.SPIDER);
    public static PlayerStatKill SURV_KILL_ZOMBIE = new PlayerStatKill("surv_kill_zombie", SpawnEggs.getItemStack(EntityType.ZOMBIE), EntityType.ZOMBIE);
    public static PlayerStatKill SURV_KILL_SILVERFISH = new PlayerStatKill("surv_kill_silverfish", SpawnEggs.getItemStack(EntityType.SILVERFISH), EntityType.SILVERFISH);
    public static PlayerStatKill SURV_KILL_MAGMA_CUBE = new PlayerStatKill("surv_kill_magma_cube", SpawnEggs.getItemStack(EntityType.MAGMA_CUBE), EntityType.MAGMA_CUBE);
    public static PlayerStatKill SURV_KILL_PIG_ZOMBIE = new PlayerStatKill("surv_kill_pig_zombie", SpawnEggs.getItemStack(EntityType.PIG_ZOMBIE), EntityType.PIG_ZOMBIE);
    public static PlayerStatKill SURV_KILL_GHAST = new PlayerStatKill("surv_kill_ghast", SpawnEggs.getItemStack(EntityType.GHAST), EntityType.GHAST);
    public static PlayerStatKill SURV_KILL_ENDERMAN = new PlayerStatKill("surv_kill_enderman", SpawnEggs.getItemStack(EntityType.ENDERMAN), EntityType.ENDERMAN);
    public static PlayerStatKill SURV_KILL_BLAZE = new PlayerStatKill("surv_kill_blaze", SpawnEggs.getItemStack(EntityType.BLAZE), EntityType.BLAZE);
    public static PlayerStatKill SURV_KILL_GUARDIAN = new PlayerStatKill("surv_kill_guardian", SpawnEggs.getItemStack(EntityType.GUARDIAN), EntityType.GUARDIAN);
    public static PlayerStatKill SURV_KILL_BAT = new PlayerStatKill("surv_kill_bat", SpawnEggs.getItemStack(EntityType.BAT), EntityType.BAT);
    public static PlayerStatKill SURV_KILL_WITHER = new PlayerStatKill("surv_kill_wither", SpawnEggs.getItemStack(EntityType.WITHER), EntityType.WITHER);
    //Break Block


    //Ores
    public static PlayerStatBreak SURV_BREAK_DIAMOND = new PlayerStatBreak("surv_break_diamond", new ItemStack(Material.DIAMOND_ORE), new MaterialData(Material.DIAMOND_ORE));
    public static PlayerStatBreak SURV_BREAK_IRON = new PlayerStatBreak("surv_break_iron", new ItemStack(Material.IRON_ORE), new MaterialData(Material.IRON_ORE));
    public static PlayerStatBreak SURV_BREAK_GOLD = new PlayerStatBreak("surv_break_gold", new ItemStack(Material.GOLD_ORE), new MaterialData(Material.GOLD_ORE));
    public static PlayerStatBreak SURV_BREAK_REDSTONE = new PlayerStatBreak("surv_break_redstone", new ItemStack(Material.REDSTONE_ORE), new MaterialData(Material.GLOWING_REDSTONE_ORE));
    public static PlayerStatBreak SURV_BREAK_COAL = new PlayerStatBreak("surv_break_coal", new ItemStack(Material.COAL_ORE), new MaterialData(Material.COAL_ORE));
    public static PlayerStatBreak SURV_BREAK_EMERALD = new PlayerStatBreak("surv_break_emerald", new ItemStack(Material.EMERALD_ORE), new MaterialData(Material.EMERALD_ORE));
    public static PlayerStatBreak SURV_BREAK_LAPIS = new PlayerStatBreak("surv_break_lapis", new ItemStack(Material.LAPIS_ORE), new MaterialData(Material.LAPIS_ORE));
    public static PlayerStatBreak SURV_BREAK_QUARTZ = new PlayerStatBreak("surv_break_quartz", new ItemStack(Material.QUARTZ_ORE), new MaterialData(Material.QUARTZ_ORE));
    //Farm Stuff
    public static PlayerStatBreak SURV_BREAK_MELON = new PlayerStatBreak("surv_break_melon", new ItemStack(Material.MELON_BLOCK), new MaterialData(Material.MELON_BLOCK));
    public static PlayerStatBreak SURV_BREAK_PUMPKIN = new PlayerStatBreak("surv_break_pumpkin", new ItemStack(Material.PUMPKIN), new MaterialData(Material.PUMPKIN));
    public static PlayerStatBreak SURV_BREAK_CARROT = new PlayerStatBreak("surv_break_carrot", new ItemStack(Material.CARROT_ITEM), new MaterialData(Material.CARROT, (byte) 7));
    public static PlayerStatBreak SURV_BREAK_POTATO = new PlayerStatBreak("surv_break_potato", new ItemStack(Material.POTATO_ITEM), new MaterialData(Material.POTATO, (byte) 7));
    public static PlayerStatBreak SURV_BREAK_WHEAT = new PlayerStatBreak("surv_break_wheat", new ItemStack(Material.WHEAT), new MaterialData(Material.CROPS, (byte) 7));
    public static PlayerStatBreak SURV_BREAK_CACTUS = new PlayerStatBreak("surv_break_cactus", new ItemStack(Material.CACTUS));
    public static PlayerStatBreak SURV_BREAK_SUGAR_CANE = new PlayerStatBreak("surv_break_sugar_cane", new ItemStack(Material.SUGAR_CANE));
    public static PlayerStatBreak SURV_BREAK_NETHERWART = new PlayerStatBreak("surv_break_nether_wart", new ItemStack(Material.NETHER_STALK), new MaterialData(Material.NETHER_WARTS, (byte) 3));

    //Random
    public static PlayerStatBreak SURV_BREAK_LOG = new PlayerStatBreak("surv_break_log", new ItemStack(Material.LOG), new MaterialData(Material.LOG), new MaterialData(Material.LOG_2));
    public static PlayerStatBreak SURV_BREAK_STONE = new PlayerStatBreak("surv_break_stone", new ItemStack(Material.STONE), new MaterialData(Material.STONE));


    private static PlayerStat s(String uniqueId, ItemStack icon) {
        return new PlayerStat(uniqueId, icon);
    }

    private static PlayerStat s(String uniqueId, Material m) {
        return s(uniqueId, new ItemStack(m));
    }


}
