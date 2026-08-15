package com.jaderoland;

import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Student s1 = new Student("Andy", 19, "BSIT");
        Student s2 = new Student("Wayne", 20, "BSCS");
        Student s3 = new Student("Ken", 21, "BSCE");
        Student s4 = new Student("Kate", 18, "BSHM");
        Student s5 = new Student("Kendra", 17, "BSHM");
        try {
            Student s7 = new Student();
            s7.setName("John");
            s7.setAge(-3);
            s7.setCourse("BSCPE");
        } catch (InvalidAgeException e) {
            System.out.println(e.getMessage());
        }
        Student s6 = new Student("Janice", 23, "BSHM");
        Roster roster = new Roster();
        roster.addStudent(s1);
        roster.addStudent(s2);
        roster.addStudent(s3);
        roster.addStudent(s4);
        roster.addStudent(s5);
        roster.addStudent(s6);
        List<Student> classRoster = roster.getStudents();
        for (Student s : classRoster) {
            System.out.println(s);
        }
        roster.filterStudents();
    }
}