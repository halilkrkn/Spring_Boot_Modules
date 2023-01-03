package com.halikrkn.springbootstudentotomation.config;

import com.halikrkn.springbootstudentotomation.data.dataAccess.StudentRepository;
import com.halikrkn.springbootstudentotomation.data.entities.Student;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;


// Burada veritabanına proje üzerinden configürasyon yaparak Hibernate ve JPA'yı kullanarak veri ekledik.

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return  args -> {
            Student halil = new Student(
                    1L,
                    "Halil",
                    "halilkrkn@gmail.com",
                    LocalDate.of(1996, 1,28),
                    27
            );

            Student ibrahim = new Student(
                    2L,
                    "İbrahim",
                    "ibrahim@gmail.com",
                    LocalDate.of(1998, 3,21),
                    25
            );

            studentRepository.saveAll(
                    List.of(halil,ibrahim)
            );

        };
    }
}
