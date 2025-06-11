package org.example.student_management;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Set;

class SimpleCachedAnalyticsTest {

    private final MockSemesterAssessment examination = new MockSemesterAssessment();
    private final Analytics cached = new SimpleCachedAnalytics(examination);

    @Test
    void callOnceForAverageMarkForSubject() {

        Double averageBySubject = cached.getAverageMarkForSubject("математика");
        cached.getAverageMarkForSubject("математика");
        cached.getAverageMarkForSubject("математика");
        cached.getAverageMarkForSubject("математика");
        cached.getAverageMarkForSubject("математика");
        cached.getAverageMarkForSubject("математика");

        Assertions.assertEquals(4.4, averageBySubject);
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

    private class MockSemesterAssessment implements Examination {
        private int calls = 0;

        @Override
        public void addScore(Score score) {

        }

        @Override
        public Score getScore(String name, String subject) {
            return null;
        }

        @Override
        public Double getAverageForSubject(String subject) {
            calls++;
            Score score = new Score("Сарк Аля", "математика", 5);
            Score score1 = new Score("Сарк Аля", "математика", 4);
            Score score2 = new Score("Абвгдеев Данил", "русский язык", 5);
            Score score3 = new Score("Марков Марк", "математика", 3);
            Score score4 = new Score("Марков Марк", "русский язык", 4);
            Score score5 = new Score("Белых Леон", "математика", 5);
            Score score6 = new Score("Белых Леон", "математика", 5);

            List<Score> scores = List.of(score, score1, score2, score3, score4, score5, score6);

            return scores.stream()
                    .filter(s -> s.getSubject().equals(subject))
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