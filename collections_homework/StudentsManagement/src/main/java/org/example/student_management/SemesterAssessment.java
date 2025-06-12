package org.example.student_management;

import java.util.*;
import java.util.stream.Collectors;

public class SemesterAssessment implements Examination {
    private static final int INITIAL_CAPACITY = 25;
    private final Map<StudentKey, Map<Subject, Score>> scores = new HashMap<>(INITIAL_CAPACITY);
    // Данный список нужен при подсчете повторныйх сдач, раз уж оригинальная
    // структура данных хранит только одну сдачу на студента, даже если их было несколько
    private final List<Score> allSubmissions = new ArrayList<>();

    // -- добавление score
    @Override
    public void addScore(Score score) {
        if(score.getScore() < 2 || score.getScore() > 5) {
            throw new IllegalArgumentException("Оценка студента не может быть меньше 2 (плохо) или больше 5 (отлично)");
        }
        allSubmissions.add(score); // добавляем в список
        // если ключа нет - создаем пустую мапу в значении по ключу.
        // Добавляем в нее название предмета (ключ) и саму сдачу (значение)
        scores.computeIfAbsent(score.getStudent(), k -> new HashMap<>()) //
                .put(score.getSubject(), score);
    }

    // -- получение score
    @Override
    public Score getScore(String name, String subject) {
        StudentKey key = toStudentKey(name);
        Subject sub = new Subject(subject);
        Map<Subject, Score> subjectScoreMap = scores.get(key); // ищем по ключу StudentKey

        // если null или не соответствует предмет
        if (subjectScoreMap == null || !subjectScoreMap.containsKey(sub)) {
            throw new StudentNotFoundException("Сдача не найдена :(");
        }
        return subjectScoreMap.get(sub);
    }

    // -- получить среднюю оценку по предмету
    @Override
    public Double getAverageForSubject(String subjectName) {
        Subject subject = new Subject(subjectName);
        return scores.values().stream()
                .map(m -> m.get(subject)) //получаем score
                .filter(Objects::nonNull)
                .mapToInt(Score::getScore) // берем только значения оценок
                .average()
                .orElse(0.0);
    }

    // -- вывести имена всех студентов, кто сдавал несколько раз (любой предмет)
    @Override
    public Set<String> multipleSubmissionsStudentNames() {
        // вот здесь используем список
        return allSubmissions.stream()
                .collect(Collectors.groupingBy( // группируем в мапу по имени студента и подсчитываем
                        Score::getStudent,
                        Collectors.counting()
                ))
                .entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .map(entry -> formatName(entry.getKey()))
                .sorted()
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public Set<String> lastFiveStudentsWithExcellentMarkOnAnySubject() {
        ListIterator<Score> iterator = allSubmissions.listIterator(allSubmissions.size());
        LinkedHashSet<String> result = new LinkedHashSet<>();

        while (iterator.hasPrevious() && result.size() < 5) {
            Score score = iterator.previous();
            if (score.getScore() == 5) {
                result.add(formatName(score.getStudent()));
            }
        }

        // на случай, если нужно чтобы выдавались 5 последних сдавших отличников,
        // не проблема! просто надо создать список, развернуть его и передать в новый связный сет
        // в порядке от самого *раннего* к самому *позднему* сдавшему.
//        List<String> reversed = new ArrayList<>(result);
//        Collections.reverse(reversed);
//        return new LinkedHashSet<>(reversed);
        // Если можно брать порядок с самого запоздавшего к самому первому, то вот актуальный вариант
        return result;
    }

    @Override
    public Collection<Score> getAllScores() {
        return scores.values().stream()
                .flatMap(m -> m.values().stream())
                .collect(Collectors.toList());
    }

    // -- преобразования --
    // Чтобы из name можно было получить StudentKey (имя + фамилия)
    private static StudentKey toStudentKey(String fullName) {
        String[] parts = fullName.split(" ");
        if (parts.length != 2) throw new IllegalArgumentException("Пожалуйста, введите имя в формате 'Имя Фамилия'");
        return new StudentKey(parts[0], parts[1]);
    }

    private static String formatName(StudentKey key) {
        return key.name() + " " + key.lastName();
    }
}