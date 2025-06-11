package org.example.student_management;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

class SemesterAssessmentTest {

    private Examination examination;

    @BeforeEach
    void setUp() {
        examination = new SemesterAssessment();
    }

    @Test
    void addScore() {
        Score score = new Score("Alya","math",5);

        examination.addScore(score);

        Score score1 = examination.getScore("Alya", "math");
        Assertions.assertEquals(score, score1);
    }

    @Test
    void getScore() {
        Score score = new Score("Alya","math",5);
        Score score1 = new Score("Alya","rus",5);
        Score score2 = new Score("Alya","rus",4);
        Score score3 = new Score("Alya","math",3);

        examination.addScore(score);
        examination.addScore(score1);
        examination.addScore(score2);
        examination.addScore(score3);

        Assertions.assertThrows(NoSuchElementException.class, () ->
                examination.getScore("Alya", "pe"));

        Score s = examination.getScore("Alya", "math");
        Assertions.assertEquals(s, score3);
    }

    @Test
    void getAverageForSubject() {
        Score score = new Score("Alya","math",5);
        Score score1 = new Score("Dane","rus",5);
        Score score2 = new Score("Marty","math",4);
        Score score3 = new Score("Logan","math",3);

        examination.addScore(score);
        examination.addScore(score1);
        examination.addScore(score2);
        examination.addScore(score3);

        Double average = examination.getAverageForSubject("math");

        Assertions.assertEquals(4.0, average);
    }

    @Test
    void multipleSubmissionsStudentNames() {
        examination.addScore(new Score("Alya", "math", 5));
        examination.addScore(new Score("Alya", "math", 4));
        examination.addScore(new Score("Dane", "rus", 5));
        examination.addScore(new Score("Marty", "math", 3));
        examination.addScore(new Score("Marty", "rus", 4));
        examination.addScore(new Score("Logan", "math", 5));
        examination.addScore(new Score("Logan", "math", 5));

        List<String> expected = List.of("Alya", "Logan");

        Set<String> result = examination.multipleSubmissionsStudentNames();

        Assertions.assertEquals(expected, List.copyOf(result));

    }

    @Test
    void lastFiveStudentsWithExcellentMarkOnAnySubject() {
        Score score = new Score("Alya","math",5);
        Score score1 = new Score("Dane","rus",5);
        Score score2 = new Score("Marty","math",4);
        Score score3 = new Score("Logan","math",5);
        Score score31 = new Score("Logan","rus",5);
        Score score4 = new Score("Enzo","math",3);
        Score score5 = new Score("Yoshek","rus",5);
        Score score6 = new Score("Connor","math",5);

        List<String> expected = List.of(score.getName(), score6.getName(), score1.getName(), score3.getName(), score5.getName());

        examination.addScore(score);
        examination.addScore(score1);
        examination.addScore(score2);
        examination.addScore(score3);
        examination.addScore(score31);
        examination.addScore(score4);
        examination.addScore(score5);
        examination.addScore(score6);

        Set<String> scores = examination.lastFiveStudentsWithExcellentMarkOnAnySubject();

        Assertions.assertEquals(expected, List.copyOf(scores));
    }

    @Test
    void getAllScores() {
        Score score = new Score("Alya","math",5);
        Score score1 = new Score("Dane","rus",5);
        Score score2 = new Score("Marty","math",4);
        Score score3 = new Score("Logan","math",3);

        List<Score> list = List.of(score, score1, score2, score3);

        examination.addScore(score);
        examination.addScore(score1);
        examination.addScore(score2);
        examination.addScore(score3);

        Assertions.assertEquals(list.size(), examination.getAllScores().size());
        Assertions.assertNotNull(examination.getScore("Alya", "math"));
    }
}