package hello.inven.helloinven.controller;


import hello.inven.helloinven.model.MyUser;
import hello.inven.helloinven.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    AdminService adminService;

    @GetMapping(value = "/admin/admin")
    public String admin() {
        return "/admin/admin";
    }

    @GetMapping(value = "/admin/employeelist")
    public String employeeList(Model model){
        List<MyUser> employees = adminService.findAll();
        for (MyUser emp: employees) {
            System.out.print(emp.getEmail());
            System.out.print(emp.getName());
            System.out.print("ROLE: " + emp.getRole().getRole());
        }
        model.addAttribute("employees", employees);
        return "admin/employeelist";
    }
}
