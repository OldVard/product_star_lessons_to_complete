package stock;

public class Stock {

    public static void main(String[] args) {
        initBD();
        showAll();
    }

    private static final StringBuilder sb = new StringBuilder();

    private final String name;
    private static final int stockSize = 10;
    private static final Stock[] stocks = new Stock[stockSize];

    public Stock(String name) {
        this.name = name;
    }

    public static void initBD() {
        stocks[0] = new Stock("утюг");
        stocks[1] = new Stock("сковородка");
        stocks[2] = new Stock("лыжи");
        stocks[3] = new Stock("утюг2");
        stocks[4] = new Stock("пылесос");
    }

    public static void showAll() {
        cleanSb();
        sb.append("Показать всех:");
        for(Stock elem : stocks) {
            sb.append("\n-- ").append(elem);
        }
        System.out.println(sb);
    }

    private static void cleanSb() {
        sb.setLength(0);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Name: [" + this.getName() + "]";
    }
}
