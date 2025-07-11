package org.example.repo;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

// Компонент для получения правильных ответов и ответов студента
@Component
public class AnswersLoader {
    private static final int MAX_TASKS = 10;
    private final Map<Integer, String> correctAnswers;
    private final Map<Integer, String> studentAnswers;

    public AnswersLoader(Environment env) {
        this.correctAnswers = loadAnswers(env, "correct");
        this.studentAnswers = loadAnswers(env, "student");
    }

    // Объект класса Environment содержит ВСЕ загруженные свойства из .properties файлов
    private Map<Integer, String> loadAnswers(Environment env, String prefix) {
        if (env == null || prefix == null || prefix.isBlank()) {
            throw new IllegalArgumentException("Окружение и/или префикс не должны быть пустыми!");
        }
        Map<Integer, String> answers = new HashMap<>();
        for (int i = 1; i <= MAX_TASKS; i++) {
            // благодаря ключу получаем значение свойства
            String key = prefix + "." + i;
            String value = env.getProperty(key);

            if (value != null) {
                // номер задания, значение
                answers.put(i, value.trim());
            } else {
                System.out.printf("Не найден ответ для %s.%d :(", prefix, i);
            }
        }
        return answers;
    }

    public Map<Integer, String> getCorrectAnswers() {
        return correctAnswers;
    }

    public Map<Integer, String> getStudentAnswers() {
        return studentAnswers;
    }
}
