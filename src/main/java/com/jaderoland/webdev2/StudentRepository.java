package com.jaderoland.webdev2;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class StudentRepository {
    private List<Student> studentList = new ArrayList<>();

    public List<Student> getStudentList() {
        return studentList;
    }

    public Student getStudentById(Long Id) {
        return getStudentList().stream().filter(s -> s.getId().equals(Id)).findFirst().orElse(null);
    }

    public void saveStudent(Student student) {
        studentList.add(student);
    }

    public void deleteStudent(Student student) {
        studentList.remove(student);
    }

    public void updateStudentDetails(Long Id, Student updatedStudent) {
        for (Student s : studentList) {
            if (s.getId().equals(updatedStudent.getId())) {
                s.setName(updatedStudent.getName());
                s.setAge(updatedStudent.getAge());
                s.setEmail(updatedStudent.getEmail());
                s.setCourse(updatedStudent.getCourse());
            }
        }
    }

    public StudentRepository() {
        studentList.add(new Student("Steve", "Computer Science", 72631L, 19, "steve@school.edu"));
        studentList.add(new Student("Vincent", "Nursing", 34684L, 21, "vincent@school.edu"));
        studentList.add(new Student("Kate", "Computer Engineering", 92856L, 18, "kate@school.edu"));
        studentList.add(new Student("Bernice", "Tourism", 23461L, 22, "bernice@school.edu"));
    }
}
