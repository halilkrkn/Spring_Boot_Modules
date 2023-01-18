package com.halilkrkn.spring_security_tutorial.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


// Burada oluşturduğumuzu templateleri kullanıyoruz.
// Mesela biz bir login ekranı yaptık.
// BU template'lerin kullanılması için @Controller annotation'u ile tanımlıyoruz.
// RequestMapping ile yolunu veriyoruz.
// GetMapping ile de ilgili login ekranın yolunu veriyoruz.
// return olarak da resources dosyası içersindeki templates dosyasında oluşturduğumuz login'i dönderiyoruz.
// Sonrasında ise ApplicationSecurityConfig içerisinde ki securityFilterChain methodun 'da loginpage("login") methoduna login'in yolunu veriyoruz.
@Controller
@RequestMapping(path = "/")
public class TemplateController {

    @GetMapping("login")
    public String getLoginView() {
        return "login";
    }

    @GetMapping("courses")
    public String getCourses() {
        return "courses";
    }

    // Başka bir sayfa tanımladık. ve gösterdik.
//    @GetMapping("signin")
//    public String getSigninView() {
//        return "signin";
//    }
}
