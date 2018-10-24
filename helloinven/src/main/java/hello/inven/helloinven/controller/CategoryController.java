package hello.inven.helloinven.controller;

import hello.inven.helloinven.model.Category;
import hello.inven.helloinven.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping(value = "/clerk/category")
    public String categoryList(Model model){
        model.addAttribute("newCategory", new Category());
        List<Category> categoryList = categoryService.findAll();
        model.addAttribute(categoryList);
        return "clerk/category";
    }

    @PostMapping(value = "/clerk/category")
    public String categoryAdd(@Valid @ModelAttribute("newCategory") Category newCategory, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            System.out.println("BINDING CATEGORY ERROR");
            return "clerk/category";
        } else {
            categoryService.save(newCategory);
            return "redirect:/clerk/category";
        }
    }
}
