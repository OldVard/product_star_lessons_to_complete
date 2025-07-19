package org.example;

import java.util.List;
import java.sql.PreparedStatement;

import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

// @Repository
// public class JdbcAccountDao implements AccountDao {

//     private static final String GET_ACCOUNT_SQL = "select id, amount from account where id = ?";
//     private static final String SET_AMOUNT_SQL = "update account set amount = ? where id = ?";
//     private static final String CREATE_ACCOUNT_SQL = "insert into account(amount) values(?)";
//     private static final String GET_ALL_ACCOUNTS_SQL = "select id, amount from account";

//     private final JdbcTemplate jdbcTemplate;
//     private static final RowMapper<Account> ACCOUNT_ROW_MAPPER = 
//         (rs, rowNum) -> new Account(rs.getLong("ID"), rs.getLong("AMOUNT"));

//     public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
//         this.jdbcTemplate = jdbcTemplate;
//         }

//     @Override
//     public Account addAccount(long id, long amount) {
//         jdbcTemplate.update(
//             "insert into account(id, amount) values(?, ?)",
//             id, amount
//         );

//         return new Account(id, amount);
//     }

//     @Override
//     public Account addAccount(long amount) {
//         KeyHolder keyHolder = new GeneratedKeyHolder();

//         jdbcTemplate.update(
//             con -> {
//                 PreparedStatement ps = con.prepareStatement(CREATE_ACCOUNT_SQL, new String[] {"id"});
//                 ps.setLong(1, amount);
//                 return ps;
//             },
//             keyHolder
//         );
//         return new Account(keyHolder.getKey().longValue(), amount);
//     }

//     @Override
//     public Account getAccount(long accountId) {
//         return jdbcTemplate.queryForObject(
//             GET_ACCOUNT_SQL, ACCOUNT_ROW_MAPPER, accountId
//         );
//     }

//     @Override
//     public void setAmount(long accountId, long amount) {
//         jdbcTemplate.update(
//             SET_AMOUNT_SQL, amount, accountId
//         );
//     }

//     @Override
//     public List<Account> getAllAccounts() {
//         return jdbcTemplate.query(
//             GET_ALL_ACCOUNTS_SQL, ACCOUNT_ROW_MAPPER
//         );
//     }
// }
