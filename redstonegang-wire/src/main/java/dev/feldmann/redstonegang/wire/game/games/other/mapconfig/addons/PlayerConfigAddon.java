package dev.feldmann.redstonegang.wire.game.games.other.mapconfig.addons;

import dev.feldmann.redstonegang.common.utils.Cooldown;
import dev.feldmann.redstonegang.wire.game.base.addons.both.effects.EffectsAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.minigames.maps.MapConfig;
import dev.feldmann.redstonegang.wire.game.base.addons.minigames.maps.Mapa;
import dev.feldmann.redstonegang.wire.game.base.addons.both.npcs.NPCAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.npcs.RedstoneNPC;
import dev.feldmann.redstonegang.wire.game.base.events.player.PlayerJoinServerEvent;
import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import dev.feldmann.redstonegang.wire.game.base.objects.maps.SaveType;
import dev.feldmann.redstonegang.wire.game.games.other.mapconfig.MapConfigGame;
import dev.feldmann.redstonegang.wire.game.games.other.mapconfig.MapConfigInfo;
import dev.feldmann.redstonegang.wire.game.games.other.mapconfig.menus.config.SetEntryMenu;
import dev.feldmann.redstonegang.wire.plugin.events.update.UpdateEvent;
import dev.feldmann.redstonegang.wire.plugin.events.update.UpdateType;
import dev.feldmann.redstonegang.wire.utils.hitboxes.Hitbox;
import dev.feldmann.redstonegang.wire.utils.items.ItemBuilder;
import dev.feldmann.redstonegang.wire.utils.items.ItemCheck;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.HashMap;

public class PlayerConfigAddon extends Addon {

    MapConfigGame game;
    HashMap<Player, MapConfigInfo> infos = new HashMap();

    Cooldown createLoc = new Cooldown(500);

    ItemStack selLoc = ItemBuilder.item(Material.STICK).name("§fSelecionar §dlocalização").build();
    ItemStack selReg = ItemBuilder.item(Material.BLAZE_ROD).name("§fSelecionar §6região").build();
    ItemStack selBlock = ItemBuilder.item(Material.IRON_SPADE).name("§fSelecionar §5bloco").build();

    //Menus
    public SetEntryMenu setMenu;


    public PlayerConfigAddon(MapConfigGame game) {
        this.game = game;
        setMenu = new SetEntryMenu(game);

    }


    public MapConfigInfo getInfo(Player p) {
        if (!infos.containsKey(p)) {
            infos.put(p, new MapConfigInfo(p));
        }
        return infos.get(p);
    }

    public void trocaMapa(Mapa m) {
        for (MapConfigInfo info : infos.values()) {
            if (info.locnpc != null) {
                game.a(NPCAddon.class).removerNPC(info.locnpc);
            }
            info.loc = null;
            info.rg1 = null;
            info.rg2 = null;
            info.block = null;
        }
        setMenu.loadPage(0);

    }

    @EventHandler
    public void interactTempLocs(PlayerInteractEvent ev) {

        if (game.getConfigurando() == null) {
            return;
        }
        if (createLoc.isCooldown(ev.getPlayer().getUniqueId())) {
            return;
        }
        createLoc.addCooldown(ev.getPlayer().getUniqueId());
        if (ItemCheck.isInHand(ev.getPlayer(), selLoc)) {
            handleLoc(ev.getPlayer());
            ev.setCancelled(true);
            return;
        }
        if (ItemCheck.isInHand(ev.getPlayer(), selReg)) {
            handleReg(ev);
            ev.setCancelled(true);
            return;
        }
        if (ItemCheck.isInHand(ev.getPlayer(), selBlock)) {
            handleBlock(ev);
            ev.setCancelled(true);
            return;
        }

    }

    public void handleBlock(PlayerInteractEvent ev) {
        if (ev.getAction() == Action.RIGHT_CLICK_BLOCK) {
            MapConfigInfo info = getInfo(ev.getPlayer());
            MapConfig config = game.getConfigurando().getConfig();
            if (info.seq != null && info.seq.getTipo() == SaveType.BLOCK) {
                if (ev.getPlayer().isSneaking()) {
                    info.seq = null;
                    ev.getPlayer().sendMessage("§cCancelado sequencia!");
                    return;
                }
                String nome = game.getNextName(config.getKeysLocations(), info.seq.getNome());
                config.addLocation(nome, ev.getClickedBlock().getLocation());
                game.update();
                ev.getPlayer().sendMessage("§dAdicionado " + nome);
                return;
            }
            info.block = ev.getClickedBlock().getLocation().toVector();
            ev.getPlayer().sendMessage("§fSetado bloco ! (" + info.block.getBlockX() + ", " + info.block.getBlockY() + ", " + info.block.getBlockZ() + ")");

        }
    }

    public void handleReg(PlayerInteractEvent ev) {
        if (ev.getAction() == Action.RIGHT_CLICK_BLOCK || ev.getAction() == Action.LEFT_CLICK_BLOCK) {
            MapConfigInfo info = getInfo(ev.getPlayer());
            if (ev.getAction() == Action.RIGHT_CLICK_BLOCK) {
                info.rg2 = ev.getClickedBlock().getLocation().toVector();
                ev.getPlayer().sendMessage("§fSetado 2ª posição! (" + info.rg2.getBlockX() + ", " + info.rg2.getBlockY() + ", " + info.rg2.getBlockZ() + ")");
            } else {
                info.rg1 = ev.getClickedBlock().getLocation().toVector();
                ev.getPlayer().sendMessage("§fSetado 1ª posição! (" + info.rg1.getBlockX() + ", " + info.rg1.getBlockY() + ", " + info.rg1.getBlockZ() + ")");
            }
        }
    }

    public void handleLoc(Player p) {

        MapConfigInfo info = getInfo(p);
        MapConfig config = game.getConfigurando().getConfig();
        if (info.seq != null && info.seq.getTipo() == SaveType.LOCATION) {
            if (p.isSneaking()) {
                info.seq = null;
                p.sendMessage("§cCancelado sequencia!");
                return;
            }
            String nome = game.getNextName(config.getKeysLocations(), info.seq.getNome());
            config.addLocation(nome, p.getLocation().clone());
            p.sendMessage("§dAdicionado " + nome);
            game.update();
            return;
        }
        if (info.locnpc != null) {
            game.a(NPCAddon.class).removerNPC(info.locnpc);
            info.locnpc = null;
        }

        info.locnpc = new RedstoneNPC(p.getLocation(), EntityType.PLAYER, "§7Loc " + p.getName());
        info.locnpc.setFly(true);
        info.locnpc.setSkin(p.getName());
        game.a(NPCAddon.class).criar(info.locnpc);
        info.loc = p.getLocation().clone();
    }

    @EventHandler
    public void chat(AsyncPlayerChatEvent ev) {
        MapConfigInfo info = getInfo(ev.getPlayer());
        if (info.editandoConfig != null) {
            String set = ev.getMessage();
            if (!set.equalsIgnoreCase("x")) {
                ev.getPlayer().sendMessage("§eConfig " + info.editandoConfig + " setada para " + set);
                game.getConfigurando().getConfig().addConfig(info.editandoConfig, set);
            } else {
                ev.getPlayer().sendMessage("§cCancelado!");
            }
            info.editandoConfig = null;
            ev.setCancelled(true);
        }
    }

    @EventHandler
    public void join(PlayerJoinServerEvent ev) {
        ev.getPlayer().getInventory().setItem(0, selLoc.clone());
        ev.getPlayer().getInventory().setItem(1, selReg.clone());
        ev.getPlayer().getInventory().setItem(2, selBlock.clone());
    }

    @EventHandler
    public void quit(PlayerQuitEvent ev) {
        MapConfigInfo info = infos.remove(ev.getPlayer());
        if (info != null) {
            if (info.locnpc != null) {
                game.a(NPCAddon.class).removerNPC(info.locnpc);
            }
        }

    }

    @EventHandler
    public void update(UpdateEvent ev) {
        if (game.getConfigurando() == null) {
            return;
        }
        if (ev.getType() == UpdateType.MS_500) {
            for (MapConfigInfo info : infos.values()) {
                if (info.rg1 != null && info.rg2 != null) {
                    server().a(EffectsAddon.class).effect(info.p, new Hitbox(info.rg1, info.rg2, true), 0.5, Effect.FLAME);
                }
                if (info.block != null) {
                    server().a(EffectsAddon.class).effect(info.p, new Hitbox(info.block, info.block, true), 0.3, Effect.MAGIC_CRIT);
                }
            }
            for (String nomeHit : game.getConfigurando().getConfig().getKeysRegions()) {
                Hitbox h = game.getConfigurando().getConfig().getRegion(nomeHit);
                server().a(EffectsAddon.class).effect(game.getConfigurando().getWorld(), h, 0.5, Effect.POTION_SWIRL);
            }
            for (String nomeLoc : game.getConfigurando().getConfig().getKeysLocations()) {
                if (game.isBlock(nomeLoc)) {
                    Vector v = game.getConfigurando().getConfig().getLocation(nomeLoc).toVector();
                    server().a(EffectsAddon.class).effect(game.getConfigurando().getWorld(), new Hitbox(v, v, true), 0.2, Effect.COLOURED_DUST);
                }
            }
        }
    }


}
