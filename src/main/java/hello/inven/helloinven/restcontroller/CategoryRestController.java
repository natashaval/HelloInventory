package hello.inven.helloinven.restcontroller;

import hello.inven.helloinven.model.Category;
import hello.inven.helloinven.model.ResponseAjax;
import hello.inven.helloinven.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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
}
