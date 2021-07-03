import edu.princeton.cs.algs4.Queue;

import java.util.HashMap;
import java.util.Map;

class LimitedCache<K, V> {
    private Map<K, V> cache = new HashMap<>();
    private Queue<K> queue = new Queue<>();
    private int limit = 100;

    int getLimit() {
        return limit;
    }

    void setLimit(int limit) {
        this.limit = limit;
    }

    boolean contains(K key) {
        return cache.containsKey(key);
    }

    V find(K key) {
        return cache.get(key);
    }

    void store(K key, V value) {
        if (cache.size() == limit) {
            K oldestKey = queue.dequeue();
            cache.remove(oldestKey);
        }
        cache.put(key, value);
        queue.enqueue(key);
    }
}
