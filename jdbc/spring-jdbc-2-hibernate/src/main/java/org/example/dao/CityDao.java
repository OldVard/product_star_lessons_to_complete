package org.example.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.example.models.City;
import org.example.models.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public class CityDao {

    private final SessionFactory sessionFactory;

    public CityDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    // Получить город по id
    public City getCity(long cityId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(City.class, cityId);
        }
    }

    // Получить все города
    public List<City> getAllCities() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from City", City.class).getResultList();
        }
    }

    // Сохранить все города
    public void saveAllCities(Collection<City> cities) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            cities.forEach(session::persist);

            transaction.commit();
        }
    }

    // Получить всех людей, живущих в городе с id
    public List<Person> getAllPeopleByCityId(long cityId) {
        try (Session session = sessionFactory.openSession()) {
            City city = getCity(cityId);

            return new ArrayList<>(city.getPeople());
        }

    }

}
