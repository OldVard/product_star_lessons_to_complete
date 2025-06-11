package org.example.warehouse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class CachedAnalyticsTest {

    private final MockStorage storage = new MockStorage();
    private final Analytics analytics = new CachedAnalytics(new BasicAnalytics(storage));

    @Test
    void callOnceForRepeatingRequests() {
        CategoryAndPlace request = new CategoryAndPlace("winter", "A");

        Integer aggregationByCategoryAndPlace = analytics.getAggregationByCategoryAndPlace(request);

        Assertions.assertEquals(10, aggregationByCategoryAndPlace);

        analytics.getAggregationByCategoryAndPlace(request);
        analytics.getAggregationByCategoryAndPlace(request);
        analytics.getAggregationByCategoryAndPlace(request);

        Assertions.assertEquals(1, storage.calls);
    }

    @Test
    void callManyTimesForRepeatingRequests() {
        CategoryAndPlace request1 = new CategoryAndPlace("winter", "A");
        CategoryAndPlace request2 = new CategoryAndPlace("winter", "B");
        CategoryAndPlace request3 = new CategoryAndPlace("summer", "B");

        analytics.getAggregationByCategoryAndPlace(request1);
        analytics.getAggregationByCategoryAndPlace(request2);
        analytics.getAggregationByCategoryAndPlace(request1);

        Assertions.assertEquals(2, storage.calls);

        analytics.getAggregationByCategoryAndPlace(request3);
        Assertions.assertEquals(3, storage.calls);

        analytics.getAggregationByCategoryAndPlace(request1);
        Assertions.assertEquals(3, storage.calls);

        analytics.getAggregationByCategoryAndPlace(request2);
        Assertions.assertEquals(4, storage.calls);

    }

    private class MockStorage implements Storage {
            private int calls = 0;

            @Override
            public void putItem(Wheel wheel) {

            }

            @Override
            public Wheel getItem(String id) {
                return null;
            }

            @Override
            public boolean containsItem(String id) {
                return false;
            }

            @Override
            public void removeItem(String id) {

            }

            @Override
            public void putAllItems(List<Wheel> wheels) {

            }

            @Override
            public Map<String, Wheel> getAllItems() {
                calls++;
                Wheel wheel = new Wheel("1", "hakkapelita","summer","A",5);
                Wheel winter = new Wheel("2", "hakkapelita","winter","A",5);
                Wheel winter2 = new Wheel("3", "hakkapelita","winter","A",5);
                Wheel winter3 = new Wheel("4", "hakkapelita","winter","B",5);
                Wheel allSeasons = new Wheel("5", "hakkapelita","allSeasons","A",5);
                List<Wheel> wheels = List.of(wheel, winter, winter2, winter3, allSeasons);
                return wheels.stream()
                        .collect(Collectors.toMap(Wheel::getId, Function.identity(),
                                (w1, w2) -> w1));
            }

            @Override
            public List<Wheel> getAllSortedByModel(Predicate<Wheel> predicate) {
                return List.of();
            }
        }
}