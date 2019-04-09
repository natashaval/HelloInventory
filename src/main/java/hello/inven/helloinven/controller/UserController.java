package hello.inven.helloinven.controller;


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

    @GetMapping(value = "/")
    public String home() { return "home"; }

    @RequestMapping(value = "/user/dashboard")
    public ModelAndView user(){
        ModelAndView modelAndView = new ModelAndView();
        UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        modelAndView.addObject(userDetails);
        modelAndView.setViewName("user/dashboard");
        return modelAndView;

    }

    @RequestMapping(value = "/login")
    public String login() {return "login";}

    @RequestMapping(value = "/403")
    public String Error403(){
        return "403";
    }

    @GetMapping("/profile")
    public String userProfile(Model model){
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
