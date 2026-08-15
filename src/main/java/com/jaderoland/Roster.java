package com.jaderoland;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Roster {
    private List<Student> students = new ArrayList<>();

    public List<Student> getStudents() {
        return students;
    }

    public Roster() {
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void filterStudents() {
        List<String> streamedNames = students.stream().filter(s -> s.getAge() >= 18).map(Student::getName)
                .collect(Collectors.toList());
        for (String name : streamedNames) {
            System.out.println(name);
        }
        System.out.println(streamedNames.size() + " are 18 or older");
    }
}
