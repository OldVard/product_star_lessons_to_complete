package org.example;

import java.util.Comparator;

public class Student implements Comparable<Student> {
    private String name;
    private String group;

    public Student(String name, String group) {
        this.name = name;
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public String getGroup() {
        return group;
    }

    @Override
    public int compareTo(Student o) {
        return this.name.compareTo(o.name);
    }

    @Override
    public String toString() {
        return "Student {" +
                "name='" + name + '\'' +
                ", group='" + group + '\'' +
                '}';
    }
}
