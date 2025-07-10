package productstar.account.manager.models;

public class Account {
    private final long id;
    private final String accountName;
    private long amount;

    public Account(long id, String accountName, long amount) {
        this.id = id;
        this.amount = amount;
        this.accountName = accountName;
    }

    public long getId() {
        return id;
    }

    public String getAccountName() {
        return accountName;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", amount=" + amount +
                '}';
    }
}
