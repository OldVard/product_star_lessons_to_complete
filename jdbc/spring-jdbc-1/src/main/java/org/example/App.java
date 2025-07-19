package org.example;

import org.example.config.ApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(
                ApplicationConfiguration.class);

        NamedJdbcAccountDao accountDao = context.getBean(NamedJdbcAccountDao.class);
        TransactionTemplate transactionTemplate = context.getBean(TransactionTemplate.class);
        AccountService accountService = context.getBean(AccountService.class);

        accountDao.deleteAllAccounts();

        accountDao.addAccount(1, 1000);
        accountDao.addAccount(2, 2000);

        try {
            accountService.transfer(1, 2, 500);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }

        prettyAccountsPrint(accountDao.getAllAccounts());
    }

    private static void prettyAccountsPrint(List<Account> accounts) {
        accounts.forEach(account -> System.out.printf("[%d] - %d\n", account.getId(), account.getAmount()));
    }
}
