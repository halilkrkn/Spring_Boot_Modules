package com.halilkrkn.spring_security_tutorial.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private int studentId;
    private String studentName;
}
