package com.halilkrkn.spring_security_tutorial.security;

import com.google.common.collect.Sets; // Guava kütüphanesi
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.halilkrkn.spring_security_tutorial.security.ApplicationUserPermission.*;

// Projeye Guava kütüphanesini ekledik.
// Guava, collections, caching, primitives support, concurrency, common annotations, string processing, I/O, and validations gibi yapıları kullanımını kolaylaştırır.
// Guava içerisindeki methodlar sayesinde kod hatalarını en aza indirir.
//Guava is a set of core Java libraries from Google that includes new collection types (such as multimap and multiset), immutable collections, a graph library, and utilities for concurrency, I/O, hashing, caching, primitives, strings, and more! It is widely used on most Java projects within Google, and widely used by many other companies as well.

// Enum'ı kullanarak Role'leri yani Authorizations(yetkilendirmeleri) tanımladık.
public enum ApplicationUserRole {
    STUDENT(Sets.newHashSet()), // Burada kullanıcılar(student) için bir yetkilendirme vermedik.
    ADMIN(Sets.newHashSet(COURSE_READ, COURSE_WRITE, STUDENT_READ, STUDENT_WRITE)), // Burada admin'e tüm yetkileri verdik.
    ADMINTRAINEE(Sets.newHashSet(COURSE_READ, STUDENT_READ)); // Burada admin trainee'ye  course_read ve student_read yetkilerini atadık.


    // ApplicationUserPermission da oluşturduğumuz permissionları Set ettik ve constructor'ını oluşturduk.
    private final Set<ApplicationUserPermission> userPermissions;

    ApplicationUserRole(Set<ApplicationUserPermission> userPermissions) {
        this.userPermissions = userPermissions;
    }


    // Permissionları kullanabilmek için ApplicationUserPermission'ı set ederek döndüren bir fonksiyon oluşturduk.
    public Set<ApplicationUserPermission> getUserPermissions() {
        return userPermissions;
    }

    // SimpleGrantedAuthority ile set ederek Authorization işlemlerini sağlattık.
    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> getUserPermissions = getUserPermissions()
                .stream()
                .map(userPermissions -> new SimpleGrantedAuthority(userPermissions.getPermission()))
                .collect(Collectors.toSet());

        getUserPermissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return getUserPermissions;
    }
}
