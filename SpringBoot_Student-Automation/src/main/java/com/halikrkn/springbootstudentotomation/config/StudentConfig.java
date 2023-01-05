package com.halikrkn.springbootstudentotomation.config;

import com.halikrkn.springbootstudentotomation.data.dataAccess.StudentRepository;
import com.halikrkn.springbootstudentotomation.data.entities.Student;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;


// Burada veritabanına proje üzerinden configürasyon yaparak ve JPA'yı kullanarak database'e veri ekledik.
// Yani burada proje içerisinde biz database'e veri ekledik ve database deki veriler bunlardır.
// Eğer bbu sınıfı kaldırırsak database'deki verilerde silenecektir.
@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return args -> {
            Student halil = new Student(
                    1L,
                    "Halil",
                    "halilkrkn@gmail.com",
                    LocalDate.of(1996, 1, 28)
            );

            Student ibrahim = new Student(
                    2L,
                    "İbrahim",
                    "ibrahim@gmail.com",
                    LocalDate.of(1991, 3, 21)
            );

            studentRepository.saveAll(
                    List.of(halil, ibrahim)
            );
        };
    }
}
