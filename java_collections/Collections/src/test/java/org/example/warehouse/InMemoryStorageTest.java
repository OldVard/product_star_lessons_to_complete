package org.example.warehouse;

import org.example.warehouse.exceptionHandling.ItemNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

class InMemoryStorageTest {

    private Storage storage;

    @BeforeEach
    void setUp() {
        storage = new InMemoryStorage();
    }

    @Test
    void putAndGetItem() {
        Wheel wheel = new Wheel(
                UUID.randomUUID().toString(),
                "hakkapelita",
                "winter",
                "A",
                10);

        storage.putItem(wheel);

        Wheel actual = storage.getItem(wheel.getId());
        Assertions.assertEquals(wheel, actual);
    }

    @Test
    void getItem() {
        Assertions.assertThrows(ItemNotFoundException.class, () -> storage.getItem("123"));
    }

    @Test
    void containsItem() {
        Wheel wheel1 = new Wheel("1", "hakkapelita","winter","A",10);
        Wheel wheel2 = new Wheel("2", "hakkapelita","winter","A",10);
        Wheel wheel3 = new Wheel("3", "hakkapelita","winter","A",10);

        storage.putItem(wheel1);
        storage.putItem(wheel3);

        Assertions.assertTrue(storage.containsItem("1"));
        Assertions.assertTrue(storage.containsItem("3"));
        Assertions.assertFalse(storage.containsItem("2"));
    }

    @Test
    void removeItem() {
        Wheel wheel1 = new Wheel("1", "hakkapelita","winter","A",10);
        Wheel wheel2 = new Wheel("2", "hakkapelita","winter","A",10);
        Wheel wheel3 = new Wheel("3", "hakkapelita","winter","A",10);
        storage.putItem(wheel1);
        storage.putItem(wheel3);

        storage.removeItem(wheel1.getId());

        Assertions.assertFalse(storage.containsItem("1"));
        Assertions.assertTrue(storage.containsItem("3"));

        Assertions.assertThrows(ItemNotFoundException.class, () -> storage.removeItem(wheel2.getId()));
    }

    @Test
    void putAllItems() {
        Wheel wheel1 = new Wheel("1", "hakkapelita","winter","A",10);
        Wheel wheel2 = new Wheel("2", "hakkapelita","winter","B",10);
        Wheel wheel3 = new Wheel("3", "hakkapelita","winter","A",10);

        storage.putAllItems(List.of(wheel1, wheel2, wheel3));

        Assertions.assertTrue(storage.containsItem("1"));
        Assertions.assertTrue(storage.containsItem("2"));
        Assertions.assertTrue(storage.containsItem("3"));
    }

    @Test
    void getAllItems() {
        Wheel hakka = new Wheel(UUID.randomUUID().toString(), "hakkapelita","winter","A",10);
        Wheel michelin = new Wheel(UUID.randomUUID().toString(), "michelin","winter","B",10);
        Wheel hakkaSummer = new Wheel(UUID.randomUUID().toString(), "hakkapelita","summer","A",10);
        Wheel nordman = new Wheel(UUID.randomUUID().toString(), "nordman","summer","A",10);
        Wheel noname = new Wheel(UUID.randomUUID().toString(), "noname","summer","A",10);

        storage.putAllItems(List.of(hakka, michelin, hakkaSummer, nordman, noname));

        Map<String, Wheel> wheelsMap = storage.getAllItems();

        Assertions.assertEquals(5, wheelsMap.size());

        wheelsMap.put("1", new Wheel("1", "hakkapelita","winter","A",10));

        Assertions.assertEquals(5, storage.getAllItems().size());
    }

    @Test
    void getAllSortedByModel() {
        Wheel hakka = new Wheel("1", "hakkapelita","winter","A",10);
        Wheel michelin = new Wheel("2", "michelin","winter","B",10);
        Wheel hakkaSummer = new Wheel("3", "hakkapelita","summer","A",10);
        Wheel nordman = new Wheel("4", "nordman","winter","A",10);
        Wheel noname = new Wheel("5", "noname","winter","A",10);

        List<Wheel> items = List.of(hakkaSummer, hakka, michelin, noname, nordman);
        storage.putAllItems(items);

        Set<String> expectedModels = new HashSet<>(List.of("hakkapelita", "michelin", "noname"));

        List<Wheel> allItemsSorted = storage.getAllSortedByModel(wheel ->
                expectedModels.contains(wheel.getModel()));

        List<Wheel> sortedOrder = List.of(hakkaSummer, hakka, michelin, noname);
        Assertions.assertEquals(4, allItemsSorted.size());
        Assertions.assertEquals(sortedOrder, allItemsSorted);
    }
}