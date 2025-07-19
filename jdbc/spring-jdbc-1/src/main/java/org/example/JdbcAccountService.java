package org.example;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class JdbcAccountService implements AccountService {

    private final AccountDao accountDao;
    private final TransactionTemplate transactionTemplate;

    @Autowired
    public JdbcAccountService(AccountDao accountDao, TransactionTemplate transactionTemplate) {
        this.accountDao = accountDao;
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public void transfer(long from, long to, long amount) {
        transactionTemplate.executeWithoutResult(
                status -> {
                    Account accountFrom;
                    Account accountTo;

                    List<Account> accounts = Stream.of(from, to)
                            .sorted()
                            .map(id -> accountDao.lockAccount(id))
                            .collect(Collectors.toList());

                    if(accounts.get(0).getId() == from) {
                        accountFrom = accounts.get(0);
                        accountTo = accounts.get(1);
                    } else {
                        accountFrom = accounts.get(1);
                        accountTo = accounts.get(0);
                    }

                    if (accountFrom.getAmount() < amount) {
                        throw new IllegalStateException("Not enough money :(");
                    }

                    long newAmountFrom = accountFrom.getAmount() - amount;
                    long newAmountTo = accountTo.getAmount() + amount;

                    accountDao.setAmount(from, newAmountFrom);
                    throw new RuntimeException("I'm evil exception! I'm gonna rollback the transaction!");
                    // accountDao.setAmount(to, newAmountTo);
                });
    }

}
