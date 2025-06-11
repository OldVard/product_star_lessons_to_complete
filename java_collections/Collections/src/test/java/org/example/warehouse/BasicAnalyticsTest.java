package org.example.warehouse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BasicAnalyticsTest {

    private Analytics analytics;
    private Storage storage;

    @BeforeEach
    void setUp() {
        storage = new InMemoryStorage();
        analytics = new BasicAnalytics(storage);
    }

    @Test
    void getCategories() {
        Wheel wheel = new Wheel("1", "hakkapelita","summer","A",5);
        Wheel winter = new Wheel("2", "hakkapelita","winter","A",5);
        Wheel winter2 = new Wheel("2", "hakkapelita","winter","A",5);
        Wheel winter3 = new Wheel("2", "hakkapelita","winter","A",5);
        Wheel allSeasons = new Wheel("3", "hakkapelita","allSeasons","A",5);

        storage.putAllItems(List.of(wheel, winter, winter2, winter3, allSeasons));

        Set<String> categories = analytics.getCategories();
        Assertions.assertEquals(3, categories.size());
        Assertions.assertTrue(categories.contains("summer"));
        Assertions.assertTrue(categories.contains("winter"));
        Assertions.assertTrue(categories.contains("allSeasons"));
    }

    @Test
    void getAggregationByCategoryAndPlace() {
        Wheel wheel = new Wheel("1", "hakkapelita","summer","A",5);
        Wheel winter = new Wheel("2", "hakkapelita","winter","A",5);
        Wheel winter2 = new Wheel("3", "hakkapelita","winter","A",5);
        Wheel winter3 = new Wheel("4", "hakkapelita","winter","B",5);
        Wheel allSeasons = new Wheel("5", "hakkapelita","allSeasons","A",5);

        storage.putAllItems(List.of(wheel, winter, winter2, winter3, allSeasons));

        Map<CategoryAndPlace, Integer> aggregationByCategoryAndPlace = analytics.getAggregationByCategoryAndPlace();
        Assertions.assertEquals(10, aggregationByCategoryAndPlace.get(new CategoryAndPlace("winter", "A")));
//        System.out.println(aggregationByCategoryAndPlace);
    }

    @Test
    void getAggregationByCategoryAndPlaceSingleRequest() {
        Wheel wheel = new Wheel("1", "hakkapelita","summer","A",5);
        Wheel winter = new Wheel("2", "hakkapelita","winter","A",5);
        Wheel winter2 = new Wheel("3", "hakkapelita","winter","A",5);
        Wheel winter3 = new Wheel("4", "hakkapelita","winter","B",5);
        Wheel allSeasons = new Wheel("5", "hakkapelita","allSeasons","A",5);

        storage.putAllItems(List.of(wheel, winter, winter2, winter3, allSeasons));

        Integer quantity = analytics.getAggregationByCategoryAndPlace(
                new CategoryAndPlace("winter", "A"));
        Assertions.assertEquals(10, quantity);
    }

    @Test
    void getTotalCount() {
        Wheel wheel = new Wheel("1", "hakkapelita","summer","A",5);
        Wheel winter = new Wheel("2", "hakkapelita","winter","A",5);
        Wheel winter2 = new Wheel("3", "hakkapelita","winter","A",5);
        Wheel winter3 = new Wheel("4", "hakkapelita","winter","A",5);
        Wheel allSeasons = new Wheel("5", "hakkapelita","allSeasons","A",5);

        storage.putAllItems(List.of(wheel, winter, winter2, winter3, allSeasons));
        Integer totalCount = analytics.getTotalCount();

        Assertions.assertEquals(25, totalCount);
    }
}