package org.example.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@ComponentScan(basePackages = "org.example")
@PropertySources({
        @PropertySource("classpath:correct_answers.properties"),
        @PropertySource("classpath:student_answers.properties"),
        @PropertySource("classpath:application.properties")
})
public class AppConfig {
    // Класс конфигурации, который получает бины внутри указанного пакета путем сканирования,
    // а также получает свойства из указанных .properties файлов:
    // - correct_answers - все верные ответы;
    // - students_answers - бланк студента;
    // - application - максимальные баллы за правильные варианты ответов в зависимости от номера задания.
}
