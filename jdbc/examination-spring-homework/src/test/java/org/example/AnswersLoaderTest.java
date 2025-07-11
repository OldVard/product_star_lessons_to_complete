package org.example;

import org.example.repo.AnswersLoader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AnswersLoaderTest {

    @Test
    public void test_ShouldReturn1Points() {
        AnswersLoader answersLoader = mock(AnswersLoader.class);
        when(answersLoader.getCorrectAnswers()).thenReturn(Map.of(1, "A"));
        when(answersLoader.getStudentAnswers()).thenReturn(Map.of(1, "A"));

        ResultsProcessor processor = new ResultsProcessor(answersLoader, 1, 2, 3);
        assertEquals(1, processor.calculateTotalScore());
    }

    @Test
    public void test_ShouldReturn0Points() {
        AnswersLoader answersLoader = mock(AnswersLoader.class);
        when(answersLoader.getCorrectAnswers()).thenReturn(Map.of(5, "B"));
        when(answersLoader.getStudentAnswers()).thenReturn(Map.of(5, "C"));

        ResultsProcessor processor = new ResultsProcessor(answersLoader, 1, 2, 3);
        assertEquals(0, processor.calculateTotalScore());
    }
}
