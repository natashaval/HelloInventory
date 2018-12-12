package hello.inven.helloinven.controller;

import hello.inven.helloinven.model.ResponseAjax;
import hello.inven.helloinven.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @GetMapping(value = "/user/item/assets")
    @ResponseBody
    public ResponseAjax getItemAssets(){
        return employeeService.getAllItemAssets();
    }

    @GetMapping("/user/request") //tidak jalan karena datatablesnya kadang tidak load
    public String userRequestPage(){
        return "user/request";
    }

    @GetMapping("/user/item/request")
    public String userItemRequest(Model model){
        ResponseAjax responseAjax = employeeService.getAllItemAssets();
        model.addAttribute("itemAssets", responseAjax.getData());
        return "user/item-request";
    }

    @PostMapping("/user/item/request")
    @ResponseBody
    public ResponseAjax userItemRequestPost(@RequestParam("itemrequests[]") List<Long> requestValues){
        for (Long requestId: requestValues) {
            System.out.println(requestId);
        }
        return null;
    }
}
