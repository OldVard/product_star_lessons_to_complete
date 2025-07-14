package com.contact_book.dao;

import com.contact_book.controllers.OperationStatus;
import com.contact_book.models.Contact;
import java.util.List;

// ContactDao - это интерфейс, который определяет методы для работы с контактами
public interface ContactDao {
    // 1. Получение всех контактов
    List<Contact> getContacts();
    // 2. Получение контакта по ID
    Contact getContactById(long contactId);
    // 3. Добавление нового контакта
    OperationStatus addContact(Contact contact);
    // 4. Обновление контакта по id (+ содержимое нового контакта)
    OperationStatus updateContact(long contactId, Contact contact);
}
