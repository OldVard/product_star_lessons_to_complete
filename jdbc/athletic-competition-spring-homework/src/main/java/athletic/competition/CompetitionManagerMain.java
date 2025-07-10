package athletic.competition;

import athletic.competition.config.CompetitionManagerConfig;
import athletic.competition.models.Competitor;
import athletic.competition.service.ResultProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class CompetitionManagerMain {
    public static void main(String[] args) {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(CompetitionManagerConfig.class);

        ResultProcessor resultProcessor = context.getBean(ResultProcessor.class);

        List<Competitor> results = resultProcessor.getNFastestCompetitors("10 км", "М", 2);
        printCompetitors(results);

        System.out.println("---------------------------");
        List<Competitor> results2 = resultProcessor.getNFastestCompetitors("10 км", "Ж", 2);
        printCompetitors(results2); // вернет 1

        System.out.println("---------------------------");
        List<Competitor> results3 = resultProcessor.getNFastestCompetitors("5 км", "Ж", 2);
        printCompetitors(results3);
    }

    private static void printCompetitors(List<Competitor> competitors) {
        for(int i = 0; i < competitors.size(); i++) {
            System.out.printf("[%d] - %s %s, дистанция - %s. Время %s\n",
                    i+1, competitors.get(i).getFirstName(), competitors.get(i).getLastName(),
                    competitors.get(i).getDistance(), competitors.get(i).getTime());
        }
    }
}
