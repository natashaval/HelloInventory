package hello.inven.helloinven.controller;
// https://grokonez.com/spring-framework/spring-security/use-spring-security-jdbc-authentication-mysql-spring-boot#4_Configure_Database

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    @RequestMapping(value = "/")
    public String home() {
        return "home";
    }

    @RequestMapping(value = "/user")
    public String user() {
        return "user";
    }

    @RequestMapping(value = "/admin")
    public String admin() {
        return "admin";
    }

    @RequestMapping(value = "/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/403")
    public String Error403(){
        return "403";
    }

//    bisa diganti menjadi dibawah pada MvcConfig.java
//      registry.addVie  wController("/home").setViewName("home");
//        registry.addViewController("/").setViewName("home");
//        registry.addViewController("/hello").setViewName("hello");
//        registry.addViewController("/login").setViewName("login");

}
