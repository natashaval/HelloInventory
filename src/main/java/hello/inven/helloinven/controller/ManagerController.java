package hello.inven.helloinven.controller;

import hello.inven.helloinven.model.MyUser;
import hello.inven.helloinven.response.ResponseAjax;
import hello.inven.helloinven.service.EmployeeService;
import hello.inven.helloinven.service.ManagerService;
import hello.inven.helloinven.service.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ManagerController {
    @Autowired
    ManagerService managerService;

    @Autowired
    EmployeeService employeeService;

    /* ========== MANAGER APPROVAL ===========*/
    @GetMapping("/manager/action/approval")
    @ResponseBody
    public ResponseAjax managerActionApprovalJSON(){
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUser manager = myUserDetails.getUser();
        return managerService.requestApproval(manager);
    }

    @GetMapping("/manager/item/approval")
    public String managerItemApproval(){
        return "manager/item-approval";
    }

    @PostMapping("/manager/action/{id}/approved")
    @ResponseBody
    public ResponseAjax actionApproved(@PathVariable Long id){
        return managerService.approvedApproval(id);
    }

    @PostMapping("/manager/action/{id}/rejected")
    @ResponseBody
    public ResponseAjax actionRejected(@PathVariable Long id){
        return managerService.rejectedApproval(id);
    }

    /* =========== MANAGER REQUEST ITEMS ===========*/
//    @GetMapping("/manager/item/request")
//    public String userItemRequest(Model model){
//        ResponseAjax responseAjax = employeeService.getAllItemAssets();
//        model.addAttribute("itemAssets", responseAjax.getData()); // karena bentuknya listSwap
//        return "manager/item-request";
//    }

}
