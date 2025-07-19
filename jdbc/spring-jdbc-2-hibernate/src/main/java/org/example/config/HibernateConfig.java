package org.example.config;

import org.example.models.City;
import org.example.models.Person;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.hibernate.cfg.Configuration;

@org.springframework.context.annotation.Configuration
public class HibernateConfig {

    // @Value("${hibernate.dialect}")
    // private String sqlDialect;

    // @Value("${hibernate.show_sql}")
    // private String showSql;

    // @Value("${hibernate.format_sql}")
    // private String formatSql;

    @Bean
    public SessionFactory sessionFactory() {
        Configuration config = new Configuration()
                // .setProperties(hibernateProperties())
                .addAnnotatedClass(Person.class)
                .addAnnotatedClass(City.class)
                .configure();

        return config.buildSessionFactory();
    }

    // private Properties hibernateProperties() {
    // Properties properties = new Properties();
    // properties.setProperty(Environment.DIALECTm )
    // }

}
