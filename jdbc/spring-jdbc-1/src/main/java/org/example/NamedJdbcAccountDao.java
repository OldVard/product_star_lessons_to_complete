package org.example;

import java.util.Collection;
import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.core.RowMapper;

@Configuration
@Primary
public class NamedJdbcAccountDao implements AccountDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public NamedJdbcAccountDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    private static final String DELETE_ALL_ACCOUNTS_SQL = "delete from account";
    private static final String ADD_ACCOUNT_SQL_ID = "insert into account(id, amount) values(:id, :amount)";
    private static final String ADD_ACCOUNT_SQL = "insert into account(amount) values(:amount)";
    private static final String ADD_ALL_ACCOUNTS_SQL = "insert into account(id, amount) values(:id, :amount)";
    private static final String GET_ACCOUNT_BY_ID_SQL = "select id, amount from account where id = :id";
    private static final String LOCK_ACCOUT_SQL = "select id, amount from account where id =:id for update";
    private static final String GET_ACCOUNTS_BY_IDS_SQL = "select id, amount from account where id in (:ids)";
    private static final String SET_AMOUNT_SQL = "update account set amount = :amount where id = :id";
    private static final String GET_ALL_ACCOUNT_SQL = "select id, amount from account";
    private static final String UPDATE_ACCOUNTS_SQL = "update account set amount = :amount where id = :id";
    private static final String GET_TOTAL_SQL = "select count(id) as totalAccountIds, sum(amount) as totalAmount from account";

    private final RowMapper<Account> ACCOUNT_ROW_MAPPER = (rs, rowNum) -> new Account(rs.getLong("id"),
            rs.getLong("amount"));

    private final RowMapper<AccountStats> ACCOUNT_STATS_ROW_MAPPER = (rs,
            rowNum) -> new AccountStats(rs.getLong("totalAccountIds"), rs.getLong("totalAmount"));

    @Override
    public Account addAccount(long id, long amount) {
        namedParameterJdbcTemplate.update(
                ADD_ACCOUNT_SQL_ID,
                new MapSqlParameterSource()
                        .addValue("id", id)
                        .addValue("amount", amount));
        return new Account(id, amount);
    }

    @Override
    public Account addAccount(long amount) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(
                ADD_ACCOUNT_SQL,
                new MapSqlParameterSource("amount", amount),
                keyHolder,
                new String[] { "id" });

        return new Account(keyHolder.getKey().longValue(), amount);
    }

    @Override
    public List<Account> addAllAccounts(List<Account> accounts) {
        MapSqlParameterSource[] args = accounts.stream()
                .map(account -> new MapSqlParameterSource()
                        .addValue("id", account.getId())
                        .addValue("amount", account.getAmount()))
                .toArray(MapSqlParameterSource[]::new);

        namedParameterJdbcTemplate.batchUpdate(
                ADD_ALL_ACCOUNTS_SQL, args);

        return accounts;
    }

    @Override
    public void deleteAllAccounts() {
        namedParameterJdbcTemplate.update(
                DELETE_ALL_ACCOUNTS_SQL, EmptySqlParameterSource.INSTANCE);
    }

    @Override
    public Account getAccount(long accountId) {
        return namedParameterJdbcTemplate.queryForObject(
                GET_ACCOUNT_BY_ID_SQL,
                new MapSqlParameterSource("id", accountId),
                ACCOUNT_ROW_MAPPER);
    }

    @Override
    public Account lockAccount(long accountId) {
        return namedParameterJdbcTemplate.queryForObject(
                LOCK_ACCOUT_SQL,
                new MapSqlParameterSource("id", accountId),
                ACCOUNT_ROW_MAPPER);
    }

    @Override
    public List<Account> getAccounts(Collection<Long> accountsIds) {
        return namedParameterJdbcTemplate.query(
                GET_ACCOUNTS_BY_IDS_SQL, ACCOUNT_ROW_MAPPER);
    }

    @Override
    public void setAmount(long accountId, long amount) {
        namedParameterJdbcTemplate.update(
                SET_AMOUNT_SQL,
                new MapSqlParameterSource()
                        .addValue("id", accountId)
                        .addValue("amount", amount));
    }

    @Override
    public void updateAccounts(Collection<Account> accounts) {
        MapSqlParameterSource[] args = accounts.stream()
                .map(account -> new MapSqlParameterSource()
                        .addValue("id", account.getId())
                        .addValue("amount", account.getAmount()))
                .toArray(MapSqlParameterSource[]::new);

        namedParameterJdbcTemplate.batchUpdate(
                UPDATE_ACCOUNTS_SQL, args);
    }

    @Override
    public List<Account> getAllAccounts() {
        return namedParameterJdbcTemplate.query(
                GET_ALL_ACCOUNT_SQL, ACCOUNT_ROW_MAPPER);
    }

    @Override
    public AccountStats getTotal() {
        return namedParameterJdbcTemplate.queryForObject(
                GET_TOTAL_SQL,
                EmptySqlParameterSource.INSTANCE,
                ACCOUNT_STATS_ROW_MAPPER);
    }
}
