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
    public String home() { return "home"; }

    @RequestMapping(value = "/user/dashboard")
    public ModelAndView user(){
        ModelAndView modelAndView = new ModelAndView();
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        https://www.baeldung.com/get-user-in-spring-security
        modelAndView.addObject(userDetails);
        modelAndView.setViewName("user/dashboard");

//        System.out.print(auth.getPrincipal());
//        System.out.print(auth.getDetails());
        System.out.print(userDetails);
        return modelAndView;

    }
//    https://www.jackrutorial.com/2018/04/spring-boot-user-registration-login.html


    @RequestMapping(value = "/login")
    public String login() {return "login";}

    @RequestMapping(value = "/403")
    public String Error403(){
        return "403";
    }

    @GetMapping("/profile")
    public String userProfile(Model model){
//        https://stackoverflow.com/questions/18975077/how-to-add-object-in-using-model-addattributes-in-spring-mvc
        MyUserDetails myUserDetails = (MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUser user = myUserDetails.getUser();
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/clerk/dashboard")
    public String clerkDashboard(){
        return "clerk/dashboard";
    }

    @GetMapping("/manager/dashboard")
    public String managerDashboard(){
        return"manager/dashboard";
    }
}
