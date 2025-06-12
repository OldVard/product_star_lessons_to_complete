package org.example.student_management;

import java.util.Objects;

public record Score(StudentKey student, Subject subject, int score) {
    public Subject getSubject() {
        return subject;
    }

    public StudentKey getStudent() {
        return student;
    }

    public int getScore() {
        return score;
    }
}
