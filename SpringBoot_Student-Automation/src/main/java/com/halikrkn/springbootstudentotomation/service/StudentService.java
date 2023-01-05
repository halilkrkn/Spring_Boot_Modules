package com.halikrkn.springbootstudentotomation.service;

import com.halikrkn.springbootstudentotomation.data.dataAccess.StudentRepository;
import com.halikrkn.springbootstudentotomation.data.entities.Student;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    //Öğrencileri listeledik.
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    // Yeni öğrenci kayıt ettik.
    // Ayrıca bir öğrenciyi kayıt ederken de tekrar aynı email üzerinden kayıt olmaması adına bir validation(doğrulama) işlemi yaptırdık.
    // Burdaki doğrulama işlemi sayesinde aynı email'e sahip bir kişi tekrar register(kayıt) işlemi yapamıyor.
    // Buradaki bu validation işlemini de StudentRepository'de methodunu ve Query'sini yazıp burda kullandık.
    public void registerNewStudent(Student student) {
        Optional<Student> studentOptinal = studentRepository.findStudentsByEmail(student.getEmail());
        if (studentOptinal.isPresent()) {
            throw new IllegalStateException("email taken, No need to take again");
        }
        studentRepository.save(student);
    }

    // Öğrenciyi database'den ve Api'den siliyoruz.
    public void deleteStudent(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if (!exists) {
            throw new IllegalStateException("student with id " + studentId + " does not exists");
        }
        studentRepository.deleteById(studentId);
    }

/*  // Bir diğer öğrenci silme methodu
    public void deleteStudents(Student student) {
        studentRepository.delete(student);
    }
*/


    // Buradaki @Transactional annotation'u, jpql sorgusu uuygulamanız gerekmediği anlamına gelir.
    // Böylece Entity'dekileri Setterları güncelleme yapıp yapmayacağını kontrol etmek için return edilir ve database'deki entity olan class'ları otomatik olarak güncellemek için kullanabilirsiniz.
    //Burada öğrencinin id'si ile var olup olmadığını kontrol edip business mantığını oluşturuyoruz.
    @Transactional
    public void updateStudent(Long studentId, String name, String email) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException(
                        "student with id" + studentId + "does not exist"
                ));

        // Burada ise eğer öğrencinin name'i null değilse ve name'in uzunluğu 0'dan büyükse ve sağlanan isim mevcut olanla aynı mı değil mi diye kontrol ediyoruz.
        // Ve eğer yeni atanan name aynı değilse database'deki ile o zaman name'î güncellemiş oluyoruz.
        // Yani mevcut olan kayıtlı öğrencinin adı değiştiyse içerisideki student.setName(name); yeni adı atamış oluyoruz.
        if (name != null && name.length() > 0 && !Objects.equals(student.getName(), name)) {
            student.setName(name);
        }

        // Burada ise eğer öğrencinin e-mail'i null değilse ve e-mail'in uzunluğu 0'dan büyükse ve sağlanan e-mail mevcut olanla aynı değilse yeniden güncellemiş oluyoruz.
        // Burada ise e-mail üzerinden e-mail'in alınmış olup olmadığını kontrol ediyoruz ve eğer aynı e-mail tekrar tanımlanmışsa hata mesajı dönderiyoruz.
        // Eğer farklı bir e-mail girilmişse güncelliyoruz.
        if (email != null && email.length() > 0 && !Objects.equals(student.getEmail(),email)){
            Optional<Student> studentOptional = studentRepository.findStudentsByEmail(email);
            if (studentOptional.isPresent()){
                throw new IllegalStateException("email taken");
            }
            student.setEmail(email);
        }

    }

}
