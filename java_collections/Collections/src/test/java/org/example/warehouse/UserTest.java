package org.example.warehouse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UserTest {

    private final Storage storage = new InMemoryStorage();

    @Test
    void getAllModelsSorted() {
        Wheel wheel1 = new Wheel(UUID.randomUUID().toString(), "model1", "winter", "A", 10);
        Wheel wheel2 = new Wheel(UUID.randomUUID().toString(), "model2", "winter", "A", 10);
        Wheel wheel3 = new Wheel(UUID.randomUUID().toString(), "model3", "winter", "A", 10);
        Wheel wheel4 = new Wheel(UUID.randomUUID().toString(), "model2", "summer", "A", 10);
        Wheel wheel5 = new Wheel(UUID.randomUUID().toString(), "model3", "summer", "B", 10);
        List<String> expect = List.of("model1", "model2", "model3");

        storage.putAllItems(List.of(wheel1, wheel2, wheel3, wheel4, wheel5));

        Map<String, Wheel> allItems = storage.getAllItems();

        Set<String> sortedOrNot = allItems.values().stream()
                .map(Wheel::getModel)
                .sorted()
                .collect(Collectors.toCollection(LinkedHashSet::new));

        Assertions.assertEquals(expect, List.copyOf(sortedOrNot));
    }

    @Test
    void buldUploadFromCsv() {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(this.getClass().getResourceAsStream("/wheels.csv")))) {

            String headerLine = reader.readLine();
            String[] positions = headerLine.split(",");
            Map<String, Integer> headerMap = new HashMap<>();

            for(int i = 0; i < positions.length; i++) {
                headerMap.put(positions[i], i);
            }
            System.out.println(headerMap);

            String line;
            while((line = reader.readLine()) != null) {
                String[] split = line.split(",");
                Wheel wheel = new Wheel(
                        UUID.randomUUID().toString(),
                        split[headerMap.get("model")],
                        split[headerMap.get("category")],
                        split[headerMap.get("place")],
                        Integer.parseInt(split[headerMap.get("quantity")])
                );

                storage.putItem(wheel);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertEquals(10, storage.getAllItems().size());
        storage.getAllItems().values().stream()
                .sorted(Comparator.comparing(Wheel::getModel))
                .forEach(System.out::println);
    }
}
