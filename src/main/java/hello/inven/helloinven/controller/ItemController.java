package hello.inven.helloinven.controller;

import hello.inven.helloinven.model.Category;
import hello.inven.helloinven.model.Item;
import hello.inven.helloinven.response.ResponseAjax;
import hello.inven.helloinven.service.CategoryService;
import hello.inven.helloinven.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
public class ItemController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    ItemService itemService;

    @GetMapping(value = "/clerk/item")
    public String item(){return "clerk/item"; }

    @GetMapping(value = "/clerk/item/list")
    public String itemList(){return "clerk/item-list";}

//    @GetMapping(value = "/clerk/item/{id}/details")
//    public String itemDetails() {return "clerk/item-view"; }

    @GetMapping(value = "/clerk/item/add")
    public String itemAdd(Model model){
        List<Category> categoryList = categoryService.getAllCategories();
        model.addAttribute("categoryList", categoryList);
        return "clerk/item-add";
    }

    @PostMapping(value = "/clerk/item/add")
    public @ResponseBody ResponseAjax itemAddPost(@ModelAttribute("newItem") Item item) throws IOException {

//        return itemService.createItem(item, file);
        return itemService.createItem(item);
    }

    @GetMapping(value = "/clerk/item/show")
    @ResponseBody
    public ResponseAjax itemShow(){
        List<Item> itemList = itemService.getAllItems();
        if (itemList.isEmpty()) return new ResponseAjax("Not Found", itemList);
        return new ResponseAjax("Found", itemList);
    }

    @DeleteMapping(value = "/clerk/item/{id}")
    @ResponseBody
    public ResponseAjax itemDelete(@PathVariable Long id){
        return itemService.deleteItem(id);
    }

//    @GetMapping(value = "/clerk/item/{id}")
//    public @ResponseBody ResponseAjax itemDetails(@PathVariable Long id){
//        return itemService.detailItem(id);
//    }

    @GetMapping(value = "/clerk/item/{id}")
    public String itemDetails(@PathVariable Long id, Model model){
        Item item = itemService.detailItem(id);
        model.addAttribute("item", item);
        return "/clerk/item-view";
    }

    @GetMapping(value = "/clerk/item/{id}/print")
    public String itemPrint(@PathVariable Long id, Model model){
        Item item = itemService.detailItem(id);
        model.addAttribute("item", item);
        return "/clerk/item-print";
    }
}
