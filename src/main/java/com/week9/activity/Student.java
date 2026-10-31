package com.week9.activity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Student {

  public Student(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public Student() {
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @OneToMany(mappedBy = "student", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true)
  private List<Enrollment> enrollments = new ArrayList<>();

  public void addEnrollment(Enrollment enrollment) {
    enrollments.add(enrollment);
    enrollment.setStudent(this);
  }

  public void removeEnrollment(Enrollment enrollment) {
    enrollments.remove(enrollment);
    enrollment.setStudent(null);
  }

  public List<Enrollment> getEnrollments() {
    return enrollments;
  }
}
