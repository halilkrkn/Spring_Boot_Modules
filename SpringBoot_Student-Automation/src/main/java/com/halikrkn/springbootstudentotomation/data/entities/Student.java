package com.halikrkn.springbootstudentotomation.data.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.Period;


@Entity
@Table
@Data
//@AllArgsConstructor
@NoArgsConstructor
public class Student {

    @Id
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "student_sequence"
    )
    private Long id;
    private String name;
    private String email;
    private LocalDate dateOfBirth;

    // @Transient
    // annotation'unu kullanarak kullanıcıdan age'i artık istemeyip kullanıcının yaşını date of Birth üzerinden JPA yardımıyla kod içerisinden uygulamaya kendi hesaplattık.
    // Ama bu durum sadece api de gözüküyor ve database'den de age sutünunu da kaldırmış olduk yani database de age ile ilgili bir şey yok.
    @Transient
    private int age;

    // Burada constructor'dan age'i sildik çünkü gerek yok artık doğum tarihi üzerinden uygulama kendi hesaplayacak ve database'e age'i eklemeyeceğiz. Sadece api'de gözükecek.
    public Student(Long id, String name, String email, LocalDate dateOfBirth) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }

    // Buraada age için uygulama içerisindeki kodlama sayesinde hesaplaması için gerekli kodları yazdık.
    public int getAge() {
        return Period.between(this.dateOfBirth,LocalDate.now()).getYears();
    }
}
