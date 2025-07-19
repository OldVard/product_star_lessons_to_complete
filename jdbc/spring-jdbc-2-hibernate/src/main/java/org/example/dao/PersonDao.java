package org.example.dao;

import java.util.List;

import org.example.models.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public class PersonDao {
    private final SessionFactory sessionFactory;

    public PersonDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public long savePerson(Person person) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            long personId = (long) session.save(person);
            transaction.commit();

            return personId;
        }
    }

    public Person getPerson(long personId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Person.class, personId);
        }
    }

    public void deletePerson(long personId) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Person person = getPerson(personId);

            if (person != null) {
                session.delete(person);
            }

            transaction.commit();
        }
    }

    public void updatePerson(Person person) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            session.update(person);
            transaction.commit();
        }
    }

    public List<Person> getAllPeopleByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Person where name = :name", Person.class)
                    .setParameter("name", name).getResultList();
        }
    }
}
