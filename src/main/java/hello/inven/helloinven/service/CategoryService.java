package hello.inven.helloinven.service;

import hello.inven.helloinven.model.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(Category category);
    Category editCategory(Category category);
    void deleteCategory(Integer id);

    List<Category> getAllCategories();

}
