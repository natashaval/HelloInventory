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

    @GetMapping("/clerk/category")
    public String category() { return "clerk/category";}

    @GetMapping("/clerk/category2/all")
    public @ResponseBody ResponseAjax category2All(){
        return new ResponseAjax("Done", categoryService.getAllCategories());
    }


    @PostMapping("/clerk/category2/add")
    public @ResponseBody ResponseAjax category2Add(@RequestBody Category category){
        return categoryService.createCategory(category);
    }

    @GetMapping(value = "/clerk/category2/{id}/delete")
    public @ResponseBody ResponseAjax category2Delete(@PathVariable Integer id){
        return categoryService.deleteCategory(id);
    }

    @GetMapping(value="/clerk/category2/{id}")
    public @ResponseBody ResponseAjax category2Details(@PathVariable Integer id){
        return new ResponseAjax("Done", categoryService.getOneCategory(id));
    }

    @PostMapping(value = "/clerk/category2/{id}/edit")
    public @ResponseBody ResponseAjax category2Edit(@RequestBody Category category, @PathVariable Integer id){
        return categoryService.editCategory(category, id);
    }



}
