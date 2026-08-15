package com.jaderoland;

public class Student implements Gradable {
    private String name;
    private int age;
    private String course;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) throws InvalidAgeException {
        if (age <= 0) {
            throw new InvalidAgeException("Age must be a positive number");
        } else {
            this.age = age;
        }
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Student(String name, int age, String course) {
        this.name = name;
        this.age = age;
        this.course = course;
    }

    public Student () {}

    @Override
    public void computeStanding() {

    }

    @Override
    public String toString() {
        return name +  " " + age + " " + course;
    }
}
