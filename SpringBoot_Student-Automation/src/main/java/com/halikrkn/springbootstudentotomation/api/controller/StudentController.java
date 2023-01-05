package com.halikrkn.springbootstudentotomation.api.controller;

import com.halikrkn.springbootstudentotomation.data.entities.Student;
import com.halikrkn.springbootstudentotomation.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // Apiden tüm verileri alma listeleme
    @GetMapping("/student")
    public List<Student> getStudents() {
        return this.studentService.getStudents();
    }

    // Apiden veri kaydetme
    @PostMapping(path = "/register")
    public void registerNewStudent(@RequestBody Student student) {
        studentService.registerNewStudent(student);
    }

    // Api'den student id ile veri silme
    @DeleteMapping(path = "/student/{studentId}")
    public void deleteStudent(@PathVariable("studentId") Long studentId) {
        studentService.deleteStudent(studentId);

    }
/*  // Bir diğer öğrenci silme yöntemi
    @DeleteMapping(path = "/student/delete")
    public void deleteStudents(@RequestBody Student student){
        studentService.deleteStudents(student);
    }
*/

    // Öğrencileri güncelleştirme
    // Burada güncelleştirme olarak name ve e-mail'i güncelleyeceğiz.
    // Burada hem ad hemde e-mail için her ikisinin de gerekli olmadığı bir parametre de verdik.
    @PutMapping(path = "{studentId}")
    public void updateStudent(
            @PathVariable("studentId") Long studentId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email
    ){
        studentService.updateStudent(studentId,name,email);

    }

}
