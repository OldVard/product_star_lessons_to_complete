package org.example;

import java.util.Collection;
import java.util.List;

public interface AccountDao {

    void deleteAllAccounts();
    Account addAccount(long id, long amount);
    Account addAccount(long amount);
    List<Account> addAllAccounts(List<Account> accounts);
    Account getAccount(long accountId);
    Account lockAccount(long accountId);
    List<Account> getAccounts(Collection<Long> accountIds);
    void setAmount(long accountId, long amount);
    void updateAccounts(Collection<Account> accounts);
    List<Account> getAllAccounts();
    AccountStats getTotal();
    
}
