package com.halilkrkn.spring_security_tutorial.api.controller;

import com.halilkrkn.spring_security_tutorial.data.entities.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/students")
public class StudentController {

    private static final List<Student> STUDENTS = Arrays.asList(
            new Student(1,"james Bond"),
            new Student(2,"Maria Jones"),
            new Student(3,"Anna Smith")
    );


    @GetMapping(path = "{studentId}")
    private Student getStudent(@PathVariable("studentId") Integer studentId){
            return  STUDENTS.stream()
                    .filter(student -> studentId.equals(student.getStudentId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Student" + studentId + "does not exists"));
    }

    @GetMapping(path = "/list")
    private List<Student> getListStudent() {
        return STUDENTS;
    }

}
