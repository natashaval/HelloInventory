package hello.inven.helloinven.controller;

import hello.inven.helloinven.dto.ItemSerialCount;
import hello.inven.helloinven.model.ActionItem;
import hello.inven.helloinven.model.ActionTransaction;
import hello.inven.helloinven.model.ItemSerial;
import hello.inven.helloinven.model.MyUser;
import hello.inven.helloinven.response.ResponseAjax;
import hello.inven.helloinven.service.EmployeeService;
import hello.inven.helloinven.service.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@PreAuthorize("hasAuthority('EMPLOYEE') or hasAuthority('MANAGER')")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    /* ========== EMPLOYEE REQUEST ITEM ===========*/
    @GetMapping(value = "/user/item/request")
    public String requestItem(Model model){
        model.addAttribute("itemAssets", employeeService.getAllItemAssets()); // karena bentuknya listSwap
        return "user/item-request";
    }

    @PostMapping("/user/item/request")
    @ResponseBody
    public ResponseAjax requestItemPost(@RequestParam("itemrequests[]") List<Long> requestValues,
                                            @RequestParam("requestcomment") String comment){
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUser employee = myUserDetails.getUser();
        List<ActionItem> actionItemList = employeeService.requestItemAssets(employee, requestValues, comment, Boolean.TRUE); // Boolean.TRUE = sedang request item
        return new ResponseAjax("Done", "Items have been requested!");
    }

    /* ============ EMPLOYEE VIEW ITEM LIST ==========*/
    @GetMapping("/user/myitem/count")
    @ResponseBody
    public ResponseAjax countMyItem(){
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUser employee = myUserDetails.getUser();
        List<ItemSerialCount> myItemCount = employeeService.countMyItem(employee);
        if (myItemCount == null) return new ResponseAjax("None", "No item found!");
        return new ResponseAjax("Count Done", myItemCount);
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
        List<ItemSerial> itemSerials = employeeService.findMyItemSerials(id, employee);
        if (itemSerials.isEmpty()) return new ResponseAjax("Not Found", itemSerials);
        return new ResponseAjax("Found", itemSerials);
    }

    /* ============ EMPLOYEE VIEW ACTION TRANSACTION ===========*/
    @GetMapping("/user/item/status")
    public String requestStatus(){return "user/request-status";}

    @GetMapping("/user/item/actions")
    @ResponseBody
    public ResponseAjax getRequestStatus(){
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUser employee = myUserDetails.getUser();
        List<ActionTransaction> transactionList = employeeService.getActionTransactions(employee);
        if (transactionList.isEmpty()) return new ResponseAjax("Not Found", transactionList);
        return new ResponseAjax("Found", transactionList);
    }

    @GetMapping("/user/item/actions/{id}")
    @ResponseBody
    public ResponseAjax getRequestActionItemStatus(@PathVariable Long id){
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUser employee = myUserDetails.getUser();
        List<ActionItem> itemList = employeeService.getActionItemStatus(id, employee);
        if (itemList.isEmpty()) return new ResponseAjax("Not Found", itemList);
        return new ResponseAjax("Found", itemList);
    }

    @PostMapping("/user/item/actions/{id}/cancel")
    @ResponseBody
    public ResponseAjax cancelRequest(@PathVariable Long id){
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUser employee = myUserDetails.getUser();
        ActionTransaction transaction = employeeService.cancelRequest(id, employee);
        return new ResponseAjax("Cancelled", "Request has been cancelled!");
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
        List<ActionItem> itemList = employeeService.getItemAssetsSent(employee);
        if (itemList.isEmpty()) return new ResponseAjax("Not Found", itemList);
        return new ResponseAjax("Found", itemList);
    }

    @PutMapping("/user/item/receive/ok")
    @ResponseBody
    public ResponseAjax receiveItemAsset(@RequestParam(value = "actionTransId")Long actionTransId, @RequestParam(value = "itemId")Long itemId){
        ActionItem actionItem = employeeService.receiveItem(actionTransId, itemId);
        return new ResponseAjax("Success", "Item has been received! Check 'My Item'");
    }

    /* ========== EMPLOYEE RETURN ITEM ===========*/

    @GetMapping("/user/item/return")
    public String returnItem(Model model){
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUser employee = myUserDetails.getUser();
        model.addAttribute("myItemSerial", employeeService.getMyItemSerial(employee));
        return "user/item-return";
    }

    @PostMapping("/user/item/return")
    @ResponseBody
    public ResponseAjax returnItemPost(@RequestParam("itemreturns[]") List<Long> returnValues,
                                        @RequestParam("returncomment") String comment){
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUser employee = myUserDetails.getUser();
        List<ActionItem> actionItemList = employeeService.requestItemAssets(employee, returnValues, comment, Boolean.FALSE); // Boolean.FALSE = sedang mengembalikan barang
        return new ResponseAjax("Done", "Items are asked to be returned!");

    }
}
