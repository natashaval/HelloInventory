package hello.inven.helloinven.controller;

import hello.inven.helloinven.model.ActionTransaction;
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

import java.util.List;

@Controller
public class ManagerController {
    @Autowired
    ManagerService managerService;

    @Autowired
    EmployeeService employeeService;

    /* ========== MANAGER APPROVAL REQUEST ===========*/
    @GetMapping("/manager/item/approval")
    public String managerItemApproval(){
        return "manager/item-approval";
    }

    @GetMapping("/manager/action/approval")
    @ResponseBody
    public ResponseAjax managerActionApprovalJSON(){
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUser manager = myUserDetails.getUser();
        List<ActionTransaction> transactionList = managerService.requestApproval(ActionTransaction.ActionType.PendingApproval, manager);
        if (transactionList.isEmpty()) return new ResponseAjax("Not Found", transactionList);
        return new ResponseAjax("Found", transactionList);
    }

    @PostMapping("/manager/action/{id}/approved")
    @ResponseBody
    public ResponseAjax actionApproved(@PathVariable Long id){
        ActionTransaction actionTransaction = managerService.approvedApproval(id, Boolean.TRUE);
        return new ResponseAjax("Done", "Request Approved and Sent to Inventory!");
    }

    @PostMapping("/manager/action/{id}/rejected")
    @ResponseBody
    public ResponseAjax actionRejected(@PathVariable Long id){
        ActionTransaction actionTransaction = managerService.rejectedApproval(id);
        return new ResponseAjax("Done", "Request Rejected!");
    }

    /* =========== MANAGER APPROVAL RETURN ===========*/
    @GetMapping("/manager/item/return-approval")
    public String managerItemReturnApproval(){return "manager/item-return-approval";}

    @GetMapping("/manager/action/return-approval")
    @ResponseBody
    public ResponseAjax managerActionReturnApprovalJSON(){
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUser manager = myUserDetails.getUser();
        List<ActionTransaction> transactionList = managerService.requestApproval(ActionTransaction.ActionType.ReturnApproval, manager);
        return new ResponseAjax("Done", transactionList);
    }

    @PostMapping("/manager/action/{id}/return-approved")
    @ResponseBody
    public ResponseAjax actionReturnApproved(@PathVariable Long id){
        ActionTransaction actionTransaction = managerService.approvedApproval(id, Boolean.FALSE);
        return new ResponseAjax("Done", "Request Approved and Waiting Inventory Response!");
    }
}
