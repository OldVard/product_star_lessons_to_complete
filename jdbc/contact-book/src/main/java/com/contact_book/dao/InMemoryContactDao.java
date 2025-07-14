package com.contact_book.dao;

import com.contact_book.models.Contact;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.contact_book.controllers.OperationStatus;

import java.util.List;

// @Repository - это аннотация, которая указывает, что этот работает с данными в памяти или из внешнего источника
@Repository
// InMemoryContactDao - это класс, который реализует интерфейс ContactDao
public class InMemoryContactDao implements ContactDao {
    // contactId - это переменная, которая хранит id контакта
    private long contactId = 1L;
    // contacts - это переменная, которая хранит все контакты
    private Map<Long, Contact> contacts = new HashMap<>();

    @Override
    public List<Contact> getContacts() {
        // возвращаем список контактов
        return contacts.values().stream().toList();
    }

    @Override
    public Contact getContactById(long contactId) {
        // фильтруем по id и находим первый соответствующий контакт
        return contacts.keySet().stream()
        .filter(id -> id == contactId)
        .findFirst()
        .map(contacts::get)
        // если контакт не найден, то выбрасываем исключение
        .orElseThrow(() -> new IllegalArgumentException("Contact with id " + contactId + " not found :("));
    }

    @Override
    public OperationStatus addContact(Contact contact) {
        // создаем новый контакт
        Contact newContact = new Contact(contact.getFirst_name(), contact.getLast_name(), contact.getPhone_number(), contact.getEmail());
        // добавляем новый контакт в мапу
        contacts.put(contactId++, newContact);
        return OperationStatus.SUCCESS;
    }

    @Override
    public OperationStatus updateContact(long contactId, Contact contact) {
        // проверяем, что контакт существует
        getContactById(contactId);
        // создаем новый контакт
        Contact newContact = new Contact(contact.getFirst_name(), contact.getLast_name(), contact.getPhone_number(), contact.getEmail());
        // обновляем контакт в мапе по id
        contacts.put(contactId, newContact);
        return OperationStatus.SUCCESS;
    }
}
