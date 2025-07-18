package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

// Объявляем конфигурацию JDBC
// @Configuration - объявляем класс как конфигурацию
@Configuration
public class JdbcConfig {

    // @Value - внедряем значения из application.properties
    @Value("${jdbc.url}")
    private String jdbcUrl;

    @Value("${jdbc.driverClassName}")
    private String driverClassName;

    @Value("${jdbc.username}")
    private String jdbcUsername;

    @Value("${jdbc.password}")
    private String jdbcPassword;

    @Value("${jdbc.initialSize}")
    private int jdbcInitialSize;

    @Value("${jdbc.maxActive}")
    private int jdbcMaxActive;

    // Объявляем бин dataSource
    // DataSource - интерфейс для работы с источником данных
    // BasicDataSource - имплементация DataSource
    @Bean
    public DataSource dataSourse() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(jdbcUrl);
        dataSource.setUsername(jdbcUsername);
        dataSource.setPassword(jdbcPassword);
        dataSource.setDriverClassName(driverClassName);
        dataSource.setInitialSize(jdbcInitialSize);
        dataSource.setMaxTotal(jdbcMaxActive);

        return dataSource;
    }

    // Щбъявляем бин jdbcTemplate
    // NamedParameterJdbcTemplate позволяет использовать именованные параметры в запросах
    @Bean
    public NamedParameterJdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }
}
