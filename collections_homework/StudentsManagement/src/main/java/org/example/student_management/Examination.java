package org.example.student_management;

import java.util.Collection;
import java.util.Set;

public interface Examination {
    void addScore(Score score); // добавить сдачу
    Score getScore(String name, String subject); //получить сдачу по имени и предмету
    Double getAverageForSubject(String subject); // получить средний бал по предмету
    Set<String> multipleSubmissionsStudentNames(); //вывод всех студентов, сдававших более одного раза
    Set<String> lastFiveStudentsWithExcellentMarkOnAnySubject(); // вывод последних пяти студентов, сдавших на отлично
    Collection<Score> getAllScores(); //вывод всех сданных предметов
}
