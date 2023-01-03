package com.halikrkn.springbootstudentotomation.api.controller;

import com.halikrkn.springbootstudentotomation.data.entities.Student;
import com.halikrkn.springbootstudentotomation.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/student")
    public List<Student> getStudents() {
         return this.studentService.getStudents();
    }
}
