package com.github.ormfux.common.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Utilities to operate on maps.
 */
public final class MapUtils {
    
    private MapUtils() {
        throw new IllegalAccessError(MapUtils.class.getSimpleName() + " class is not intended to be instantiated");
    }
    
    /**
     * Creates a new map from the entries.
     * 
     * @param entries The map entries.
     * @return The map with the entries.
     */
    @SafeVarargs
    public static <K, V> Map<K, V> fillMap(final Entry<K, V>... entries) {
        final Map<K, V> result = new HashMap<>();
        Arrays.stream(entries).forEach(entry -> result.put(entry.key, entry.value));
        
        return result;
    }
    
    /**
     * Convenience method to create an entry instance for {@link #fillMap(Entry...)}
     * 
     * @param key Key.
     * @param value Value.
     * @return Key and value wrapped in an Entry.
     */
    public static <K, V> Entry<K, V> entry(final K key, final V value) {
        return new Entry<K, V>(key, value);
    }
    
    /**
     * A map entry.
     *
     * @param <K>
     * @param <V>
     */
    public static class Entry<K, V> {
        
        /**
         * Entry key.
         */
        private final K key;
        
        /**
         * Entry value.
         */
        private final V value;
        
        /**
         * @param key Key.
         * @param value Value.
         */
        public Entry(final K key, final V value) {
            this.key = key;
            this.value = value;
        }
        
    }
    
}
