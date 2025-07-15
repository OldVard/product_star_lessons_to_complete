package ru.productstar.servlets.model;

// Сущность, означающая как расход, так и доход.
public class Transaction {
    private final String name;
    private final int sum;

    public Transaction(String name, int sum) {
        this.name = name;
        this.sum = sum;
    }

    public String getName() {
        return name;
    }

    public int getSum() {
        return sum;
    }

    @Override
    public String toString() {
        return  "Transaction{" +
                "name='" + name + '\'' +
                ", sum=" + sum +
                '}';
    }
}
