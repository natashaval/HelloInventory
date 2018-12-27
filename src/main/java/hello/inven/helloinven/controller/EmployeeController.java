package hello.inven.helloinven.controller;

import hello.inven.helloinven.model.MyUser;
import hello.inven.helloinven.model.ResponseAjax;
import hello.inven.helloinven.service.EmployeeService;
import hello.inven.helloinven.service.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    /* ========== EMPLOYEE REQUEST ITEM ===========*/
    @GetMapping(value = {"/user/item/request", "/manager/item/request"})
    public String userItemRequest(Model model){
        ResponseAjax responseAjax = employeeService.getAllItemAssets();
        model.addAttribute("itemAssets", responseAjax.getData()); // karena bentuknya listSwap
        return "user/item-request";
    }

    @PostMapping("/user/item/request")
    @ResponseBody
    public ResponseAjax userItemRequestPost(@RequestParam("itemrequests[]") List<Long> requestValues,
                                            @RequestParam("requestcomment") String comment){
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUser employee = myUserDetails.getUser();

        for (Long requestId: requestValues) {
            System.out.println(requestId);
        }
        System.out.println(comment);
        return employeeService.requestItemAssets(employee, requestValues, comment);
    }

    /* ============ EMPLOYEE VIEW ITEM LIST ==========*/
    @GetMapping("/user/myitem/count")
    @ResponseBody
    public ResponseAjax countMyItem(){
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUser employee = myUserDetails.getUser();
        return employeeService.countMyItem(employee);
    }

    @GetMapping("/user/myitem")
    public String myItem(){
        return "user/item-myitem";
    }

    /* ============ EMPLOYEE VIEW ITEM SERIAL FROM LIST ===========*/
    @GetMapping("/user/myitem/{id}/serial")
    @ResponseBody
    public ResponseAjax findMyItemSerial(@PathVariable Long id){
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUser employee = myUserDetails.getUser();
        return employeeService.findMyItemSerials(id, employee);
    }

    /* ============ EMPLOYEE VIEW ACTION TRANSACTION ===========*/
    @GetMapping("/user/item/status")
    public String requestStatus(){return "user/request-status";}

    @GetMapping("/user/item/actions")
    @ResponseBody
    public ResponseAjax getRequestStatus(){
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUser employee = myUserDetails.getUser();
        return employeeService.getActionTransactions(employee);
    }

    @GetMapping("/user/item/actions/{id}")
    @ResponseBody
    public ResponseAjax getRequestActionItemStatus(@PathVariable Long id){
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUser employee = myUserDetails.getUser();
        return employeeService.getActionItemStatus(id, employee);
    }

    @PostMapping("/user/item/actions/{id}/cancel")
    @ResponseBody
    public ResponseAjax cancelRequest(@PathVariable Long id){
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUser employee = myUserDetails.getUser();
        return employeeService.cancelRequest(id, employee);
    }

    @GetMapping("/user/item/refresh")
    @ResponseBody
    public ResponseAjax refreshRequest(){
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUser employee = myUserDetails.getUser();
        return employeeService.refreshRequest(employee);
    }

    /* =========== EMPLOYEE RECEIVE ITEM ========== */
    @GetMapping("/user/item/receive")
    public String receiveItem(){
        return "user/item-receive";
    }

    @GetMapping("/user/item/receiveitem")
    @ResponseBody
    public ResponseAjax getItemSent(){
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUser employee = myUserDetails.getUser();
        return employeeService.getItemAssetsSent(employee);
    }

    @PutMapping("/user/item/receive/ok")
    @ResponseBody
    public ResponseAjax receiveItemAsset(@RequestParam(value = "actionTransId")Long actionTransId, @RequestParam(value = "itemId")Long itemId){
        return employeeService.receiveItem(actionTransId, itemId);
    }
}
