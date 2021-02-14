package dev.feldmann.redstonegang.common.player.cache;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.common.utils.ParameterLock;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerCache {


    /*
     * Por que caralhos eu to usando Optional?
     * COncurrentHashMap não permite key ou valor null
     * Então pra armazenar q um jogador nao existe eu uso um Optional.empty()
     * */
    private ConcurrentHashMap<String, Optional<Integer>> nameMap = new ConcurrentHashMap<>();
    private ConcurrentHashMap<UUID, Optional<Integer>> uuidMap = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Integer, Optional<User>> cache = new ConcurrentHashMap<>();

    public boolean contains(int id) {
        return cache.containsKey(id);
    }


    public void cache(User pl) {
        nameMap.put(pl.getDisplayName(), Optional.of(pl.getId()));
        uuidMap.put(pl.getUuid(), Optional.of(pl.getId()));
        cache.put(pl.getId(), Optional.of(pl));
    }

    public void clearCache(Integer id) {
        if (id != null) {
            User pl = cache.remove(id).orElse(null);
            if (pl != null) {
                nameMap.remove(pl.getDisplayName());
                uuidMap.remove(pl.getUuid());
            }
        }
    }

    public void clearCache(UUID uid) {
        if (uuidMap.containsKey(uid)) {
            Integer id = uuidMap.remove(uid).orElse(null);
            clearCache(id);
        }
    }

    public User get(int id) {
        synchronized (ParameterLock.getCanonicalParameterLock("loadID-" + id)) {
            if (cache.containsKey(id)) {
                User cached = cache.get(id).orElse(null);
                if (cached != null && cached.getLoadedTime() + (1000 * 60 * 30) < System.currentTimeMillis()) {
                    clearCache(id);
                } else {
                    return cached;
                }
            }
            User rg = RedstoneGang.instance.user().getDb().loadPlayerById(id);
            if (rg != null) {
                cache(rg);
            } else {
                cache.put(id, Optional.empty());
            }
            return rg;
        }
    }

    public boolean isCached(int id) {
        return cache.containsKey(id);
    }

    public User get(UUID uuid) {
        synchronized (ParameterLock.getCanonicalParameterLock("loadUUID-" + uuid.toString())) {
            if (uuidMap.containsKey(uuid)) {
                if (uuidMap.get(uuid).isPresent()) {
                    return cache.get(uuidMap.get(uuid).get()).orElse(null);
                }
            }
            Integer id = RedstoneGang.instance().databases().users().findIdByUUID(uuid);
            if (id != null) {
                return get(id);
            }
            return null;
        }
    }


    public User get(String name) {
        synchronized (ParameterLock.getCanonicalParameterLock("loadName-" + name)) {
            if (nameMap.containsKey(name)) {
                if (nameMap.get(name).isPresent()) {
                    return cache.get(nameMap.get(name).get()).orElse(null);
                } else {
                    return null;
                }
            }
            Integer id = RedstoneGang.instance().databases().users().findIdByName(name);
            if (id != null) {
                return get(id);
            } else {
                nameMap.put(name, Optional.empty());
                return null;
            }
        }
    }


}
