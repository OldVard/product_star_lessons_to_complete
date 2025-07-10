package athletic.competition;

import athletic.competition.models.Competitor;
import athletic.competition.service.ResultProcessor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

public class ResultProcessorTest {

    private ResultProcessor resultProcessor;

    @BeforeEach
    void setUp() {
        resultProcessor = new ResultProcessor();
    }

    @Test
    void testGetAllCompetitors() {
        List<Competitor> result = resultProcessor.getAllCompetitors();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(7, result.size());
        Assertions.assertFalse(result.isEmpty());

//        System.out.println(result);
    }

    @Test
    void testValidCompetitors() {
        List<Competitor> result = resultProcessor.getAllCompetitors();

        Competitor firstCompetitor = result.getFirst();
        Competitor lastCompetitor = result.getLast();

        Assertions.assertEquals("Константин", firstCompetitor.getLastName());
        Assertions.assertNotNull(firstCompetitor.getLastName());
        Assertions.assertNotNull(firstCompetitor.getFirstName());
        Assertions.assertNotNull(firstCompetitor.getSex());
        Assertions.assertNotNull(firstCompetitor.getDistance());
        Assertions.assertNotNull(firstCompetitor.getTime());

        Assertions.assertEquals("Дибаба", lastCompetitor.getLastName());
        Assertions.assertNotNull(lastCompetitor.getLastName());
        Assertions.assertNotNull(lastCompetitor.getFirstName());
        Assertions.assertNotNull(lastCompetitor.getSex());
        Assertions.assertNotNull(lastCompetitor.getDistance());
        Assertions.assertNotNull(lastCompetitor.getTime());
    }

    @Test
    void getNFastestCompetitors_ShouldReturn3SortedMen() {
        List<Competitor> result = resultProcessor.getNFastestCompetitors("10 км", "М", 3);
        Competitor expectedCompetitor1 = new Competitor("Константин", "Джон", "М", "10 км", LocalTime.of(0, 51, 5));
        Competitor expectedCompetitor2 = new Competitor("Славов", "Борислав", "М", "10 км", LocalTime.of(0, 53, 4));
        Competitor expectedCompetitor3 = new Competitor("Иванов", "Иван", "М", "10 км", LocalTime.of(1, 0, 20));

        Assertions.assertEquals(3, result.size());

        Assertions.assertEquals(result.getFirst().getLastName(), expectedCompetitor1.getLastName());
        Assertions.assertEquals(result.getFirst().getTime(), expectedCompetitor1.getTime());

        Assertions.assertEquals(result.get(1).getLastName(), expectedCompetitor2.getLastName());
        Assertions.assertEquals(result.get(1).getTime(), expectedCompetitor2.getTime());

        Assertions.assertEquals(result.getLast().getLastName(), expectedCompetitor3.getLastName());
        Assertions.assertEquals(result.getLast().getTime(), expectedCompetitor3.getTime());

//        System.out.println(result);
    }

    @Test
    void getNFastestCompetitors_ShouldReturn2SortedWomen() {
        List<Competitor> result = resultProcessor.getNFastestCompetitors("5 км", "Ж", 2);

        Competitor expectedCompetitor1 = new Competitor("Королева", "Жанна", "Ж", "5 км", LocalTime.of(0, 24, 20));
        Competitor expectedCompetitor2 = new Competitor("Дибаба", "Тирунеш", "Ж", "5 км", LocalTime.of(0, 28, 30));

        Assertions.assertEquals(2, result.size());

        Assertions.assertEquals(result.getFirst().getLastName(), expectedCompetitor1.getLastName());
        Assertions.assertEquals(result.getFirst().getTime(), expectedCompetitor1.getTime());

        Assertions.assertEquals(result.get(1).getLastName(), expectedCompetitor2.getLastName());
        Assertions.assertEquals(result.get(1).getTime(), expectedCompetitor2.getTime());

//        System.out.println(result);
    }

    @Test
    void getNFastestCompetitors_ShouldReturnEmpty() {
        List<Competitor> result = resultProcessor.getNFastestCompetitors("800 м", "Ж", 3);

        Assertions.assertEquals(result, Collections.emptyList());
    }
}
