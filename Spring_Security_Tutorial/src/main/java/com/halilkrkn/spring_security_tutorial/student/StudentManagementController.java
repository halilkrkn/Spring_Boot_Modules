package com.halilkrkn.spring_security_tutorial.student;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(path = "management/api/v1/students")
public class StudentManagementController {

    private static final List<Student> STUDENTS = Arrays.asList(
            new Student(1,"james Bond"),
            new Student(2,"Maria Jones"),
            new Student(3,"Anna Smith")
    );

    //preAuthorize'ın hasRole('ROLE_') hasAnyRole('ROLE_') hasAuthority('permission') hasAnyAuthority('permission') tanımlanmass.
    //preAuthorize ile ilgili GetMapping, PostMapping, DeleteMapping, PutMapping gibi yapıları öncesinden Authorize edebiliriz. Yani aktif olarak Role'lerini ve Yetkilerini tanımlayabiliriz.
    @GetMapping(path = "/list")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN, ROLE_ADMINTRAINEE')")
    public List<Student> getAllStudents() {
        return STUDENTS;
    }

    @PostMapping(path = "/register")
    @PreAuthorize("hasAuthority('student:write')")
    public void registerNewStudent(@RequestBody Student student) {
        System.out.println(student);
    }

    @DeleteMapping(path = "{studentId}")
    @PreAuthorize("hasAuthority('student:write')")
    public void deleteStudent(@PathVariable("studentId") Integer studentId) {
        System.out.println(studentId);
    }

    @PutMapping(path = "{studentId}")
    @PreAuthorize("hasAuthority('student:write')")
    public void updateStudent(@PathVariable("studentId") Integer studentId, @RequestBody Student student) {
        System.out.println(String.format("%s %s", studentId,student));
    }
}
