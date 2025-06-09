import java.util.*;

public class Main {
    public static void main(String[] args) {
        LRUCache<String, Integer> cache = new LRUCache<>(2);

        cache.put("one", 1);
        cache.put("two", 2);
        cache.put("three", 3);
        System.out.println(cache);
        cache.get("two"); // обновили использование
        cache.put("four", 4);
        System.out.println(cache);
    }
}