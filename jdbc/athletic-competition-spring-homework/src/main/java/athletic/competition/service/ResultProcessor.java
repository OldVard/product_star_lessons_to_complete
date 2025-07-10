package athletic.competition.service;

import athletic.competition.models.Competitor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Service
public class ResultProcessor {
    private final List<Competitor> competitors;

    public ResultProcessor() {
        competitors = extractCompetitors();
    }

    public List<Competitor> getNFastestCompetitors(String distance, String sex, int limit) {
        return competitors.stream()
                .filter(c -> c.getDistance().equals(distance)
                        && c.getSex().equals(sex))
                .sorted(Comparator.comparing(Competitor::getTime))
                .limit(limit)
                .toList();
    }

    public List<Competitor> getAllCompetitors() {
        return competitors;
    }

    // -- ВСПОМОГАТЕЛЬНОЕ --
    private List<Competitor> extractCompetitors() {
        try(BufferedReader reader = new BufferedReader(
                new InputStreamReader(this.getClass().getResourceAsStream("/competition_members.csv")))) {

            List<Competitor> extractedCompetitors = reader.lines()
                    .skip(1)
                    .map(line -> line.split(","))
                    .map(values -> new Competitor(
                            values[0], values[1], values[2], values[3], convertTime(values[4])
                    ))
                    .toList();

            return extractedCompetitors;

        } catch (Exception e) {
            throw new RuntimeException("Что-то пошло не так! Не удалось прочитать файл :(");
        }
    }

    private LocalTime convertTime(String rawString) {
        String[] hms = rawString.split(":");
        int[] values = Arrays.stream(hms).mapToInt(Integer::parseInt).toArray();
        return LocalTime.of(values[0], values[1], values[2]);
    }
}
