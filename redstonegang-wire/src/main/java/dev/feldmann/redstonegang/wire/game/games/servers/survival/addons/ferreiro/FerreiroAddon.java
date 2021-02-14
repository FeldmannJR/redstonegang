package dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.ferreiro;

import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.common.player.permissions.GroupOption;
import dev.feldmann.redstonegang.common.player.permissions.PermissionDescription;
import dev.feldmann.redstonegang.common.utils.Cooldown;
import dev.feldmann.redstonegang.common.utils.formaters.time.TimeUtils;
import dev.feldmann.redstonegang.wire.game.base.addons.server.economy.EconomyAddon;
import dev.feldmann.redstonegang.wire.game.base.apis.configurablemap.ConfigurableMapAPI;
import dev.feldmann.redstonegang.wire.game.base.apis.configurablemap.MapConfigurable;
import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import dev.feldmann.redstonegang.wire.game.base.objects.annotations.AConfig;
import dev.feldmann.redstonegang.wire.game.base.objects.annotations.Dependencies;
import dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.ferreiro.menus.FerreiroMenu;
import dev.feldmann.redstonegang.wire.utils.items.InventoryHelper;
import dev.feldmann.redstonegang.wire.utils.items.ItemUtils;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Dependencies(apis = ConfigurableMapAPI.class, hard = EconomyAddon.class)
public class FerreiroAddon extends Addon {

    public List<FerreiroCraft> ferreiroItems = new ArrayList();
    public List<ItemStack> blocked = new ArrayList<>();

    @AConfig
    public int precoReparo = 20;

    private HashMap<Integer, FerreiroCache> cache = new HashMap<>();

    @AConfig
    public int maxReparos = 10;

    @AConfig
    public int multiplicadorPorReparo = 2;

    FerreiroDB db;
    long HORA = 1000 * 60 * 60;

    @AConfig
    public long tempoCraftDiamante = HORA * 4;

    @AConfig
    public int precoCraftDiamante = 100;

    @MapConfigurable
    NPC ferreiro = null;

    Cooldown clickFerreiro = new Cooldown(2000);

    private PermissionDescription CAN_CRAFT_WITHOUT;
    public PermissionDescription CAN_DISENCHANT;

    private GroupOption MAX_ITEMS;
    private GroupOption REPAIR_SPEED_MULTIPLIER;

    @Override
    public void onEnable() {
        this.db = new FerreiroDB();

        addDiamante(1, Material.DIAMOND_SWORD, new ItemStack(Material.DIAMOND, 2), new ItemStack(Material.WOOD, 32));
        addDiamante(1, Material.DIAMOND_PICKAXE, new ItemStack(Material.DIAMOND, 3), new ItemStack(Material.WOOD, 32));
        addDiamante(1, Material.DIAMOND_AXE, new ItemStack(Material.DIAMOND, 3), new ItemStack(Material.WOOD, 32));
        addDiamante(1, Material.DIAMOND_HOE, new ItemStack(Material.DIAMOND, 2), new ItemStack(Material.WOOD, 32));
        addDiamante(1, Material.DIAMOND_SPADE, new ItemStack(Material.DIAMOND, 1), new ItemStack(Material.WOOD, 32));

        addDiamante(1, Material.DIAMOND_BOOTS, new ItemStack(Material.DIAMOND, 4), new ItemStack(Material.IRON_BLOCK, 1));
        addDiamante(1.5, Material.DIAMOND_LEGGINGS, new ItemStack(Material.DIAMOND, 7), new ItemStack(Material.IRON_BLOCK, 2));
        addDiamante(1.5, Material.DIAMOND_CHESTPLATE, new ItemStack(Material.DIAMOND, 8), new ItemStack(Material.IRON_BLOCK, 3));
        addDiamante(1, Material.DIAMOND_HELMET, new ItemStack(Material.DIAMOND, 5), new ItemStack(Material.IRON_BLOCK, 1));

        ferreiroItems.add(
                new FerreiroCraft(
                        150,
                        HORA * 12,
                        new ItemStack(Material.GOLDEN_APPLE, 8, (byte) 1),
                        new ItemStack(Material.GOLDEN_APPLE, 8),
                        new ItemStack(Material.DIAMOND_BLOCK, 8),
                        new ItemStack(Material.EMERALD_BLOCK, 8),
                        new ItemStack(Material.GOLD_BLOCK, 64)
                )
        );
        blocked.add(new ItemStack(Material.GOLDEN_APPLE, 1, (byte) 1));
        MAX_ITEMS = new GroupOption("Ferreiro max itens", "ferreiro_maxItems", "maximo de itens ao mesmo tempo no ferreriro", 1);
        REPAIR_SPEED_MULTIPLIER = new GroupOption("Multiplicador do tempo de reparo", "ferreiro_speedMultiplier", "Multiplicador do tempo que irá reparar o item", 1);
        CAN_CRAFT_WITHOUT = new PermissionDescription("Não precisa do ferreiro", generatePermission("cancraftwithout"), "Não precisa usar o ferreiro pra craftar itens de diamantes");
        CAN_DISENCHANT = new PermissionDescription("Desencantar Itens!", generatePermission("disenchant"), "Pode desencantar itens no ferreiro" +
                "");
        addOption(MAX_ITEMS, REPAIR_SPEED_MULTIPLIER, CAN_CRAFT_WITHOUT);
    }


    public void addDiamante(double horas, Material result, ItemStack... need) {
        addCraft(precoCraftDiamante, (long) (horas * HORA), result, need);
    }

    public void addCraft(int preco, long tempo, Material result, ItemStack... need) {
        ferreiroItems.add(new FerreiroCraft(preco, tempo, new ItemStack(result), need));

    }

    public int getMaxItems(User pl) {
        double option = pl.getOption(MAX_ITEMS);
        if (option < 0) return 0;
        return (int) option;
    }


    public boolean canRepair(ItemStack it) {

        int reparada = getReparada(it);
        if (reparada >= maxReparos) {
            return false;
        }
        if (it.getType().getMaxDurability() == 0) {
            return false;
        }
        short metade = (short) (it.getType().getMaxDurability() / 2);

        return it.getDurability() >= metade;

    }

    public FerreiroCache getCache(Player p) {
        int pid = getPlayerId(p);
        if (!cache.containsKey(pid)) {
            cache.put(pid, new FerreiroCache(db.loadPlayer(pid)));
        }
        return cache.get(pid);
    }


    public int getReparada(ItemStack it) {
        String lore = ItemUtils.findLore(it, "Reparos: ", true);
        if (lore == null) {
            return 0;
        }
        lore = lore.split(":")[1].trim();
        try {
            return Integer.valueOf(lore);
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    public void setReparos(ItemStack it, int reparos) {
        ItemUtils.removeLoreStartsWith(it, "Reparos: ", true);
        ItemUtils.addLore(it, "§cReparos: §f" + reparos);
    }


    private FerreiroItem repara(ItemStack item, int player, long tempo) {
        item = item.clone();
        item.setDurability((short) 0);
        FerreiroItem fitem = new FerreiroItem(-1, item, FerreiroItem.REPARAR, player, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis() + tempo));
        db.save(fitem);
        return fitem;

    }

    public void craft(Player p, FerreiroCraft craft) {

        for (ItemStack it : craft.getPrecisa()) {
            if (!InventoryHelper.inventoryContains(p.getInventory(), it, it.getAmount())) {
                C.error(p, "Você não tem %% para dar para o %%!", it, getNpcName());
                return;
            }
        }
        FerreiroCache cache = getCache(p);
        int max = getMaxItems(getUser(p));
        if (max != -1 && cache.itens.size() >= max) {
            C.error(p, "Você só pode deixar %% itens com o %% ao mesmo tempo!", max, getNpcName());
            return;
        }
        if (!a(EconomyAddon.class).hasWithMessage(p, craft.preco)) {
            return;
        }
        a(EconomyAddon.class).remove(p, craft.preco);
        for (ItemStack it : craft.getPrecisa()) {
            InventoryHelper.removeInventoryItemsAndReturn(p.getInventory(), it, it.getAmount());
        }
        FerreiroItem item = crafta(craft.getResult().clone(), getPlayerId(p), craft.tempo);
        cache.add(item);
        C.info(p, "Volte em %% no %% para pegar seu item!", TimeUtils.millisToString(craft.tempo), getNpcName());
    }

    public long getTempo(long base, Player p) {
        double multipier = getUser(p).getOption(this.REPAIR_SPEED_MULTIPLIER);
        if (multipier <= 0) multipier = 1;
        return (long) (base * multipier);
    }

    public long getTempoReparo(ItemStack it, Player p) {
        long tempo = getTempoReparo(it);
        return getTempo(tempo, p);
    }

    public long getTempoReparo(ItemStack it) {
        if (it.getType() == Material.DIAMOND_CHESTPLATE || it.getType() == Material.IRON_CHESTPLATE) {
            return HORA * 2;
        }
        if (it.getType().name().startsWith("DIAMOND_")) {
            return HORA * 2;
        }
        if (it.getType().name().startsWith("GOLD_")) {
            return HORA / 4;
        }
        if (it.getType().name().startsWith("CHAINMAIL_")) {
            return HORA / 2;
        }
        if (it.getType().name().startsWith("IRON_")) {
            return HORA;
        }
        if (it.getType().name().startsWith("STONE_")) {
            return HORA / 4;
        }
        if (it.getType().name().startsWith("LEATHER")) {
            return HORA / 6;
        }
        if (it.getType() == Material.BOW) {
            return HORA * 2;
        }
        return HORA / 12;
    }

    public boolean repara(Player p, ItemStack it) {

        FerreiroCache cache = getCache(p);
        int max = getMaxItems(getUser(p));
        if (max != -1 && cache.itens.size() >= max) {
            C.error(p, "Você só pode deixar %% itens com o %% ao mesmo tempo!", max, getNpcName());
            return false;
        }
        it = it.clone();
        setReparos(it, getReparada(it) + 1);
        FerreiroItem item = repara(it, getPlayerId(p), getTempoReparo(it, p));
        cache.add(item);
        C.info(p, "Volte em %% no %% para pegar seu item!", TimeUtils.millisToString(getTempoReparo(it)), getNpcName());
        return true;
    }

    public FerreiroItem crafta(ItemStack item, int player, long tempo) {
        FerreiroItem fitem = new FerreiroItem(-1, item, FerreiroItem.CRIAR, player, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis() + tempo));
        db.save(fitem);
        return fitem;
    }

    public String getNpcName() {
        return "Forjador";
    }

    public boolean retiraItem(Player p, FerreiroItem id) {
        if (id.retirado) return false;
        if (InventoryHelper.getFreeSlots(p) < 1) {
            C.error(p, "Você não tem espaço no inventário para retirar o item!");
            return false;
        }
        p.getInventory().addItem(id.item);
        db.delete(id);
        getCache(p).remove(id.id);
        id.retirado = true;
        C.info(p, "O item %% foi adicionado ao seu inventário!", id.item);
        return true;
    }

    @EventHandler
    public void rightClick(NPCRightClickEvent ev) {

        if (ev.getNPC() == ferreiro) {
            if (clickFerreiro.isCooldownWithMessage(ev.getClicker().getUniqueId())) {
                return;
            }
            clickFerreiro.addCooldown(ev.getClicker().getUniqueId());
            new FerreiroMenu(ev.getClicker(), this).open(ev.getClicker());
        }
    }

    @EventHandler
    public void quit(PlayerQuitEvent ev) {
        cache.remove(getPlayerId(ev.getPlayer()));
    }


    @EventHandler
    public void prepareCraft(PrepareItemCraftEvent ev) {
        HumanEntity pl = ev.getView().getPlayer();
        ItemStack result = ev.getRecipe().getResult();
        for (ItemStack itemStack : blocked) {
            if (itemStack.isSimilar(result)) {
                ev.getInventory().setResult(null);
                C.error(pl, "Você não tem a habilidade necessária para craftar este item!");
                C.info(pl, "Talvez o %% possa ajudar você!", getNpcName());
                return;
            }
        }
        if (!pl.hasPermission(this.CAN_CRAFT_WITHOUT.getKey())) {
            for (FerreiroCraft craft : ferreiroItems) {
                if (craft.getResult().isSimilar(result)) {
                    ev.getInventory().setResult(null);
                    C.error(pl, "Você não pode craftar este item, vá no %% para ele fazer pra você!", getNpcName());
                    C.info(pl, "Vips não precisam ir no %% !", getNpcName());
                }
            }
        }
    }


    public Cooldown drop = new Cooldown(1000);

    @EventHandler
    public void drop(PlayerDropItemEvent ev) {
        if (drop.isCooldown(ev.getPlayer().getUniqueId()))
            ev.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void AoRepararNaBigorna(InventoryClickEvent ev) {
        if (ev.isCancelled()) {
            return;
        }
        Player p = (Player) ev.getWhoClicked();
        Inventory inv = ev.getInventory();
        if (!(inv instanceof AnvilInventory)) {
            return;
        }
        AnvilInventory anvil = (AnvilInventory) inv;
        InventoryView view = ev.getView();
        int rawSlot = ev.getRawSlot();
        if (rawSlot != view.convertSlot(rawSlot)) {
            return;
        }
        if (rawSlot != 2) {
            return;
        }
        ItemStack[] items = anvil.getContents();
        ItemStack item1 = items[0];
        ItemStack item2 = items[1];
        ItemStack item3 = ev.getCurrentItem();
        if (item3 != null && item3.getType() != Material.AIR) {
            if (item2 == null || !item2.getType().equals(Material.ENCHANTED_BOOK)) {
                drop.addCooldown(p.getUniqueId());
                ev.setResult(Event.Result.DENY);
                ev.setCancelled(true);
                C.error(p, "Você só pode usar a bigorna para juntar livros de encantamento!");
                C.info(p, "Para reparar seus itens vá no %% !", getNpcName());
            } else {
                fixName(item1, item3);
            }

        }

    }

    public void fixName(ItemStack original, ItemStack edit) {
        String originalName = null;
        if (original.hasItemMeta() && original.getItemMeta().hasDisplayName()) {
            originalName = original.getItemMeta().getDisplayName();
        }
        String editName = null;
        if (edit.hasItemMeta() && edit.getItemMeta().hasDisplayName()) {
            editName = edit.getItemMeta().getDisplayName();
        }
        if (editName == null && originalName == null) {
            return;
        }
        if (original == null) {
            ItemUtils.setItemName(edit, null);
            return;
        }

        if (!original.equals(editName)) {
            ItemUtils.setItemName(edit, originalName);
        }
    }

}