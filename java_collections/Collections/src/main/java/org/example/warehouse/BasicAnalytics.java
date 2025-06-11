package org.example.warehouse;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class BasicAnalytics implements Analytics {

    private final Storage storage;

    public BasicAnalytics(Storage storage) {
        this.storage = storage;
    }

    @Override
    public Set<String> getCategories() {
        return storage.getAllItems().values().stream()
                .map(Wheel::getCategory)
                .collect(Collectors.toSet());
    }

    //CategoryAndPlace — это просто пара значений, и с record ты получаешь
    // корректную реализацию equals() и hashCode(),
    // необходимые при использовании объекта как ключа в Map.
    @Override
    public Map<CategoryAndPlace, Integer> getAggregationByCategoryAndPlace() {
        // Сюда будем класть агрегации и возвращать эту мапу
        Map<CategoryAndPlace, Integer> aggregations = new HashMap<>();
        // Идем по элементам
        for(Wheel wheel : storage.getAllItems().values()) {
            // Создаем ключ (комбинация category + place)
            CategoryAndPlace key = new CategoryAndPlace(wheel.getCategory(), wheel.getPlace());
            // Берем текущую сумму по ключу. Если
            Integer currentSum = aggregations.getOrDefault(key, 0);
            currentSum += wheel.getQuantity();
            aggregations.put(key, currentSum);
        }
        return aggregations;
    }

    @Override
    public Integer getAggregationByCategoryAndPlace(CategoryAndPlace categoryAndPlace) {
        Integer quantity = 0;
        for(Wheel wheel : storage.getAllItems().values()) {
            if(wheel.getCategory().equals(categoryAndPlace.category()) &&
                    wheel.getPlace().equals(categoryAndPlace.place())) {
                quantity += wheel.getQuantity();
            }
        }
        return quantity;
    }

    @Override
    public Integer getTotalCount() {
        return storage.getAllItems().values().stream()
                .map(Wheel::getQuantity)
                .mapToInt(Integer::intValue).sum();
    }
}
