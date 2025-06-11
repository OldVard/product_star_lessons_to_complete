package org.example.warehouse;

import java.util.Map;
import java.util.Set;

//Объект CategoryAndPlace нужен как ключ для Map,
// чтобы агрегировать (суммировать) количество колёс по комбинации category + place
record CategoryAndPlace(String category, String place) { }

public interface Analytics {
    Set<String> getCategories();
    Map<CategoryAndPlace, Integer> getAggregationByCategoryAndPlace();
    // для кеша
    Integer getAggregationByCategoryAndPlace(CategoryAndPlace categoryAndPlace);
    Integer getTotalCount();
}
