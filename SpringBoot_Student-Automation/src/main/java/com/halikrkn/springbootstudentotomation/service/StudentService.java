package com.halikrkn.springbootstudentotomation.service;

import com.halikrkn.springbootstudentotomation.data.dataAccess.StudentRepository;
import com.halikrkn.springbootstudentotomation.data.entities.Student;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StudentService {

    @Autowired
    private final StudentRepository studentRepository;

    // Burada constructor işlemini Lombok kullanarak yaptık.
    // Yani @AllArgsConstructor annotation'unu kullanarak constructor yapıya dönüştürdük.

//    public StudentService(StudentRepository studentRepository) {
//        this.studentRepository = studentRepository;
//    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void saveStudent(Student student){
        studentRepository.save(student);
    }
}
