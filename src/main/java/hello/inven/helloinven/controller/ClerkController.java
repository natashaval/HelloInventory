package hello.inven.helloinven.controller;

import hello.inven.helloinven.model.ResponseAjax;
import hello.inven.helloinven.service.AdminService;
import hello.inven.helloinven.service.ClerkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ClerkController {

    @Autowired
    ClerkService clerkService;

    @GetMapping(value = "/clerk/employeelist") // hanya menampilkan employee yang termasuk Role Manager / Employee
    public @ResponseBody ResponseAjax managerAndEmployeeList(){
        return clerkService.findManagerAndEmployee();
    }


}
