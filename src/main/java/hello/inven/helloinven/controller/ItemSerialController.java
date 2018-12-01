package hello.inven.helloinven.controller;

import hello.inven.helloinven.model.MyUser;
import hello.inven.helloinven.model.ResponseAjax;
import hello.inven.helloinven.service.ItemSerialService;
import hello.inven.helloinven.service.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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
        return itemSerialService.getItemSerialByItemId(id);
    }

    @GetMapping(value = "/clerk/item/{id}/serial/enabled")
    public @ResponseBody ResponseAjax changeToItemSerial(@PathVariable Long id){
        return itemSerialService.changeToItemSerial(id);
    }

    @GetMapping(value = "/clerk/item/{id}/serial")
    public String serialAdd(@PathVariable Long id, Model model){
        model.addAttribute("itemId", id);
        return "/clerk/item-asset-add";
    }

    @PostMapping(value = "/clerk/item/{id}/serial")
    public @ResponseBody ResponseAjax serialAddPost(@PathVariable Long id, @RequestParam(value = "serials[]") List<Long> itemSerialValues){
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUser clerk = myUserDetails.getUser();

        System.out.println("Item Id: " + id);
        for (Long itemSerial: itemSerialValues) {
            System.out.println(itemSerial);
        }

        return itemSerialService.createItemSerial(clerk, id, itemSerialValues);
//        return null;

    }


}
