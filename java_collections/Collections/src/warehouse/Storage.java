package warehouse;

import java.util.HashMap;

public interface Storage {
    void putItem(Wheel wheel);
    Wheel getItem(String id);
    boolean containsItem(String id);
    void removeItem(String id);
}
