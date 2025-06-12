package org.example.student_management;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

class SimpleCachedAnalyticsTest {

    private final MockSemesterAssessment examination = new MockSemesterAssessment();
    private final SimpleCachedAnalytics cached = new SimpleCachedAnalytics(examination);

    @Test
    void callOnceForAverageMarkForSubject() {

        Double averageBySubject = cached.getAverageMarkForSubject("математика");
        cached.getAverageMarkForSubject("математика");
        cached.getAverageMarkForSubject("математика");
        cached.getAverageMarkForSubject("математика");
        cached.getAverageMarkForSubject("математика");
        cached.getAverageMarkForSubject("математика");

        Assertions.assertEquals(4.333333, averageBySubject, 0.0001);
        Assertions.assertEquals(1, examination.calls);
    }

    @Test
    void callManyTimesForAverageMarkForSubject() {

        cached.getAverageMarkForSubject("математика");
        cached.getAverageMarkForSubject("русский язык");
        cached.getAverageMarkForSubject("русский язык");
        cached.getAverageMarkForSubject("математика");
        cached.getAverageMarkForSubject("математика");

        Assertions.assertEquals(2, examination.calls);

        cached.getAverageMarkForSubject("физ-ра");

        Assertions.assertEquals(3, examination.calls);
    }

    @Test
    void testCache() {
        cached.getAverageMarkForSubject("математика"); // кэшируем
        cached.invalidateSubject("математика");       // инвалидируем
        cached.getAverageMarkForSubject("математика"); // пересчёт

        Assertions.assertEquals(2, examination.calls);
    }

    private class MockSemesterAssessment implements Examination {
        private int calls = 0;

        @Override
        public void addScore(Score score) {
            examination.addScore(score);
            cached.invalidateSubject(score.getSubject().name());
        }

        @Override
        public Score getScore(String name, String subject) {
            return null;
        }

        @Override
        public Double getAverageForSubject(String subject) {
            calls++;

            Subject sub = new Subject(subject);

            List<Score> scores = List.of(
                    new Score(new StudentKey("Аля", "Сарк"), new Subject("математика"), 4),
                    new Score(new StudentKey("Аля", "Сарк"), new Subject("математика"), 5),
                    new Score(new StudentKey("Данил", "Абвгдеев"), new Subject("русский язык"), 5),
                    new Score(new StudentKey("Марк", "Марков"), new Subject("математика"), 3),
                    new Score(new StudentKey("Марк", "Марков"), new Subject("математика"), 3),
                    new Score(new StudentKey("Леон", "Белых"), new Subject("математика"), 5),
                    new Score(new StudentKey("Леон", "Белых"), new Subject("математика"), 5)
            );

            // Используем LinkedHashMap для сохранения порядка вставки
            Map<StudentKey, Score> lastSubmissions = new LinkedHashMap<>();

            for (Score score : scores) {
                if (score.getSubject().equals(sub)) {
                    lastSubmissions.put(score.student(), score); // последняя перезапишет предыдущую
                }
            }

            return lastSubmissions.values().stream()
                    .mapToDouble(Score::getScore)
                    .average()
                    .orElse(0.0);
        }

        @Override
        public Set<String> multipleSubmissionsStudentNames() {
            return Set.of();
        }

        @Override
        public Set<String> lastFiveStudentsWithExcellentMarkOnAnySubject() {
            return Set.of();
        }

        @Override
        public Collection<Score> getAllScores() {
            return List.of();
        }
    }
}