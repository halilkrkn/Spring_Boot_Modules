package com.halilkrkn.spring_security_tutorial.security;


import com.halilkrkn.spring_security_tutorial.authentication.ApplicationUserService;
import com.halilkrkn.spring_security_tutorial.jwt.JwtConfig;
import com.halilkrkn.spring_security_tutorial.jwt.JwtTokenVerifier;
import com.halilkrkn.spring_security_tutorial.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.SecretKey;

import static com.halilkrkn.spring_security_tutorial.security.ApplicationUserRole.*;

// AUTHENTICATION, ROLES, PERMISSION İŞLEMLERİ
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final ApplicationUserService applicationUserService;
    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    public ApplicationSecurityConfig(
            PasswordEncoder passwordEncoder,
            ApplicationUserService applicationUserService,
            SecretKey secretKey,
            JwtConfig jwtConfig)
    {
        this.passwordEncoder = passwordEncoder;
        this.applicationUserService = applicationUserService;
        this.secretKey = secretKey;
        this.jwtConfig = jwtConfig;
    }



    // Burada Authentication işlemleri için gereken yapıları tanımladık.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        AuthenticationManager authenticationManager = authenticationConfiguration.getAuthenticationManager();

        httpSecurity
                .csrf().disable()
                // JWT Username Password Filter İşlemleri - Filter ve Stateless Session Kullanımı,
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager, jwtConfig, secretKey))
                .addFilterAfter(new JwtTokenVerifier(secretKey, jwtConfig), JwtUsernameAndPasswordAuthenticationFilter.class)

                .authorizeHttpRequests()
                .requestMatchers("/","index", "/css/*","/js/*").permitAll() // index.html, css, js hepsine izin verildi.
                .requestMatchers("/api/**").hasRole(STUDENT.name()) // Role bazlı authentication
                .anyRequest()
                .authenticated();

        return httpSecurity.build();
    }

    // DATABASE AUTHENTICATION
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    // DATABASE AUTHENTICATION
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(applicationUserService);
        return provider;
    }
}
