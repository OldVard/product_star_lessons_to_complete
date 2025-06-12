package org.example.student_management;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashSet;
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
        Score score = new Score(new StudentKey("Аля", "Сарк"), new Subject("математика"), 5);

        examination.addScore(score);

        Score score1 = examination.getScore("Аля Сарк", "математика");
        Assertions.assertEquals(score, score1);

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                examination.addScore(new Score(new StudentKey("Валерия", "Сушенко"),
                        new Subject("математика"), 1)));
    }

    @Test
    void getScore() {
        Score score1 = new Score(new StudentKey("Аля", "Сарк"), new Subject("математика"), 5);
        Score score2 = new Score(new StudentKey("Аля", "Сарк"), new Subject("русский язык"), 5);
        Score score3 = new Score(new StudentKey("Аля", "Сарк"), new Subject("русский язык"), 4);
        Score score4 = new Score(new StudentKey("Аля", "Сарк"), new Subject("математика"), 3);

        examination.addScore(score1);
        examination.addScore(score2);
        examination.addScore(score3);
        examination.addScore(score4);

        Assertions.assertThrows(StudentNotFoundException.class, () ->
                examination.getScore("Аля Сарк", "физ-ра"));

        Score s = examination.getScore("Аля Сарк", "математика");
        Assertions.assertEquals(score4, s);
    }

    @Test
    void getAverageForSubject() {
        examination.addScore(new Score(new StudentKey("Аля", "Сарк"), new Subject("математика"), 3));
        examination.addScore(new Score(new StudentKey("Аля", "Сарк"), new Subject("математика"), 5));
        examination.addScore(new Score(new StudentKey("Данил", "Абвгдеев"), new Subject("русский язык"), 5));
        examination.addScore(new Score(new StudentKey("Марк", "Марков"), new Subject("математика"), 4));
        examination.addScore(new Score(new StudentKey("Леон", "Белых"), new Subject("математика"), 3));

        Assertions.assertEquals(4.0, examination.getAverageForSubject("математика"));

        examination.addScore(new Score(new StudentKey("Боб", "Спанч"), new Subject("математика"), 3));

        Assertions.assertEquals(3.75, examination.getAverageForSubject("математика"));
    }

    @Test
    void multipleSubmissionsStudentNames() {
        examination.addScore(new Score(new StudentKey("Аля", "Сарк"), new Subject("математика"), 5));
        examination.addScore(new Score(new StudentKey("Аля", "Сарк"), new Subject("математика"), 4));
        examination.addScore(new Score(new StudentKey("Данил", "Абвгдеев"), new Subject("русский язык"), 5));
        examination.addScore(new Score(new StudentKey("Марк", "Марков"), new Subject("математика"), 3));
        examination.addScore(new Score(new StudentKey("Марк", "Марков"), new Subject("русский язык"), 4));
        examination.addScore(new Score(new StudentKey("Леон", "Белых"), new Subject("математика"), 5));
        examination.addScore(new Score(new StudentKey("Леон", "Белых"), new Subject("математика"), 5));

        Set<String> result = examination.multipleSubmissionsStudentNames();

        Set<String> expected = Set.of("Аля Сарк", "Леон Белых", "Марк Марков");

        Assertions.assertEquals(expected, result);
    }

    @Test
    void multipleSubmissionsStudentNamesIfThereIsNoSuch() {
        Set<String> result = examination.multipleSubmissionsStudentNames();
        Assertions.assertEquals(Set.of(), result);
    }

    @Test
    void lastFiveStudentsWithExcellentMarkOnAnySubject() {
        examination.addScore(new Score(new StudentKey("Аля", "Сарк"), new Subject("математика"), 5));
        examination.addScore(new Score(new StudentKey("Данил", "Абвгдеев"), new Subject("русский язык"), 5));
        examination.addScore(new Score(new StudentKey("Марк", "Марков"), new Subject("математика"), 4));
        examination.addScore(new Score(new StudentKey("Леон", "Белых"), new Subject("математика"), 5));
        examination.addScore(new Score(new StudentKey("Леон", "Белых"), new Subject("русский язык"), 5));
        examination.addScore(new Score(new StudentKey("Александр", "Сонный"), new Subject("математика"), 3));
        examination.addScore(new Score(new StudentKey("Йошек", "Вилюш"), new Subject("русский язык"), 5));
        examination.addScore(new Score(new StudentKey("Николай", "Светлых"), new Subject("математика"), 5));
        examination.addScore(new Score(new StudentKey("Виталий", "Солин"), new Subject("математика"), 5));
        examination.addScore(new Score(new StudentKey("Алексей", "Соколов"), new Subject("математика"), 5));

        Set<String> result = examination.lastFiveStudentsWithExcellentMarkOnAnySubject();

//        Set<String> expected = new LinkedHashSet<>(List.of("Алексей Соколов", "Виталий Солин", "Николай Светлых", "Йошек Вилюш", "Леон Белых"));
        Set<String> expected = new LinkedHashSet<>(List.of("Леон Белых", "Йошек Вилюш", "Николай Светлых", "Виталий Солин", "Алексей Соколов"));

        Assertions.assertEquals(expected, result);
    }

    @Test
    void getAllScores() {
        Score score1 = new Score(new StudentKey("Аля", "Сарк"), new Subject("математика"), 5);
        Score score2 = new Score(new StudentKey("Данил", "Абвгдеев"), new Subject("русский язык"), 5);
        Score score3 = new Score(new StudentKey("Марк", "Марков"), new Subject("математика"), 4);
        Score score4 = new Score(new StudentKey("Леон", "Белых"), new Subject("математика"), 3);

        List<Score> list = List.of(score1, score2, score3, score4);

        examination.addScore(score1);
        examination.addScore(score2);
        examination.addScore(score3);
        examination.addScore(score4);

        Assertions.assertEquals(list.size(), examination.getAllScores().size());

        Assertions.assertNotNull(examination.getScore("Аля Сарк", "математика"));
    }
}