package com.halikrkn.springbootstudentotomation.data.dataAccess;

import com.halikrkn.springbootstudentotomation.data.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
}
