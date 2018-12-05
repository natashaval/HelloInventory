package hello.inven.helloinven.controller;

import hello.inven.helloinven.model.MyUser;
import hello.inven.helloinven.model.ResponseAjax;
import hello.inven.helloinven.service.AdminService;
import hello.inven.helloinven.service.ClerkService;
import hello.inven.helloinven.service.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ClerkController {

    @Autowired
    ClerkService clerkService;

    @GetMapping(value = "/clerk/employeelist") // hanya menampilkan employee yang termasuk Role Manager / Employee
    @ResponseBody
    public ResponseAjax managerAndEmployeeList(){
        return clerkService.findManagerAndEmployee();
    }

    @GetMapping(value = "/clerk/item/{id}/assign")
    public String itemAssign(@PathVariable Long id, Model model){
        return "clerk/item-serial-assign";
    }

    @PostMapping(value = "/clerk/item/{id}/assign")
    @ResponseBody
    public ResponseAjax itemAssignPost(@PathVariable Long id){
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUser clerk = myUserDetails.getUser();

        return clerkService.getItemSerialHandleByClerk(clerk, id);
    }


}
