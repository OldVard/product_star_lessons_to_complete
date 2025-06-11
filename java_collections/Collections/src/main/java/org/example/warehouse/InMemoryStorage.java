package org.example.warehouse;

import org.example.warehouse.exceptionHandling.ItemNotFoundException;

import java.util.*;
import java.util.function.Predicate;

public class InMemoryStorage implements Storage {

    private static final int INITIAL_STORE_CAPACITY = 256;
    private final Map<String, Wheel> store = new HashMap<>(INITIAL_STORE_CAPACITY);

    @Override
    public void putItem(Wheel wheel) {
        store.put(wheel.getId(), wheel);
    }

    @Override
    public Wheel getItem(String id) {
        return Optional.ofNullable(store.get(id))
                .orElseThrow(() -> new ItemNotFoundException("Не найдено :("));
    }

    @Override
    public boolean containsItem(String id) {
        return store.containsKey(id);
    }

    @Override
    public void removeItem(String id) {
        if(containsItem(id)) {
            store.remove(id);
        } else {
            throw new ItemNotFoundException("Нельзя удалить несуществующий элемент!");
        }

    }

    @Override
    public void putAllItems(List<Wheel> wheels) {
        for(Wheel item : wheels) {
            store.put(item.getId(), item);
        }
    }

    @Override
    public Map<String, Wheel> getAllItems() {
        // чтобы не трогать оригинальную структуру данных
        return new HashMap<>(store);
    }

    @Override
    public List<Wheel> getAllSortedByModel(Predicate<Wheel> predicate) {
        return store.values().stream()
                .filter(predicate)
                .sorted(Comparator.comparing(Wheel::getModel)
                        .thenComparing(Wheel::getCategory)
                        .thenComparing(Wheel::getPlace)
                        .thenComparing(Wheel::getId))
                .toList();
    }
}
