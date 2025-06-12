package org.example.schedule;

import java.util.*;

public class ScheduleMain {
    public static void main(String[] args) {
        Schedule schedule = new Schedule();
        schedule.addEvent(9, 10, "Daily Meeting");
        schedule.addEvent(11, 12, "1:1 with Ivan");
        schedule.addEvent(15, 16, "Sync - Big Bet Project");
        schedule.addEvent(17, 20, "Java Community meeting");
        schedule.addEvent(19, 21, "Private appointment");

        System.out.println(schedule.getNextThree(9));
        System.out.println(schedule.hasOverlaps());
    }
}

class Schedule {
    private final TreeSet<Event> schedule = new TreeSet<>();
    private final TreeMap<Integer, Integer> scheduleMap = new TreeMap<>();

    void addEvent(int start, int end, String name) {
        schedule.add(new Event(start, end, name));

        scheduleMap.put(start, scheduleMap.getOrDefault(start, 0) + 1);
        scheduleMap.put(end, scheduleMap.getOrDefault(end, 0) - 1);
    }

    public List<Event> getNextThree(int time) {
        Event event = new Event();
        event.setStart(time);

        return schedule.tailSet(event, true).stream()
                .limit(3)
                .toList();
    }

    public boolean hasOverlaps() {
        int count = 0;
        for(Integer k : scheduleMap.keySet()) {
            count += scheduleMap.get(k);
            if(count > 1) {
                return true;
            }
        }
        return false;
    }
}

class Event implements Comparable<Event> {
    private int start;
    private int end;
    private String title;

    public Event(int s, int e, String t) {
        this.start = s;
        this.end = e;
        this.title = t;
    }

    public Event() { }

    @Override
    public int compareTo(Event o) {
        if (this.start == o.start) {
            return Integer.compare(this.end, o.end);
        } else {
            return Integer.compare(this.start, o.end);
        }
    }

    public void setStart(int start) {
        this.start = start;
    }

    @Override
    public String toString() {
        return "[ " + start + " - " + end + "] : " + title;
    }
}
