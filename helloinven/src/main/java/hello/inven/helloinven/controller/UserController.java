package hello.inven.helloinven.controller;
// https://grokonez.com/spring-framework/spring-security/use-spring-security-jdbc-authentication-mysql-spring-boot#4_Configure_Database

import hello.inven.helloinven.model.MyUser;
import hello.inven.helloinven.service.MyUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

//    why use ModelAndView? This interface allows us to pass all the information required by Spring MVC in one return
//    https://www.baeldung.com/spring-mvc-model-model-map-model-view

    @GetMapping(value = "/")
//    public String home() {
//        return "home";
//    }
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        return modelAndView;
    }

    @RequestMapping(value = "/user")
//    public String user() {
//        return "user";
//    }
    public ModelAndView user(){
        ModelAndView modelAndView = new ModelAndView();
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        https://www.baeldung.com/get-user-in-spring-security
        modelAndView.addObject(userDetails);
        modelAndView.setViewName("user");

//        System.out.print(auth.getPrincipal());
//        System.out.print(auth.getDetails());
        System.out.print(userDetails);
        return modelAndView;

    }
//    https://www.jackrutorial.com/2018/04/spring-boot-user-registration-login.html


    @RequestMapping(value = "/login")
//    public String login() {
//        return "login";
//    }
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value = "/403")
    public String Error403(){
        return "403";
    }

//    bisa diganti menjadi dibawah pada MvcConfig.java
//        registry.addViewController("/home").setViewName("home");
//        registry.addViewController("/").setViewName("home");
//        registry.addViewController("/hello").setViewName("hello");
//        registry.addViewController("/login").setViewName("login");

    @GetMapping("/user/userprofile")
    public String userProfile(ModelMap modelMap){
//        https://stackoverflow.com/questions/18975077/how-to-add-object-in-using-model-addattributes-in-spring-mvc
        MyUserDetails myUserDetails = (MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUser user = myUserDetails.getUser();
        System.out.print(user);
        modelMap.put("user", user);
        return "userprofile";
    }

    // LEBIH RAPI YANG /user/userprofile
    /*
    @GetMapping("/user/profile")
    public String profileJelek(Model model, Authentication authentication){
        MyUser employee = ((MyUserDetails)authentication.getPrincipal()).getUser();
        System.out.print(employee);
        model.addAttribute("empemail", employee.getEmail());
        model.addAttribute("empphone", employee.getPhone());
        model.addAttribute("empimage", employee.getPhoto());
//        model.addAttribute(orang);
        System.out.print("EmAIL: " + employee.getEmail() + "PHONE: " + employee.getPhone());
        return "user/profile";
    }
    */

}
