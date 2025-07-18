package org.example.dao;

import org.example.models.Contact;

import java.util.List;

// Объявляем интерфейс ContactDao
public interface ContactDao {
    // получить все контакты
    List<Contact> getAllContacts();
    // получить контакт по id
    Contact getContactById(long contactId);
    // добавить контакт
    Contact addContact(Contact contact);
    // обновить телефонный номер контакта
    void updatePhoneNumberById(long contactId, String phoneNumber);
    // обновить email контакта
    void updateEmailById(long contactId, String email);
    // удалить контакт по id
    void deleteContactById(long contactId);
}
