package org.example.student_management;

import java.util.*;
import java.util.stream.Collectors;

public class SemesterAssessment implements Examination {
    private static final int INITIAL_CAPACITY = 25; // например, количество студентов в одной группе
    // у студента может быть несколько сдач как по одному, так и по разным предметам
    private final Map<String, List<Score>> scores = new HashMap<>(INITIAL_CAPACITY);

    // -- добавление score
    @Override
    public void addScore(Score score) {
        scores.computeIfAbsent(score.getName(), k -> new ArrayList<>()).add(score);
    }

    // -- получение score
    @Override
    public Score getScore(String name, String subject) {
        return scores.getOrDefault(name, List.of()).stream()
                .filter(s -> s.getSubject().equals(subject))
                .reduce((first, second) -> second) // берём последний найденный
                .orElseThrow(() -> new NoSuchElementException("Сдача не найдена :("));
    }

    // -- получить среднюю оценку по предмету
    @Override
    public Double getAverageForSubject(String subject) {
        return scores.values().stream()
                .map(scoreList -> scoreList.stream()
                        .filter(s -> s.getSubject().equals(subject))
                        .reduce((first, second) -> second)
                        .orElse(null))
                .filter(Objects::nonNull)
                .mapToInt(Score::getScore)
                .average()
                .orElse(0.0);
    }

    // -- вывести имена всех студентов в (алфавитном порядке), кто сдавал несколько раз
    @Override
    public Set<String> multipleSubmissionsStudentNames() {
        return scores.entrySet().stream()
                .filter(entry -> {
                    Map<String, Long> subjectCounts = entry.getValue().stream()
                            .collect(Collectors.groupingBy(Score::getSubject, Collectors.counting()));
                    return subjectCounts.values().stream().anyMatch(count -> count > 1);
                })
                .map(Map.Entry::getKey)
                .sorted()
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public Set<String> lastFiveStudentsWithExcellentMarkOnAnySubject() {
        return scores.values().stream()
                .flatMap(List::stream)
                .filter(s -> s.getScore() == 5)
                .map(Score::getName)
                .distinct()
                .sorted()
                .limit(5)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    // получить все сдачи (?) все существующие сдачи - сданные предметы?
    @Override
    public Collection<Score> getAllScores() {
        return scores.values().stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }
}
