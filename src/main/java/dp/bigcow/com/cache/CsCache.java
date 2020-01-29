package dp.bigcow.com.cache;

import dp.bigcow.com.cache.store.DataStore;
import dp.bigcow.com.cache.store.StoreAccessException;
import dp.bigcow.com.cache.store.ValueHolder;

/**
 * Create by suzhiwu on 2019/02/09
 */
public class CsCache<K, V> {

    private final DataStore<K, V> store;

    public CsCache(final DataStore<K, V> dataStore) {
        store = dataStore;
    }

    public V get(final K key) {
        try {
            ValueHolder<V> value = store.get(key);
            if (null == value) {
                return null;
            }
            return value.value();
        } catch (StoreAccessException e) {
            System.out.println(("store access error : " + e.getMessage()));
            System.out.println(e.getStackTrace().toString());
            return null;
        }
    }

    public void put(final K key, final V value) {
        try {
            store.put(key, value);
        } catch (StoreAccessException e) {
            System.out.println("store access error : " + e.getMessage());
        }
    }

    public V remove(K key) {
        try {
            ValueHolder<V> value = store.remove(key);
            return value != null ? value.value() : null;
        } catch (StoreAccessException e) {
            System.out.println("store access error : " + e.getMessage());
            System.out.println(e.getStackTrace().toString());
            return null;
        }
    }

    public void clear() {
        try {
            store.clear();
        } catch (StoreAccessException e) {
            System.out.println("store access error : " + e.getMessage());
        }
    }
}
