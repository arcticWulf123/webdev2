package com.jaderoland.webdev2;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    /*
    TODO:
    1. Create delete endpoint, link must be available to delete a student
    2. Create edit endpoint and edit form
    
    */
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping()
    public String getAllStudents(Model model) {
        List<Student> students = studentService.getStudentList();
        model.addAttribute("students", students);
        return "students";
    }

    @PostMapping()
    public String createStudent(@Valid @ModelAttribute("student") Student student, BindingResult result) {
        if (result.hasErrors()) {
            return "create-student-form";
        }
        studentService.createStudent(student);
        return "redirect:/students";
    }

    @PostMapping("/{id}/delete")
    public String deleteStudent(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);
        if (student != null) {
            studentService.deleteStudent(student, id);
        }

        return "redirect:/students";
    }

    // @PostMapping()
    // public String createStudent(@Valid @ModelAttribute("student") Student
    // student, BindingResult result) {
    // if (result.hasErrors()) {
    // return "student-form";
    // }
    // studentService.createStudent(student);
    // return "redirect:/students";
    // }

    @GetMapping("/create")
    public String showCreateStudentForm(Model model) {
        model.addAttribute("student", new Student());
        return "create-student-form";
    }

    @GetMapping("/delete")
    public String showDeleteStudentForm(Model model) {
        model.addAttribute("student", new Student());
        return "delete-student-form";
    }

    // @GetMapping("/create")
    // public String showCreateStudentForm(Model model) {
    // model.addAttribute("student", new Student());
    // return "student-form";
    // }

}
