package com.halilkrkn.spring_security_tutorial.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.concurrent.TimeUnit;

import static com.halilkrkn.spring_security_tutorial.security.ApplicationUserRole.*;

// AUTHENTICATION
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig {

    @Autowired
    private final PasswordEncoder passwordEncoder;

    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    // Burada Auth işlemleri için gereken yapıları tanımladık.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/","index", "/css/*","/js/*").permitAll() // index.html, css, js hepsine izin verildi.
                .requestMatchers("/api/**").hasRole(STUDENT.name()) // Role bazlı authentication
//  Yetkilendirmeleri(Authorizations) ve Rolleri(Roles) bu şekilde yapmak çok mantıklı bir durum olmadığı için ilgili Controller içerisinde preAuthorize ile bu yetkilendirmeleri ve Role'leri atamak daha mantıklıdır.
//                .requestMatchers(HttpMethod.DELETE,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission()) // Yetki bazlı authentication
//                .requestMatchers(HttpMethod.POST,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission())  // Yetki bazlı authentication
//                .requestMatchers(HttpMethod.PUT,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission()) // Yetki bazlı authentication
//                .requestMatchers("/management/api/**").hasAnyRole(ADMIN.name(), ADMINTRAINEE.name()) // Yetkileri Role'lere atadık.
                .anyRequest()
                .authenticated()
                .and()
                //Login ve Redirect İşlemleri
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .defaultSuccessUrl("/courses",true) // Burda bir redirect işlemi yaptık yani loginden sonra courses sayfasına yönlendirdik.
                    .passwordParameter("passwordy")
                    .usernameParameter("username")
                .and()
                //RememberMe işlemleri
                .rememberMe()
                    .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
                    .key("somethingverysecured")
                    .rememberMeParameter("remember-me")
                .and()
                // Logout İşlemleri
                .logout()
                    .logoutUrl("/logout")
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID","remember-me")
                    .logoutSuccessUrl("/login");


        return httpSecurity.build();
    }


    // InMemoryUserDetailsManager ile user yönetimi auth(role) ve permissions(izinler) işlemi yaptık.
    // Buarada InMemory'de kayıtlı user yönetimi yaptık.
    // Yani username, password atadık. ilgili user'ın role'ünü ve permissions'ları atadık.
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("user") // USERNAME_STUDENT
                .password(passwordEncoder.encode("password")) // PASSWORD_STUDENT - ENCODE edilmiş yani password kodlanmış güvenlik için
//                .roles(STUDENT.name()) // ROLE_STUDENT - Enum içerisinde oluşturduğumuz Student role'ü ve permission'ını buraya atadık.
                .authorities(STUDENT.getGrantedAuthorities()) // User Yetkilendirmesi verildi. Hiç bir yetkisi yok.
                .build();


        UserDetails adminUser = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("password123"))
//                .roles(ADMIN.name()) // ROLE_ADMIN - Enum içerisinde oluşturduğumuz ADMIN Role'ü ve permission'larını buraya atadık.
                .authorities(ADMIN.getGrantedAuthorities()) // ADMIN Yetkilendirmesi verildi. Yani Admin tamemen yetki sahibi oldu  hem student'ı hem de course'ın tamamında(COURSE_READ, COURSE_WRITE, STUDENT_READ, STUDENT_WRITE) yetki sahibi oldu.
                .build();

        UserDetails adminTraineeUser = User.builder()
                .username("admin_trainee")
                .password(passwordEncoder.encode("password123"))
//                .roles(ADMINTRAINEE.name()) // ROLE_ADMINTRAINEE - Enum içerisinde oluşturduğumuz ADMINTRAINEE Role'ü ve permission'larını buraya atadık.
                .authorities(ADMINTRAINEE.getGrantedAuthorities()) // ADMIN_TRAINEE Yetkilendirmesi verildi. Buda sadece course'u ve student'ı okuma yetkisine sahip.(COURSE_READ, STUDENT_READ)
                .build();

        return new InMemoryUserDetailsManager(
                user,
                adminUser,
                adminTraineeUser
        );

    }

}
