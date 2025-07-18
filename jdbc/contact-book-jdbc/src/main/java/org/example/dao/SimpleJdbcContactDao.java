package org.example.dao;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.example.models.Contact;
import java.util.List;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.GeneratedKeyHolder;

// Имплементация интерфейса ContactDao
@Repository
public class SimpleJdbcContactDao implements ContactDao {

    // Используем NamedParameterJdbcTemplate для работы с параметрами
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public SimpleJdbcContactDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Запросы
    private static final String GET_ALL_CONTACTS_QUERY = "select * from contacts";
    private static final String GET_CONTACT_BY_ID_QUERY = "select * from contacts where id = :id";
    private static final String ADD_CONTACT_QUERY = "insert into contacts (first_name, last_name, phone_number, email) values (:first_name, :last_name, :phone_number, :email)";
    private static final String UPDATE_PHONE_BY_ID_QUERY = "update contacts set phone_number = :phone_number where id = :id";
    private static final String UPDATE_EMAIL_BY_ID_QUERY = "update contacts set email = :email where id = :id";
    private static final String DELETE_CONTACT_BY_ID_QUERY = "delete from contacts where id = :id";

    // Маппер для преобразования результата запроса в объект Contact
    private final RowMapper<Contact> CONTACT_ROW_MAPPER = (rs, rowNum) -> new Contact(
            rs.getLong("id"),
            rs.getString("first_name"),
            rs.getString("last_name"),
            rs.getString("phone_number"),
            rs.getString("email"));

    // 1. Получить все контакты
    @Override
    public List<Contact> getAllContacts() {
        return jdbcTemplate.query(
                GET_ALL_CONTACTS_QUERY, CONTACT_ROW_MAPPER);
    }

    // 2. Получить контакт по id
    @Override
    public Contact getContactById(long id) {
        return jdbcTemplate.queryForObject(
                GET_CONTACT_BY_ID_QUERY,
                new MapSqlParameterSource("id", id),
                CONTACT_ROW_MAPPER);
    }

    // 3. Добавить контакт
    @Override
    public Contact addContact(Contact contact) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                ADD_CONTACT_QUERY, new MapSqlParameterSource()
                        .addValue("first_name", contact.getFirstName())
                        .addValue("last_name", contact.getLastName())
                        .addValue("phone_number", contact.getPhoneNumber())
                        .addValue("email", contact.getEmail()),
                keyHolder,
                new String[] { "id" });

        return new Contact(
                keyHolder.getKey().longValue(),
                contact.getFirstName(),
                contact.getLastName(),
                contact.getPhoneNumber(),
                contact.getEmail());
    }

    // 4. Обновить телефонный номер контакта
    @Override
    public void updatePhoneNumberById(long contactId, String newPhone) {
        jdbcTemplate.update(
                UPDATE_PHONE_BY_ID_QUERY, new MapSqlParameterSource()
                        .addValue("phone_number", newPhone)
                        .addValue("id", contactId));
    }

    // 5. Обновить email контакта
    @Override
    public void updateEmailById(long contactId, String newEmail) {
        jdbcTemplate.update(
                UPDATE_EMAIL_BY_ID_QUERY, new MapSqlParameterSource()
                        .addValue("email", newEmail)
                        .addValue("id", contactId));
    }

    // 6. Удалить контакт по id
    @Override
    public void deleteContactById(long contactId) {
        jdbcTemplate.update(
                DELETE_CONTACT_BY_ID_QUERY,
                new MapSqlParameterSource("id", contactId));
    }
}