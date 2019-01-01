package hello.inven.helloinven.controller;

import hello.inven.helloinven.dto.ItemSerialOnly;
import hello.inven.helloinven.model.ItemSerial;
import hello.inven.helloinven.model.MyUser;
import hello.inven.helloinven.response.ResponseAjax;
import hello.inven.helloinven.service.ItemSerialService;
import hello.inven.helloinven.service.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ItemSerialController {

    @Autowired
    ItemSerialService itemSerialService;

    @GetMapping(value = "/clerk/item/{id}/serialJSON")
    public @ResponseBody ResponseAjax getItemSerial(@PathVariable Long id){
        List<ItemSerialOnly> serialOnly = itemSerialService.getItemSerialByItemId(id);
        return new ResponseAjax("Done", serialOnly);
    }

    @GetMapping(value = "/clerk/item/{id}/serial")
    public String serialAdd(@PathVariable Long id, Model model){
        model.addAttribute("itemId", id);
        return "clerk/item-serial";
    }

    @PostMapping(value = "/clerk/item/{id}/serial")
    @ResponseBody
    public ResponseAjax serialAddPost(@PathVariable Long id, @RequestParam(value = "serials[]") List<Long> itemSerialValues){
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUser clerk = myUserDetails.getUser();
//
//        System.out.println("Item Id: " + id);
//        for (Long itemSerial: itemSerialValues) {
//            System.out.println(itemSerial);
//        }

        itemSerialService.createItemSerial(clerk, id, itemSerialValues);
        return new ResponseAjax("Done", "Item Serials has been saved!");

    }

    @DeleteMapping(value = "/clerk/item/serial/{serialId}")
    @ResponseBody
    public ResponseAjax serialDelete(@PathVariable Long serialId){
        itemSerialService.deleteItemSerial(serialId);
        return new ResponseAjax("Deleted", "Item Serial has been deleted!");
    }


}
