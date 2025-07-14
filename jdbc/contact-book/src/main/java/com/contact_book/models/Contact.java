package com.contact_book.models;

// Данный класс - класс контактов, состоящий из:
// - Имени
// - Фамилии
// - Телефонного номера
// - Email
// Также переопределен метод toString для вывода информации о контакте
public class Contact {
    private String first_name;
    private String last_name;
    private String phone_number;
    private String email;

    public Contact(String first_name, String last_name, String phone_number, String email) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone_number = phone_number;
        this.email = email;
    }

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

    @Override
    public String toString() {
        return "Contact [first_name=" + first_name + ", last_name=" + last_name + ", phone_number=" + phone_number
                + ", email=" + email + "]";
    }
}
