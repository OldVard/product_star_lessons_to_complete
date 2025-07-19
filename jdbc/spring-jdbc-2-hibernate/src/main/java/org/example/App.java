package org.example;

import java.util.List;
import java.util.stream.Collectors;

import org.example.analytics.CountryStats;
import org.example.config.ApplicationConfig;
import org.example.dao.CityDao;
import org.example.dao.PersonDao;
import org.example.models.City;
import org.example.models.Gender;
import org.example.models.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Hello world!
 *
 */
public class App {

    private static final List<City> CITIES = List.of(
            new City("Moscow", "Russia"),
            new City("Rostov", "Russia"),
            new City("Vancouver", "Canada"),
            new City("Paris", "France"));

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        prepareData(context);

        SessionFactory sessionFactory = context.getBean(SessionFactory.class);

        try (Session session = sessionFactory.openSession()) {

            // 1. Получаем все города
            Query<City> query = session.createQuery("From City c");
            List<City> cities = query.list();
            System.out.println("--- Cities: " + cities);

            // 2. Получаем все города в России
            query = session.createQuery("From City c where c.country = :country");
            query.setParameter("country", "Russia");
            cities = query.list();
            System.out.println("--- Cities in Russia: " + cities);

            Query<Object[]> query3 = session.createQuery("select count(*), c.country from City c group by c.country");
            List<CountryStats> citiesCountByCountry = query3.getResultList().stream()
                    .map(CountryStats::fromObject)
                    .collect(Collectors.toList());
            System.out.println("--- Cities count by country: " + citiesCountByCountry);

            // 3. Получаем всех мужчин старше 18 лет
            Query<Person> query2 = session.createQuery("from Person p where p.gender = 'MALE' and p.age > 18");
            List<Person> people = query2.list();
            System.out.println("--- Men older than 18: " + people);

            // 4. Получаем город, в котором живет Иван
            query = session.createQuery("select p.city from Person p inner join City c on p.city.id = c.id");
            City city = query.getSingleResult();
            System.out.println("--- Ivan's city: " + city);
        }
    }

    private static void prepareData(ApplicationContext context) {
        PersonDao personDao = context.getBean(PersonDao.class);
        CityDao cityDao = context.getBean(CityDao.class);

        cityDao.saveAllCities(CITIES);
        List<City> cities = cityDao.getAllCities();

        Person person = person(cities.get(0));
        personDao.savePerson(person);
    }

    private static Person person(City city) {
        Person person = new Person();
        person.setName("Ivan");
        person.setSurname("Ivanov");
        person.setAge(25);
        person.setGender(Gender.MALE);
        person.setCity(city);

        return person;
    }
}
