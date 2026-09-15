package com.jaderoland.webdev2;

import jakarta.validation.constraints.*;

public class Student {
    @NotBlank(message = "This is a required field")
    private String name;
    @NotBlank(message = "You must put an email")
    @Email(message = "Provide a valid email address")
    private String email;

    private String course;
    private Long Id;
    @Min(value = 0, message = "Age cannot be less than 0")
    private int age;

    public Student(String name, String course, Long Id, int age, String email) {
        this.name = name;
        this.course = course;
        this.Id = Id;
        this.age = age;
        this.email = email;
    }

    public Student() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
