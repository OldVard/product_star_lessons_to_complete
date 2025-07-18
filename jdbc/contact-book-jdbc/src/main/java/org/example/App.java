package org.example;

import org.example.config.ApplicationConfig;
import org.example.dao.ContactDao;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.example.models.Contact;
import java.util.List;

public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                ApplicationConfig.class);

        // Получаем бин ContactDao из контекста
        ContactDao contactDao = context.getBean(ContactDao.class);
        // Добавляем контакты
        contactDao.addContact(new Contact("John", "Doe", "1234567890", "jd@example.com"));
        contactDao.addContact(new Contact("William", "Williams", "0987654321", "www@example.com"));

        System.out.println("Получить контакт по id");
        Contact contact = contactDao.getContactById(2L);
        prettyContactPrint(contact);

        System.out.println("------------------------------");

        System.out.println("Получить все контакты");
        List<Contact> contacts = contactDao.getAllContacts();
        prettyContactListPrint(contacts);

        System.out.println("------------------------------");

        System.out.println("Обновить телефонный номер контакта");
        contactDao.updatePhoneNumberById(2L, "6667778889");
        contact = contactDao.getContactById(2L);
        prettyContactPrint(contact);

        System.out.println("------------------------------");

        System.out.println("Обновить email контакта");
        contactDao.updateEmailById(2L, "JANE@example.com");
        contact = contactDao.getContactById(2L);
        prettyContactPrint(contact);

        System.out.println("Получить все контакты");
        contacts = contactDao.getAllContacts();
        prettyContactListPrint(contacts);

        System.out.println("------------------------------");

        System.out.println("После удаления контакта");
        contactDao.deleteContactById(1L);
        contacts = contactDao.getAllContacts();
        prettyContactListPrint(contacts);

        // Закрываем контекст
        context.close();
    }

    // Вспомогательное
    private static void prettyContactPrint(Contact contact) {
        System.out.printf("[%d] %s %s, %s, email: %s\n",
                contact.getId(), contact.getLastName(), contact.getFirstName(), contact.getPhoneNumber(),
                contact.getEmail());
    }

    private static void prettyContactListPrint(List<Contact> contacts) {
        for (Contact contact : contacts) {
            prettyContactPrint(contact);
        }
    }
}
