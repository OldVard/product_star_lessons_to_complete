package org.example;

import org.example.config.AppConfig;
import org.example.repo.AnswersLoader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

public class App {
    public static void main( String[] args ) {
        ApplicationContext context =
               new AnnotationConfigApplicationContext(AppConfig.class);

        ResultsProcessor resultsProcessor = context.getBean(ResultsProcessor.class);
        AnswersLoader answersLoader = context.getBean(AnswersLoader.class);
        System.out.println("[ПРАВИЛЬНЫЕ ОТВЕТЫ]");
        prettyMapPrint(answersLoader.getCorrectAnswers());

        System.out.println("-----------------------");

        System.out.println("[БЛАНК СТУДЕНТА]");
        prettyMapPrint(answersLoader.getStudentAnswers());

        System.out.println("-----------------------");

        System.out.println("Итого баллов: " + resultsProcessor.calculateTotalScore()
                + " из " + resultsProcessor.calculateMaxPoints());
    }

    private static void prettyMapPrint(Map<Integer, String> map) {
        map.forEach((key, value) ->
                System.out.printf("[%d] - %s\n", key, value));
    }
}
