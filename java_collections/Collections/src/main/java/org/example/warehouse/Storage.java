package org.example.warehouse;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public interface Storage {
    void putItem(Wheel wheel);
    Wheel getItem(String id);
    boolean containsItem(String id);
    void removeItem(String id);
    void putAllItems(List<Wheel> wheels);
    Map<String, Wheel> getAllItems();
    List<Wheel> getAllSortedByModel(Predicate<Wheel> predicate);
}
