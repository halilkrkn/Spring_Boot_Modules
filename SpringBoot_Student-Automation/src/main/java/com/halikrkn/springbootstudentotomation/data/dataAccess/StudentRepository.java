package com.halikrkn.springbootstudentotomation.data.dataAccess;

import com.halikrkn.springbootstudentotomation.data.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Burdaki Repository'de Data Access işlemleri yapılıyor.
// Yani Database ile ilişkili ORM işlemleri Hibernate, JPA gibi database ile ilgili işlemler repository'de yapılıyor.
@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

    // Repository'imizi açıyoruz. Business Logic için gerekli olan işlemlerin methodlarını oluşturacaz.
    // Ve Business Logic Katmanında yani StudentService'de burdaki methodu çağırıp iş kuralını yazacaz.
    // Burada e-posta yoluyla bir kullanıcıyı bulan özel bir işleve sahip olmak istiyoruz. ve bunun iş kuralını yazacağız.
    // Burada Optional generic Class'ını kullanıyoruz ki isteğe bağlı bir durum oluşturmak için.
    // Yani burada database'e sorgu yaptırıp e-mail üzerinden kullanıcı filtrelemesi yapacağız.
    // SELECT * FROM student WHERE email= ? gibi sorgu yaptırmaya çalışıyoruz.
    // Tüm bunları ise JPA ile yapıyoruz.
    @Query("SELECT s FROM  Student s WHERE  s.email = ?1")
    Optional<Student> findStudentsByEmail(String email);
}
