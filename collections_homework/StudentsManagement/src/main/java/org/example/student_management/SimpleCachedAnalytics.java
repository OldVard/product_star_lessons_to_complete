package org.example.student_management;

import java.util.LinkedHashMap;
import java.util.Map;

public class SimpleCachedAnalytics implements Analytics {

    private final AverageMarkCache<String, Double> markCache = new AverageMarkCache<>(3);
    private final Examination examination;

    public SimpleCachedAnalytics(Examination examination) {
        this.examination = examination;
    }

    @Override
    public Double getAverageMarkForSubject(String subject) {
        return markCache.computeIfAbsent(subject, examination::getAverageForSubject);
    }

    public void invalidateSubject(String subject) {
        markCache.invalidate(subject);
    }
}

class AverageMarkCache<K, V> extends LinkedHashMap<K, V> {
    private final int capacity;

    public AverageMarkCache(int capacity) {
        super(capacity, 0.75f, true);
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > capacity;
    }

    public void invalidate(K key) {
        this.remove(key);
    }
}
