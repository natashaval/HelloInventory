package hello.inven.helloinven.controller;

import hello.inven.helloinven.model.Category;
import hello.inven.helloinven.response.ResponseAjax;
import hello.inven.helloinven.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@PreAuthorize("hasAuthority('CLERK')")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/clerk/category")
    public String category() { return "clerk/category";}

    @GetMapping("/clerk/category2/all")
    public @ResponseBody ResponseAjax category2All(){
        return new ResponseAjax("Done", categoryService.getAllCategories());
    }

    @PostMapping("/clerk/category2/add")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseAjax category2Add(@Valid @RequestBody Category category){
        return new ResponseAjax("Saved", categoryService.createCategory(category));
    }

    @DeleteMapping(value = "/clerk/category2/{id}")
    @ResponseBody
    public ResponseAjax category2Delete(@PathVariable Integer id){
        categoryService.deleteCategory(id);
        return new ResponseAjax("Deleted", "Category has been successfully deleted");
    }

    @GetMapping(value="/clerk/category2/{id}")
    @ResponseBody
    public ResponseAjax category2Details(@PathVariable Integer id){
        return new ResponseAjax("Done", categoryService.getOneCategory(id));
    }

    @PutMapping(value = "/clerk/category2/{id}")
    public @ResponseBody ResponseAjax category2Edit(@RequestBody Category category, @PathVariable Integer id){
        Category category1 = categoryService.editCategory(category, id);
        return new ResponseAjax("Updated",category1);
    }

}
