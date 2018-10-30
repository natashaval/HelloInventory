package hello.inven.helloinven.restcontroller;

import hello.inven.helloinven.model.Category;
import hello.inven.helloinven.model.ResponseAjax;
import hello.inven.helloinven.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/category")
public class CategoryRestController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping(value = "/all")
    public ResponseAjax getResource(){
        ResponseAjax responseAjax = new ResponseAjax("Done", categoryService.getAllCategories());
        return responseAjax;
    }

    @GetMapping(value = "/{id}/edit")
    public ResponseAjax getResourceById(@PathVariable Integer id){
        Optional<Category> categoryOptional = categoryService.getOneCategory(id);
        ResponseAjax responseAjax;
        if (categoryOptional != null) {
            responseAjax = new ResponseAjax("Done", categoryOptional);
            return responseAjax;
        }
        return null;
    }
}
