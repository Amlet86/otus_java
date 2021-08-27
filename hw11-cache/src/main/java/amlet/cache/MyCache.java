package amlet.cache;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {

    private final WeakHashMap<K, V> cache;
    private final List<WeakReference<HwListener<K, V>>> listeners;

    public MyCache() {
        cache = new WeakHashMap<>();
        listeners = new ArrayList<>();
    }

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
        listeners.forEach(listenerWeakReference -> {
            HwListener<K, V> listener = listenerWeakReference.get();
            if (listener != null) {
                listener.notify(key, value, "put");
            }
        });
    }

    @Override
    public void remove(K key) {
        var value = cache.get(key);
        cache.remove(key);
        listeners.forEach(listenerWeakReference -> {
            HwListener<K, V> listener = listenerWeakReference.get();
            if (listener != null) {
                listener.notify(key, value, "remove");
            }
        });
    }

    @Override
    public V get(K key) {
        return cache.get(key);
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(new WeakReference<>(listener));
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.remove(new WeakReference<>(listener));
    }
}
