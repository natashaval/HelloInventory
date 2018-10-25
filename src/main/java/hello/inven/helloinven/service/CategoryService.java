package hello.inven.helloinven.service;

import hello.inven.helloinven.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Category createCategory(Category category);
    Category editCategory(Category category);
    void deleteCategory(Integer id);

    List<Category> getAllCategories();
    Optional<Category> getOneCategory(Integer id);

}
