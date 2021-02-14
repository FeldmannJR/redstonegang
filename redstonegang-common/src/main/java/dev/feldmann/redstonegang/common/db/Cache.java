package dev.feldmann.redstonegang.common.db;

import sun.misc.LRUCache;

import java.util.HashMap;

public abstract class Cache<I, T> {

    private HashMap<I, T> cache = new HashMap();

    protected abstract I getIdentifier(T t);

    protected abstract T load(I identifier);

    public T get(I identifier) {
        if (!cache.containsKey(identifier)) {
            cache.put(identifier, load(identifier));
        }

        return cache.get(identifier);
    }

    public void set(I identifier, T value) {
        cache.put(identifier, value);
    }

    public void removeCache(I identifier) {
        cache.remove(identifier);
    }

}

