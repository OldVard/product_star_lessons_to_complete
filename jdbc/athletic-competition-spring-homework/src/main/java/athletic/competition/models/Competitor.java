package athletic.competition.models;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Competitor {
    private final String lastName;
    private final String firstName;
    private final String sex;
    private final String distance;
    private final LocalTime time;

    public Competitor(String lastName, String firstName, String sex, String distance, LocalTime time) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.sex = sex;
        this.distance = distance;
        this.time = time;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSex() {
        return sex;
    }

    public String getDistance() {
        return distance;
    }

    public String getTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return time.format(formatter);
    }

    @Override
    public String toString() {
        return "Competitor{" +
                "lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", sex='" + sex + '\'' +
                ", distance='" + distance + '\'' +
                ", time=" + time +
                '}';
    }
}
