import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private static final boolean KEEP_LAST_ACCESSED = true;
    private final int capacity;

    public LRUCache(int capacity) {
        super(capacity, 0.75f, KEEP_LAST_ACCESSED);
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > capacity;
    }
}
