package com.contact_book.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.contact_book.dto.ContactDto;
import com.contact_book.services.ContactService;


import java.util.List;

// ContactController - это класс контроллера, который обрабатывает запросы, связанные с контактами
// @RestController - это аннотация, которая указывает, что этот класс является контроллером,
// предоставляющим доступ к ресурсам через HTTP-методы
@RestController
// @RequestMapping("/contacts") - это аннотация, которая указывает базовый адресс для всех методов контроллера
@RequestMapping("/contacts")
public class ContactController {

    // ContactService отвечает за бизнес логику
    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    // 1. Получение всех контактов
    @GetMapping
    public List<ContactDto> getContacts() {
        return contactService.getContacts();
    }

    // 2. Получение контакта по ID
    @GetMapping("/{id}")
    public ContactDto getContactById(@PathVariable long id) {
        return contactService.getContactById(id);
    }

    // 3. Добавление нового контакта
    @PostMapping
    public OperationStatus addContact(@RequestBody ContactDto contactDto) {
        return contactService.addContact(contactDto);
    }

    // 4. Обновление контакта по id (+ содержимое нового контакта)
    @PutMapping("/{id}")
    public OperationStatus updateContact(@PathVariable long id, @RequestBody ContactDto contactDto) {
        return contactService.updateContact(id, contactDto);
    }
}
