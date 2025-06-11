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
        Score score = new Score("Сарк Аля","математика",5);

        examination.addScore(score);

        Score score1 = examination.getScore("Сарк Аля", "математика");
        Assertions.assertEquals(score, score1);
    }

    @Test
    void getScore() {
        Score score = new Score("Сарк Аля","математика",5);
        Score score1 = new Score("Сарк Аля","русский язык",5);
        Score score2 = new Score("Сарк Аля","русский язык",4);
        Score score3 = new Score("Сарк Аля","математика",3);

        examination.addScore(score);
        examination.addScore(score1);
        examination.addScore(score2);
        examination.addScore(score3);

        Assertions.assertThrows(NoSuchElementException.class, () ->
                examination.getScore("Сарк Аля", "физ-ра"));

        Score s = examination.getScore("Сарк Аля", "математика");
        Assertions.assertEquals(s, score3);
    }

    @Test
    void getAverageForSubject() {
        Score score = new Score("Сарк Аля","математика",3);
        Score score11 = new Score("Сарк Аля","математика",5);
        Score score1 = new Score("Абвгдеев Данил","русский язык",5);
        Score score2 = new Score("Марков Марк","математика",4);
        Score score3 = new Score("Белых Леон","математика",3);

        examination.addScore(score);
        examination.addScore(score11);
        examination.addScore(score1);
        examination.addScore(score2);
        examination.addScore(score3);

        Double average = examination.getAverageForSubject("математика");

        Assertions.assertEquals(4.0, average);

        Score score4 = new Score("спанч боб","математика",3);
        examination.addScore(score4);

        Assertions.assertEquals(3.75, examination.getAverageForSubject("математика"));
    }

    @Test
    void multipleSubmissionsStudentNames() {
        examination.addScore(new Score("Сарк Аля", "математика", 5));
        examination.addScore(new Score("Сарк Аля", "математика", 4));
        examination.addScore(new Score("Абвгдеев Данил", "русский язык", 5));
        examination.addScore(new Score("Марков Марк", "математика", 3));
        examination.addScore(new Score("Марков Марк", "русский язык", 4));
        examination.addScore(new Score("Белых Леон", "математика", 5));
        examination.addScore(new Score("Белых Леон", "математика", 5));

        List<String> expected = List.of("Белых Леон", "Сарк Аля");

        Set<String> result = examination.multipleSubmissionsStudentNames();

        Assertions.assertEquals(expected, List.copyOf(result));

    }

    @Test
    void lastFiveStudentsWithExcellentMarkOnAnySubject() {
        Score score = new Score("Сарк Аля","математика",5);
        Score score1 = new Score("Абвгдеев Данил","русский язык",5);
        Score score2 = new Score("Марков Марк","математика",4);
        Score score3 = new Score("Белых Леон","математика",5);
        Score score31 = new Score("Белых Леон","русский язык",5);
        Score score4 = new Score("Сонный Александр","математика",3);
        Score score5 = new Score("Вилюш Йошек","русский язык",5);
        Score score6 = new Score("Светлых Николай","математика",5);

        List<String> expected = List.of(score1.getName(), score3.getName(), score5.getName(), score.getName(), score6.getName());

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
        Score score = new Score("Сарк Аля","математика",5);
        Score score1 = new Score("Абвгдеев Данил","русский язык",5);
        Score score2 = new Score("Марков Марк","математика",4);
        Score score3 = new Score("Белых Леон","математика",3);

        List<Score> list = List.of(score, score1, score2, score3);

        examination.addScore(score);
        examination.addScore(score1);
        examination.addScore(score2);
        examination.addScore(score3);

        Assertions.assertEquals(list.size(), examination.getAllScores().size());
        Assertions.assertNotNull(examination.getScore("Сарк Аля", "математика"));
    }
}