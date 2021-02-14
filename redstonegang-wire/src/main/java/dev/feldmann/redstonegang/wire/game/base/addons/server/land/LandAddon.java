package dev.feldmann.redstonegang.wire.game.base.addons.server.land;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.common.player.config.types.BooleanConfig;
import dev.feldmann.redstonegang.common.player.config.types.IntegerConfig;
import dev.feldmann.redstonegang.common.player.permissions.GroupOption;
import dev.feldmann.redstonegang.common.player.permissions.PermissionDescription;
import dev.feldmann.redstonegang.common.player.permissions.PermissionValue;
import dev.feldmann.redstonegang.common.utils.RandomUtils;
import dev.feldmann.redstonegang.wire.game.base.addons.server.economy.EconomyAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.cmds.CmdTerreno;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.cmds.admin.CmdTerrenoAdmin;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.listener.InformativeListener;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.listener.ProtectionListener;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.listener.PvPListener;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.listener.RandomTeleportListener;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.properties.PlayerProperties;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.properties.PlayerProperty;
import dev.feldmann.redstonegang.wire.game.base.apis.worldguard.WorldGuardAPI;
import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import dev.feldmann.redstonegang.wire.game.base.objects.annotations.Dependencies;
import dev.feldmann.redstonegang.wire.integrations.CustomFlags;
import dev.feldmann.redstonegang.wire.plugin.events.update.UpdateEvent;
import dev.feldmann.redstonegang.wire.plugin.events.update.UpdateType;
import dev.feldmann.redstonegang.wire.utils.hitboxes.Hitbox2D;
import dev.feldmann.redstonegang.wire.utils.location.LocUtils;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Dependencies(apis = WorldGuardAPI.class)
public class LandAddon extends Addon {

    public static final double PRECO_POR_BLOCO = 1.5;
    public static final int ESPACO_ENTRE = 10;
    public static final int MAX_SIZE = 100;

    public static final String TERRENOS_ADMIN = "rg.terrenos.staff";
    public static final String TERRENOS_WILD = "rg.terrenos.wild";

    public BooleanConfig BYPASS_CONFIG;
    public BooleanConfig BYPASS_WILD_CONFIG;
    public BooleanConfig SHOW_ENTER_TITLE;
    private IntegerConfig FREE_LAND_CLAIMED;
    public static GroupOption MAX_TERRENOS = new GroupOption("maximo de terrenos", "terrenos_maxTerrenos", "maximo de terrenos que um jogador pode ter", 1);
    public static GroupOption MAX_AREA = new GroupOption("maximo de area", "terrenos_maxArea", "maximo de area que o jogador pode ter em blocos²", 20 * 20);

    private LandDB db;
    private String databaseName;

    private LandFlags flagsManager;
    private HashMap<Integer, Land> terrenos = new HashMap();
    public HashMap<Integer, LandPlayer> players = new HashMap();
    private int freeArea = 10 * 10;

    public LandAddon(String databaseName) {
        this.databaseName = databaseName;
    }

    @Override
    public void onEnable() {
        flagsManager = new LandFlags(this);
        flagsManager.onEnable();

        db = new LandDB(this.databaseName, this);
        terrenos = db.loadAllTerrenos();

        BYPASS_CONFIG = new BooleanConfig(generateConfigName("bypass"), false);
        BYPASS_WILD_CONFIG = new BooleanConfig(generateConfigName("bypasswild"), false);
        SHOW_ENTER_TITLE = new BooleanConfig(generateConfigName("showentertitle"), true);
        FREE_LAND_CLAIMED = new IntegerConfig(generateConfigName("freelandclaimed"), 0);

        addConfig(BYPASS_CONFIG, BYPASS_WILD_CONFIG, SHOW_ENTER_TITLE, FREE_LAND_CLAIMED);

        registerCommand(new CmdTerreno(this));
        registerCommand(new CmdTerrenoAdmin(this));
        registerListener(new InformativeListener(this));
        registerListener(new PvPListener(this));
        registerListener(new RandomTeleportListener(this));
        registerListener(new ProtectionListener(this));
        addOption(new PermissionDescription("terrenos admin", TERRENOS_ADMIN, "pode editar terrenos dos outros", true));
        addOption(new PermissionDescription("quebrar sem dono", TERRENOS_WILD, "pode quebrar onde não tem terreno", true));
        addOption(MAX_TERRENOS);
        addOption(MAX_AREA);
        super.onEnable();
    }

    @EventHandler
    public void quit(PlayerQuitEvent ev) {
        players.remove(getPlayerId(ev.getPlayer()));
    }

    public LandFlags getFlagsManager() {
        return flagsManager;
    }

    public Location getEmptyLocation(Player player, int min, int max, int maxAttempts) {
        for (int i = 0; i < maxAttempts; i++) {
            int x = RandomUtils.randomInt(min, max);
            int z = RandomUtils.randomInt(min, max);
            Block block = player.getWorld().getHighestBlockAt(x, z).getRelative(BlockFace.UP, 1);
            Hitbox2D box = new Hitbox2D(block.getX() - 1, block.getY() - 1, block.getX() + 1, block.getY() + 1);
            boolean terreno = intersectsAny(getPlayerId(player), box, player.getWorld().getName());
            if (terreno) continue;
            if (!canBuyWorldGuard(player.getWorld(), box)) continue;
            if (!hasBuyPermission(player, player.getWorld(), box)) continue;
            return block.getLocation().add(0.5, 0.5, 0.5);
        }
        return null;
    }

    public boolean intersectsAny(int pId, Hitbox2D hit, String world, Integer currentId) {
        for (Land terreno : terrenos.values()) {
            if (!terreno.getWorldName().equals(world)) {
                continue;
            }

            if (terreno.getOwnerId() == pId || can(pId, terreno, PlayerProperty.BUILDCLOSE)) {
                if (currentId != null && terreno.getId() == currentId) {
                    continue;
                }
                if (terreno.getRegion().collides(hit)) {
                    return true;
                }
            } else {
                if (terreno.getRegion().expands(ESPACO_ENTRE).collides(hit)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isNearby(int owner, int x, int y, int borda, String world) {
        for (Land terreno : terrenos.values()) {
            if (!terreno.getWorldName().equals(world)) {
                continue;
            }
            if (terreno.getOwnerId() == owner) {
                if (terreno.getRegion().expands(borda).isInside(x, y)) {
                    return true;
                }
            }

        }
        return false;
    }

    public boolean intersectsAny(int pId, Hitbox2D hit, String world) {
        return intersectsAny(pId, hit, world, null);
    }

    public Land getTerreno(Location loc) {
        for (Land t : terrenos.values()) {
            if (!t.getWorldName().equals(loc.getWorld().getName())) {
                continue;
            }
            if (t.isInside(loc)) {
                return t;
            }
        }
        return null;
    }


    public boolean checagem(Player p, PlayerBuyingInfo info) {
        Hitbox2D box = info.getRg();
        World world = Bukkit.getWorld(info.getMundo());
        int pId = RedstoneGang.getPlayer(p.getUniqueId()).getId();
        double preco = info.getPreco();


        int blocos;
        if (info instanceof PlayerExpandingInfo) {
            blocos = ((PlayerExpandingInfo) info).getDif();
            if (intersectsAny(pId, box, info.getMundo(), ((PlayerExpandingInfo) info).getTerreno().getId())) {
                C.error(p, "Alguem já tem um terreno aqui!");
                return false;
            }
        } else {
            if (getMaxTerrenos(p) >= 0 && getPlayer(pId).terrenos >= getMaxTerrenos(p)) {
                C.error(p, "Você chegou ao seu limite de terrenos!");
                C.info(p, "Você pode comprar somente %% !", getMaxTerrenos(p) + " terrenos");
                return false;
            }
            blocos = info.getRg().getHeight() * info.getRg().getWidth();
            if (intersectsAny(pId, box, info.getMundo())) {
                C.error(p, "Alguem já tem um terreno aqui!");
                return false;
            }
        }
        if (!canBuyWorldGuard(world, box)) {
            C.error(p, "Você não pode comprar terrenos em lugares oficiais do servidor!");
            return false;
        }
        if (!hasBuyPermission(p, world, box)) {
            C.error(p, "Você não pode comprar terrenos! Aqui é uma area VIP!");
            return false;
        }

        WorldBorder border = world.getWorldBorder();
        // Comprando na borda do mundo
        if (!LocUtils.getWorldBorderHitbox(border).shrink(2).isInside(info.getRg())) {
            C.error(p, "Você não pode comprar um terreno fora do mundo!");
            return false;
        }
        if (info.getRg().getWidth() > MAX_SIZE || info.getRg().getHeight() > MAX_SIZE) {
            C.error(p, "O terreno pode ter no máximo " + MAX_SIZE + " blocos em cada lado!");
            return false;
        }
        if (getBlocos(p) >= 0 && getPlayer(pId).blocosUsados + blocos > getBlocos(p)) {
            C.error(p, "Você atingiu seu limite de blocos!");
            C.info(p, "Seu limite é %% e você está usando %% !", getBlocos(p), getPlayer(pId).blocosUsados);
            return false;
        }
        double descontoArea = getRemainingFreeArea(pId) * PRECO_POR_BLOCO;
        preco = Math.max(preco - descontoArea, 0);
        if (preco > 0) {
            if (!a(EconomyAddon.class).has(p, preco)) {
                C.error(p, "Você não tem " + preco + " moedas para comprar o terreno!");
                return false;
            }
        }
        return true;
    }

    public double getRemainingFreeArea(int playerId) {
        int currentArea = getUser(playerId).getConfig(this.FREE_LAND_CLAIMED);
        if (currentArea < freeArea) {
            return (freeArea - currentArea);
        }
        return 0;
    }

    public double getDesconto(int playerId) {
        return getRemainingFreeArea(playerId) * PRECO_POR_BLOCO;
    }

    public void addFreeClaim(int playerId, int area) {
        User user = getUser(playerId);
        user.setConfig(FREE_LAND_CLAIMED, user.getConfig(FREE_LAND_CLAIMED) + area);
    }

    public boolean canEditTerreno(Player p, Land t) {
        if (t == null || t.getId() == -1) {
            C.error(p, "Você não está em um terreno!");
            return false;
        }
        if (t.getOwnerId() != RedstoneGang.getPlayer(p.getUniqueId()).getId()) {
            C.error(p, "Este terreno não é seu!");
            return false;
        }
        return true;
    }

    public Land getTerreno(Block b) {
        return getTerreno(b.getLocation());
    }

    public Land getTerreno(Entity ent) {
        return getTerreno(ent.getLocation());
    }

    public LandDB getDB() {
        return db;
    }

    public boolean canBuyWorldGuard(World world, Hitbox2D hitbox) {
        ApplicableRegionSet rm = api(WorldGuardAPI.class).getApplicableRegions(world, hitbox);
        return rm.testState(null, CustomFlags.CAN_BUY_LAND);
    }


    public boolean hasBuyPermission(Player player, World world, Hitbox2D hitbox) {
        ApplicableRegionSet rm = api(WorldGuardAPI.class).getApplicableRegions(world, hitbox);
        String permission = rm.queryValue(null, CustomFlags.BUY_LAND_PERMISSION);
        if (permission != null && !permission.isEmpty()) {
            return player.hasPermission(permission);
        }
        return true;
    }

    public void removeDisplay(Player p, PlayerBuyingInfo info) {
        for (Block b : info.getDisplaing()) {
            p.sendBlockChange(b.getLocation(), b.getType(), b.getData());
        }
        info.getDisplaing().clear();
    }

    @EventHandler
    public void update(UpdateEvent ev) {
        if (ev.getType() == UpdateType.SEC_4) {
            for (int pid : players.keySet()) {
                LandPlayer pl = getPlayer(pid);
                if (pl.buying != null && pl.buyingStart + 30000L < System.currentTimeMillis()) {
                    if (getOnlinePlayer(pid) != null) {
                        C.error(getOnlinePlayer(pid), "O tempo para confirmar a ação do terreno acabou!");
                        removeDisplay(getOnlinePlayer(pid), pl.buying);
                        pl.buying = null;
                        pl.buyingStart = -1;
                    }
                }
            }
        }
    }

    public Land getTerrenoById(int id) {
        return terrenos.get(id);
    }

    public void display(Player p, PlayerBuyingInfo info) {
        if (!p.getWorld().getName().equals(info.getMundo())) {
            return;
        }
        Hitbox2D box = info.getRg();
        int higher = p.getLocation().getBlockY();
        for (int x = box.getMinX(); x <= box.getMaxX(); x++) {
            for (int z = box.getMinY(); z <= box.getMaxY(); z++) {
                int y = p.getWorld().getHighestBlockYAt(x, z);
                if (y > higher) {
                    higher = y;
                }
            }
        }
        higher += 2;
        for (int x = box.getMinX(); x <= box.getMaxX(); x++) {
            for (int z = box.getMinY(); z <= box.getMaxY(); z++) {
                if (x == box.getMinX() || x == box.getMaxX() || z == box.getMinY() || z == box.getMaxY()) {
                    int y = p.getWorld().getHighestBlockYAt(x, z) - 1;
                    p.sendBlockChange(p.getWorld().getBlockAt(x, higher, z).getLocation(), Material.WOOL, (byte) 14);
                    p.sendBlockChange(p.getWorld().getBlockAt(x, y, z).getLocation(), Material.WOOL, (byte) 14);
                    info.getDisplaing().add(p.getWorld().getBlockAt(x, higher, z));
                    info.getDisplaing().add(p.getWorld().getBlockAt(x, y, z));

                }
            }
        }
    }

    public LandPlayer getPlayer(int id) {
        if (!players.containsKey(id)) {
            LandPlayer pl = new LandPlayer(this, id);
            terrenos.values().stream().filter(t -> t.getOwnerId() == id).forEach(t -> {
                pl.terrenos++;
                pl.blocosUsados += t.getRegion().getWidth() * t.getRegion().getHeight();
            });
            players.put(id, pl);
        }
        return players.get(id);
    }

    public boolean can(Player p, Land t, PlayerProperty prop) {
        return can(RedstoneGang.getPlayer(p.getUniqueId()).getId(), t, prop);
    }

    public boolean can(int pId, Land t, PlayerProperty prop) {
        if (t != null && t.getId() != -1) {
            if (t.getOwnerId() == pId) {
                return true;
            }
            LandPlayer tp = getPlayer(t.getOwnerId());
            if (t.hasPlayerProperty(pId)) {
                PlayerProperties props = t.getPlayerProperty(pId);
                PermissionValue val = props.get(prop);
                if (val != PermissionValue.NONE) {
                    return val == PermissionValue.ALLOW;
                }
            }
            PlayerProperties props = tp.getProperty(pId);
            if (props != null) {
                return props.get(prop) == PermissionValue.ALLOW;
            }
            Boolean terrenoDefault = t.getFlags().get(prop);
            if (terrenoDefault != null) {
                return terrenoDefault;
            }
        }
        return false;
    }

    public List<Land> getTerrenos(int owner) {
        return terrenos.values().stream().filter(x -> x.getOwnerId() == owner).collect(Collectors.toList());
    }

    public void addTerreno(Land t) {
        db.insertTerreno(t);
        terrenos.put(t.getId(), t);
        if (players.containsKey(t.getOwnerId())) {
            getPlayer(t.getOwnerId()).blocosUsados += t.getRegion().getWidth() * t.getRegion().getHeight();
            getPlayer(t.getOwnerId()).terrenos++;

        }
    }

    public void removeTerreno(Land terreno) {
        terrenos.remove(terreno.getId());
        if (players.containsKey(terreno.getOwnerId())) {
            getPlayer(terreno.getOwnerId()).blocosUsados -= terreno.getRegion().getWidth() * terreno.getRegion().getHeight();
            getPlayer(terreno.getOwnerId()).terrenos--;
        }
        getDB().deleteTerreno(terreno.getId());
        terreno.setId(-1);
    }


    public int getBlocos(Player p) {
        User player = RedstoneGang.getPlayer(p.getUniqueId());
        return (int) player.getOption(MAX_AREA);
    }

    public int getMaxTerrenos(Player p) {
        User player = RedstoneGang.getPlayer(p.getUniqueId());
        return (int) player.getOption(MAX_TERRENOS);
    }


}
