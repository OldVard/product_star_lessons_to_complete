package warehouse;

public class InMemoryStorage implements Storage {
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
}
