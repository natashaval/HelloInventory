package hello.inven.helloinven.controller;

import hello.inven.helloinven.model.ActionItem;
import hello.inven.helloinven.model.MyUser;
import hello.inven.helloinven.response.ResponseAjax;
import hello.inven.helloinven.service.ClerkService;
import hello.inven.helloinven.service.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ClerkController {

    @Autowired
    ClerkService clerkService;

    /* =========== CLERK DO BULK ASSIGNMENT OF ITEM SERIAL TO EMPLOYEE =========== */

    // hanya menampilkan employee yang termasuk Role Manager / Employee untuk memilih Assign Employee
    @GetMapping(value = "/clerk/employeelist")
    @ResponseBody
    public ResponseAjax managerAndEmployeeList(){

        List<MyUser> managerAndEmployee = clerkService.findManagerAndEmployee();
        if (! managerAndEmployee.isEmpty())return new ResponseAjax("Done", managerAndEmployee);
        else return new ResponseAjax("Not Found", managerAndEmployee);
    }

    // Assign employee to item serial
    @PostMapping(value = "/clerk/item/{id}/assign")
    @ResponseBody
    public ResponseAjax itemAssignPost(@PathVariable Long id, @RequestParam(value = "serial_employee[]") List<Long> employeeValues){
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUser clerk = myUserDetails.getUser();
        List<Long> serialSave = clerkService.assignItemSerial(clerk, id, employeeValues);
        return new ResponseAjax("Success", serialSave);
    }

    /* =========== CLERK APPROVE (SENT) / REJECT ITEM FROM EMPLOYEE REQUEST ========== */

    // Clerk get lsit of requested item under his name
    @GetMapping(value = "/clerk/item/receive")
    @ResponseBody
    public ResponseAjax receiveItem(){
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUser clerk = myUserDetails.getUser();
        List<ActionItem> items = clerkService.receiveItemRequest(clerk, Boolean.TRUE);
        return new ResponseAjax("Found", items);
    }

    @GetMapping(value = "/clerk/item/approval")
    public String clerkItemApproval(){
        return "clerk/item-approval";
    }

    // Clerk approve request item form
    @PostMapping(value = "/clerk/item/approve")
    @ResponseBody
    public ResponseAjax approveItem(
            @RequestParam(value = "actionTransId")Long actionTransId,
            @RequestParam(value = "itemId")Long itemId,
            @RequestParam(value = "itemSerial") Long itemSerial){
        ActionItem actionItem = clerkService.itemRequestActions(actionTransId, itemId, itemSerial, Boolean.TRUE);
        return new ResponseAjax("Approved", "Item Sent to Employee");
    }

    // Clerk reject request item form
    @PostMapping(value = "/clerk/item/reject")
    @ResponseBody
    public ResponseAjax rejectItem(
            @RequestParam(value = "actionTransId")Long actionTransId,
            @RequestParam(value = "itemId")Long itemId,
            @RequestParam(value = "itemSerial") Long itemSerial){
        ActionItem actionItem = clerkService.itemRequestActions(actionTransId, itemId, itemSerial, Boolean.FALSE);
        return new ResponseAjax("Rejected", "Item Request has been rejected!");
    }

    /* =========== CLERK APPROVE (GET RETURNED BACK) / REJECT ITEM FROM EMPLOYEE RETURN ========== */
    @GetMapping(value = "/clerk/item/return-approval")
    public String clerkItemReturnApproval(){return "clerk/item-return-approval";}

    // Clerk get list of returned item under his name
    @GetMapping("/clerk/item/return")
    @ResponseBody
    public ResponseAjax returnItem(){
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUser clerk = myUserDetails.getUser();
        List<ActionItem> items = clerkService.receiveItemRequest(clerk, Boolean.FALSE);
        return new ResponseAjax("Found", items);
    }

    // Clerk accept returned item
    @PutMapping(value = "/clerk/item/return-approve")
    @ResponseBody
    public ResponseAjax approveItemReturn(
            @RequestParam(value = "actionTransId")Long actionTransId, @RequestParam(value = "itemId")Long itemId){
        ActionItem actionItem = clerkService.itemReturnActions(actionTransId, itemId);
        return new ResponseAjax("Received", "Item has arrived back in inventory!");
    }


}
