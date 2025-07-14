package com.contact_book.services;

import org.springframework.stereotype.Service;

import com.contact_book.dao.ContactDao;
import com.contact_book.dto.ContactDto;
import java.util.List;
import com.contact_book.models.Contact;
import com.contact_book.controllers.OperationStatus;

// ContactService - это класс, который отвечает за бизнес логику
@Service
public class ContactService {
    // contactDao - это переменная, которая хранит контакты
    private final ContactDao contactDao;

    public ContactService(ContactDao contactDao) {
        this.contactDao = contactDao;
    }

    // получаем список контактов
    public List<ContactDto> getContacts() {
        return contactDao.getContacts().stream()
        .map(ContactDto::new)
        .toList();
    }

    // получаем контакт по id
    public ContactDto getContactById(long contactId) {
        return new ContactDto(contactDao.getContactById(contactId));
    }

    // добавляем контакт
    public OperationStatus addContact(ContactDto contactDto) {
        contactDao.addContact(new Contact(
            contactDto.getFirst_name(),
            contactDto.getLast_name(),
            contactDto.getPhone_number(),
            contactDto.getEmail()));
        return OperationStatus.SUCCESS;
    }

    // обновляем контакт
    public OperationStatus updateContact(long contactId, ContactDto contactDto) {
        contactDao.updateContact(contactId, new Contact(
            contactDto.getFirst_name(),
            contactDto.getLast_name(),
            contactDto.getPhone_number(),
            contactDto.getEmail()));
        return OperationStatus.SUCCESS;
    }
}
