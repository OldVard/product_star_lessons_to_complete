package org.example;

import org.example.repo.AnswersLoader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ResultsProcessorTest {

    @Mock
    private AnswersLoader answersLoader;

    @Test
    public void testCalculateTotalScore_AllShouldBeCorrect() {
        when(answersLoader.getCorrectAnswers())
                .thenReturn(Map.of(1, "A", 5, "B", 9, "C"));
        when(answersLoader.getStudentAnswers())
                .thenReturn(Map.of(1, "A", 5, "B", 9, "C"
                ));

        ResultsProcessor processor = new ResultsProcessor(answersLoader, 1, 2, 3);
        assertEquals(6, processor.calculateTotalScore());
    }

    @Test
    public void testCalculateTotalScore_SomeIncorrect() {
        when(answersLoader.getCorrectAnswers())
                .thenReturn(Map.of(1, "A", 2, "B", 5, "C", 9, "D"
                ));
        when(answersLoader.getStudentAnswers())
                .thenReturn(Map.of(1, "A", 2, "X", 5, "C", 9, "X"
                ));

        ResultsProcessor processor = new ResultsProcessor(answersLoader, 1, 2, 3);
        assertEquals(3, processor.calculateTotalScore());
    }

    @Test
    public void testCalculateTotalScore_EmptyAnswers() {
        when(answersLoader.getCorrectAnswers()).thenReturn(Map.of());
        when(answersLoader.getStudentAnswers()).thenReturn(Map.of());

        ResultsProcessor processor = new ResultsProcessor(answersLoader, 1, 2, 3);
        assertEquals(0, processor.calculateTotalScore());
    }

    // Подсчет групп - 1+1+2+2+3+3=12
    @Test
    public void testCalculateMaxPoints_AllGroups() {
        when(answersLoader.getCorrectAnswers())
                .thenReturn(Map.of(
                        1, "A",  // группа 1
                        4, "D",  // группа 1
                        5, "E",  // группа 2
                        8, "H",  // группа 2
                        9, "I",  // группа 3
                        10, "J"  // группа 3
                ));

        ResultsProcessor processor = new ResultsProcessor(answersLoader, 1, 2, 3);
        assertEquals(12, processor.calculateMaxPoints());
    }

    // Просто подсчет всего
    @Test
    public void testCalculateMaxPoints_10Tasks() {
        when(answersLoader.getCorrectAnswers())
                .thenReturn(Map.of(1, "A", 2, "D", 3, "E", 4, "H", 5, "I",
                        6, "B", 7, "B", 8, "B", 9, "B", 10, "B"
                ));

        ResultsProcessor processor = new ResultsProcessor(answersLoader, 1, 2, 3);
        assertEquals(18, processor.calculateMaxPoints());
    }
}
