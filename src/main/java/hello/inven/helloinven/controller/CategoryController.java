package hello.inven.helloinven.controller;

import hello.inven.helloinven.model.Category;
import hello.inven.helloinven.response.ResponseAjax;
import hello.inven.helloinven.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

//    @DeleteMapping(value = "/clerk/category2/{id}/delete")
    @DeleteMapping(value = "/clerk/category2/{id}")
    public @ResponseBody ResponseAjax category2Delete(@PathVariable Integer id){
        return categoryService.deleteCategory(id);
    }

    @GetMapping(value="/clerk/category2/{id}")
    @ResponseBody
    public ResponseAjax category2Details(@PathVariable Integer id){
        return new ResponseAjax("Done", categoryService.getOneCategory(id));
    }

//    @PutMapping(value = "/clerk/category2/{id}/edit")
    @PutMapping(value = "/clerk/category2/{id}")
    public @ResponseBody ResponseAjax category2Edit(@RequestBody Category category, @PathVariable Integer id){
        Category category1 = categoryService.editCategory(category, id);
        return new ResponseAjax("Updated",category);
    }

    @GetMapping(value = "/clerk/category/{id}")
    @ResponseBody
    public ResponseEntity<ResponseAjax> categoryDetails(@PathVariable Integer id){
        Category category = categoryService.getOneCategory(id);
        if (category == null) {
            ResponseAjax responseAjax = new ResponseAjax("Not Found", category);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseAjax);
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseAjax("Found", category));
    }

}
