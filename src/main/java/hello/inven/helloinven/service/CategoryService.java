package hello.inven.helloinven.service;

import hello.inven.helloinven.model.Category;
import hello.inven.helloinven.response.ResponseAjax;

import java.util.List;

public interface CategoryService {
    Category createCategory(Category category);
    Category editCategory(Category category, Integer id);
    Category deleteCategory(Integer id);

    List<Category> getAllCategories();
    Category getOneCategory(Integer id);

}
