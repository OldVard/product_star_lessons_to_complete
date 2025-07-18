package org.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

// Объявляем конфигурацию свойств
// @PropertySource внедряет значения из .properties файла
@Configuration
@PropertySource("classpath:jdbc-config.properties")
public class PropertiesConfig { }
