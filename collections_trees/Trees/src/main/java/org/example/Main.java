package org.example;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        ResultsBoard resultsBoard = new ResultsBoard();
        resultsBoard.addStudent("Alya", 4.2f);
        resultsBoard.addStudent("Dane", 3.0f);
        resultsBoard.addStudent("Marty", 4.9f);
        resultsBoard.addStudent("Logan", 2.8f);

        System.out.println(resultsBoard.topThree());
    }
}

class ResultsBoard {

    private TreeSet<Score> scores = new TreeSet<>();;

    public ResultsBoard() { }

    void addStudent(String name, Float score) {
        scores.add(new Score(score, name));
    }

    List<String> topThree () {
        return scores.descendingSet().stream().limit(3).map(Score::getStudentName).toList();
    }
}

final class Score implements Comparable<Score> {
    private final String studentName;
    private final Float score;

    public Score(Float score, String studentName) {
        this.studentName = studentName;
        this.score = score;
    }

    public String getStudentName() {
        return studentName;
    }

    @Override
    public int compareTo(Score o) {
        return this.score.compareTo(o.score);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Score) obj;
        return Objects.equals(this.studentName, that.studentName) &&
                Objects.equals(this.score, that.score);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentName, score);
    }

    @Override
    public String toString() {
        return "[ " + studentName + " : " + score + " ]";
    }
}