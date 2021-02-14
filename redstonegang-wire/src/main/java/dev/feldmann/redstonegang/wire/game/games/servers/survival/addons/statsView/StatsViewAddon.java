package dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.statsView;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.common.utils.formaters.DateUtils;
import dev.feldmann.redstonegang.common.utils.formaters.numbers.NumberUtils;
import dev.feldmann.redstonegang.common.utils.msgs.CenterMessageUtils;
import dev.feldmann.redstonegang.wire.game.base.addons.both.stats.Stats;
import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import dev.feldmann.redstonegang.wire.utils.items.book.BookUtil;
import dev.feldmann.redstonegang.wire.utils.items.book.PageBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class StatsViewAddon extends Addon {


    @Override
    public void onEnable() {
        registerCommand(new StatsCMD(this));
    }

    public void view(Player viewer, int playerId) {
        BookUtil.openBook(createBook(playerId), viewer);
    }

    private ItemStack createBook(int playerId) {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta) book.getItemMeta();

        BookUtil.setPages(meta,
                buildFirstPage(playerId),
                buildGeral(playerId),
                buildMobKills(playerId),
                buildMoreMobKills(playerId),
                buildMoreMobKills2(playerId),
                buildOres(playerId),
                buildFarm(playerId));

        book.setItemMeta(meta);
        return book;
    }

    private String buildFirstPage(int playerId) {
        User pl = RedstoneGang.getPlayer(playerId);

        return new PageBuilder()
                .centerPage("§c§lRedstoneGang")
                .newLine()
                .centerPage("§1" + pl.getDisplayName())
                .centerPage(pl.getPrefix().trim())
                .newLine()
                .centerPage("§4> Registrado <")
                .centerPage(DateUtils.formatDate(pl.getRegistred()))
                .newLine()
                .centerPage("§0► §5§nGeral§0§l ◄")
                .setHover("§fClique aqui para ver\ninformações gerais sobre a conta!")
                .gotoPage(2)
                .newLine()
                .centerPage("§0► §4§nMob Kills§r§0 ◄")
                .gotoPage(3)
                .setHover("§fClique aqui para ver\nquantos mobs você matou!")
                .newLine()
                .centerPage("§0§l► §2§nRecursos§0§l ◄")
                .gotoPage(6)
                .setHover("§fClique aqui para ver\nquantos recursos você\njá coletou!")
                .create();
    }


    private String buildGeral(int playerId) {
        User pl = RedstoneGang.getPlayer(playerId);
        long horas = Stats.SURV_MINUTES_ONLINE.getPoints(playerId) / 60L;
        long mortesMobs = Stats.SURV_DEATH_MOBS.getPoints(playerId);
        long mortesPlayer = Stats.SURV_DEATH_PLAYER.getPoints(playerId);
        long mortesOther = Stats.SURV_DEATH_OTHER.getPoints(playerId);
        long mortesTtl = mortesMobs + mortesPlayer + mortesOther;
        long blocos = Stats.SURV_BLOCKS_WALKED.getPoints(playerId);
        long kills = Stats.SURV_KILL_PLAYER.getPoints(playerId);
        return new PageBuilder()
                .centerPage("§4> Horas Online <")
                .centerPage("" + horas)
                .newLine()
                .centerPage("§4> Blocos Percor. §4<")
                .centerPage(NumberUtils.convertToString(blocos))
                .setHover("§f" + blocos)
                .newLine()
                .centerPage("§4> Kills <")
                .centerPage(kills + "")
                .newLine()
                .centerPage("§4§lMortes")
                .addLine("  §2Mobs: §0" + NumberUtils.convertToString(mortesMobs))
                .addLine("  §1Jogadores: §0" + NumberUtils.convertToString(mortesPlayer))
                .addLine("  §6Outros: §0" + NumberUtils.convertToString(mortesOther))
                .create();
    }

    private String buildMobKills(int playerId) {
        User pl = RedstoneGang.getPlayer(playerId);

        long skeleton = Stats.SURV_KILL_SKELETON.getPoints(playerId);
        long zombies = Stats.SURV_KILL_ZOMBIE.getPoints(playerId);
        long creeper = Stats.SURV_KILL_CREEPER.getPoints(playerId);
        long aranha = Stats.SURV_KILL_SPIDER.getPoints(playerId);
        long enderman = Stats.SURV_KILL_ENDERMAN.getPoints(playerId);
        PageBuilder pb = new PageBuilder()
                .centerPage("§4§lMob Kills 1")
                .newLine()

                .centerPage("§2Esqueletos")
                .centerPage(NumberUtils.convertToString(skeleton))
                .setHover("§f" + skeleton)

                .centerPage("§2Zumbis")
                .centerPage(NumberUtils.convertToString(zombies))
                .setHover("§f" + zombies)

                .centerPage("§2Aranhas")
                .centerPage(NumberUtils.convertToString(aranha))
                .setHover("§f" + aranha)

                .centerPage("§2Creepers")
                .centerPage(NumberUtils.convertToString(creeper))
                .setHover("§f" + creeper)

                .centerPage("§2Endermans")
                .centerPage(NumberUtils.convertToString(enderman))
                .setHover("§f" + enderman)
                .newLine()
                .newLine();
        buildPages(pb, 1);
        return pb.create();

    }

    private PageBuilder buildPages(PageBuilder pb, int selected) {
        pb.addText(CenterMessageUtils.restBook("[1] §l[2] [3]"));
        String sel = "§1§l";
        String other = "§9§n";
        int startPage = 3;
        for (int x = 1; x <= 3; x++) {
            if (x != 1) {
                pb.addText(" ");
            }
            if (selected == x) {
                pb.addText(sel);
            } else {
                pb.addText(other);
            }
            pb.addText("[" + x + "]§r");
            pb.gotoPage(startPage + x - 1);
            if (selected != x) {
                pb.setHover("§fClique para ir para\n§fa pagina " + x + " !");
            }
        }
        return pb;
    }

    private String buildMoreMobKills(int pId) {
        long slime = Stats.SURV_KILL_SLIME.getPoints(pId);
        long cave_spider = Stats.SURV_KILL_CAVE_SPIDER.getPoints(pId);
        long witch = Stats.SURV_KILL_WITCH.getPoints(pId);
        long silverfish = Stats.SURV_KILL_SILVERFISH.getPoints(pId);
        long bat = Stats.SURV_KILL_BAT.getPoints(pId);


        PageBuilder pb = new PageBuilder()
                .centerPage("§4§lMob Kills 2")
                .newLine()

                .centerPage("§2Slimes")
                .centerPage(NumberUtils.convertToString(slime))
                .setHover("§f" + slime)

                .centerPage("§2Aranhas Da Caverna")
                .centerPage(NumberUtils.convertToString(cave_spider))
                .setHover("§f" + cave_spider)

                .centerPage("§2Bruxas")
                .centerPage(NumberUtils.convertToString(witch))
                .setHover("§f" + witch)

                .centerPage("§2Traças")
                .centerPage(NumberUtils.convertToString(silverfish))
                .setHover("§f" + silverfish)

                .centerPage("§2Morcegos")
                .centerPage(NumberUtils.convertToString(bat))
                .setHover("§f" + bat)


                .newLine()
                .newLine();
        buildPages(pb, 2);
        return pb.create();
    }

    private String buildMoreMobKills2(int pId) {
        long skeleton_wither = Stats.SURV_KILL_WITHER_SKELETON.getPoints(pId);
        long pigzombie = Stats.SURV_KILL_PIG_ZOMBIE.getPoints(pId);
        long magma = Stats.SURV_KILL_MAGMA_CUBE.getPoints(pId);
        long blaze = Stats.SURV_KILL_BLAZE.getPoints(pId);
        long ghast = Stats.SURV_KILL_GHAST.getPoints(pId);


        PageBuilder pb = new PageBuilder()
                .centerPage("§4§lMob Kills 3")
                .newLine()

                .centerPage("§2Esqueletos Wither")
                .centerPage(NumberUtils.convertToString(skeleton_wither))
                .setHover("§f" + skeleton_wither)

                .centerPage("§2Homem-porco Zumbi")
                .centerPage(NumberUtils.convertToString(pigzombie))
                .setHover("§f" + pigzombie)

                .centerPage("§2Cubos de Magma")
                .centerPage(NumberUtils.convertToString(magma))
                .setHover("§f" + magma)

                .centerPage("§2Blazes")
                .centerPage(NumberUtils.convertToString(blaze))
                .setHover("§f" + blaze)

                .centerPage("§2Ghasts")
                .centerPage(NumberUtils.convertToString(ghast))
                .setHover("§f" + ghast)


                .newLine()
                .newLine();
        buildPages(pb, 3);
        return pb.create();
    }

    private String buildOres(int pId) {
        long iron = Stats.SURV_BREAK_IRON.getPoints(pId);
        long gold = Stats.SURV_BREAK_GOLD.getPoints(pId);
        long diamante = Stats.SURV_BREAK_DIAMOND.getPoints(pId);
        long redstone = Stats.SURV_BREAK_REDSTONE.getPoints(pId);
        long esperalda = Stats.SURV_BREAK_EMERALD.getPoints(pId);
        long lapiz = Stats.SURV_BREAK_LAPIS.getPoints(pId);
        long carvao = Stats.SURV_BREAK_COAL.getPoints(pId);
        long quartz = Stats.SURV_BREAK_QUARTZ.getPoints(pId);
        long stone = Stats.SURV_BREAK_STONE.getPoints(pId);
        long madeira = Stats.SURV_BREAK_LOG.getPoints(pId);


        PageBuilder b = new PageBuilder()
                .centerPage("§8§lRecursos 1")
                .newLine()

                .addLine("§5Madeira: §0" + NumberUtils.convertToString(madeira))
                .setHover("§f" + madeira)

                .addLine("§8Pedras: §0" + NumberUtils.convertToString(stone))
                .setHover("§f" + stone)

                .addLine("§7Ferro: §0" + NumberUtils.convertToString(iron))
                .setHover("§f" + iron)

                .addLine("§6Ouro: §0" + NumberUtils.convertToString(gold))
                .setHover("§f" + gold)

                .addLine("§9Lapis: §0" + NumberUtils.convertToString(lapiz))
                .setHover("§f" + lapiz)

                .addLine("§8Carvão: §0" + NumberUtils.convertToString(carvao))
                .setHover("§f" + carvao)

                .addLine("§cRedstone: §0" + NumberUtils.convertToString(redstone))
                .setHover("§f" + redstone)

                .addLine("§bDiamante: §0" + NumberUtils.convertToString(diamante))
                .setHover("§f" + diamante)

                .addLine("§7Quartz: §0" + NumberUtils.convertToString(quartz))
                .setHover("§f" + quartz)

                .addLine("§aEsmeralda: §0" + NumberUtils.convertToString(esperalda))
                .setHover("§f" + esperalda)

                .newLine()

                .centerPage("§9§nMais")
                .setHover("§fVer mais informações\n§fsobre recursos!")
                .gotoPage(7);

        return b.create();
    }

    private String buildFarm(int pId) {
        long trigo = Stats.SURV_BREAK_WHEAT.getPoints(pId);
        long cana = Stats.SURV_BREAK_SUGAR_CANE.getPoints(pId);
        long cactus = Stats.SURV_BREAK_CACTUS.getPoints(pId);
        long batata = Stats.SURV_BREAK_POTATO.getPoints(pId);
        long cenoura = Stats.SURV_BREAK_CARROT.getPoints(pId);
        long melon = Stats.SURV_BREAK_MELON.getPoints(pId);
        long pumpkin = Stats.SURV_BREAK_PUMPKIN.getPoints(pId);
        long netherwart = Stats.SURV_BREAK_NETHERWART.getPoints(pId);

        PageBuilder b = new PageBuilder()
                .centerPage("§8§lRecursos 2")
                .newLine()

                .addLine("§2Trigo: §0" + NumberUtils.convertToString(trigo))
                .setHover("§f" + trigo)

                .addLine("§2Cana: §0" + NumberUtils.convertToString(cana))
                .setHover("§f" + cana)

                .addLine("§2Cactus: §0" + NumberUtils.convertToString(cactus))
                .setHover("§f" + cactus)

                .addLine("§2Batata: §0" + NumberUtils.convertToString(batata))
                .setHover("§f" + batata)

                .addLine("§2Cenoura: §0" + NumberUtils.convertToString(cenoura))
                .setHover("§f" + cenoura)

                .addLine("§2Melão: §0" + NumberUtils.convertToString(melon))
                .setHover("§f" + melon)

                .addLine("§2Abóbora: §0" + NumberUtils.convertToString(pumpkin))
                .setHover("§f" + pumpkin)

                .addLine("§2Fungo: §0" + NumberUtils.convertToString(netherwart))
                .setHover("§f" + netherwart)


                .newLine()

                .centerPage("§9§nVoltar")
                .setHover("§fClique para voltar")
                .gotoPage(6);

        return b.create();
    }


}
