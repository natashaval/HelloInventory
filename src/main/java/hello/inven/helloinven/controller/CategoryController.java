package hello.inven.helloinven.controller;

import hello.inven.helloinven.model.Category;
import hello.inven.helloinven.model.ResponseAjax;
import hello.inven.helloinven.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class CategoryController {

    @Autowired
    CategoryService categoryService;

//    https://javabeginnerstutorial.com/spring-boot/making-spring-boot-thymeleaf-crud-application/
//    https://www.baeldung.com/spring-boot-start
//    https://progressive-code.com/post/10/Simple-Spring-Boot-CRUD-application-with-Thymeleaf,-JPA-and-Bootstrap

    @GetMapping(value = "/clerk/category")
    public String categoryList(Model model){
        model.addAttribute("newCategory", new Category());
        List<Category> categoryList = categoryService.getAllCategories();
        model.addAttribute(categoryList);
        return "clerk/category";
    }

    @PostMapping(value = "/clerk/category")
    public String categoryAdd(@Valid @ModelAttribute("newCategory") Category newCategory, BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            System.out.println("BINDING CATEGORY ERROR");
            return "redirect:/clerk/category?failedadd";
        } else {
            categoryService.createCategory(newCategory);
//            model.addAttribute("message", "Category has been created");
            return "redirect:/clerk/category?successadd";
        }
    }

    @GetMapping(value = "/clerk/category/{id}/delete")
    public String categoryDelete(@PathVariable Integer id, Model model){
        categoryService.deleteCategory(id);
        // tidak bisa langsung model addAttribute karena REDIRECT sehingga ke flush message nya
        // jadi harus menggunakan addFlashAttribute
        model.addAttribute("message", "Category has been deleted");
        return "redirect:/clerk/category";
    }

    @GetMapping(value = "/clerk/category/{id}/edit")
    public String categoryUpdateShow(@PathVariable Integer id, Model model){
        Optional<Category> category = categoryService.getOneCategory(id);
        model.addAttribute("editCategory", category);
        return "clerk/categoryedit";
    }

    @PostMapping(value = "/clerk/category/{id}/edit")
    public String categoryUpdate(@Valid @ModelAttribute("editCategory") Category editCategory, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            System.out.println("BINDING CATEGORY ERROR");
            return "clerk/category";
        } else {
            categoryService.createCategory(editCategory);
            model.addAttribute("message", "Category has been edited");
            return "redirect:/clerk/category";
        }
    }

//    clerk category2

    @GetMapping("/clerk/category2")
    public String category2(){ return "clerk/category2";}

    @GetMapping("/clerk/category2/all")
    public String category2all(Model model){
        List<Category> categoryList = categoryService.getAllCategories();
        model.addAttribute("categoryList", categoryList);
        return "clerk/category2all";
    }

    @PostMapping("/clerk/category2/add")
    public @ResponseBody ResponseAjax category2Add(@RequestBody Category category){
        return categoryService.createCategory(category);
    }

    @GetMapping(value = "/clerk/category2/{id}/delete")
    public @ResponseBody ResponseAjax category2Delete(@PathVariable Integer id){
        return categoryService.deleteCategory(id);
    }



}
