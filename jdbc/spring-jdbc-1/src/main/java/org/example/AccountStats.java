package org.example;

public class AccountStats {
    private final long totalAccountIds;
    private final long totalAmount;

    public AccountStats(long totalAccountIds, long totalAmount) {
        this.totalAccountIds = totalAccountIds;
        this.totalAmount = totalAmount;
    }

    public long getTotalAccountIds() {
        return totalAccountIds;
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    @Override
    public String toString() {
        return "AccountStats{" +
                "totalAccountIds=" + totalAccountIds +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
