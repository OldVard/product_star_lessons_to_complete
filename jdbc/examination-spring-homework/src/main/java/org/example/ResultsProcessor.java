package org.example;

import org.example.repo.AnswersLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

// ResultProcessor служит для подсчета баллов студента
@Component
public class ResultsProcessor {
    private final AnswersLoader answersLoader;
    private final int group1Score;
    private final int group2Score;
    private final int group3Score;

    public ResultsProcessor(
            AnswersLoader answersLoader,
            @Value("${task.scores.group1}") int group1Score,
            @Value("${task.scores.group2}") int group2Score,
            @Value("${task.scores.group3}") int group3Score
    ) {
        this.answersLoader = answersLoader;
        this.group1Score = group1Score;
        this.group2Score = group2Score;
        this.group3Score = group3Score;
    }

    public int calculateTotalScore() {
        Map<Integer, String> correct = answersLoader.getCorrectAnswers();
        Map<Integer, String> student = answersLoader.getStudentAnswers();
        int totalPoints = 0;

        // перебираем только те задания, которые есть в correct
        for (Map.Entry<Integer, String> entry : correct.entrySet()) {
            int taskNumber = entry.getKey(); // номер задания
            String correctAnswer = entry.getValue(); // правильный ответ

            // проверяем, что студент ответил на это задание и ответ правильный
            if (student.containsKey(taskNumber) &&
                    correctAnswer.equals(student.get(taskNumber))) {
                if (taskNumber <= 4) {
                    totalPoints += group1Score;
                } else if (taskNumber <= 8) {
                    totalPoints += group2Score;
                } else {
                    totalPoints += group3Score;
                }
            }
        }
        return totalPoints;
    }

    // Дополнительный метод просто патаму чта
    public int calculateMaxPoints() {
        return answersLoader.getCorrectAnswers().keySet().stream()
                .mapToInt(taskNumber -> {
                    if (taskNumber <= 4) return group1Score;
                    else if (taskNumber <= 8) return group2Score;
                    else return group3Score;
                })
                .sum();
    }
}