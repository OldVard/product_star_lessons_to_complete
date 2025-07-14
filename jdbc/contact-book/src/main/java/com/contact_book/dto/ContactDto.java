package com.contact_book.dto;

import com.contact_book.models.Contact;
import com.fasterxml.jackson.annotation.JsonProperty;

// ContactDto - это класс, который используется для передачи данных между слоями приложения
public class ContactDto {
    @JsonProperty("first_name")
    private String first_name;
    @JsonProperty("last_name")
    private String last_name;
    @JsonProperty("phone_number")
    private String phone_number;
    @JsonProperty("email")
    private String email;

    public ContactDto(Contact contact) {
        this.first_name = contact.getFirst_name();
        this.last_name = contact.getLast_name();
        this.phone_number = contact.getPhone_number();
        this.email = contact.getEmail();
    }

    // пустой публичный конструктор без аргментов для того, чтобы создать объект, а потом заполнить его данными
    public ContactDto() { }

    // геттеры для получения данных
    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getEmail() {
        return email;
    }
}
